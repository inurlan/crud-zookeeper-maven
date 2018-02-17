package nurlan.zookeeper.app;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class UpdateZNode {
	
	private  ZooKeeper zk;
		
	public  void update(String path, byte[] data) throws KeeperException, InterruptedException {
		zk.setData(path, data, zk.exists(path,true).getVersion());
	}
	
	public void setConnector(ZooKeeper zk){
		this.zk = zk;
	}
}
