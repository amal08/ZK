package Fonctionne;

import com.jacob.activeX.ActiveXInvocationProxy;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class TestEvents extends ActiveXInvocationProxy {

    static Variant machineNumber = new Variant(1, true);

    Variant tmpData2 = new Variant();

    Variant Size = new Variant();

    Variant dwEnrollNumber = new Variant(900, true);

    boolean tmpUserAdded = false;

    Variant FingerTmp = new Variant("", true);

    @Override
    public Variant invoke(String methodName, Variant[] targetParameters) {
        System.out.println("Received event from  : " + methodName + "\n");
        Variant fingerLength = new Variant(0, true);
        if (methodName.equals("OnEnrollFinger")) {
            Variant fingerIndex = new Variant(6, true);
            if (Dispatch.call(IService.INSTANCE, "SSR_GetUserTmpStr", machineNumber, dwEnrollNumber, fingerIndex, FingerTmp, fingerLength).getBoolean()) {
                System.out.println("fingerLength " + fingerLength);
                System.out.println("FingerTmp: " + FingerTmp);
                tmpUserAdded = true;
            }
        }
        if (methodName.equals("OnNewUser") && tmpUserAdded) {
            Dispatch.call(IService.INSTANCE, "EnableDevice", machineNumber, false);
            // delete tmp user
            if (Dispatch.call(IService.INSTANCE, "SSR_DeleteEnrollDataExt", machineNumber, dwEnrollNumber, 12).getBoolean()) {
                // create final user
                boolean userAdded = Dispatch.call(IService.INSTANCE, "SSR_SetUserInfo", machineNumber, dwEnrollNumber, new Variant("finalUser", true), new Variant("", true),
                        new Variant(0, true), new Variant(true, true)).getBoolean();
                System.out.println(FingerTmp + "\n" + machineNumber + "\n" + dwEnrollNumber);
                if (userAdded) {
                    boolean isfingerAdded = Dispatch.call(IService.INSTANCE, "SetUserTmpExStr", machineNumber, dwEnrollNumber, new Variant(0, true), new Variant(1, true),
                            FingerTmp).getBoolean();
                    if (isfingerAdded) {
                        System.out.println("finger added :p");
                    } else {
                        Variant message = new Variant();
                        Dispatch.call(IService.INSTANCE, "GetLastError", message);
                        System.out.println(message);
                    }
                    Dispatch.call(IService.INSTANCE, "RefreshData", machineNumber);
                    Dispatch.call(IService.INSTANCE, "EnableDevice", machineNumber, true);
                }
            }
            tmpUserAdded = false;
            Dispatch.call(IService.INSTANCE, "EnableDevice", machineNumber, true);
        }
        return null;
    }
}