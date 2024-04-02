package com.zhuang.util.math;

import cn.hutool.core.util.ArrayUtil;
import lombok.Data;
import org.apache.commons.math3.linear.DefaultRealMatrixChangingVisitor;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.MathArrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SGFilterUtils {

    public static final int MODE_INTERP = 1;
    public static final int MODE_CONSTANT = 2;
    public static final int MODE_MIRROR = 3;
    public static final int MODE_NEAREST = 4;
    public static final int MODE_WARP = 5;

    private static int Deriv = 0;
    private static double Delta = 1d;
    private static double Cval = 0d;

    public static double[] filter(double[] sourceData, int win, int polyorder) {

        PreprocessDataResult preprocessDataResult = preprocessData(sourceData);
        double[] data1 = preprocessDataResult.getData();
        List<Integer> nanIndex = preprocessDataResult.getNanIndex();
        int begin = preprocessDataResult.begin;
        int end = preprocessDataResult.end;

        //var waveData = data1.Where(item = > !double.IsNaN(item)).ToArray();
        double[] waveData = Arrays.stream(data1).filter(c -> !Double.isNaN(c)).toArray();

        if (waveData.length >= win) {
            //var data = new Matrix(1, end - begin + 1, waveData);
            RealMatrix data = MatrixUtils.createRowRealMatrix(waveData);
            RealMatrix res = sgWave(data, win, polyorder, MODE_INTERP);
            for (int i = 0; i < nanIndex.size(); i++) {
                if (nanIndex.get(i) < end && nanIndex.get(i) > begin)
                    res.setEntry(0, nanIndex.get(i) - begin, Double.NaN);
            }
            double[] result = new double[sourceData.length];
            for (int i = 0; i < begin; i++)
                result[i] = sourceData[i];
            for (int i = 0; i < end - begin + 1; i++)
                result[i + begin] = res.getRow(0)[i];
            for (int i = end + 1; i < sourceData.length; i++)
                result[i] = sourceData[i];

            return result;
        } else
            return MathArrays.copyOf(sourceData);
    }

    public static RealMatrix sgWave(RealMatrix sourceData, int win, int polyorder, int mode) {
        boolean Transpose = false;
        if (isColumnVector(sourceData)) {
            Transpose = true;
            sourceData = sourceData.transpose();
        } else {
            if (!isRowVector(sourceData))
                throw new RuntimeException("SGFilter 暂时只支持一位数组");
        }
        RealMatrix result = MatrixUtils.createRealMatrix(sourceData.getRowDimension(), sourceData.getColumnDimension());
        result.walkInRowOrder(new DefaultRealMatrixChangingVisitor() {
            @Override
            public double visit(int row, int column, double value) {
                return Double.NaN;
            }
        });
        if (win % 2 == 0)
            win += 1;
        double[] bodycofe = savgolCoeffs(win, polyorder, -1, true);
        int half = win / 2;

        switch (mode) {
            case MODE_INTERP: {
                RealMatrix headdata = MatrixUtils.createRealMatrix(1, win);
                RealMatrix bottomdata = MatrixUtils.createRealMatrix(1, win);
                RealMatrix xData = MatrixUtils.createRealMatrix(1, win);

                for (int i = 0; i < win; i++) {
                    //xData.getRow(0)[i] = i;
                    xData.setEntry(0, i, i);
                    headdata.setEntry(0, i, sourceData.getRow(0)[i]);
                    bottomdata.setEntry(0, i, sourceData.getRow(0)[sourceData.getColumnDimension() - win + i]);
                }

                double[] headcofe = polyFit(xData, headdata, polyorder);
                double[] bottomcofe = polyFit(xData, bottomdata, polyorder);
                for (int i = 0; i < half; i++) {
                    double insertData = 0d;
                    for (int j = 0; j < headcofe.length; j++) {
                        insertData += Math.pow(xData.getRow(0)[i], j) * headcofe[j];
                    }
                    result.setEntry(0, i, insertData);
                    insertData = 0d;
                    for (int j = 0; j < bottomcofe.length; j++) {
                        insertData += Math.pow(xData.getRow(0)[i + half + 1], j) * bottomcofe[j];
                    }
                    result.setEntry(0, result.getColumnDimension() - half + i, insertData);
                }
            }
            break;
            case MODE_CONSTANT:
                for (int i = 0; i < half; i++) {
                    result.setEntry(0, i, Cval);
                    result.setEntry(0, result.getColumnDimension() - half + i, Cval);
                }
                break;
            case MODE_MIRROR:
                for (int i = 0; i < half; i++) {
                    result.setEntry(0, i, result.getEntry(0, win - 1 - i));

                    result.setEntry(0, result.getColumnDimension() - half + i, result.getEntry(0, result.getColumnDimension() - win + i));
                }
                break;
            case MODE_NEAREST:
                for (int i = 0; i < half; i++) {
                    result.setEntry(0, i, result.getEntry(0, half));
                    result.setEntry(0, result.getColumnDimension() - half + i, result.getEntry(0, result.getColumnDimension() - half - 1));
                }
                break;
            case MODE_WARP:
                for (int i = 0; i < half; i++) {
                    result.setEntry(0, i, result.getEntry(0, result.getColumnDimension() - half + i));

                    result.setEntry(0, result.getColumnDimension() - half + i, result.getEntry(0, i));
                }
                break;

        }

        for (int i = half; i < result.getColumnDimension() - half; i++) {
            double insertData = 0d;
            for (int j = i; j < i + win; j++) {
                insertData += sourceData.getEntry(0, j - half) * bodycofe[j - i];
            }
            result.setEntry(0, i, insertData);
        }
        if (Transpose) {
            sourceData = sourceData.transpose();
            result = result.transpose();
        }
        return result;
    }

    @Data
    public static class PreprocessDataResult {
        private double[] data;
        private List<Integer> nanIndex;
        private int begin;
        private int end;
    }

    public static double[] polyFit(RealMatrix xData, RealMatrix yData, int degree) {
        boolean tx = false;
        boolean ty = false;
        if (isRowVector(xData)) {
            tx = true;
            xData = xData.transpose();
        }
        if (isRowVector(yData)) {
            ty = true;
            yData = yData.transpose();
        }
        if ((xData.getRowDimension() != yData.getRowDimension()) || yData.getColumnDimension() != 1 || xData.getColumnDimension() != 1)
            throw new RuntimeException("Polyfit fail:params error");
        int n = xData.getRowDimension();
        RealMatrix xMatrix = MatrixUtils.createRealMatrix(n, degree + 1);
        for (int i = 0; i <= degree; i++)
            for (int j = 0; j < n; j++) {
                xMatrix.setEntry(j, i, Math.pow(xData.getEntry(j, 0), i));
            }

        RealMatrix xT = xMatrix.transpose();
        //(xT * xMatrix).InverseMatrix(out var xT11);
        RealMatrix xT11 = inverseMatrix(xT.multiply(xMatrix));
        RealMatrix b = xT11.multiply(xT).multiply(yData);
        if (tx)
            xData = xData.transpose();
        if (ty)
            yData = yData.transpose();
        return b.getColumn(0);

    }

    public static boolean isRowVector(RealMatrix matrix) {
        return matrix.getColumnDimension() > 0 && matrix.getRowDimension() == 1;
    }

    public static boolean isColumnVector(RealMatrix matrix) {
        return matrix.getColumnDimension() == 1 && matrix.getRowDimension() > 0;
    }


    public static PreprocessDataResult preprocessData(double[] data) {
        PreprocessDataResult resultModel = new PreprocessDataResult();
        List<Integer> nanIndex = new ArrayList<>();
        List<int[]> idGroup = new ArrayList<>();
        int n = data.length;
        int begin = 0;
        int end = n - 1;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            if (Double.isNaN(data[i])) {
                int[] a = new int[2];
                a[0] = i;
                for (int j = i + 1; j < n; j++) {
                    if (!Double.isNaN(data[j])) {
                        a[1] = j - 1;
                        i = j;
                        idGroup.add(a);
                        result[j] = data[j];
                        break;
                    } else {
                        if (j == n - 1) {
                            a[1] = n - 1;
                            idGroup.add(a);
                            i = n - 1;
                        }
                    }
                }


            } else
                result[i] = data[i];
        }
        for (int i = 0; i < idGroup.size(); i++) {
            int[] a = idGroup.get(i);
            if (a[0] == a[1])
                nanIndex.add(a[0]);
            else
                for (int j = a[0]; j <= a[1]; j++)
                    nanIndex.add(j);
            double preData = Double.NaN;
            double nextData = Double.NaN;

            if (a[0] > 0)
                preData = data[a[0] - 1];
            if (a[1] < n - 1)
                nextData = data[a[1] + 1];
            if (Double.isNaN(preData)) {
                for (int j = a[0]; j <= a[1]; j++) {
                    result[j] = Double.NaN;
                }
                begin = a[1] + 1;
            } else {
                if (Double.isNaN(nextData)) {
                    for (int j = a[0]; j <= a[1]; j++) {
                        result[j] = Double.NaN;
                    }
                    end = a[0] - 1;
                } else {
                    double scale = (nextData - preData) / (a[1] - a[0] + 2);
                    for (int j = a[0]; j <= a[1]; j++) {
                        result[j] = preData + (j - a[0] + 1) * scale;
                    }
                }
            }

        }
        resultModel.setData(result);
        resultModel.setBegin(begin);
        resultModel.setEnd(end);
        resultModel.setNanIndex(nanIndex);
        return resultModel;
    }

    public static double[] savgolCoeffs(int win, int polyorder, int pos, boolean useConv) {
        if (pos < 0)
            pos = win / 2;
        if (pos > win)
            throw new RuntimeException("SG滤波窗口必须大于初始位置");
        if (Deriv > polyorder) {
            //return new Matrix(1, win, MatrixInitType.Zero).GetData();
            return MatrixUtils.createRealMatrix(1, win).getData()[0];
        }

        //Matrix x = new Matrix(1, win);
        RealMatrix x = MatrixUtils.createRealMatrix(1, win);


        for (int i = 0; i < win; i++) {
            if (useConv) {
                //x[0, win - 1 - i] =i - pos;
                x.setEntry(0, win - 1 - i, i - pos);
            } else {
                //x[0, i] =i - pos;
                x.setEntry(0, i, i - pos);
            }
        }
        //  A = new Matrix(win, polyorder + 1);
        RealMatrix A = MatrixUtils.createRealMatrix(win, polyorder + 1);
        for (int i = 0; i <= polyorder; i++)
            for (int j = 0; j < win; j++)
                A.setEntry(j, i, Math.pow(x.getEntry(0, j), i));
        //var y = new Matrix(polyorder + 1, 1, MatrixInitType.Zero);
        RealMatrix y = MatrixUtils.createRealMatrix(polyorder + 1, 1);
        double factorial = 1d;

        if (Deriv > 171)
            factorial = Double.POSITIVE_INFINITY;
        else if (Deriv == 0)
            factorial = 1d;
        else
            for (int i = 1; i < Deriv; i++)
                factorial *= i;
        y.setEntry(Deriv, 0, factorial / Math.pow(Delta, Deriv));

        //var AT = A.Transpose();
        RealMatrix AT = A.transpose();
        //(AT * A).InverseMatrix(out var AT11);
        //RealMatrix AT11 = MatrixUtils.inverse(AT.multiply(A));
        //RealMatrix AT11= new CholeskyDecomposition(AT.multiply(A)).getSolver().getInverse();
        //RealMatrix AT11= new LUDecomposition().getSolver().getInverse();
        RealMatrix AT11 = inverseMatrix(AT.multiply(A));

        //AT11.GetRowVector(Deriv, out Matrix vector);
        RealMatrix vector = MatrixUtils.createRealMatrix(new double[][]{AT11.getRow(Deriv)});
        //var b = vector * AT * y[Deriv];
        RealMatrix b = vector.multiply(AT).scalarMultiply(y.getEntry(Deriv, 0));
        return b.getRow(0);
    }

    public static RealMatrix inverseMatrix(RealMatrix matrix) {

        if (matrix.getRowDimension() != matrix.getColumnDimension()) {
            throw new RuntimeException("不是方阵没有逆矩阵！");
        }
        int rows = matrix.getRowDimension();
        int columns = matrix.getColumnDimension();

        RealMatrix result = makeUnitMatrix(rows);

        //用于进行 行列变换的临时矩阵
        RealMatrix tempData = matrix.copy();
        // 对角线上数字为0时，用于交换的行号
        int line = 0;
        double tmp;
        //进行行初等变换，将临时矩阵变成对角线上全为1的上三角矩阵
        for (int i = 0; i < rows; i++) {
            if (tempData.getEntry(i, i) == 0) {
                if (++line >= rows) {
                    throw new RuntimeException("此矩阵没有逆矩阵！");
                }
                for (int j = 0; j < columns; j++) {
                    tmp = tempData.getEntry(i, j);
                    tempData.setEntry(i, j, tempData.getEntry(line, j));
                    tempData.setEntry(line, j, tmp);
                    tmp = result.getEntry(i, j);
                    result.setEntry(i, j, result.getEntry(line, j));
                    result.setEntry(line, j, tmp);
                }
                i--;
                continue;
            }

            for (int row = i; row < rows; row++) {
                if (tempData.getEntry(row, i) != 0) {
                    if (tempData.getEntry(row, i) != 1) {
                        tmp = tempData.getEntry(row, i);
                        for (int j = 0; j < columns; j++) {
                            tempData.setEntry(row, j, tempData.getEntry(row, j) / tmp);
                            result.setEntry(row, j, result.getEntry(row, j) / tmp);
                        }
                    }
                    if (row != i) {
                        for (int j = 0; j < columns; j++) {
                            tempData.setEntry(row, j, tempData.getEntry(row, j) - tempData.getEntry(i, j));
                            result.setEntry(row, j, result.getEntry(row, j) - result.getEntry(i, j));
                        }
                    }
                }
            }
        }
        //进行hang初等变换，将临时矩阵变成单位矩阵
        for (int row = rows - 2; row >= 0; row--) {
            for (int rownow = row; rownow >= 0; rownow--) {
                if (tempData.getEntry(rownow, row + 1) != 0) {
                    tmp = tempData.getEntry(rownow, row + 1);
                    for (int col = 0; col < columns; col++) {
                        tempData.setEntry(rownow, col, tempData.getEntry(rownow, col) - tempData.getEntry(row + 1, col) * tmp);
                        result.setEntry(rownow, col, result.getEntry(rownow, col) - result.getEntry(row + 1, col) * tmp);
                    }
                }
            }
        }
        return result;
    }

    public static RealMatrix makeUnitMatrix(int nSize) {
        RealMatrix result = MatrixUtils.createRealMatrix(nSize, nSize);
        for (int i = 0; i < nSize; ++i)
            result.setEntry(i, i, 1);
        return result;
    }

}
