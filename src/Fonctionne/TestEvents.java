package Fonctionne;

import com.jacob.activeX.ActiveXInvocationProxy;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class TestEvents extends ActiveXInvocationProxy {

    static Variant machineNumber = new Variant(1, true);

    Variant tmpData2 = new Variant();

    Variant Size = new Variant();

    Variant dwEnrollNumber = new Variant(900, true);
boolean deleted = false;
Variant FingerTmp = new Variant("", true);
    @Override
    public Variant invoke(String methodName, Variant[] targetParameters) {
        System.out.println("Received event from  : " + methodName + "\n");
       
        Variant fingerLength = new Variant(0, true);
        if (methodName.equals("OnEnrollFinger")) {
            Dispatch.call(IService.INSTANCE, "RefreshData", machineNumber);
            Variant fingerIndex = new Variant(6, true);
            byte b = 0;

            if (Dispatch.call(IService.INSTANCE, "SSR_GetUserTmpStr", machineNumber, dwEnrollNumber, fingerIndex, FingerTmp, fingerLength).getBoolean()) {
                System.out.println("fingerLength " + fingerLength);
                System.out.println("FingerTmp: " + FingerTmp);

                deleted = true;

            }
        }
        if (methodName.equals("OnNewUser")&& deleted) {
            // delete tmp user
            if (Dispatch.call(IService.INSTANCE, "SSR_DeleteEnrollDataExt", machineNumber, dwEnrollNumber, 12).getBoolean()) {
                // create final user
                System.out.println(Dispatch.call(IService.INSTANCE, "SSR_SetUserInfo", machineNumber, dwEnrollNumber, new Variant("finalUser", true), new Variant("", true),
                        new Variant(0, true), new Variant(true, true)).getBoolean());
                System.out.println(FingerTmp);
                 System.out.println(Dispatch.call(IService.INSTANCE, "SetUserTmpExStr", machineNumber, dwEnrollNumber, new Variant(6, true),FingerTmp).getBoolean());
                Dispatch.call(IService.INSTANCE, "RefreshData", machineNumber);
            }
            deleted =false;
        }
        return null;
    }
}