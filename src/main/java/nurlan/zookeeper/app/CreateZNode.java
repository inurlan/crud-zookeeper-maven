package nurlan.zookeeper.app;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class CreateZNode {
	
	private ZooKeeper zk;
		
	public  void create(String path, byte[] data) throws KeeperException, InterruptedException {
		
		// open acl unsafe makes znode visible from everywhere
		zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	
	public void setConnector(ZooKeeper zk){
		this.zk = zk;
	}
}
