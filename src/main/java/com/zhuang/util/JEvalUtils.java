package com.zhuang.util;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import java.util.Map;

public class JEvalUtils {

    public static String eval(String exp, Map variables) {
        Evaluator evaluator = new Evaluator();
        evaluator.setVariables(variables);
        try {
            return evaluator.evaluate(exp);
        } catch (EvaluationException e) {
            throw new RuntimeException(e);
        }
    }
}
