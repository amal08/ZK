package Fonctionne;

import com.jacob.activeX.ActiveXInvocationProxy;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class TestEvents extends ActiveXInvocationProxy {

    static Variant machineNumber = new Variant(1, true);

    Variant dwEnrollNumber = new Variant(900, true);

    @Override
    public Variant invoke(String methodName, Variant[] targetParameters) {
        System.out.println("Received event from  : " + methodName + "\n");
        if (methodName.equals("OnEnrollFinger")) {
            Dispatch.call(IService.INSTANCE, "RefreshData", machineNumber);
            Variant fingerIndex = new Variant(6, true);
            byte b = 0;
            Variant FingerTmp = new Variant("", true);
            Variant fingerLength = new Variant(0, true);
            if (Dispatch.call(IService.INSTANCE, "SSR_GetUserTmpStr", machineNumber, dwEnrollNumber, fingerIndex, FingerTmp, fingerLength).getBoolean()) {
                System.out.println("fingerLength " + fingerLength);
                System.out.println("FingerTmp: " + FingerTmp);

                // delete tmp user
                Dispatch.call(IService.INSTANCE, "SSR_DeleteEnrollDataExt", machineNumber, dwEnrollNumber, 12);

                // create final user
                System.out.println(Dispatch.call(IService.INSTANCE, "SSR_SetUserInfo", machineNumber, dwEnrollNumber, new Variant("finalUser", true), new Variant("", true),
                        new Variant(0, true), new Variant(true, true)).getBoolean());
                System.out.println(Dispatch.call(IService.INSTANCE, "SetUserTmp", machineNumber, dwEnrollNumber, new Variant(6, true), FingerTmp.getString()).getBoolean());
                Dispatch.call(IService.INSTANCE, "RefreshData", machineNumber);
            }
        }
        return null;
    }
}