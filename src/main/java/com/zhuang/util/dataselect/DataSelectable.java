package com.zhuang.util.dataselect;

import java.util.List;

public interface DataSelectable<T extends DataSelectArgs, R extends DataSelectResult> {

    List<R> selectList(T args);

}
