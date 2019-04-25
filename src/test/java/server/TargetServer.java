package server;

import java.io.IOException;
import java.net.Socket;

/**
 * This is the target server class which contain data of the secure api server properties
 * @author Melchior Vrolijk
 */
public class TargetServer {

    //region Local instances
    public static final String IP_ADDRESS = "127.0.0.1"; //SET YOUR OWN IP IF THE SERVER IS HOSTING ON ANOTHER IP CHANGE IT TO THE TARGET IP 'IN THIS CASE IT IS SET TO LOCAL IP ADDRESS'
    public static final int PORT_NR = 3000;
    public static final String BASE_URL = String.format("https://%s:%d",IP_ADDRESS,PORT_NR);
    //endregion

    //region Determine if a server is reachable
    /**
     * Determine if the target server is running
     * @param IP_ADDRESS The target IP Address
     * @param PORT_NR The target server port nr
     * @return 'True' if server is running and 'False' if server is not running
     */
    public static boolean isReachable(String IP_ADDRESS, int PORT_NR) {
        try (Socket server_socket = new Socket(IP_ADDRESS, PORT_NR)) {
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    //endregion
}
