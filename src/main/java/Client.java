import logic.ZKManager;
import logic.ZKManagerImpl;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class Client {
    public static void main(String[] args){
        System.out.println("Hello world!");
        try {
            ZKManagerImpl zkManager = new ZKManagerImpl();
            zkManager.run();
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
