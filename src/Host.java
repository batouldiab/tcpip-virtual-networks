import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Scanner;

public class Host extends UnicastRemoteObject implements IHost {

	static String registryReply = "";
	static InetAddress selfIP;
	static InetAddress connectedRouter;
	private static IRouter look_up;
	Scanner in;
	PrintWriter out;
	Socket socket;
	protected Host() throws RemoteException {
		
	}

	@SuppressWarnings({ "deprecation", "resource" })
	public static void main(String args[]) {
		System.setSecurityManager(new RMISecurityManager());
		try {
			// registration
			Host h = new Host();
			registryReply = h.register(h);
			while (!registryReply.equals("success")) {
				Thread.sleep(1000);
				registryReply = h.register(h);
			}
			System.out.println("Host Succesfully registered!\nIP=" + selfIP + "\n");

			// connecting to available router
			String s = connectToRouter();
			while (!s.equals("success")) {
				Thread.sleep(1000);
				s = connectToRouter();
			}
			System.out.println("Router Succesfully connected!\n");

			Scanner input = new Scanner(System.in);
			String m = "";
			InetAddress dest;
			
			// get new request
			while (true) {
				System.out.println("Enter the message:\n");
				m = input.next();
				System.out.println("Enter the IP of the destination host:\n");
				dest = InetAddress.getByName(input.next());
				Packet p = new Packet(selfIP, dest, m, connectedRouter);
				migrate(p);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}