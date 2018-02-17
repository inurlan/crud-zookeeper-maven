package nurlan.zookeeper.app;



import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.ZooKeeper.States;

public class ZkConnector {
	
	
	private ZooKeeper zk;
	

	
	private CountDownLatch connSignal = new CountDownLatch(1);
	
	public ZooKeeper connect(String host) throws IOException, InterruptedException, IllegalStateException{
		
		// 10000 is 10 second timeout for connection
		zk = new ZooKeeper(host, 10000, new Watcher() {
			
			public void process(WatchedEvent event) {
				if(event.getState() == KeeperState.SyncConnected) {
					connSignal.countDown();
				}
			}
		});
		
		// wait while user is not going to shut the program
		connSignal.await();
		
		return zk;
	}
	
	
	public void close() throws InterruptedException{
		zk.close();
	}
	

}