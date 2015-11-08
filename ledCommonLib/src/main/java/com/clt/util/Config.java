package com.clt.util;


/**
 *  配置文件
 *
 */
public class Config
{
    // 使用的端口号
    public static final int TCP_PORT = 9042;

    public static final int UDP_RESOURCE_PORT = 9040;

    public static final int UDP_TARGET_PORT = 9041;

    public static final int UPLOAD_PORT = 7879;

    // Socket的IO读写缓冲区的大小
    public static int SEND_BUF_SIZE = 1024 * 2;// 2K

    public static int RECEVICE_BUF_SIZE = 1024 * 2;// 2K

//    // public static final String SAVE_PATH =
//    // "/mnt/sdcard/Android/data/com.color.home/files/Download/";
//    public static final String SAVE_PATH = "/mnt/usb_storage/USB_DISK0/udisk0/";
//
//    public static final String USB_PATH_0 = "/mnt/usb_storage/USB_DISK0/udisk0/";
//
//    public static final String USB_PATH_1 = "/mnt/usb_storage/USB_DISK1/udisk1/";
//
//    public static final String SDCARD_PATH = Environment
//            .getExternalStorageDirectory().getAbsolutePath() + "/";
//
//    public static final String SDCARD_DOWNLOAD_PATH = "/mnt/sdcard/Android/data/com.color.home/files/Download/";
//    public static final String SDCARD_USB_PATH = "/mnt/sdcard/Android/data/com.color.home/files/Usb/";
//
//    // public static final String SAVE_PATH = Environment
//    // .getExternalStorageDirectory().getAbsolutePath() + "/";
//
//    public static final String PROGRAM_EXTENSION = ".vsn";
    
    
}
