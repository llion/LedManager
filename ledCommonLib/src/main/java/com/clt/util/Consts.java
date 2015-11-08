package com.clt.util;

public class Consts
{
    

    /**
     * 上传操作
     * @author Administrator
     *
     */
    public static final String TYPE = "type";

    public static final String DATA = "data";

    public static final String DELETE = "delete";

    public static final String PROCESS_SPEED = "process_speed";

    public static final String PROCESS_PROGRESS = "process_progress";

    public static final String PROCESS = "process";

    public static final String URL = "url";

    public static final String ERROR_CODE = "error_code";

    public static final String ERROR_INFO = "error_info";

    public static final String IS_PAUSED = "is_paused";

    public static final String UPLOAD_TYPE = "upload";

    public static final class UploadType
    {
        public static final int PROCESS = 0;

        public static final int COMPLETE = 1;

        public static final int START = 2;

        public static final int PAUSE = 3;

        public static final int DELETE = 4;

        public static final int CONTINUE = 5;

        public static final int ADD = 6;

        public static final int STOP = 7;

        public static final int PERPARE = 8;

        public static final int ERROR = 9;

        public static final int HASUPLOADED = 9;

    }
}
