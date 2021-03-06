import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Himanshu on 3/3/2016.
 */
public class CacheServer {

    final static Logger log = Logger.getLogger(WorkerThread.class);

    private String serverID;
    private String ipAdd;
    private String parentIPAdd;
    private int portNum;
    private ArrayList<String> childrenIPAdd;
    private HashMap<String, CacheContent> contentList;
    private HashMap<String, ArrayList<String>> sessionMap;
    private LRU lru;
    public AtomicInteger pageCount;

    public CacheServer(String ipAdd, String serverID, String parentIPAdd) {
        this.serverID = serverID;
        this.ipAdd = ipAdd;
        this.parentIPAdd = parentIPAdd;
        this.contentList = new HashMap<String,CacheContent>();
        this.sessionMap = new HashMap<String, ArrayList<String>>();
        this.childrenIPAdd = new ArrayList<String>();
        this.lru = new LRU(3,this);
        this.pageCount = new AtomicInteger(0);
    }

    public LRU getLru() {
        return lru;
    }

    public String getServerID() {
        return serverID;
    }

    public String getIpAdd() {
        return ipAdd;
    }

    public String getParentIPAdd() {
        return parentIPAdd;
    }

    public int getPortNum() {
        return portNum;
    }

    public ArrayList<String> getChildrenIPAdd() {
        return childrenIPAdd;
    }

    public HashMap<String, CacheContent> getContentList() {
        return contentList;
    }

    public boolean hasContent(String contentName) {
        return contentList.containsKey(contentName);
    }

    public void startCS(int portNum) {
        this.portNum = portNum;
        MainThread mt = new MainThread(portNum, this, parentIPAdd);
        mt.run();
    }

    public String getContentPath(String contentName) {
        return contentList.get(contentName).getContentPath();
    }
}
