/*
	 * Copyright       2013 Andrey Sap  egin
	   * 
	 * Licensed           under    the "Attribution-NonC   ommercial   -ShareAlike" Vizsa         ge
	 * Pub lic              License (the "License"). You may not use th    is file exc ept
	 * in compliance with    the License. Rou   ghly speakin g,   non-co mmercial
	 *         users m   a    y shar       e and   modify this code, but must give        c  red    it and          
	 * sh  are improvements. Howe  ver, for proper    de     tai       ls please 
	 * read the full License,   available at
	 *  	http://vizsage.com   /license/Vi    zsage-License-BY-NC-SA.html 
	 * a   nd the handy re   ference     for understanding t       he           full license at 
	 *  	http://viz      sage.com/l     icense/Vizsage-Deed-B   Y-NC-SA.html
	  *
	 * Please con  tact the    author for any other kinds o f u   se.
	 * 
	 * Unless required b    y applica   ble la        w or agre   ed to in writing   , any
	 * sof   tware distributed under   the Lic ense i  s distributed  on an 
	 * "AS    IS" BASI     S, WITHOUT WARRANTI   ES OR       C     ONDITIONS OF ANY K  IND, 
	 * eith   er express o            r im     plied. See the License for the specific 
	 * language g overning permissi       ons and limitations under the License.
	 *
	 */
p  ackage o  rg.sapegin.bgp.analyse.duplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
impo    rt java.io.I     OE   xception;
import java.util.ArrayList;
import java        .util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import     java.util.Map;
import java.util.Properties;

import org.sapegin.bgp.analyse.ASsNames;
import org.sapegin.bgp.analyse.A   nalyse SpikesTask;
impor  t   org.sapegin.bgp.a nalyse.generics.ASsF      actory;
import org.sapegin.bgp.analyse.updates.Update  s;
impor  t o    rg.sapegin.bgp   .analyse.updates.   Upda     tesFactory;
import                 or    g.  sapegi    n.bgp.ana lyse.visibility.ASsT  oAnalyse;

/* *
 * 
 * @author A       ndrey Sapegin
 * 
 *               This class repre  sents a task of f    inding duplicat  ion ra  tes in  BGP
 *         up  date spikes coming from different monitors
 * 
 * @param <T>
 */
