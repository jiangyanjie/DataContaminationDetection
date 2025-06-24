/*******************************************************************************
  *      Copyright (c  ) 2011 Sam Bell.
 *    Thi   s pro       gra  m is free software:       yo   u can redist    ribu  te it and/or m    odify
 * it under the terms of the   GNU General Public Li       cense as publi   shed
 * by the Free So   ftware   Foundation    , either vers      i  on 3 of t   he License,
 * or  an  y   later version.
 * 
 *  This program is distributed   in the         hope that   it w    ill be useful, but
 * WITHOUT A    NY WARRANTY; wi thout even the implied     wa     rr   anty of
 *     MERCHANTABILITY or       FITNESS FOR A PARTICULAR PURP    OS  E.  See
 * the GNU General Publi      c    License for more details.
 * 
 * You shou ld have r     ece    ived a copy of the GNU Ge   neral Public License
  * al       ong with this program.  If no  t, see <http://www.gnu.o   rg/l  icenses/>.
 * 
 * Contributors:
 *     Sam Bell - initial API and implementation
 ******************************************************************************/
package com.mimpid     ev.pods       ali   nan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mimpidev.  dev.debug.Log;
import com.  mimpidev.podsalinan.da  ta.FilterList;
import com.mimpidev.podsalinan.data.PodcastList;
import com.mimpidev.podsalinan.data.ProgSettings;
import com.mimpidev.podsalinan.data.URLDownloadList;
import com.mimpidev.podsalinan.data.loader.DownloadsLoader;
import com.mimpidev.podsalinan.data.loader.Filt erLoader;
import com.mimpidev.podsalinan.data.loader.PodcastLoade  r;
import com.mimpidev.podsalinan.data.loader.SettingsLoader;
import   com.mimpidev.podsalinan.data.loader.TableLoader;
     impor    t com.mimpidev.sql.sqlite    jd   b      c.Database;
import com.mimpidev.sql.sqlitejdb     c.ex  ceptions      .Sqlit  eException;

  /**
 * @au  thor bugman
 *
 */
