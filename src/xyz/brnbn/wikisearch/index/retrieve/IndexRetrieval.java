package xyz.brnbn.wikisearch.index.retrieve;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import xyz.brnbn.wikisearch.index.hbase.setup.CreateHBaseTables;
import xyz.brnbn.wikisearch.utils.Porter;

public class IndexRetrieval {

	static Configuration conf = null;
	static HTable table_post = null;
	static HTable table_pagerank = null;
	static HTable table_docid_title = null;
	
	static {
		
		try {
			
			conf = HBaseConfiguration.create();
			table_post = new HTable(conf, CreateHBaseTables.HTABLE_POSTINGLIST);
			table_pagerank = new HTable(conf, CreateHBaseTables.HTABLE_PAGERANK);
			table_docid_title = new HTable(conf, CreateHBaseTables.HTABLE_DOCIDTITLE);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> retrieve(String query) throws IOException {
		
		ArrayList<String> result = new ArrayList<>();
		
		query = (new Porter()).stripAffixes(query);
		
		System.out.println("************ " + query);
		
		ArrayList<Document> docs = new ArrayList<>();
		Get get = new Get(Bytes.toBytes(query));
		Result rs = table_post.get(get);
		for(KeyValue kv : rs.raw()) {
			
			String family = new String(kv.getFamily());
			String docId = new String(kv.getQualifier());
			String tf = new String(kv.getValue());
			if (family.equals(CreateHBaseTables.COLFAMILY_DOCID_TF)) {
				
				Document doc = new Document();
				doc.setDocId(Integer.parseInt(docId));
				doc.setTf(Double.parseDouble(tf));
	/*			
				Get getPR = new Get(Bytes.toBytes(docId));
				Result rsPR = table_pagerank.get(getPR);
				for (KeyValue kvPR : rsPR.raw()) {
					
					String PR = new String(kvPR.getValue());
					doc.setPagerank(Double.parseDouble(PR));
				}
		*/		
				Get getT = new Get(Bytes.toBytes(docId));
				Result rsT = table_docid_title.get(getT);
				for (KeyValue kvT : rsT.raw()) {
					
					@SuppressWarnings("deprecation")
					String title = URLDecoder.decode(new String(kvT.getValue()));
					doc.setTitle(title);
				}
				docs.add(doc);
			}
	    }
		Collections.sort(docs);
		
		for (Document doc:docs) {
		
			System.out.println(doc.getTitle());// + " | " + doc.getTf() + " * " + doc.getPagerank() + " : " + String.valueOf(doc.getTf()*doc.getPagerank()));
			result.add(doc.getTitle());
		}
		
		return result;
	}
	
	public static void main(String args[]) throws IOException {
		
		retrieve("wedding");
	}
}