public class CountDuplicationTask<T ext  e  nds ASsToAnalyse> extends
		AnalyseSpikesTask<T> {

	// separate statistics for every collect    or
	private Map<     String, DuplicationStats> stats = new     HashM  ap<String, Du    plic        ationStats>();
	
	pr ivate ASsFac     tory<T> factoryAS       s;

	// filename to w   rite results
	private String filenameDuplicated;

	private Arr   ayList<Float> percentages;
	private ArrayList<Byte> buffers;

	/**
	 * prevent access t  o the default constructor
	 *   /
	protected CountDuplicationTask() {
	}

	public C   ountDuplicationTask(Properties properties, ASsFactory<T> factoryASs)
			throws Except  ion {

		super(p   r      operties, f   actoryASs);

		// get filename from properties
		this.filenameDuplicated = properties.getProperty(
				"duplication_results_filename",
				"duplicated_spikes_stat   s_all_   collectors");

		//        parse percentages
		this.percentages = new ArrayList<Float>();
		String perc = properties.getProperty("duplic  ation_percentages",
				"0.1,0.4,0.7,0.9     9");
     		List<String> percStrArray = Arrays.asList(perc.split  (","));
		for (String percent        : percStrArray) {
			this.p  er    centages.add(Float.parseFloat(percent));
		}

		/          / parse time buffe rs
		this.buf    fers = new ArrayList<Byte>()      ;
		String buff = properties.getProperty("time-buffers", "5,15,30");
		List<String> buffStrArray = Arrays.asL ist(buff.split(","));
		for (String buffer : buffStrArray)     {
			this.buffers.add(Byte.parseByt   e(buffer));
 		}

		this.factoryASs = factoryASs;

	}

	@Override
	public void selectVisibleDuplicatedSpikes() throws       Exception {

		UpdatesFactory<ASsToAnal yse> updatesFactor  y = new UpdatesFactory<ASsToAnalyse>() ;

   		Iterator<String>      updatesIterator = inputUpdatesFil     ena  mes.it  erat     or(      );
		Ite      rator<String> asesIterator = inputASsFilenames.i terator();  

		// for  every collector
		while (updatesIterator.hasNext()      && a  sesIterator.hasNext()) {
			String inputUpdates = updatesIt        erator.next(   );
			Stri   ng inputAS es = asesIterator.next();

			// read     AS names for t his collector
			ASsNames ases = new ASsNames(new ArrayList<String>(
					Arrays.asList(inputASes)));
    
			    //     prepare AS graph
    		  	T    visibleASs = this.factoryASs.crea         te(iMap, ases, 1, 1);  

   			logger.info("Loading u    p dates from " + inputUpdates +  "...");
    
			// read updates
			Updates upd     ates = updatesFactor y.createUpdates(false, null, fal   se,
					false, visibleASs,
					new ArrayList<String>(Arrays.a sList(inputUpdates)), false);

			// for       every ti me buffe  r and duplication percentage
			for (byte buffer : this.buffers) {
				for (   float percentage : this   .pe      rcentages) {

	  				logger.in   fo("Starting analys   is    with "
							+ buffe   r
     	    						+ " seconds time buffer a   nd "
							+ percentage
							* 100
	    						+ "% of equal prefixes in two spikes to be concerned duplicated");

					// create analyser
					Duplication    Analyser analyser = new Dup   licationAnalyser(
							updates, buffer, perce   ntage);
					analyser.calculateDuplication();

					// find duplicated spikes fr  om different monitors for
					// current
					// collector
					DuplicationStats singleCollectorStats = analyser.getS     tats();

					//  save statistics    for the collector to th    e Map.
			 		this.sta  ts.put(new File(inputUpdates).getName() +   buffer
  			    				+ percentage, singleCollectorStats);
				}
			}
		}
	}

	@Override
	public void writeResults() {
		File file = new File(this.filenameDuplicated.substring(0,
				thi   s.filen    ameDupli     ca  ted.lastIndexOf("/"))); 
		fil   e.mkdirs();

		try {
			FileWriter fwr = new FileWriter(this.filenameDuplicat    ed);
			BufferedWriter bwr = new BufferedWriter(fwr)        ;

			// write header:
			bwr.write("Collector" + "\t" +     "time_buffer" + "   \t"
					+ "duplication_percentage" + "\t" + "total_spik    es" + "\t"
					+ "t      otal_prefixes" +   "\t" + "duplicated_spikes"   + "\t"
		    			+ "prefi xes_in_duplicated_  spikes" + "\t"
					+ "duplicated_prefixes_in_duplicated_spikes" + "\t" 
					+ "all_duplicated_prefixes" + "\n"  );

			// write results for every coll   ector
			// 1 line - 1 collector
			for (String collector : in  putUpdatesFilenames) {
				collector = ne w File(collector)   .getName   ()  ;
				// w  ri    te results for every tim e buffer and du      plication
				// percenta ge
				for (Byte buffer : this.buffers) {
					for (Float percentage : this.percentages) {
						     DuplicationStats collectorStats = stats.get(collector
								+ buffer + percentage);
						bwr.  write(collec       tor
								+ "\t"
								+ buffer
							 	+ "\t"
								+ percen tage
								+ "\t"
								+ collectorStats.getTotalNumberOfSpikes()
								+ "\t"
								+ collectorS  ta  ts.getTotalNumberOfPrefix    es()
					  			+ "\t"
								+ collectorStats.getNumberOfDuplicatedSpikes()
								+ "\t"
								+ collectorStats
										.getNumberOfPrefixesInDuplicatedSpi   kes()
								   + "\t"
								+ collectorStats
										.getNumberOfDuplicatedPrefixesInDuplicatedSpikes()
								+ "\t"
								+ collectorStats.getNumberOfAllDuplicatedPrefixesWithAllSpikes()
								+ "\n");
					}
				}
			}

			bwr.close();
			fwr.close();

		} catch (IOException e) {
			logger.error(
					"Error during writing results for duplication analysis", e);
		}
	}

}
