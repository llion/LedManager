package com.clt.netmessage;

public class NetMessageType
{

    public static final int DetectTerminal = 1;

    public static final int DetectTerminalAnswer = 2;

    public static final int DetectSender = 11;

    public static final int DetectSenderAnswer = 12;

    public static final int SetSenderControlArea = 21;

    public static final int SetSenderControlAreaAnswer = 22;

    public static final int SetRcvLayout = 31;

    public static final int SetRcvLayoutAnswer = 32;

    public static final int SetSenderBright = 41;// 设置亮度

    // public static final int SetSenderBrightAnswer = 42;
    public static final int SetColorTemperture = 43;// 设置色温

    public static final int SetColorTempertureRGB = 44;// 设置色温RGB

    public static final int SaveSenderBright = 51;

    public static final int SaveSenderBrightAnswer = 52;

    public static final int SaveSenderColorTemp = 53;

    public static final int SaveSenderColorTempAnswer = 54;

    public static final int SaveBrightAndColorTemp = 55;

    public static final int SaveBrightAndColorTempAnswer = 56;

    public static final int SetPlayProgram = 61;

    public static final int SetPlayProgramAnswer = 62;

    public static final int FindTerminate = 71;

    public static final int FindTerminateAnswer = 72;

    public static final int SetSenderShowOnOff = 81;

    // public static final int SetSenderShowOnOffAnswer = 82;

    public static final int HeartBreak = 91;

    public static final int HeartBreakAnswer = 92;

    public static final int netNeedClosed = 101;

    public static final int Password = 201;

    public static final int TryConnect = 301;

    public static final int TryConnectAnswer = 302;

    public static final int KickOutOf = 401;

    public static final int SetSenderBasicParameters = 501;

    public static final int SetSenderBasicParametersAnswer = 502;

    public static final int SetTestMode = 601;

    public static final int SetTestModeAnswer = 602;

    public static final int SetEDID = 702;

    public static final int SetEDIDAnswer = 703;

    public static final int setDayPeriodBright = 801;// 分时段连亮度调节

    public static final int setDayPeriodBrightAnswer = 802;// 分时段连亮度调节

    public static final int upload = 901;// 分时段连亮度调节

    public static final int getProgramsNames = 1001;// 获取节目名

    public static final int getProgramsNamesAnswer = 1002;// 获取节目名
    
    public static final int deleteProgram=1003;//删除某个节目
    
    public static final int deleteProgramAnswer=1004;//删除某个节目回应
    
    public static final int getReceiveCardInfo=1010;//获取接收卡参数信息
    
    public static final int getReceiveCardInfoAnswer=1011;//获取接收卡参数信息
    
    public static final int setReceiveCardSettingInfoSend=1012;//发送接收卡参数
    
    public static final int setReceiveCardSettingInfoSendAnswer=1013;//发送接收卡参数
    
    public static final int setReceiveCardSettingInfoSaveToReceiver=1014;//固化接收卡参数
    
    public static final int setReceiveCardSettingInfoSaveToReceiverAnswer=1015;//固化接收卡参数
    
    public static final int setReceiveCardSetting=2001;//设置接收卡参数
    
    public static final int setReceiveCardSettingAnswer=2002;//设置接收卡参数
    
    public static final int setConnectionToSenderCard=2003;//发送连接关系
    
    public static final int setConnectionToSenderCardAnswer=2004;//发送连接关系结果
    
    public static final int setConnectionToReceiverCard=2005;//固化连接关系
    
    public static final int setConnectionToReceiverCardAnswer=2006;//固化连接关系结果
    
    public static final int ConnectSuccess=2011;
    
    public static final int SetAutoBrightness=2021;//设置亮度曲线
    
    public static final int SetPortAreaByXml=3001;//设置亮度曲线
    
    public static final int getRTC=3011;//
    
    public static final int SaveUidEncrpt=3021;//
    
    public static final int ModifyTerminateName=3031;//改变终端名
    
    public static final int ModifyTerminateNameAnswer=3032;
    
    public static final int GetSomeInfo=3041;//获得一些信息
    
    public static final int GetSomeInfoAnswer=3042;//获得一些信息

    public static final int MSG_WHAT_PROGRAM_UPDATE =3043;//通知节目更新

}
