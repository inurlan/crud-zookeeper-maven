package nurlan.zookeeper.app;

import java.io.IOException;

import java.util.Scanner;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class Master {

	private static ZkConnector connector;
	private static Scanner console;

	private static UpdateZNode update;
	private static DeleteZNode delete;
	private static ReadZNode read;
	private static CreateZNode create;

	private static boolean isClosed;
	private static String host;

	private static void init() {
		connector = new ZkConnector();
		console = new Scanner(System.in);
		update = new UpdateZNode();
		delete = new DeleteZNode();
		read = new ReadZNode();
		create = new CreateZNode();

		isClosed = false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		init();

		establishConncetion();

		System.out.println(
				"Usage; please specify one of the following commands:  \"create\", \"read\", \"append\", \"delete\", \"close\", \"exit\", \"connect\"");

		while (!isClosed) {
			switch (console.next()) {
			case "connect":
				performClose();
				establishConncetion();

				break;
			case "create":
				performCreation();

				break;
			case "read":
				performRead();

				break;
			case "append":
				performAppend();

				break;
			case "delete":
				performDelete();

				break;
			case "close":
				performClose();

				break;
			case "exit":
				System.exit(0);

				break;
			}
		}

	}

	public static void establishConncetion() {
		// TODO Auto-generated method stub

		System.out.println("Please enter public IP:");

		host = console.next();

		boolean success = false;

		try {
			ZooKeeper zk = connector.connect(host);

			update.setConnector(zk);
			create.setConnector(zk);
			read.setConnector(zk);
			delete.setConnector(zk);

			success = true;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable conncet to host " + host + "!");
			System.err.println(
					"***Java environment or Java application is not in an appropriate state for the requested operation.***");
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable conncet to host " + host + "!");
			System.err.println("***An input or output operation is failed or interpreted***");
			// e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable conncet to host " + host + "!");
			System.err.println("***The thread is interrupted, either before or during the activity***");
			// e.printStackTrace();
		}

		if (success)
			System.out.println("Successfully connnected to host " + host + "!");

	}

	public static void performCreation() {
		// TODO Auto-generated method stub

		System.out.println("Please specify Znode path and default data which you are going to create:");

		System.out.print("path: ");

		String path = console.next();

		System.out.println("data: ");
		console.nextLine();

		byte[] data = console.nextLine().getBytes();

		boolean success = false;
		try {
			create.create(path, data);

			success = true;
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to create " + path + " on host " + host + "!");
			System.err.println("***Please view Keeper Exception***");
			// e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to create " + path + " on host " + host + "!");
			System.err.println("***The thread is interrupted, either before or during the activity***");
			// e.printStackTrace();
		}

		if (success)
			System.out.println("Successfully created Znode on path " + path + " on host " + host + "!");
	}

	private static void performRead() {
		// TODO Auto-generated method stub

		System.out.println("Please specify Znode path from which you are going to read:");

		System.out.print("path: ");

		String path = console.next();

		byte[] data = null;

		boolean success = false;

		try {
			data = read.read(path);

			success = true;
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to read from " + path + " on host " + host + "!");
			System.err.println("***Please view Keeper Exception***");
			// e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to read from " + path + " on host " + host + "!");
			System.err.println("***The thread is interrupted, either before or during the activity***");
			// e.printStackTrace();
		}

		if (success) {
			System.out.println("Successfully read Znode from path " + path + " on host " + host + "!");

			System.out.println("See below the output:");

			if (data != null)
				for (byte b : data) {
					System.out.print((char) b);
				}
		}
	}

	private static void performAppend() {
		// TODO Auto-generated method stub

		System.out.println("Please specify Znode path and data to which you are going to append:");

		System.out.print("path: ");

		String path = console.next();

		System.out.println("data: ");

		console.nextLine();

		byte[] newData = console.nextLine().getBytes();

		byte[] oldData = null;

		boolean success = false;

		try {
			oldData = read.read(path);

			success = true;
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to read from " + path + " on host " + host + " in order to append!");
			System.err.println("***Please view Keeper Exception***");
			// e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to read from " + path + " on host " + host + " in order to append!");
			System.err.println("***The thread is interrupted, either before or during the activity***");
			// e.printStackTrace();
		}

		if (success) {
			success = false;

			byte[] data = concat(oldData, newData);

			try {
				update.update(path, data);

				success = true;

			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				System.err.println("Unable to append to " + path + " on host " + host + "!");
				System.err.println("***Please view Keeper Exception***");
				// e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.err.println("Unable to append to " + path + " on host " + host + "!");
				System.err.println("***The thread is interrupted, either before or during the activity***");
				// e.printStackTrace();
			}

			if (success)
				System.out.println("Successfully appended data to Znode on path " + path + " on host " + host + "!");
		}

	}

	private static void performDelete() {
		// TODO Auto-generated method stub

		System.out.println("Please specify Znode path which you are going to delete:");

		System.out.print("path: ");

		String path = console.next();

		boolean success = false;

		try {
			delete.delete(path);

			success = true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to delete " + path + " on host " + host + "!");
			System.err.println("***The thread is interrupted, either before or during the activity***");
			// e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to delete " + path + " on host " + host + "!");
			System.err.println("***Please view Keeper Exception***");
			// e.printStackTrace();
		}

		if (success)
			System.out.println("Successfully deleted Znode on path " + path + " on host " + host + "!");
	}

	private static void performClose() {
		// TODO Auto-generated method stub

		System.out.println("Closing connection on host " + host + "!");

		boolean success = false;

		try {
			success = true;

			connector.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to close the host " + host + "!");
			System.err.println("***The thread is interrupted, either before or during the activity***");
			// e.printStackTrace();
		}

		if (success)
			System.out.println("Successfully closed host " + host + "!");
	}

	private static byte[] concat(byte[] oldData, byte[] newData) {

		byte[] data = null;

		try {
			data = new byte[oldData.length + newData.length];

			int i;
			for (i = 0; i < oldData.length; i++) {
				data[i] = oldData[i];
			}

			for (int j = 0; j < newData.length; j++, i++) {
				data[i] = newData[j];
			}

		} catch (Exception e) {
			System.err.println("***Unable to concatinate old and new data***");
			e.printStackTrace();
		}

		return data;
	}

}
