package org.hadoop.vimox.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Put;



public class HBaseInsert {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Configuration config = HBaseConfiguration.create();
		HTable table = new HTable(config,"user");
		Put put = new Put(Bytes.toBytes("rowkey1"));
		
		put.add(Bytes.toBytes("Id"),Bytes.toBytes("Person Id"),Bytes.toBytes("1001"));
		put.add(Bytes.toBytes("Id"),Bytes.toBytes("Person Name"),Bytes.toBytes("Vimox"));
		
		Put put1 = new Put(Bytes.toBytes("rowkey2"));
		put1.add(Bytes.toBytes("Id"),Bytes.toBytes("Person Id"),Bytes.toBytes("1002"));
		put1.add(Bytes.toBytes("Id"),Bytes.toBytes("Person Name"),Bytes.toBytes("Jay"));
		
		
		/*put.add(Bytes.toBytes("Id"),Bytes.toBytes("Person Id"),Bytes.toBytes("1002"));
		put.add(Bytes.toBytes("name"),Bytes.toBytes("Person Name"),Bytes.toBytes("jay"));
		*/
		table.put(put);
		
		Get get = new Get(Bytes.toBytes("rowkey1"));
		Result r= table.get(get);
		byte[] id = r.getValue(Bytes.toBytes("Id"), Bytes.toBytes("Person Id"));
		byte[] name = r.getValue(Bytes.toBytes("Id"), Bytes.toBytes("Person Name"));
		
		String id1 = Bytes.toString(id);
		String name1 = Bytes.toString(name);
		
		System.out.println("GET: " +"Id: "+ id1+"Name: "+name1);
		
		Scan s = new Scan();
		s.addColumn(Bytes.toBytes("Id"), Bytes.toBytes("Person Id"));
		s.addColumn(Bytes.toBytes("Id"), Bytes.toBytes("Person Name"));
		
		ResultScanner scanner = table.getScanner(s);

		try
		  {
		     for (Result rnext = scanner.next(); rnext != null; rnext = scanner.next())
		     {
		        System.out.println("Found row : " + rnext);
		     }
		  } finally
		    {
		       scanner.close();
		    }
	 
	}
}	
		

