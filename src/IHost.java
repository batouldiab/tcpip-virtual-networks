import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHost extends Remote {
	String register(IHost host) throws RemoteException;
}