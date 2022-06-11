import logic.ZKManager;
import logic.ZKManagerImpl;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.net.SocketException;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");
        ZKManagerImpl zkManager = new ZKManagerImpl();

        try {
            zkManager.initialize();
            zkManager.run();
        } catch (IOException e){
            System.out.println("Socket is not connected!");
        }
        catch (InterruptedException | KeeperException ignored) {
        }finally {
            zkManager.closeConnection();
        }
    }
}
