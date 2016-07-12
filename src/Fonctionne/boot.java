package Fonctionne;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class boot {

    public static boolean runSystemCommand(String command) {
        // TODO Auto-generated method stub

        try {
            Process p = Runtime.getRuntime().exec("ping " + command);
            p.getOutputStream();
            System.out.println("ping *** " + p.getOutputStream().toString());
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s = "";
            // reading output stream of the command
            while ((s = inputStream.readLine()) != null) {
                System.out.println(s);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String gks[]) throws UnknownHostException, IOException {
        // InetAddress IP = null;
        // try {
        // IP = InetAddress.getLocalHost();
        // } catch (UnknownHostException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // System.out.println(IP.toString());
        //
        // String ip = "172.16.0.5";
        // runSystemCommand(ip);
        // int timeout = 0;
        System.out.println(InetAddress.getByName("172.16.0.181").isReachable(60));
    }

}
