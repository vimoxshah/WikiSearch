package xyz.brnbn.wikisearch.index.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class TitleTitleIDMapper 
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
			
			String docTitle = doc.getDocumentElement().
								getElementsByTagName("title").item(0).getTextContent();
		
			context.write(new Text(docTitle), new Text(docID));
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
		}
	}

}
