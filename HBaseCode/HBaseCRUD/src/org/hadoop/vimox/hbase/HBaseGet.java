package org.hadoop.vimox.hbase;

import java.util.Date;
import java.util.Timer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseGet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Configuration conf = HBaseConfiguration.create();
            HTable table = new HTable(conf, "index");
           Scan s = new Scan();
            ResultScanner ss = table.getScanner(s);
       /*     for(Result r:ss){
                for(KeyValue kv : r.raw()){
                   System.out.print(new String(kv.getRow()) + " ");
                   System.out.print(new String(kv.getFamily()) + ":");
                   System.out.print(new String(kv.getQualifier()) + " ");
                   System.out.print(kv.getTimestamp() + " ");
                   System.out.println(new String(kv.getValue()));
                }
            }*/
            
           
           Date d =new Date();
           long st = d.getTime();
            Get get = new Get(Bytes.toBytes("bigdata"));
            
            Result rs = table.get(get);
            for(KeyValue kv : rs.raw()){
                System.out.print(new String(kv.getRow()) + " " );
                System.out.print(new String(kv.getFamily()) + ":" );
               // System.out.print(new String(kv.getQualifier()) + " " );
                //System.out.print(kv.getTimestamp() + " " );
                System.out.println(new String(kv.getValue()));
            }
            System.out.println(d.getTime()-st);
        }
		catch (Exception e){
           e.printStackTrace();
       }
	}

}
