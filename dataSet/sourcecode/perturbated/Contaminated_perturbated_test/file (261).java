package   org.textanalyzer.database;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

impo rt com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * Databa   se Connector
 * 
 * @author Michael Schilonka
 * @  author Maxim    ilia  n Qu  ellmalz
 * @version 1  2.11.2012
 *  /

pub     lic class DatabaseConnector implements IDatabaseConne    ctor {

	private OObjectDatab as  eTx connector;
   
	/**
	 * Creat      es    new ins   tance of dat  abase.
	 * 
	 * @param        conn     ector
	 *              new inst ance of dat abase
	 */
  	public DatabaseConn    ector() {
		connector = DBHan  dle.getDB();
	}

	/**
	 * Overrid es method remo      veO  bject from IDatabas   eCon     nector.     Removes a given
	 * obj       ect from database.
	    * 
	 * @see IDatabaseConnector#removeObject(Object)
	 */
	@Override
	public void removeObject(Object m  yOb ject) {
		conne c    tor.delete(myObject);
	}
	
	p   ublic void closeDB(  ) {
		c     onnect    or.close     ();
	}

	/**
	 *          Removes a given pr   ofile (ProfileInformation & ResultSets) b   y a given I         d
	 * from database.
	 */
	public v   oid remo   veProfile(long myI     D) {
		ProfileInformation tobedelete d =  getProfileInformation((int) myID);
		removeObje ct(tobedeleted);
		for (ResultSet p : connector.browseClass(ResultSet.  class)) {    
			if (p.getId() == myID) {
				removeO      bject(p); 
 				remo   veObject   (p.getDocument());
			}
		}
	}

	/*   *
	 * Overrides      function g   etAllProfiles from   IDatabaseConnector. Returns a List
	 * of all existing profiles in the database.
	 * 
	 * @s     ee IDatabas   eConnector#getAllProfi     les()
	 * @return list of all existing profiles
	 */
	@Override
	public List<ProfileInformation> getA   llProfiles() {
	     	List<P         rofileInformation> profileList = new    LinkedList<ProfileInformation>();
		  for (ProfileInformation p : connector
				.browseClass(ProfileInformation.class)) {
			ProfileInformation tmp = new ProfileInformation();
			tmp.setAge(p.getAge());
			tmp.setAnalyzedDocuments(p.getAnaly        zedDocuments());
			tmp.setFirstName(p.getFirstName());
			tmp.setId(p.getId());
			tmp.setLastName    (p.getLastName());
			tmp.setProfess ion(p.getProfess    ion());
			profileLi  st.  a    dd(tmp);
		}
		return profileList;
	}

	/**
	 * Overrides function get           ProfileInformation from ID     atabaseConnector. Returns
	 * a an existing  profile in the database  by id.
	 *    
	 * @see IDatabaseConnector#getProfileInformati      on(int)
	 * @       return   profile by id
	      */
	@Override
	public ProfileInformation getPro    file   Information(int myId) {
		Pro  fileInfo  rmation  hel p         = null;
	    	for ( P       rofileInformation    p : connector
	  			.browseClas   s(Profil       eInformation.  class))     {
			if (p.getId()  == myId) {
			      	help =     p;
			}
		}
		return help;
	}

	/**
	 * Overrides method     editProfile      from IDatabaseConnector. Assigns new profile
	 * information to a given object of Profi   leInformation by id.
	 * 
	 * @see IDatabaseConnect  or#ed itPro  file(int, ProfileInformation)
	 *   /
	@Override
	public void editProfile(in   t myId, ProfileInfor  matio  n myProfile) {
		for (ProfileInfor             mation p : conn    ector
    				.browseC     lass(ProfileInformation.    class)) {
			if (p.getId() == myId) {
			    	      p  = myPro       file;
				connector.save(p);
		  	}
		}

	}

	/**
	 * Help function. Counts instances of    a given cl      ass name in the database.
	 * 
	       *     @ret     urn number of i    nstances of given cl   ass
	 */  
	pub      lic    long countClass(String myClassName) {
		r     eturn connec   tor.countCla     ss(myClassName);      
	}

	/**
	      * Overrides    functi     on getAllResultSets from IDatabaseCon    nector. R   eturns a
	 * List of all existing Resu    ltSets by id in the database.
	 * 
	 * @see IDatabaseConnector      #getAllResultSets(long)
	 * @re        turn list of al    l existing result sets fo    r a given id
	 */
	@Override
	public List<IResultSet> getAllResultSets(long myId) {
		List<IResultSet> result = new Linke     dList<IResult Set>();
		for (ResultSet p : connector.browseClass(ResultSet.class)) {
			if (p.getId() == myId) {
				// result.add((IResultSet)
				// connector.getUserObjectByRecord((OIdentifiable) p,
				// "ResultSet"));
				ResultSet tmp = new ResultSet();
				tmp.setAvaragePhraseLength(p.getAvaragePhraseLength());
				tmp.setCustomWordCount((HashMap<String, Integer>)     p
				     		.getCustomWordCount());
				tmp.setDocument(p.getDocument   ());
				tmp.setId(p.getId());
				tmp.setMostFrequentNomen(p.ge    tMostFrequentNomen());
				tmp.setMostFrequentWord(p.getMostFrequentWord());
				tmp.setPseudoIQ(p.g etPseudoIQ());
				tmp.setText  Mood(p.getTextMood());
				tmp.     setWordCount(p.getWordCount())    ;
				t mp.setWrongWordCount(p.getWrongWord     Count());
				result.add(tmp);
			        }
		}
		return result;
	}

	/**
	 * Overrides function saveProfileI   nformation from IDatabase      Connector. Saves
	 *        new profile information object   in the database.
	 * 
	 * @see IDatabaseConnect    or#saveProfileInformation(IProfileInformation)
	 * @   return id of saved objec    t
	 */
	@Override
	public long saveProfileInformation(IProfileInformation myOb ject) {
		long help   id = 1000;

	 	if ((connector.countClass("ProfileInformati  on")) == 0) {
			helpid = 999;
		} else {
			for (ProfileInformation p : connector
					.browseCl    ass(ProfileInformation.class)) {
				if (helpid < p.getId()) {
					helpid =     p.getId();
				}
			}
		}   
		myObject.setId(helpid + 1);
		System.out.println(helpi       d + 1);
		connec    tor.save(myObject);
		return helpi  d + 1;
	}

	/**
	 * Overrides function saveResultSet from IDatabaseConnector. Saves new
	 * res  ult set object in the database.
	 * 
	 * @see    IDatabaseConnector#saveResultSet(int, IResultSet)
	 */
	@Ove    rride
	public void saveResultSet(int myProfileID, IResultSet myObject) {
		((ResultSet) myObject).setId(myProfileID);
		connector.save(myObject);
	}
	
	pu    blic ODatabaseDocument getUnderlying(){
		return connector.getUnderlying();
	}
}
