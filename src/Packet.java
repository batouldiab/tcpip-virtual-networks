import java.net.InetAddress;
public class Packet {
	InetAddress source,dest,nextHop;
	String msg;
	
	public Packet(){
		msg="";
	}
	
	public Packet(InetAddress s,InetAddress d, String m, InetAddress nh){
		source=s;
		msg=m;
		dest=d;
		nextHop=nh;
	}
}