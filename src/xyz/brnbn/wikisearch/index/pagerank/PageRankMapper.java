package xyz.brnbn.wikisearch.index.pagerank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class PageRankMapper 
		extends Mapper<Object, Text, IntWritable, DoubleWritable> {
	
	public static double NODOC = 6262;
	public static double INITIALRANK = 1000.0/NODOC;
	
	@Override
	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException{
		
		ArrayList<Integer> OGLinks = new ArrayList<Integer>();
		StringTokenizer st = new StringTokenizer(value.toString(), ",");
		
		Integer c_doc = Integer.parseInt(st.nextToken());
		
		while(st.hasMoreElements()) {
			
			System.out.println("hi");
			OGLinks.add(Integer.parseInt(st.nextToken()));
		}
		
		if (OGLinks.isEmpty()) {
			
			System.out.println("Tell Sagar to handle zero outgoing exception !!!!!");
			System.exit(0);
		}
		
		double pr_og = INITIALRANK/OGLinks.size();
		
		IntWritable ogIDIW = new IntWritable();
		DoubleWritable add_pr = new DoubleWritable(); 
		
		for (Integer ogID : OGLinks) {
			
			ogIDIW.set(ogID);
			add_pr.set(pr_og);
			
			System.out.println(ogID + " gets " + pr_og);
			
			context.write(ogIDIW, add_pr);
		}
		ogIDIW.set(c_doc);
		add_pr.set(0.0);
		context.write(ogIDIW, add_pr);
	}
} 
