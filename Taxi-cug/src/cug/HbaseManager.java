package cug;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class HbaseManager {
	public static Admin admin = null;
	public static FileSystem fs = null;
	public void createAdmin(String rootdir,String zookeeper) throws IOException{
		Configuration conf = HBaseConfiguration.create();
		conf.addResource(new Path("/home/lin/Desktop/testHBase/hadoop-2.6.0/etc/hadoop/hdfs-site.xml"));
		conf.addResource(new Path("/home/lin/Desktop/testHBase/hadoop-2.6.0/etc/hadoop/core-site.xml"));
		conf.addResource(new Path("/home/lin/Desktop/testHBase/hadoop-2.6.0/etc/hadoop/mapred-site.xml"));
		conf.addResource(new Path("/home/lin/Desktop/testHBase/hadoop-2.6.0/etc/hadoop/yarn-site.xml"));
		fs = FileSystem.get(conf);
		conf.set("hbase.rootdir", rootdir);
		conf.set("hbase.zookeeper.quorum", zookeeper);
		conf.set("hbase.zookeeper.property.clientPort","2181");
		Connection connect= ConnectionFactory.createConnection(conf);
		admin = connect.getAdmin();
	}
}
