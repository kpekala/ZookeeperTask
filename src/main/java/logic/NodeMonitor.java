package logic;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

public class NodeMonitor implements Watcher {

    private final String path;

    public NodeMonitor(String path){
        this.path = path;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Event occurs");
        if (event.getPath() != null && event.getPath().equals(path)){
            System.out.println("Event occurs at good path");
        }
    }

    public String getPath() {
        return path;
    }
}
