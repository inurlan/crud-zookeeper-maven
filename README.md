# CRUD ZooKeeper Maven

## What is ZooKeeper?

ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services. All of these kinds of services are used in some form or another by distributed applications. Each time they are implemented there is a lot of work that goes into fixing the bugs and race conditions that are inevitable. Because of the difficulty of implementing these kinds of services, applications initially usually skimp on them ,which make them brittle in the presence of change and difficult to manage. Even when done correctly, different implementations of these services lead to management complexity when the applications are deployed.

## How to start?

In order to start use this project first you will need to have several servers or simply several independent machines. You can use several virtual operating systems as well. Then you will need to install Apache ZooKeeper to each of them separately.

## How to install Apache ZooKeeper?

First go to `http://www-us.apache.org/dist/zookeeper/` and move under "stable" directory. Here you need to copy link location of zookeeper tar file in my case it is `http://www-us.apache.org/dist/zookeeper/stable/zookeeper-3.4.10.tar.gz`. Open terminal of your machine where you are aimed to set up Apache ZooKeeper and execute the following command `wget http://www-us.apache.org/dist/zookeeper/stable/zookeeper-3.4.10.tar.gz`. It will install that tar file on your machine. Next untar installed file using `tar xvzf <whatever your tar file name>` command. Go to that directory and then move to the conf directory where you are going to find "zoo.cfg" file. You need to configure this file and add info about other your machines to the end of the file in this format: `server.<sequence number of you machine>=<name of your machine>:<peer-to-peer communication port number>:<peer-to-master connection port number>`. 

#### Example 
  server.1=some_machine_name_1:3000:3001  
  server.2=0.0.0.0:3000:3001   
  server.3=some_machine_name_3:3000:3001

In the example above I assume that there are 3 machines and I am currently configuring second one. Then you need to start Apache Zookeper. For this move to the bin directory which is under your tar directory that you have already installed. Execute the following sh file "zkServer.sh" via `zkServer.sh start` command on your terminal. You can stop it via `zkServer.sh stop` and view status via `zkServer.sh status` as well. Finally, you can remotely connect to your distributed cluster via crud-zookeeper-maven project and be sure that your machines will be sequentially consistent.

