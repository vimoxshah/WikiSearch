package org.hadoop.vimox.hbase;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;



import java.io.IOException;

public class HBaseCreateTable {

	public static void main(String args[]) throws IOException{

		HBaseConfiguration hconfig = new HBaseConfiguration(new Configuration());
		
		HTableDescriptor htable = new HTableDescriptor("index");
		htable.addFamily(new HColumnDescriptor("postinglist1"));
		htable.addFamily(new HColumnDescriptor("postinglist2"));
		
		System.out.println("Conecting..");
		
		HBaseAdmin admin = new HBaseAdmin(hconfig);
		System.out.println( "Creating Table..." );
		admin.createTable(htable) ;
		System.out.println("Done!");
		
		
	}
}
