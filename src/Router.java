import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Router extends UnicastRemoteObject implements IRouter {

	static String registryReply = "";
	static InetAddress selfIP;
	static Set<InetAddress> connectedRouter = new HashSet<InetAddress>();
	static Set<RouterEntry> routingTable = new HashSet<RouterEntry>();

	protected Router() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String register(IRouter router) throws RemoteException {

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
			connectedRouter.add(InetAddress.getByName(routerip));
			RouterEntry newRouter = new RouterEntry(InetAddress.getByName(routerip), 1,InetAddress.getByName(routerip));
			//check if already exists in routing table
			Iterator<RouterEntry> iterator = routingTable.iterator();
			while (iterator.hasNext()) {
				RouterEntry re=iterator.next();
				if(re.ip==newRouter.ip)
				{
					re.cost=1;
					re.nextHop=re.ip;
					return "success";
				}
			}
			routingTable.add(newRouter);
			iterateConnectedRouters(connectedRouter,newRouter);
			return "success";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void iterateConnectedRouters(Set<InetAddress> connectedRouter,RouterEntry newRouter) throws MalformedURLException, RemoteException, NotBoundException {
		Iterator<InetAddress> iterator = connectedRouter.iterator();
		while (iterator.hasNext()) {
			InetAddress ipRouter=iterator.next();
			IRouter router= (IRouter) Naming.lookup("//localhost/MyRouter" + ipRouter);
			String response = router.newRouter(newRouter,selfIP);
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
			System.out.println("Router Succesfully registered!\nIP=" + selfIP + "\n");

			try {

				Naming.rebind("//localhost/MyRouter" + selfIP, new Router());
				System.err.println("Router ready");

			} catch (Exception e) {

				System.err.println("Router exception: " + e.toString());
				e.printStackTrace();

			}

			// connecting to available router
			String s = connectToRouter();
			while (!s.equals("success")) {
				Thread.sleep(1000);
				s = connectToRouter();
			}
			System.out.println("Router Succesfully connected!\n");

			Scanner input = new Scanner(System.in);
			String m = "";
			int option = 1;
			InetAddress dest;
			// get new request
			while (true) {
				System.out.println("Enter the Router Ip to connect:\n");
				m = input.next();
				System.out.println("Enter the IP of the destination host:\n");
				dest = InetAddress.getByName(input.next());
				Packet p = new Packet(selfIP, dest, m, bestRoute(dest));
				migrate(p);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private static InetAddress bestRoute(InetAddress dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hostConnect(InetAddress ip) throws RemoteException {
		RouterEntry host = new RouterEntry(ip, 1, ip);
		routingTable.add(host);
		try {
			iterateConnectedRouters(connectedRouter,host);
		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// connectedRouter.add(ip);
		return null;
	}

	@Override
	public String newRouter(RouterEntry newRouter,InetAddress sendingRouter) {
		if (connectedRouter.contains(newRouter.ip))
			return "success";
		Iterator<RouterEntry> iterator = routingTable.iterator();
		while (iterator.hasNext()) {
			RouterEntry re=iterator.next();
			if(re.ip==newRouter.ip)
			{
				if (re.cost>newRouter.cost+1) {
					re.cost=newRouter.cost+1;
					re.nextHop=sendingRouter;
					try {
						iterateConnectedRouters(connectedRouter,newRouter);
					} catch (MalformedURLException | RemoteException | NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
					return "success";
				}
				else return "success";
			}
		}
		routingTable.add(new RouterEntry(newRouter.ip,newRouter.cost+1,sendingRouter));
		
		return "success";
	}



}