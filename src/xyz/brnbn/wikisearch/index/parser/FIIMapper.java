package xyz.brnbn.wikisearch.index.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import xyz.brnbn.wikisearch.utils.*;



public class FIIMapper 
			extends Mapper<Object, Text, Text, Text> {
	
	@Override
	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {
		
		Porter pstemmer = new Porter();
		
		try {
			
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder =  dFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(
						new ByteArrayInputStream(value.toString().getBytes())));
			
			String docID = doc.getDocumentElement().
									getElementsByTagName("id").item(0).getTextContent();
			
			String doc_frq = docID + ",1";
			
			String text = doc.getDocumentElement().
								getElementsByTagName("text").item(0).getTextContent();
			
			StringTokenizer st = new StringTokenizer(text, "\n");
			
			
			while(st.hasMoreTokens()) {
				
				String line = st.nextToken();
				char sc = line.charAt(0);
				if (sc==' ' || sc=='*' | sc=='|' || sc=='{' ||
						sc=='}' || sc=='#' || sc=='=') {
					
					continue;
				}
				
				line = line.replaceAll("[^A-Za-z]", " ");
				
				line = line.toLowerCase();
				
				StringTokenizer wordst = new StringTokenizer(line, " ");
				String word;
				while(wordst.hasMoreTokens()) {
					
					word = pstemmer.stripAffixes(wordst.nextToken().trim());
					if (!word.isEmpty())
						context.write(new Text(word), new Text(doc_frq));
				}
			}
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
		}
	}

}
