package server;

import java.io.IOException;
import java.net.Socket;

public class TargetServer {

    public static final String IP_ADDRESS = "192.168.1.100";
//    public static final String IP_ADDRESS = "10.38.146.61";
    public static final int PORT_NR = 3000;
    public static final String BASE_URL = String.format("https://%s:%d",IP_ADDRESS,PORT_NR);

    public static boolean isReachable(String IP_ADDRESS, int PORT_NR) {
        try (Socket server_socket = new Socket(IP_ADDRESS, PORT_NR)) {
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
