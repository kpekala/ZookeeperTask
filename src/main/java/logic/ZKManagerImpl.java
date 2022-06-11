package logic;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ZKManagerImpl implements ZKManager {
    private static ZooKeeper zkeeper;
    private static ZKConnection zkConnection;

    private final NodeMonitor nodeMonitor = new NodeMonitor("/z");

    public void initialize() throws IOException, InterruptedException, KeeperException {
        zkConnection = new ZKConnection();
        zkeeper = zkConnection.connect("localhost");
        zkeeper.addWatch(nodeMonitor.getPath(),nodeMonitor, AddWatchMode.PERSISTENT);
        nodeMonitor.setZooKeeper(zkeeper);
    }

    public void run() throws InterruptedException {
        while (true){
            try {
                var out = System.in.read();
                if (out == 116){
                    nodeMonitor.printNodeTree();
                }
            } catch (IOException e) {
                System.out.println("ZKManager");
                break;
            }
        }
    }

    public void closeConnection() throws InterruptedException {
        zkConnection.close();
    }

    public void create(String path, byte[] data)
            throws KeeperException,
            InterruptedException {

        zkeeper.create(
                path,
                data,
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    public Object getZNodeData(String path, boolean watchFlag) throws KeeperException, InterruptedException {
        byte[] b = zkeeper.getData(path, null, null);
        return new String(b, StandardCharsets.UTF_8);
    }

    public void update(String path, byte[] data) throws KeeperException, InterruptedException {
        int version = zkeeper.exists(path, true).getVersion();
        zkeeper.setData(path, data, version);
    }
}