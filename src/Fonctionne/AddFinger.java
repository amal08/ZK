package Fonctionne;

import com.jacob.activeX.ActiveXDispatchEvents;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class AddFinger {

    public static void main(String args[]) {
        Variant machineNumber = new Variant(1, true);
        Variant dwEnrollNumber = new Variant(900, true);
        Variant connected = Dispatch.call(IService.INSTANCE, "Connect_Net", "172.16.0.124", 4370);
        Variant Name = new Variant("Amal03", true);
        Variant Password = new Variant("", true);
        Variant Privilege = new Variant(0, true);
        Variant Enabled = new Variant(true, true);
        TestEvents event = new TestEvents();

        // add virtual user
        System.out.println("#######create new user##########");
        // System.out.println(Dispatch.call(IService.INSTANCE, "SSR_SetUserInfo", machineNumber, dwEnrollNumber, Name, Password, Privilege, Enabled).getBoolean());

        Variant dwFingerIndex = new Variant(6, true);
        Variant Flag = new Variant(1, true);

        if (connected.getBoolean()) {
            System.out.println("connected...");
            registerAllRealTimeEvents();
            ActiveXDispatchEvents de = new ActiveXDispatchEvents(IService.INSTANCE, event);
            if (Dispatch.call(IService.INSTANCE, "StartEnrollEx", dwEnrollNumber, dwFingerIndex, Flag).getBoolean()) {
                Dispatch.call(IService.INSTANCE, "StartIdentify");
            }
            while (connected.getBoolean()) {
                if (Dispatch.call(IService.INSTANCE, "ReadRTLog", machineNumber).getBoolean()) {
                    while (Dispatch.call(IService.INSTANCE, "GetRTLog", machineNumber).getBoolean()) {
                    }
                }
            }

        }

    }

    public static void registerAllRealTimeEvents() {
        Variant EventMask = new Variant(65534, true);
        if (Dispatch.call(IService.INSTANCE, "RegEvent", new Variant(1, true), EventMask).getBoolean())
            System.out.println("REAL TIME EVENTS SUCCESSFULLY REGISTRED");
        else
            System.out.println("FAILED TO REGISTER REAL TIME EVENTS");
    }

}
