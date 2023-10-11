import java.net.InetAddress;

public class RouterEntry {
	InetAddress ip;
	int cost;
	InetAddress nextHop;

	public RouterEntry() {

	}

	public RouterEntry(InetAddress ip, int cost, InetAddress nextHop) {
		super();
		this.ip = ip;
		this.cost = cost;
		this.nextHop = nextHop;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public InetAddress getNextHop() {
		return nextHop;
	}

	public void setNextHop(InetAddress nextHop) {
		this.nextHop = nextHop;
	}

}