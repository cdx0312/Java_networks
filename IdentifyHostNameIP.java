import org.omg.CORBA.portable.UnknownException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by cdxu0 on 2017/7/11.
 */
public class IdentifyHostNameIP {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            try {
                InetAddress address = InetAddress.getByName(args[i]);
                System.out.print("Host name: " + address.getHostName() + " ");
                System.out.println("IP adress: " + address.getHostAddress());
            } catch (UnknownHostException ex) {
                System.out.println("Unknown host or IP address " + args[i]);
            }
        }
    }
}
