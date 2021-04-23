package com.yunitski.passkeeper;

import android.provider.BaseColumns;

public class InputData {
    public static final String DB_NAME = "list";
    public static final int DB_VERSION = 1;
    static class TaskEntry implements BaseColumns {
        public static final String TABLE = "table_names";
        public static final String NAMES = "names";
        public static final String NAMESTR = "namestr";
        public static final String LINKS = "links";
        public static final String PASSES = "passes";
    }
}
