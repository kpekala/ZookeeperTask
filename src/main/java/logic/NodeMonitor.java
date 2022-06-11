package logic;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.awt.*;
import java.awt.desktop.OpenFilesEvent;
import java.awt.desktop.OpenFilesHandler;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class NodeMonitor implements Watcher {

    private final String path;
    Process paint = null;


    public NodeMonitor(String path){
        this.path = path;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Event occurs");
        if (event.getPath() != null && event.getPath().equals(path)){
            System.out.println("Event occurs at good path");
            if(event.getType().equals(Event.EventType.NodeCreated)){
                openPaint();
            }if (event.getType().equals(Event.EventType.NodeDeleted)){
                closePaint();
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
}
