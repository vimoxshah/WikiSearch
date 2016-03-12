package xyz.brnbn.wikisearch.index.hbase.setup;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class CreateHBaseTables {

	final static String HTABLE_POSTINGLIST = "post_idf_tf";
	final static String HTABLE_PAGERANK = "pagerank";
	final static String HTABLE_DOCWORDCOUNT = "doc_word_count";
	final static String HTABLE_DOCIDTITLE = "docid_title";
	final static String HTABLE_TITLEDOCID = "title_docid";
	
	public static void main(String[] args) 
			throws IOException {

		@SuppressWarnings("deprecation")
		HBaseConfiguration hconfig = new HBaseConfiguration(new Configuration());
		HTableDescriptor htable = null;
		
		System.out.println("Conecting to HBase..");
		HBaseAdmin admin = new HBaseAdmin(hconfig);
		
		/************************************************************************
		 * creating PostingList Tf_Idf table                                    *
		 ************************************************************************/
		htable = new HTableDescriptor(HTABLE_POSTINGLIST);
		htable.addFamily(new HColumnDescriptor("IDF_COLFAMILY"));
		htable.addFamily(new HColumnDescriptor("DOCID_TF_COLFAMILY"));
		
		System.out.printf( "Creating Table(%s)...\n", HTABLE_POSTINGLIST );
		admin.createTable(htable) ;
		System.out.println("Done " + HTABLE_POSTINGLIST);
		
		/************************************************************************
		 * creating Document PageRank table                                     *
		 ************************************************************************/
		htable = new HTableDescriptor(HTABLE_PAGERANK);
		htable.addFamily(new HColumnDescriptor("RANK_COLFAMILY"));
		
		System.out.printf("Creating Table(%s)...\n", HTABLE_PAGERANK);
		admin.createTable(htable) ;
		System.out.println("Done " + HTABLE_PAGERANK);
		
		/************************************************************************
		 * creating Document Word Count table                                   *
		 ************************************************************************/
		htable = new HTableDescriptor(HTABLE_DOCWORDCOUNT);
		htable.addFamily(new HColumnDescriptor("WORDCOUNT_COLFAMILY"));
		
		System.out.printf("Creating Table(%s)...\n", HTABLE_DOCWORDCOUNT);
		admin.createTable(htable) ;
		System.out.println("Done " + HTABLE_DOCWORDCOUNT);
		
		/************************************************************************
		 * creating Document ID Title table                                     *
		 ************************************************************************/
		htable = new HTableDescriptor(HTABLE_DOCIDTITLE);
		htable.addFamily(new HColumnDescriptor("TITLE_COLFAMILY"));
		
		System.out.printf("Creating Table(%s)...\n", HTABLE_DOCIDTITLE);
		admin.createTable(htable) ;
		System.out.println("Done " + HTABLE_DOCIDTITLE);
	
		/************************************************************************
		 * creating Title Document Id table                                     *
		 ************************************************************************/
		htable = new HTableDescriptor(HTABLE_TITLEDOCID);
		htable.addFamily(new HColumnDescriptor("DOCID_COLFAMILY"));
		
		System.out.printf("Creating Table(%s)...\n", HTABLE_TITLEDOCID);
		admin.createTable(htable) ;
		System.out.println("Done " + HTABLE_TITLEDOCID);

		System.out.println("Succesfully created all tables!!");
	}
}
