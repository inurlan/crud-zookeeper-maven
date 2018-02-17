package nurlan.zookeeper.app;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class DeleteZNode {

	private ZooKeeper zk;

	public void delete(String path) throws InterruptedException, KeeperException {
		zk.delete(path, zk.exists(path, true).getVersion());
	}
	
	public void setConnector(ZooKeeper zk){
		this.zk = zk;
	}
}
