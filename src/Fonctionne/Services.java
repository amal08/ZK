package Fonctionne;

import java.io.File;
import java.io.IOException;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Services {

    StringBuilder pid = new StringBuilder("zkemkeeper.ZKEM.1");

    ActiveXComponent zk = new ActiveXComponent(pid.toString());

    Variant dwMachineNumber;

    @SuppressWarnings("deprecation")
    public Services() {
        Dispatch.call(zk, "Connect_Net", "172.16.0.23", 4370).toBoolean();
    }

    /**
     * 
     * @return
     */
    public Variant GetDeviceIP() {
        Variant ipAdresse = new Variant("", true);
        Dispatch.call(zk, "GetDeviceIP", dwMachineNumber, ipAdresse).getBoolean();
        return ipAdresse;
    }

    public Variant SetDeviceIP() {
        Variant ipAdresse = new Variant("172.16.0.23", true);
        Dispatch.call(zk, "SetDeviceIP", dwMachineNumber, ipAdresse).getBoolean();
        return ipAdresse;
    }

    public Variant GetDeviceMac() {
        Variant sMAC = new Variant("", true);
        Dispatch.call(zk, "GetDeviceMAC", dwMachineNumber, sMAC).getBoolean();
        return sMAC;
    }

    public void Users_List() {
        Variant dwEnrollNumber = new Variant("", true);
        Variant Name = new Variant("", true);
        Variant Password = new Variant("", true);
        Variant Privilege = new Variant(0, true);
        Variant Enabled = new Variant(Boolean.valueOf(String.valueOf(0)), true);

        System.out.println("#######Liste des utilisateurs##########");
        while (Dispatch.call(zk, "SSR_GetAllUserInfo", dwMachineNumber, dwEnrollNumber, Name, Password, Privilege, Enabled).getBoolean()) {
            System.out.println("Privilege " + Privilege + "--Nom " + Name + "--ID de l'utilisateur " + dwEnrollNumber + "--Enable: " + Enabled);
        }

    }

    public void Delete_User(Variant dwEnrollNumber) {
        Variant dwBackupNumber = new Variant(0, true);
        Dispatch.call(zk, "SSR_DeleteEnrollDataExt", dwMachineNumber, dwEnrollNumber, dwBackupNumber).getBoolean();
    }

    public void ReadAllGLogData() {
        Dispatch.call(zk, "ReadAllGLogData", dwMachineNumber).getBoolean();
    }

    public void Users_Log() {
        Variant dwEnrollNumber = new Variant("", true);
        Variant dwVerifyMode = new Variant(0, true);
        Variant dwInOutMode = new Variant(0, true);
        Variant dwYear = new Variant(0, true);
        Variant dwMonth = new Variant(0, true);
        Variant dwDay = new Variant(0, true);
        Variant dwHour = new Variant(0, true);
        Variant dwMinute = new Variant(0, true);
        Variant dwSecond = new Variant(0, true);
        Variant dwWorkcode = new Variant(0, true);
        System.out.println("#######Liste des Log##########");
        while (Dispatch.call(zk, "SSR_GetGeneralLogData", dwMachineNumber, dwEnrollNumber, dwVerifyMode, dwInOutMode, dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond,
                dwWorkcode).getBoolean()) {

            System.out.println("User Number:" + dwEnrollNumber.toInt() + " Mode de verification " + dwVerifyMode + "--Entree sortie " + dwInOutMode + "--Jour " + dwDay.toInt()
                    + "/" + dwMonth.toInt() + "/" + dwYear.toInt() + "--Heure " + dwHour.toInt() + ":" + dwMinute.toInt() + ":" + dwSecond.toInt());
            dwSecond = new Variant(0, true);
            dwMinute = new Variant(0, true);
            dwHour = new Variant(0, true);
            dwYear = new Variant(0, true);
            dwMonth = new Variant(0, true);
            dwDay = new Variant(0, true);
            dwEnrollNumber = new Variant("", true);
        }
    }

    /***
     * Begin Real time Event
     * 
     */
    public void RegEvent() {
        // Dispatch.call(zk, "EnableDevice", 1,false).toBoolean();
        Variant EventMask = new Variant(65535, true);

        System.out.println("#######Event##########");
        Dispatch.call(zk, "RegEvent", dwMachineNumber, EventMask).getBoolean();
    }

    // Enregistrer la résultat de RegEvent dans le buffer de pc
    public void ReadRTLog() {
        System.out.println("#######Read Event##########");
        Dispatch.call(zk, "ReadRTLog", dwMachineNumber).getBoolean();

    }

    // Recuperation des evennements à partir du buffer
    public void GetRTLog() {
        System.out.println("#######Get Event##########");
        Dispatch.call(zk, "GetRTLog", dwMachineNumber).getBoolean();
    }

    /***
     * End Real time Event
     * 
     */

    public void GetDeviceTime() {
        Variant dwYear = new Variant(0, true);
        Variant dwMonth = new Variant(0, true);
        Variant dwDay = new Variant(0, true);
        Variant dwHour = new Variant(0, true);
        Variant dwMinute = new Variant(0, true);
        Variant dwSecond = new Variant(0, true);

        System.out.println("#######Time##########");
        Dispatch.call(zk, "GetDeviceTime", dwMachineNumber, dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond).getBoolean();
        System.out.println(dwYear + " " + dwMonth + " " + dwDay);
    }

    public void PowerOffDevice() {
        Dispatch.call(zk, "PowerOffDevice", dwMachineNumber).getBoolean();
    }

    public void RestartDevice() {
        Dispatch.call(zk, "RestartDevice", dwMachineNumber).getBoolean();
    }

    public void SleepDevice() {
        Dispatch.call(zk, "SleepDevice", dwMachineNumber).getBoolean();
    }

    public void EnableClock() {
        Dispatch.call(zk, "EnableClock", 0).getBoolean();
    }

    public void SerialNumber() {
        Variant dwSerialNumber = new Variant("", true);
        System.out.println("#######Serial number##########");
        Dispatch.call(zk, "GetSerialNumber", dwMachineNumber, dwSerialNumber).getBoolean();
        System.out.println(dwSerialNumber);
    }

    public void Ubdate_Attendance() {
        Variant dwSerialNumber = new Variant("", true);
        System.out.println("#######Update Attendance##########");
        Dispatch.call(zk, "GetSerialNumber", dwMachineNumber, dwSerialNumber).getBoolean();
        System.out.println(dwSerialNumber);
    }

    @SuppressWarnings("deprecation")
    public void GetTZInfo() {
        Variant TZ = new Variant("", true);
        // Dispatch.call(zk, "Connect_Net", "192.168.0.201", 4370).toBoolean();
        while (Dispatch.call(zk, "GetTZInfo", 0, 1, TZ).toBoolean()) {
            System.out.println("hh" + TZ);
        }
    }

    public void SSR_DelUserTmpExt(Variant dwMachineNumber, Variant dwEnrollNumber, Variant dwFingerIndex) {
        System.out.println("start enroll1...");
        Dispatch.call(zk, "SSR_DelUserTmpExt", dwMachineNumber, dwEnrollNumber, dwFingerIndex).toBoolean();
    }

    /**
     * register real time event function
     */

    public void registerAllRealTimeEvents() {
        // Dispatch.call(zk, "EnableDevice", 1,false).toBoolean();
        Variant EventMask = new Variant(65535, true);
        if (Dispatch.call(zk, "RegEvent", dwMachineNumber, EventMask).getBoolean())
            System.out.println("REAL TIME EVENTS SUCCESSFULLY REGISTRED");
        else
            System.out.println("FAILED TO REGISTER REAL TIME EVENTS");
    }

    public void registerRealTimeEvent(long dwMachineNumber) {
        long OnEnrollFingerEventMask = 8;
        boolean isOnEnrollFinger = Dispatch.call(zk, "RegEvent", dwMachineNumber, OnEnrollFingerEventMask).toBoolean();
        System.out.println("OnEnrollFinger registred = " + isOnEnrollFinger);
        long EnrollNumber = 0, FingerIndex = 0, ActionResult = 0, TemplateLength = 0;
        if (isOnEnrollFinger) {
            Dispatch.call(zk, "OnEnrollFingerEventHandler", EnrollNumber, FingerIndex, ActionResult, TemplateLength);
            Dispatch.call(zk, "OnEnrollFingerEventHandler", EnrollNumber, FingerIndex, ActionResult, TemplateLength);
            ActiveXComponent app = new ActiveXComponent("OnEnrollFinger");
        }
        // Dispatch.call(zk, "OnEnrollFingerEx", EnrollNumber, FingerIndex, ActionResult, TemplateLength).toDispatch();
        // System.out.println("EnrollNumber =" + EnrollNumber);
        // System.out.println("FingerIndex =" + FingerIndex);
        // System.out.println("ActionResult =" + ActionResult);
        // System.out.println("TemplateLength =" + TemplateLength);
    }

    // When you are enrolling your finger,this event will be triggered.
    private void axCZKEM1_OnEnrollFinger(int iEnrollNumber, int iFingerIndex, int iActionResult, int iTemplateLength) {
        if (iActionResult == 0) {
            System.out.println("RTEvent OnEnrollFiger Has been Triggered....");
            System.out.println(".....UserID: " + iEnrollNumber + " Index: " + iFingerIndex + " tmpLen: " + iTemplateLength);
        } else {
            System.out.println("RTEvent OnEnrollFiger was Triggered by Error");
        }
    }

    /****
     * Begin Enroll user with finger
     */
    public boolean StartEnroll(Variant UserID, Variant FingerID, Variant Flag) {
        System.out.println("start enroll...");
        return Dispatch.call(zk, "StartEnrollEx", UserID, FingerID, Flag).toBoolean();
    }

    public void StartIdentify() {
        Dispatch.call(zk, "StartIdentify").toBoolean();
    }

    public void CancelOperation() {
        Dispatch.call(zk, "CancelOperation").toBoolean();
    }

    public void Set_User(Variant dwEnrollNumber, Variant Name, Variant Password, Variant Privilege, Variant Enabled) {
        System.out.println("#######create des utilisateurs##########");
        Dispatch.call(zk, "SetStrCardNumber", new Variant("6708521", true)).getBoolean();
        System.out.println(Dispatch.call(zk, "SSR_SetUserInfo", dwMachineNumber, dwEnrollNumber, Name, Password, Privilege, Enabled).getBoolean());
    }

    /****
     * End Enroll user with finger
     */

    public void GetHIDEventCardNumAsStr() {
        Variant strHIDEventCardNum = new Variant("", true);
        Dispatch.call(zk, "GetHIDEventCardNumAsStr", strHIDEventCardNum).toBoolean();
        System.out.println("strHIDEventCardNum = " + strHIDEventCardNum);
    }

    public void OnFingerFeature() {
        Variant score = new Variant("", true);
        Dispatch.call(zk, "OnFingerFeature", score).toBoolean();
        System.out.println(score);
    }

    public void OnHIDNum() {
        Variant score = new Variant("", true);
        Dispatch.call(zk, "OnHIDNum", score).toBoolean();
        System.out.println(score);
    }

    public void GetUserTmp() {
        // Variant dwEnrollNumber = new Variant(2, true);
        // Variant dwFingerIndex = new Variant(1, true);
        // byte i = 0;
        // byte[] FingerTmp = new byte[2000];
        // Variant TmpData = new Variant(i, true);
        // Variant TmpLength = new Variant(0, true);
        // // Dispatch.call(zk, "ReadAllTemplate", dwMachineNumber).toBoolean();
        // Dispatch.call(zk, "GetUserTmp", dwMachineNumber, dwEnrollNumber, dwFingerIndex, FingerTmp, TmpLength).toBoolean();
        int machineNum = 0;
        Variant userId = new Variant("2", true);
        int fingerIndex = 1;
        byte b = 0;
        Variant finger = new Variant(b, true);
        // byte[] FingerTmp = new byte[2000];
        Variant FingerTmp = new Variant("", true);
        Variant fingerLength = new Variant(0, true);
        Dispatch.call(zk, "SSR_GetUserTmp", new Variant(machineNum), userId, new Variant(fingerIndex), FingerTmp, fingerLength).getBoolean();
        Variant flag = new Variant(0, true);
        Dispatch.call(zk, "SSR_GetUserTmpExStr", new Variant(machineNum), new Variant(userId), new Variant(fingerIndex), flag, FingerTmp, fingerLength).getBoolean();
        System.out.println("fingerData " + finger);
        System.out.println("fingerLength " + fingerLength);
        System.out.println("FingerTmp: " + FingerTmp);
        // for (byte byte1 : FingerTmp) {
        // System.out.print(byte1 + "|");
        // }
        // System.out.println("TmpData " + TmpData);
        // System.out.println("TmpLength " + TmpLength);
    }

    public void GetFPTempLength() {
        byte b = 1;
        Variant dwEnrollData = new Variant(b, true);
        Variant Len = new Variant(0, true);
        Dispatch.call(zk, "GetFPTempLength", dwEnrollData).toBoolean();
        System.out.println("dwEnrollData " + dwEnrollData);
        System.out.println("Len " + Len);
    }

    public void SetUserInfoEx() {
        Variant dwEnrollNumber = new Variant(2, true);
        Variant VerifyStyle = new Variant(0, true);
        Variant Reserved = new Variant((byte) 0, true);
        Dispatch.call(zk, "SetUserInfoEx", dwMachineNumber, dwEnrollNumber, VerifyStyle, Reserved).toBoolean();
    }

    public void GetDataFile() throws IOException {
        Variant DataFlag = new Variant(0, true);
        File temp = File.createTempFile("FingerFile", ".tmp");
        Variant FileName = new Variant("FingerFile", true);
        Dispatch.call(zk, "GetDataFile", dwMachineNumber, DataFlag, FileName).toBoolean();
    }

}
