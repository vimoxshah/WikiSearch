package org.hbase.vimox.title.id;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HbaseBulkLoadMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, KeyValue> {
    private String hbaseTable;	
    private String dataSeperator;
    private String columnFamily1;
    private String columnFamily2;
    private ImmutableBytesWritable hbaseTableName;
    KeyValue kv;

    public void setup(Context context) {
        Configuration configuration = context.getConfiguration();		
        hbaseTable = configuration.get("hbase.table.name");		
        dataSeperator = configuration.get("data.seperator");		
        columnFamily1 = configuration.get("COLUMN_FAMILY_1");		
        	
        hbaseTableName = new ImmutableBytesWritable(Bytes.toBytes(hbaseTable));		
    }

    public void map(LongWritable key, Text value, Context context)throws IOException,InterruptedException {
        try {		
            String[] values = value.toString().split(dataSeperator);
            
          
            String rowKey = values[0];			
        
            StringTokenizer str = new StringTokenizer(value.toString(),",");
        	//String freq  = values[values.length - 1];
        	//put.add(Bytes.toBytes(columnFamily1), Bytes.toBytes(rowKey), Bytes.toBytes(freq));
        	
        	if(str.hasMoreTokens()){
        			
          	for(int i=1;i<=values.length;i++){
           		
          		kv = new KeyValue(rowKey.getBytes(), columnFamily1.getBytes(), values[i].getBytes(), values[i].getBytes());
          
            	 //put.add(Bytes.toBytes(columnFamily1), Bytes.toBytes(values[i]),(values[i+1]).getBytes());
            	 
           //		 i++;
           		
         	
            hbaseTableName.set(rowKey.getBytes());
           	context.write(new ImmutableBytesWritable(rowKey.getBytes()) , kv);			
             }
            }
            
        } catch(Exception exception) {			
            exception.printStackTrace();			
        }
    }

}