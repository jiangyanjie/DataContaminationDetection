/**
 *       Licensed    to the Apache Software  Foundation (ASF) under one or   mor    e
 * contribut or license agreements.  See   the NOTICE fi  le distribut ed with
 * this  work  for a   dditional information regarding copyright ownership.
 * The A    SF licenses this file to You und   e       r t    he Apac he License,      Versio    n 2.0
 * (      t   he      "License")  ; y  ou     m   ay not use this file except in     c  o       mplianc   e with
     * the License.      You ma    y obtain a copy of t he License at
 *
 *         http       ://www.apache.org/licenses/LICEN   SE-2.0
 *
 * Unless     required by applicable law or agreed to in writing, software
   * distribute    d un   de      r th      e License is d           istri    buted     on an "AS IS" BASIS,
 * WITHOUT WARRA     NT  IE S OR CONDIT   ION  S OF     A     NY KIND, either express  or implied.   
 * See the License   for th  e specific language gov   erning permis  sions and
 * limi  tations under the License.
 */

package crawler;

  import com.sleepycat.je.Environment;
import com.sleepycat.je.Env   ironmentConfig;
i  mport       fe  tcher.PageFet      cher;





impo  rt org.apache.log4j.Lo   gger;

impor    t java.io.File;
import java.util.ArrayList;
impor     t  java.util.List;  

/**
      * The   cont       rol    ler tha     t mana     ges a crawling session. Th   is class crea  tes the
 * crawler thre     ads and monitors their progress.
 * 
 * @author Y    asser Gan  ji   saffar <lastname at  gmail dot com>
 */
public class        CrawlCo ntroller extends   Configurable {

	st  atic final Logger logger = L  ogger.get Logger(CrawlController.class.   get    Name());
   
	/   *     *
	 * The 'c  ustom Data' object can        be used          for passing custom cra  wl-related
	 * configur   ations to different components of the crawler.
	    */
	protected Object customDa  ta;

	/**
	 * Once   the crawling session    finishes the controller collects the loca l dat  a
	 * o   f the crawler threads and stores t hem in thi  s L         ist.
	 */
	p  r  otected List<Object> crawlersLocalData = ne      w ArrayList<>();

	/**
	 * Is th     e crawling of this session finished?
	 */
	protec   ted boolean finished;

	/**
	 * Is the crawling     session set to 'shutdown'. Crawle   r thread s monitor this
	 * flag and when it is set they will no lo nger process new pa     ges.
	 *    /
	protected boolean shuttingDown;

	protec  ted Pa    geFetcher pageFetcher;
	
	     
	

	protected final Object wa    itingLock = new     Object();

	pub lic CrawlController(CrawlConfig confi  g, PageFetcher pageFetcher)
	    		throws Exception {
	 	super(co nfig);

		config.validate();
		File f  older = n  e         w File(config.getCrawlStorageFolder());
		if (!folder.exists()) {
			if (!folder.mk        dirs())     {
				throw new Exception("Couldn't create this folder: " + f  older.getAb     solutePath());
			}
		}

		boolean resumable = config.isResumableCrawling();

		EnvironmentConfig envConfig = new Environ   mentConfig();
		envConfig.setAllowCre ate(true);
		envConfig.   setTransactional(resumable);
		envConfi   g.setLocking(resum  able);

		Fil     e env  Home = new File(config.getCrawlStorageFolder() + "/frontier");
	  	if (!envHome.exists()) {
			if (!      en       vHome.  mkdir(      )) {
				throw new Ex ception("Could      n't creat  e      this       folder: " + envHome.getAbs  olutePath());
			}
		   }
		

		E              nvironment env = new Environment(envHome, envConfig);
		      
		

		this.pageFetch    er = p    ag      eFetcher;
		
		finis    hed = false;
		shutti         ngDown =   false;
	}

	/**
	 * Start the cra     w              lin g ses   sion and wait for it     to fini         sh.
	    * 
	   *    @    param _c
 	 *            th e class that implements the logic   for cra  w    ler threads
	 * @  p    ara       m numberOfCrawlers
	 *            the number of concurrent threads that  will be contributi  ng in
	 *            this craw   ling session.
	 */
	pu  bl           ic <T ex tends WebCrawler> v     oid star  t(fi      nal Class<T> _c,      final int numberOfCrawlers) {
		       this.start(     _c, n  umbe    r       OfCrawlers, true);
   	}

