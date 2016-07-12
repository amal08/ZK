package Fonctionne;

import java.io.IOException;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Example {

    @SuppressWarnings("deprecation")
    public static void main(String args[]) throws InterruptedException, IOException {
        System.out.println("hello");
        StringBuilder pid = new StringBuilder("zkemkeeper.ZKEM.1");
        ActiveXComponent zk = new ActiveXComponent(pid.toString());
        Services s = new Services();
        String ip = "172.16.0.124";
        boolean connect = Dispatch.call(zk, "Connect_Net", ip, 4370).toBoolean();
        System.out.println("Connection.... " + ip + " " + connect);
        Dispatch.call(zk, "EnableDevice", 1, true).toBoolean();
        // s.registerRealTimeEvent(1);
        s.registerAllRealTimeEvents();
        s.GetHIDEventCardNumAsStr();

        System.out.println("/////////////////");
        s.CancelOperation();
        Variant dwMachineNumber = new Variant("1", true);
        s.SSR_DelUserTmpExt(dwMachineNumber, new Variant("3", true), new Variant("3", true));
        boolean enrolled = s.StartEnroll(new Variant("3", true), new Variant("3", true), new Variant("1", true));
        Thread.sleep(10000);
        if (enrolled) {
            System.out.println("ReadRTLog read = " + Dispatch.call(zk, "ReadRTLog", 1).toBoolean());
            System.out.println("GetRTLog read = " + Dispatch.call(zk, "GetRTLog", 1));
        }
        s.StartIdentify();
        // s.Set_User(new Variant("2", true), new Variant("Achraf", true), new Variant("", true), new Variant(0, true), new Variant(true, true));
        s.Set_User(new Variant("3", true), new Variant("Amal", true), new Variant("", true), new Variant(0, true), new Variant(true, true));

        // s.Usersjj_List();
        // s.CancelOperation();

        // s.Users_Log();

        /* ###Liste des présences */
        // s.ReadAllGLogData();

        s.GetUserTmp();
        s.GetFPTempLength();
        s.GetDataFile();
    }
}