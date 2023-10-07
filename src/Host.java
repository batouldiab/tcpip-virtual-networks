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

    @Override
	public String register(IHost host) throws RemoteException {

		Random r = new Random();
		String ip = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
		try {
			InetAddress selfIP = InetAddress.getByName(ip);
			return "success";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static String connectToRouter() throws MalformedURLException, RemoteException, NotBoundException {
		Scanner input = new Scanner(System.in);
		String routerip = "";
		System.out.println("Enter the router ip:\n"); // this is usually done using hardware connections
		routerip = input.next();
		try {
			connectedRouter = InetAddress.getByName(routerip);
			// rmi connect to register host in router
			look_up = (IRouter) Naming.lookup("//localhost/MyRouter" + connectedRouter);
			String response = look_up.hostConnect(selfIP);

			return "success";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void migrate(Packet p) {

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