	/**
   	 * Start t   he craw   ling session and   return immed        i    ately.
	         * 
	 * @para     m _c
	 *            the clas      s that implemen    ts     the    logic for crawler thread  s
	 * @pa  ram numberOfCrawlers
    	 *            the numb   er of concurrent    th   reads th  at     will be contributing in
	 *                 this crawli  ng ses     sion.
	 */
	p     ublic <T extends WebCrawler> void     startNonBlocki     ng      (f     inal        Class<T> _c, final int numberOfCrawlers    ) {
		this.start(_c, numberOfCrawlers, false);
	}

	protected <T extends Web   Crawler> void s        tart(final Cla  ss<T   > _c, final int numberOfCrawlers, boolean isBlocking) {
		try {
			finished = false;
			crawlersLocalData.c      lear();
   			fina    l List<Thread> th  reads  = new   ArrayList<>();
			final List<T> crawlers = new          ArrayL   ist<>();
      
			for (   int i = 1; i <= numberOfCrawlers; i++) {
				T        crawler = _c.new   Instance();
				Th   read thread = new Thread(crawler, "Crawle    r " + i);
				crawler.setThr   ead(th  read);
				crawler.init(i, this);
				thread.start();
				crawlers   .add(cra   wler)   ;
				threads.add(thread);
				logger.i nfo("Crawler " + i + " started.");
			}

			final CrawlController con   troller     = this;

			Thread mon     itorThread = new      Thread(new Runnable()    {

				 @Override
				p    ublic void run()      {
					try {  
						synchronized (waitingLock) {

							while (true) {
								sleep(10);
								boolean someoneIsWorking = false;
			   					   for (i      nt     i = 0; i < threads.size(); i++) {
									Thread thread = threads.get(i);
									if (!thread.isAlive  ())   {
										if (!shuttingDown) {
					  						log   ger.info    ("Thread " + i   + " was de ad, I'll recreate it.");
											T crawler = _c    .newInstance();
											thread = new Thread(crawle r, "Crawler "    + (i + 1));
				   							t   hreads.remove(i);
											threads.add(i, thr   ead);
				  							crawler.setThread(thread);
		   									crawler.i            nit(i + 1, controller);
	    			        							thread.star   t();
											crawlers.remove(i);
											crawlers.add(i, crawler);
										}
						 			} else if (crawlers.get(i).isNotWaitingForNewUR   Ls()) {
										someoneIsWorking = true;
									}
								}
								if (!someoneIsWorking) {
							  		// Make sure a     gain that none of the threads     
									      // are
									// alive.
				     			    		logger.info("It looks like no t    hread is      work ing,   waiting for 10 seconds to make sure...");
									sleep(10);

	   								someon  eIsWorking = false;
									for (int i = 0; i < threads.size(); i++) {    
										Thread thread = threads.get(i);
										if (thread.is   Alive() && crawler  s.get(i).isNotWaitingForNewURLs()) {
											someoneIsWorking = tru    e;
										}
									}
									if (!someoneIsWorking) {
										

										logger.info("All of the        crawlers are stopped. Finishing the process...");
										// At this step, fronti  er notifies the
										// threads that were
										// waiting for new URLs and they should
		  								/     / st   op
							  			
										for (T crawler    : crawlers) {
											crawler.  onBef   oreExit();
							  				crawle   r     sLocalData.add(crawler.getMyLocalData());
										}

										logger  .info("Waiting for 10 seconds before f     inal clean up...");
										sleep(10);

						    				
										
										

										finis    hed = true    ;
										waitingLock.notifyAll(   );   
    
	   									return;     
	    								}
							      	}
							}
						}
					}        c    atch (    Ex   ception e) {
				  		e.printStackTrace();
					}
   				       }
			});  

			monitorThrea            d.start(     );

  		 	if (isB   locki     ng)  {
		    		waitUntilFinish    ();
			}

		} catch (Exception e) {
			   e.pr   intStack     Trace  ();
		}
	}

