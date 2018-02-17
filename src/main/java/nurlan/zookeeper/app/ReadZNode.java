package nurlan.zookeeper.app;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class ReadZNode {
	
	private  ZooKeeper zk;
		
	public  byte[] read(String path) throws KeeperException, InterruptedException {
		return zk.getData(path, true, zk.exists(path, true));
	}
	
	public void setConnector(ZooKeeper zk){
		this.zk = zk;
	}
}
