package logic;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class NodeMonitor implements Watcher {

    private final String path;

    private ZooKeeper zooKeeper;
    private Process paint = null;


    public NodeMonitor(String path){
        this.path = path;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Event occurs");
        if (event.getPath() != null && event.getPath().equals(path)){
            System.out.println("Event occurs at /z path");
            switch(event.getType()) {
                case NodeCreated -> openPaint();
                case NodeDeleted -> closePaint();
                case NodeChildrenChanged -> {
                    showChildrenUpdate();
                }
            }
        }
    }

    private void showChildrenUpdate() {
        try {
            int childrenNumber = zooKeeper.getAllChildrenNumber(path);
            System.out.printf("Children number of path %s: %d%n",path,childrenNumber);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    public void printNodeTree(){
        try {
            printNodeTreeRec(path);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    private void printNodeTreeRec(String node) throws InterruptedException, KeeperException {
        var queue = new LinkedList<String>();
        queue.offerLast(node);

        while (!queue.isEmpty()){
            var peek = queue.removeFirst();
            System.out.println(peek);

            for (var child: zooKeeper.getChildren(peek,false)){
                queue.offerFirst(peek + "/" + child);
            }
        }
    }

    private void closePaint() {
        paint.destroy();
    }

    private void openPaint() {
        if (paint != null && paint.isAlive())
            return;
        ProcessBuilder pb = new ProcessBuilder("C:\\Windows\\System32\\mspaint.exe");
        try {
            paint = pb.start();
            long pid = paint.pid();
            System.out.println(pid);
        } catch (IOException ex) {
            System.out.println("Paint error");
        }
    }

    public String getPath() {
        return path;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }


}