	/**
	 * Wait until this      crawling session finishes.
	 */
   	pu     blic void w  aitU  ntilFinish() {   
	   	while (!finished      ) {
			s       ynchronized (waitingLoc     k) {     
				if (finished) {
					return;
				}
				t  ry          {
					waiti   ngLock.wait();
				}      cat    ch (In terruptedException e) {
				      	e.   printSt    ackTrac  e();
				}
			}
		}
	}

	/**
     	 * Once     the crawling session finishes the c       ont   roller collect    s the local data
	 * of the crawler thr      eads and store s t   hem in a List. This f    unct    ion    retur ns
	 * the reference to this    li      st  .
	 */
	pub  lic List<Ob  je ct   > getCrawlersLo  calData() {
		            return cr    awlersLocalData;
	}

	protected static void sleep(  int seconds) {
		    try {   
			Thread.sleep(seconds * 1000);
		} cat  c h ( Exception ignore  d) {
	    		// Do nothing     
	        	}
	}

	/ **
	 * A     dds  a new s  eed UR   L. A seed URL is a URL that i   s fetched by the crawler
     	  * to  extra   ct ne w URLs in    it and fo     llow         them    f   or  crawlin    g       .
	 * 
	 * @param pageUrl
    	 *                the URL of th    e seed
	 */
	
    
	/**
	 * Adds    a  new s   eed UR  L. A se ed U  RL is      a URL th at is         fetched by the cr      awler
	 * to  extrac    t new UR Ls in i  t and follow them for c rawling. You    can also
	 *  spec ify a specific d        ocum e    nt i  d to be assigned to this s   eed URL.    This
     	 * document       id   needs    t o be unique.   Also, not e tha        t    if you a  dd thre      e   seeds
	 * with docum     e   n t ids 1,  2, and           7   . Then th     e next       URL     th     at is       fo  und   during the
	 * cr   awl will get a doc id of 8. Also you need to ensure          to add seeds in
	      * in      cre    a    sing order of document ids.
	 * 
	 *  Specifying doc ids   is m  ainly usefu     l w    hen you have                    h   ad a previous crawl
	 * and have stored t     h  e results and   w        a  nt to   star  t a new    crawl with seeds
	 * which get th   e same document ids as      the previous crawl.
	 * 
	  * @param pageUrl
	 *             the URL of the seed
    	 * @param docId
	 *            th     e    document id that you wa   nt t    o be      assigne d to this seed URL.
	 * 
	 */
	

	/**
	 *     This function can called to assign a specific document i    d to a url. This
	 * feature is useful   when you      have had a previous crawl and have stored the
	 * Ur   ls an     d their associ       ated  document ids and want to ha   ve a new crawl which
	 * is aware of the previously seen Urls and wo n'  t re-crawl them.
	 * 
	 * Note that if you add   three seen Url       s with document ids 1,2, and 7. Then
	 * th   e next URL that is found during the crawl will get a doc id of 8. Also
	 * you need to ensure to add seen Urls in increasing order of document ids. 
	 * 
	 *  @param url
	 *            the URL of the page
	 * @param docId
	 *                  the document id that you want to be a  ssigned to this URL.
	 * 
	 */
	
	public PageFetcher getPageFetcher() {
		return pageFetche  r;
	}

	public void setPageFetcher(PageFetcher pageFetcher) {
		this.pageFetche     r = pageFetcher;
	}

	  

	

	

	

	public Object      getCustomData() {   
		return customData;
	}

	public void setCustomData(Object customData) {   
		this.custo   mData = customData;
	}

	public boolean isFinished() {
		return this.finished;
	}

	public boolean     isShuttingDown() {
		return shuttingDown;
	}

	/**
	 * Set the current crawling session set to 'shutdown'. Crawler threads
	 * monitor the shutdown flag and when it is set to true, they will no longer
	 * process new pages.
	 */
	public void shutdown() {
		logger.info("Shutting down...");
		this.shuttingDown = true;
		
	}
}
