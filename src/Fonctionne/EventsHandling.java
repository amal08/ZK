package Fonctionne;

import com.jacob.activeX.ActiveXDispatchEvents;
import com.jacob.com.Dispatch;
import com.jacob.com.InvocationProxy;
import com.jacob.com.Variant;

public class EventsHandling implements Runnable {

    public static void main(String[] args) {
        (new Thread(new EventsHandling())).start();
    }

    public EventsHandling() {
    }

    @Override
    public void run() {
        // deprecated
        // System.runFinalizersOnExit(true);
        // Dispatch test = new ActiveXComponent("MathTest.Math");
        Variant connected = Dispatch.call(IService.INSTANCE, "Connect_Net", "172.16.0.124", 4370);
        registerAllRealTimeEvents();
        Variant machineNumber = new Variant(1, true);
        // Variant reg = Dispatch.call(IService.INSTANCE, "RegEvent", machineNumber, 65534);

        // if (reg.toBoolean()) {
        ActiveXDispatchEvents de = new ActiveXDispatchEvents(IService.INSTANCE, new TestEvents_());
        while (connected.getBoolean()) {
            if (Dispatch.call(IService.INSTANCE, "ReadRTLog", machineNumber).getBoolean()) {
                while (Dispatch.call(IService.INSTANCE, "GetRTLog", machineNumber).getBoolean()) {

                }
            }
        }
        // } else {
        // boolean diconnect = Dispatch.call(IService.INSTANCE, "Disconnect").toBoolean();
        // }

    }

    public void registerAllRealTimeEvents() {
        // Dispatch.call(zk, "EnableDevice", 1,false).toBoolean();
        Variant EventMask = new Variant(65534, true);
        if (Dispatch.call(IService.INSTANCE, "RegEvent", 1, EventMask).getBoolean())
            System.out.println("REAL TIME EVENTS SUCCESSFULLY REGISTRED");
        else
            System.out.println("FAILED TO REGISTER REAL TIME EVENTS");
    }

    public class TestEvents_ extends InvocationProxy {

        @Override
        public Variant invoke(String methodName, Variant[] targetParameters) {
            System.out.println("Received event from  : " + methodName + "\n");
            if (targetParameters.length == 1) {
                System.out.println(targetParameters[0] + "\n");
            }
            if (targetParameters.length == 2) {
                System.out.println(targetParameters[0] + "\n");
                System.out.println(targetParameters[1] + "\n");
            }
            if (targetParameters.length == 3) {
                System.out.println(targetParameters[0] + "\n");
                System.out.println(targetParameters[1] + "\n");
                System.out.println(targetParameters[2] + "\n");
            }
            if (targetParameters.length == 4) {
                System.out.println(targetParameters[0] + "\n");
                System.out.println(targetParameters[1] + "\n");
                System.out.println(targetParameters[2] + "\n");
                System.out.println(targetParameters[3]);
            }
            if (targetParameters.length == 11) {
                System.out.println(targetParameters[0] + "\n");
                System.out.println(targetParameters[1] + "\n");
                System.out.println(targetParameters[2] + "\n");
                System.out.println(targetParameters[3] + "\n");
                System.out.println(targetParameters[4] + "\n");
                System.out.println(targetParameters[5] + "\n");
                System.out.println(targetParameters[6] + "\n");
                System.out.println(targetParameters[7] + "\n");
                System.out.println(targetParameters[8] + "\n");
                System.out.println(targetParameters[9] + "\n");
                System.out.println(targetParameters[10] + "\n");
            }
            return null;
        }

    }

}
