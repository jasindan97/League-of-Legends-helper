

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * 
 * @author Jasindan Rasalingam - 100584595
 *
 */
public interface FileInterface extends Remote {
   public byte[] downloadFile(String fileName) throws RemoteException;
   public ArrayList<String> championBuildz(String champName, ArrayList<String> items) throws RemoteException;
   public String champRolez() throws RemoteException;
   public ArrayList<String> matchHistory(String summonerName) throws RemoteException;
   public String teamStats() throws RemoteException;
   public String champCounters(String champName) throws RemoteException;
}