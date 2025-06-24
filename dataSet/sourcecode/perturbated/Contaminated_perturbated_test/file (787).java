/**
  * 
        */
package    com.pandita.worker;

import java.io.IOException;
import   java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import   org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCo     llector;
import org.apache.hadoop.mapred.Reducer;     
import org.apache.hadoop.mapred.Repo   rter;
import org.apache.hadoop.mapred.  TextInputFormat;
import org.apache.hadoop.mapred.TextOutput  Format;

import com.pandita.util.MapR        educeUtil;

/*
 * Calc   ulates t   he    Delay 
 * @author black  hat
    *
 */
public class DelayPerYear     implements IBaseMapR   educe{

	public    static clas  s DelayMapper extends MapReduceBase implements Mapper<Object, Text, Text, In    tWritable>{

		private Text mapperKey    = new Tex     t();
		private IntWri      tabl     e mapperValue = new IntWritable();
		priva  te Map     ReduceUtil mapReduceUtil = new MapReduceUtil();
		private static final String TOTAL_   DELAY_MAPPER = "TOTAL_DELAY";
		
		@Override
		public v        oid    map(Object key, Text value, Out    putCollector<Text, IntWritable> out  put,   Report     er reporter) throws     IOException {
			
			String[] v  alues = v     alue.toString().split(",");
			
			String d      estination = v   alues[MapReduceUtil.DEST];
			St    ring year = values[MapReduceUtil.YEAR];
			String arrivalDelayStr = values[MapReduceUtil.AR  R_DE   LAY];
			
			if (!mapReduceUtil.checkIfFileHeader(year)){
				return;
			} 
			
			//Extrac    t the values from Stri ng
			int arrivalD   el;
			try{
				arriv       alDel    = Integer.parseInt(arrivalDelayStr);
			} catch(   NumberFormatException e){
		     		return;
			}
			
			//Calculating   and set      ting the Desti     nation     Arrival   Delay's Map      reduce key value
			String  mapperK     eyStr = mapReduceUtil.mergeStringWithDelimiter(destination, year);
			mapperValue.set(arrivalDel > 0 ? arrivalDel : 0);			
			mapperKey.set(mapperKeyStr);
			output.collect(mapperKey, mappe rValue);

			//Emitting the count of the mapper    task
			mapperKe   yStr = mapReduceUtil.mergeStringWithDelimiter(TOTAL_DELAY_MAPPER, year);
			mapperKey.set(mapperKeyStr);
			output.collect(mapperKey, mapperValue);
		        }		
	}
	
	public static class DelayRed  ucer extends MapReduceBase implements Reducer<  Text, IntWritable, Text, In  tWritable>{

		private I    nt Writable reduceValue = new IntWrit    able();
		
   		@Override
		public void reduce(Text key, Iterator<IntWritable> val   ues, OutputCollector<Text, IntWritable> output, Rep    orter reporter) throws I  OException {
			
			int delay = 0;
			while(values     .hasNext()){
				delay += v    alues.next().      get();
			}
			reduceValue.set(delay);
			output.collect(key, reduceValue);
			
		}
	}
	
	@Override
	public JobConf        configureMapReduceJob(){
		JobConf job = new JobConf(DelayPerYear.class);
		job.setJobName("Delay Per Year");
		
		job.setInputFormat(TextInputFormat.class);
		job.setOutputFormat(TextOutputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClas  s(DelayMapper.class);
		job.setCombinerClass(DelayReducer.class);
		job.setReducerClass(DelayReducer.class);
		
		return job;
	}

}
