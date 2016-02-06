package xyz.brnbn.wikisearch.index.parser;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class OutGoingLinksMapper 
			extends Mapper<Object, Text, Text, Text> {
	
	@Override
	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {

		try {
			
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder =  dFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(
						new ByteArrayInputStream(value.toString().getBytes())));
			
			String docID = doc.getDocumentElement().
									getElementsByTagName("id").item(0).getTextContent();
			
			String text = doc.getDocumentElement().
								getElementsByTagName("text").item(0).getTextContent();
			
			Pattern p = Pattern.compile("\\[\\[(.*?)\\]\\]");
			Matcher m = p.matcher(text);

			String OGL = "";
			
			while(m.find()) {
			    @SuppressWarnings("deprecation")
				String OGTitle = URLEncoder.encode(m.group(1));
			    
			    Configuration conf = HBaseConfiguration.create();
	            HTable table = new HTable(conf, "word_title");
	                
	            Get get = new Get(Bytes.toBytes(OGTitle));
	            Result rs = table.get(get);
		           
	            String OGTitleID = "";
	            for(KeyValue kv : rs.raw()){
	        	   System.out.print(new String(kv.getRow()) + " " );
	        	   System.out.print(new String(kv.getFamily()) + ":" );
	        	   System.out.print(new String(kv.getQualifier()) + " " );
	        	   
	        	   OGTitleID = new String(kv.getValue());
	        	    
	        	   System.out.println(OGTitleID);
	            }
	            
	            OGL+=(OGTitleID+",");
			}
			OGL = OGL.substring(0, OGL.length()-1);
			
			System.out.println("for " + docID + " OGList " + OGL);
			
			context.write(new Text(docID), new Text(OGL));
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
	}
}
