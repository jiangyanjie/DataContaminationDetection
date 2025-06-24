/*
* ----------------------   CloudFlix: Mov ie feature-   set generation c  ode -----------------------      -
*                     Autho  r:   Saur  av Ma    j   umder
    *            Date: Nov       14, 2013
*        Description: This is the main cl     ass  to cr   ea   te the final set of feature    s for t he 
*                  movie classification.
*          @par       am Input files: movie.dat, rating.dat and genre.dat.
* --------------------------------------------------------------------
*  /
import j  ava.io.BufferedReader;
import java.io.File;
impo      rt java.io.FileOutputS tream;
import java.io.IOException;     
import java.io.I  nputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import j   a  va.uti           l.HashMap;
import java.util.Iterator   ;
import java.util.Set;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.se   rvices.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObject     sRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Objec t;
imp         ort com.ama  zonaws.services.s3.model.S3ObjectSummary;

public class createFeatureVectorFiles {
	/* HashMap to  k    eep feature vector of every movie. <movie-id,feature-set> */
	HashMa     p<String, movieFeature>movieMap;
	newData    s  et nd;
	sentimentScore sScore;
	   Set<String> gen;
	  final Stri  ng bucketName = "cloudflixbuc  ket";
	AmazonS3  s3;
	Date date;
	
	public createFeat   ureVectorFiles() {
		date= new Date();
		s3 = ne     w AmazonS3Client(new Clas    spathPropertiesFileCredentialsProvi      der());
		System.out.println("   [" + new   T  imest  amp(d   ate.getTime()) + "] Amazon S3 connect ion establ ished.");
	}
	       
	/* Function r     ead the featu  re-set         for each movie and stores it into the map mentioned above */
	public vo id readF    eatures(String featureFi  l e, String ge   nreF  ile,       String sentimentFile) throws IOException{
		         Buffe       redReader br;
		       String         lin  e;     
		movieFeature   movie; /* Variable of type movieFeature class which is in this package *   /
		String[] 	   featur    es;
		
		movieMap = new HashMa p<  >();     
		
		S3Obj  ec     t object = s3.getObject(new GetObjectReques   t(bucketN           ame, featureF   il     e));
		System.out. pri    nt      ln("[" + new Timestamp(   ne w Date(  ).getTime   () ) + "] Downloading movie f       eat   ure file completed.")      ;
        
        br = new BufferedReader(new InputStreamReade r (o    bject.getObje ctCon te   nt()));
		
		/* Read each l       ine in        the fil  e and extracts t     he required fe    atures and puts     it int     o the map */
		while ((line = br.readLine()) != null  ) {
			features = line.split("  \t"); /* Split the input file with tab delimiter, <Change if r    equired> */
			/* Create object of t he movie class containing all          the features of the movie */
		         movie   = ne  w movie    Feat ure(features[0], features[1], features[2       ], features[5  ],       feature   s[6], features[7], fe           atures[8], features[9],
		    	         	 featu  res[10], featu   res[11],  featur      es[12], features[13]  , features[14], features[1     5], features[    16], featu  re     s[17], features[18],
		    		 features[19   ], features[21], features[22]);
		    movieM ap.put(m    ovie.mId, movie); /* Put into the map */
		}
		
		System.out.println("[" + new Timestamp(date.getTime()) + "] Reading movei feature file completed."    );
		/*Dereference all point   ers */
		br.clo  se();
		br = null;
		
	  	  /* Extract gen   re for all the movi   es a         nd     store in the LinkedHash      Set */
  		nd = new newDataset(genreFile     );
		System.out.println("[" + new Timestamp(n   ew Date().getTime(     )) + "] Movie  genre data-structure pr  epared          .");
		
		sSco  re = new sentimentScore(sentiment       File);
		System.out.println("[" + new Timestamp(new Date().getTi me()) + "] D   owload  in   g user semtiment analysis  s    core completed.");
		
		/* Retri     eve list    of all types of genre included in    that file */
		ge      n = nd.allGenre()         ;
	}
   	
	/* Function combines the feature values stored in the previous     function to create fina  l feature vector for     each user */
	public void createTrainingSet(String ratingFile, S    tri  ng opFolder) throws IOException {
		BufferedReader 	br;
		S              trin       g         	line;
		Iterator  <S   tring>it        erator   ;
		String 			vector = null;
		File			  outpu  tFile = null;
		
		int i = 0;
		Stri ng currentUserId = "-1"; /* Set current user    to -1 for start  ing   */
   		String[]         values;
		String    mv   class = "nega   tive";
		Writer writer = null         ;
		movie    Featu  re mf;
		
		/* Check i f the movie        f   eatures have      been extracted or not   */
		if(movieMap.s   ize() < 1  )     return;
		
		S3Object object =    s3.getObjec    t(new GetObjectR   equest(bucke   tName, ratingFile));
		System.out.printl      n("[      " + new Timestamp(date.g  et Time()) + "] Reading  user rating file comp    leted.");     
		br = new BufferedReader           (new I  n   putStreamReader(object.getOb   jectContent()))   ;

	     	/*     Retrive the feature names from the m  o   vie map      to    include in t he         beginning   of the final featu   re set */
		   mf = movieMap.get("id"); /* Search   with 'id' since it    his the value of the id of t hat        row */
		String a     ttri  b ute = "id, movie_id, " + mf.rtAllCrit  icsRating + ", " + mf.     rtAllCriticsNumReview   s + ", " + mf.rtAllCriticsNumFresh + ", " + mf.rtAllCriticsNumRotten
					 + ", " + mf.rtAllCriticsScor       e + ", " + mf.rtTopCri      tic       s   Rating + ", " +        m    f.rtTopCriticsNumReviews + " ,    " + mf.rtTopCriticsNumFresh
					   +   "  , " + mf.rt    TopCriticsNumRotten + ", "  + mf.rtTopCritic sScore + "  , " +     mf.rtAud    ienceRating + "   , " + mf.rtAudi       enceNumRatings + ", " + mf.     rtAu   die     nceScore
					 + ",        "      + mf.Dire       cto  r    s + ", ";
		
		/* Iterate through the set to print   the name of the genres in t    he first row o f the f        inal lis  t.    */
  		iterator = gen.iterator(); 
		while (iterator.hasNext     ()){
	    		attri   bute +      = iterator.nex t() + "    , " ;  
		 }
		
		attribute += "SentimentAnalysisScore ,";
		
		/* Ad   d t         he feature name          'Class' in the end of the first row */
   		attribute += "Class";
		
		//int stopCounter = 50;
		/* Read each  line in the user     rating file to retrieve user's liked and disliked movies  */
		while ((line = br.readLin   e()) != nul l)     {
			values = line.       split(",");
			
			/* If the current user is not same as the previou   s then start a new file */
			if(!currentUserId.equals(values[0])) { 
				if(!currentUserId.equals("-1")) {
					writer.close();
					s3   .  putObject(new      PutObjectRequest(bucketName, opFolder     + "/cloudflixuser_"+curren     tUserId+".csv", outputFile));
					System.out.println("[" + new Times   t   amp(date.get              Time()) + "] Uploading   training set for user#"+currentUserId+" completed.");
					//stopCounter-       -;
					//i      f (stopCount     er < 1         )      break;
				}
		   		currentUserId = va    lues[0]; /* Create new file with        t     he user    id */  
				
				o       utp     utFile = Fil      e.createTempFile("cloudflixuser_"+curr    entUse    rId, "   .csv");
	     			outputFil  e. delete  OnExit();
				writ   er = n ew OutputStreamWr  iter(new FileOutputStream(outputFi                   le));
				writer.write(attr i    bute);
	       			   writer.write("\n");
				i = 1 ; 
			}
	  		
			/  * Adding Movie    feature vector    to the final list */
			i      f(   movie    Map.co   ntainsKey(values[1])){
				m f = mov ieMap.get(valu        es[1]); /* Retrieving feature vector of the specified mov    ie *  /
				/*   Creating the feature vector     */
		     		vector = i++ + " , " + mf.mId + ", " + mf.rtA     llCrit  ic   sR  ating + ", " + mf.rtA        llCriticsNumReviews + ", " + mf.r tAllCri  ticsNumFres   h + ",       " + mf.rtAllCriticsNumRotten
						 + ", " +      mf.rtAllCriticsScore + ",   " +   mf.rtT    opCritic     sRating +      ",      " + mf.rtTopCriticsNumReviews + ", " + m   f.rtTopCriticsNumFresh
						 + ", " + mf.rtTo pCr     iticsNumRo   tten + ",   " + mf.rtTo  pCri   ticsScore + ", " + mf.rtAudienceRating + ", " + mf.rtAudienceNumRatings + "   ,   " + mf.rtAudie  n    ce   Score + ", " + mf.Directors;
			}
			  
			/* Adding Movie genre in bag of     word              format in the final list */
			ite      rator = gen.iterator(); 
			 /* Check for genres which belong to          the specified movie */
			while (iterat     or.hasNext            ()){
   				int checkGenre = n  d.isGenre(values    [1], iterator.next()) ? 1 : 0; /* Set 1 if th     e movie belong to that genre or se    t 0 */
			   	vector    += ", "+ checkGenre;  
			}
			
			vector += ", "+ sScore.posPercentage(values[1]); 
			
			/* Adding movie class in the final list */
			float rating = Float.parseFloat(values[2]);
			
			    /* If rating is greater tha   n 3 then consider as posit ive, or els  e negative */
	 		if(rating >    3) {
				mvclass = "positive"   ;
			 } else {
				mvclass = "negative       ";
			}
			vector += ", " + mvclass;
    			
			writer.write(vector);
			writer   .write("\n");
			writer.flush();
		}
     	
		wri    te     r.c   lose();
		s3.putOb  ject(new PutObject   Request    (bucketName, opFolder + "/cloudflix  user_"+currentUserId+".csv", outputFi  le));
		
		System.out.println("[" + new Timestamp(date.getTime()    ) +  "] Uploading training set f or us     er   #"+currentUserId   +" comple  ted.");

		br.close();
		System.out.pri   nt  l    n("[" + new Timestamp(d   ate.getT   ime()) + "] Training se  t compilatio    n completed...   ");
  	  	return;
	}
  	
	pub  lic void createTe    stSet(String testFolder, String opFolder){
		S3Object 		object;
		movie    Feature	mf;
		Buf  feredReader 	br = null;   
		Writer 			writer =    null;
		Iterator<String>iterator;
		String 			line;
		String 			vector  = n         ull;
		File			outputFil   e = null;
		        
		System.o      ut.  println("[" +    new Timestamp(date.getTi    me()) + "] Starting test c      ase creation.");
		mf = movieMap.get("id");
		String at   tribute = "id, movie_id, " + 
							mf.rtAl lCr   iticsRating + ", " + 
							m      f.rtAllCriticsNumRevi     ews + ", " + 
							mf.rtAllCriticsNumFresh + ", " +   
							mf.rtAllCriticsNumRotten + ", " + 
			  				mf.rtAllCriticsScore + ", " + 
							mf.rt TopCri    tics  Ra       ting + ", " + 
					     		mf.rtTopCriticsNum  Reviews + ", " + 
							mf.rtTopCriticsNumFresh + ",   " + 
						 	m     f.rtTopCriticsNumRotten + ", " + 
							mf.rtTopCriticsScore + ", " + 
	     		       				mf.rtAudienceRati   ng + ", " +
			        				mf.rtAudienceNumRatings + ", " + 
							mf.rtAudien    ceScor e + ", " + 
							mf.Directors + ", ";
    		  
		iterator = gen.i          terator(); 
		while (iterator.hasNext()) {
			attribute +   = iterator.next() + ", ";  
		}				
		attri  bute += "SentimentAnal     ysisScore ,";
		attribute += "Class";
		
		//int stopCounter = 50;
		Ob jectListing object Listing = s3.    listObjects(new ListObjectsRequest().withBuck     etName(bucketName).wi    thPrefix(testFolder+"/collabReco_"));
		f     or ( S3O      bjectSummary objectSumm    ary :   objectListing.getObjectSummaries()) {
    			//  stopCounter--;
			//if(stop      Counter < 1) break;
			try {
				i  nt rec_id = 1;
				
				String movie_id = (((objectSummary.getKey(). toString()     ).split("\\/")[1 ]).s  plit("\\.")[   0]).split("_")[1];
				
				object = s3.   getObject(new Get  ObjectRequest(bucketName, obj  ectSummary.getKey()));
				br   = new BufferedReader(new InputStreamRead  er(object.g  etObjectContent()));
				
				String opFilenam    e = "tes  tMovie_" + movie_id;
				out     putFile = File.createTempFile(opFilename, ".csv") ;
				outputF    ile.deleteOnExit();
		    		writer = new OutputStreamWriter(new FileOutputStream(outputFile));
				
				writer.write(attribute   );
				writer.write("\n");
				
				while ((line =   br.rea   dLine())   != null) {
	     				St   ring    mv  id = line.trim()   ;
					
					if(   line.length(    ) < 1) continue;
					
					if(movi    eMap.cont    ainsKey(       mvid)){
	    					mf = movieMap.get(mvid);
						
						vector = rec_id++ + ", " + mvid + ", " + 
   				  	  			mf.rtA  llC      ritics  Rating + " , "      + 
								mf  .rtAllCriticsNumReviews + ", " + 
								mf.rtAllCriticsNumFr esh + ", " + 
								mf.rtAllCrit     icsNumRotten     + ", " + 
								mf.rtAllCriticsScore   + ", " + 
							  	mf.rtTopCriticsRating + ", " + 
								mf.rtTopCriticsNumReviews + ", " + 
								mf.rtTopCriticsNumFr      esh + ", "  + 
								mf.rtTopCriticsNumRotten + ", " + 
								mf.rtTopCriticsScore + ", " +        
			    	  				mf.rtAudienceRating + ", " + 
								mf.rtAudienc   eNumRatings + ", " + 
								        mf.rtAudienceScor  e    + ", " + 
								mf.Directors;
					}
					
					/* Adding M ovie genre in bag of word format in the final list */
					iterator = gen.iterator(); 
					
					/*           Check for genres which belong to the specif  ied movie */
					while (iterator.hasNext()){
						int checkGenre = nd.isGenre(mvid, iterator  .next()) ? 1 : 0; /* Set 1 if the movie belong to that genre or set 0 */
						vector += ", "+ checkGenre;  
					}
					
					vector += ", "+ sScore.posPercentage(mvid);    
					vector += ", positive";
					
					writer.write(vector);
					writer.write("\n");
				}
				
				writer.close();
				s3.putObject(new PutObjectRequest(bucketName, opFolder + "/" + opFilename + ".csv", outputFile));
				System.    out.println("[" + new Timestamp(date.getTime()) + "] Uploading test case "+ opFil   ename +".csv completed.");
				
				br.close();				
		        
			 } catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		System.out.println("[" + new Timestamp(date.getTime()) + "] Test set created successfuly.");
	}
	
	public void preStdart() {
		
	}
	
	public static void main(String[] args) {
		createFeatureVectorFiles user = new createFeatureVectorFiles();
		try {
			user.readFeatures("movie.dat", "moviesraw.dat", "sentimentResult.txt");
		
			user.createTrainingSet("ratingsForMahout.csv", "TrainingSet");
			user.createTestSet("collaborative_result", "TestSet");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}