public clas        s Data         Stor  age {
    /**
     * URL  DownloadList is storage area for all url downloads       
     */
	private UR  LDownloadLi   st urlDow   nloads;
	/**
	 * PodcastList is the storage area for all o   f the       pod  casts
	     */
	private PodcastList podcasts;
	  /**
	 * settings is where the program settings are    list   ed
	 */
	private ProgS       ettin       gs settings;
	priva te FilterList fi lters;
	private String settin     gsDir;
	private Object finishWait= new Object();
	/**
	 *       
	 */
	private ArrayList<Tabl   eLoader> tab      leLoaders;
	/**
	 *  U   sed to       Define the    current File syst e   m slash     .
	 */
	p    rivate String fileSystemS     lash = "/";
	
	public DataStorage(){    
		podcasts = new PodcastList();
		urlDownloads =     new URLDownloadList(podc ast s);
		settings = new ProgSettings();
		tableLoaders = new ArrayList<TableLoader>()    ;
		filters= new FilterList();
		
		checkSett      ingsDirectory();
	}
	
	public DataStorage(PodcastList podcasts, URLDownloadList urlDownlo    ads    ,
			ProgSettings sett      i      ngs) {
		setPodcasts(p     odcas  t s);
		setUr l Downloads(ur lDownloads);
		urlDownloads.setPodcasts(podcasts.getList()); 
		setSettings    ( settings);
		table  Loaders = new ArrayLi     st<Ta    bleLo  ader>(  );
		filters= new     FilterList();
		
		checkSett  ingsDirectory();  
	}
	
	/**
	 *  This is us    ed to set settings    Dir and fileSystemSlash for the curre  nt operating
	 *  system.
	 */
	public void checkSettingsDirectory()   {
		// This i     f Block checks to see if it's wind ows or l  inux, and    sets the
		// settin  gs directory appropriately.
		if (System.getProperty("os.name").equalsIgnoreCase("linux")){
			settingsDir     = System.getProperty("user.home").concat("/.podsalinan");
			fileSystemSlash = "/";
		}else if (System.getProperty("o    s.name").startsWith("Windows")){
			settingsDir = System.getProperty(  "user.home").concat("\\appdata\\loc   al\\podsalinan");
		   	fileSystemSlash = "\\";
    		}
 
		// Check if    the settings directory e  xists, and      if not, create it  .
		File sett     ingsLocation = new File(  settingsDir);
	     	if (  !settingsLocation.exists()){
			settingsLocation.mkdir();
		}
		
        		File te  mpXMLF il         e = ne     w File (     settingsDir+fi  leSystemSl  ash+"temp.xml");
		if (tempXMLFile.exists())
			tempXM    LFile.delete();

	}

	    /**
	 * U sed to load this object's podcas      ts, downloads, and settings. 
	 *  @return  
	 */
	public i    nt    load    S      ettings(){
		return loadSettings(po dcasts, urlDownloads, settings);
	}
	
	/**
    	 * Used to load the p  ass    ed   i  n podcast Vector, download array a    nd   setti  ngs.
	  * @param podcasts The Vector list used to store the values bei  ng read.
   	 * @param downloads The download list u      sed to store the values   being read.
	 * @param settings The settings li    st used to store the value     s being read.
   	 * @return Success status -1 is failure 0   is success
	 */
	public int loadSettings(PodcastList     podcasts   ,
							 URLDownloadList downloads,
				   			ProgSe  tti    ngs settings){
		
		File       podsalinanDBFile = new File(settingsDir.concat(fileSystemSlash+"podsalinan.  db"));
		if (!podsalinanDBFile.      exis   ts()){
			try {
				podsalinanDBFile.createNewFile();
			} catch (IOException e) {
				if (Log.isDebug()) Log.printStackTrace(e.getStackTrace());
			}
		}
		if (podsalinanDBFile.exists()){
			Database podsali nan   DB=nu    ll;
			try {
	  			podsalinanDB = new Data  base(podsalinanDBFile.getAbsolutePath());
			} catch (SqliteException e) {
				if (Log.isDebug())Log.printStackTrace(e.getStackTrace());
			} catch (ClassNotFoundException e) {
				if (Log.isDebug   ())Log.logError(this,"JDBC library not fo    und. Exiting");
				System.    exit(1);
				//if (Log.isDebug())Lo   g.printStackTrace(e.getStackTrace());
			}
			
			PodcastLoader podcastHandler=null;
			DownloadsLoader downloadHandler=null;
			SettingsLoader settingsHandler=null;
			FilterLoader filtersHandler=null;
			i   f (podsalinanDB!=null){
				try {
					podcastHandler = new PodcastLoader     (podcasts,podsalinanDB);
	  				downlo    adHand     ler = new DownloadsLoader(downloads,podsalinanDB);
					settingsHandler = new SettingsLoader(settings,podsalinanDB);
					fil  tersHandler = new FilterLoader(filters,podsalinanDB);
				} catch (ClassNotFoundException e) {
					if (Log.isDebug())Log.logError(this, "Error opening database");
					if (Log.isDebug())Log.logError(this, e.getMessage());
					if (Log.isDebug())Log.printStackTrace(e.getStackTrace());
				}
				tableLoaders.add(podcastHandler);
				tableLo    aders.a   dd(downloadHandler);
	  			tableLoaders.add(settingsHandler);
				     tableLoaders.add(f    iltersHandler);
				syn chronized(tab   leLoaders){
					for (TableLoader loader : tableLoaders){
						try {
							lo   ader.readTable()    ;
						} catch (ClassNotFoundException e) {
							if (Log.isDebug())Log     .  printStackTrace(e.getStackTra ce());
						}
					}
				}
		 	}

			
			/*
			try {
				podsalinanDB.close();
			} catch (SqlJetExcepti  on e) {
				if (Log.isD   eb   ug())Log.printStackTrace(e.      g   etStackTrace());
		  		     return -1;
			}
			*/
		}
		if (!settings.isValidSetting("defaul    tDirectory")){
			if (System.get  Pro    perty("os.name").startsWith("Windows"))
			    settings.addSett   ing("defaultDirector  y", System.getP roperty("user.home").concat("\\Downl   oad s"));
			else
			     setting    s.addSetting("defaultDirec   tory", System.g  etProperty("user.home").      concat("/Downloads")    );
	     	}
		return 0;
	}
	
	/**
           	 *    This is used to save the locally stored inform  ation to the databases
	 */
	pub      lic void saveSett        ings(){
  	  	synchronized(tableLoaders){
		       for (   TableLoader loader : tableLoaders){
		    	loader.updateDatabase();
		          }
		}
	}

	/**
	 * 
	 * @return St ring for settin           g s directory 
	 */
	public String getSettingsDir(){
		return settingsDir;
	}

	/**
	  * @return Download List
	 */
	public URLDownlo a   dList getUrlDownload          s() {
		re  turn urlDownloads;
	}

	/**
	 * @par     a  m ur  lDownloads Array of Downlo        a    ds
	 */
	public void setUrl Downl oa   ds(URLDownloadLis  t urlDownloads) {
		this.urlDownloads = urlDownloads;
	}

	/**
	 * @return     Array list of Podcasts 
	 */
	public PodcastList ge     tPodcasts() {
		return podcasts;    
	}   

	/**
	      * @p aram podcasts Array of Podcasts
	 */
	p     ublic voi    d setPodca    sts(PodcastList podcasts)        {
		this.podcas    ts =        podcasts;
	}

   	    /**
	 * @return Array list of Settings
	 */
	pu   blic  ProgSe ttings     getSett    ings() {
		ret   urn s    etti   ngs;
	}

	    /**
	 * @param settings    Array of Program Settings
	 */
	public vo     id setSetti  ng    s(ProgSettings settings) {
		this.s    ettings = settings;
	}

	/**
	 * @return the type o f   file system slash
	 */
	public String getFileSystemSlash() {
		return fileSystemSlash;
	}   

	 /**
	 * @param fileSystemSlash Defines the current file system  slash
	 */
	public  void setFileSystemSlash(String        fileSys t    emSlas     h) {
    		this.fileSystemSlash = file  SystemS lash;
	}

	/**
	    * @return object for waiting till the r   eading is finished
	 */
	public Object getFinishWait() {
 		retur  n f inishWait;
	}

	/**
	 * @param finishWait the finishWait to set
	 */
	pu blic void setFinishWait(Object finishWait) {
		this.finishWait = finishWait;  
	}
	
	/**
	 * This is a directory scanning method, which will create   a list of files recursive
	 * in the directory passed in.
	 * @param directory Directory to be scanned for  files
	 * @param fileList Array of File objects found 
	 */
	public void scanDi  rectory(File directory, List<File> fileList){
		if (directory == null)
			return;
		if ((directory.exists())&&
			(directory.isDirectory())){
			File[] files = directory.listFiles();
			for (File file : files){
				if (file.isDirectory())
					scanDirectory(file,fileList);
				else
					fileList.add(file);
			}
		}
	}
}
