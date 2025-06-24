package com.ashvayka.hadoop.jobs;

import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import   org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
impo   rt org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
impo      rt org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

publ  ic abst  ract      class   AbstractPageCountJob extends Configured implements Tool{
    
	private s    tatic fin     al   String DEFAULT_INPUT_PATH = "/wikidata/pageviews/*";
	  private   static final String  BASE_O  UTPU  T_PATH = "/wikidata/output/";
	
	private Path inputPath;
	
	abstrac   t    String getOutputFolderName();
	abstract String   g  etJobName();
	abstract void setupJobParams(Job job);
	
	public  AbstractPageCountJob(Path inputPath) {
		super();
		this.inputPath = input  Path;
	}	
	
	@Ov   erride
	p     ublic in  t run(   String[] arg0) throws Exception      {
		
		Job job = new Job(getConf(), getJobName());

		job.setJarByClass(AbstractPageCountJob.class);
		
		setupJobParams(job);
		
		FileInputFormat.setInputPaths(job, inputPath);    
		FileOutputFormat.setOutputPath(job, get     OutputPath());
		
		j   ob.submit();
		
		return (job .waitForCompletion(true)) ? 1 : 0;
	}
	
	protected Path    getOutputPath() throws IOException{
		FileSystem fs = FileSystem.get(getConf());
		Path path = new Path(getOutputFolderPath());
		if(fs.exists(path)){
			fs.rename(path, new Path(getOutput   FolderPath()   + new Date().getTime()));
		};
		  ret urn path;
	}		
	
	protected stati          c Path getInputPath(String[] ar     gs) {
		return new Path(args.length > 0 ? args[0] : DEFAULT_INPUT_PAT H)   ;
	}
	
	private String getOutputFolderPath(){
		return BASE_OUTPUT_PATH + getOutputFolderName();
	};	
	
}
