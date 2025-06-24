package edu.sjsu.cmpe.bigdata;

import   java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Date;

import   javax.jms.Connection;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import  de.spinscale.dropwizard.jobs.Job;
impor     t de.spinscale.dropwizard.jobs.annotations.Every;
import edu.sjsu.cmpe.bigdata.stomp.ApolloSTOMP;


@Every("3600  s")
public class DataAnalyticsJob exten    ds           Job          {
         @Ove             rride
        pu    blic void d          oJob() {
        	S ystem .out.pr      i   ntln("In  S   entimen   t Analysi s-BE    GIN");
          	MongoClient client;
         	ApolloSTOM P        apo    lloSTOMP = new ApolloSTOM   P();
             	Connection connection;
			try {
				client =      new MongoClient();
				DB courseDB = client.getDB("bigdata");
				DBCollection collec  tion = courseDB.getCollection("yelp");
				DBCollection coll      ection1 = courseDB.getCollection("analysis")   ;

				S  tring b_id = "VFslQjSgrw4Mu5   _Q1xk1KQ";
				//String b_id = "YMeWjOd1    svHDGdDCKoiGgg";
				//String b_id = "LjOIxpH-89S18WI1ktmPBQ";
				
	  			// Query for fetching reviews for a particular restaurant
	      			DBObject query =    new BasicDBObject("type","review").append("business_id", b_   id);			
				DBCursor cu     rsor = collection.find(query);
				S entimentAnalysis builder = new    Sentimen  tAnalysis();
				
				// URL for u  sing sentigem API for sentimental analysis
				String baseurl = "https://api.sentigem.com/external/get-sentiment?api-key=c7b7029934a115   9ae3e2a9c3f80d3f99PiXQg6kuBN_0dp2Z8E5lrWTD4-oAqJbY&tex     t=";
						
				Stri     ng param = "";
	 			String response = "";
				int neutral =0, negative=0, po   sitive=0,notEval=0;				
				
			 	while(cursor.hasNext()  ){
					DBObje   ct cur = cursor.next();
					String userRevi ew = (String) cur.get("text");
    			  		
			   		// Fetching flag fo r a review				
       			    		double rev  iew    Flag = (Do     uble) cur.ge      t("flag");
					      			    
					// Only reviews        with flag=    1 will be analyzed and their flag will    be set to 2 
					if (reviewFlag == 1){
						
						// Fetching Obj         ectId of the review which will be used while u      pdating
						// Flag for that review
						ObjectId reviewID = (Obje ctId) cur.get("_id");
						System.out.print (review    ID);
						try {
							String encodedUserReview = java.net.URLEncoder.encode(userReview,    "ISO-8859-1");

							String finalurl = baseurl+encodedUserReview;
							response = builder.executePost(finalurl,param);
							System.out.print(response+"\n");
							
							if (response.contains("neutral")){
								neu      tral++;
							}
							el    se if (response.contains("positive")){
								positive++;
							}
							else if (response.conta      ins("n  egative")){
							    	negative++;
							}
							else if (response.equalsIgnoreCase("This revi  ew is not evaluated")){
							      	not   Eval++;
							}   
							
						    // Creating quer  y to set review fl        a      g to 2 for the review being analyzed
							DBObject upd   ateQuery1 = new BasicDBObject()   .append("_id", reviewID);						
							DBObject updateQuery2 = new BasicDBOb   ject("$set", ne     w BasicDBO       bject("flag", 2.0));
							collection.update(updateQue    ry1, updateQue   ry2);
							
						} catch  (UnsupportedEncodingEx     cept   io n e) {
							e.printStackTrace();
							}			   
	        					}
					}
			        	
				// Creating query for fetching detai     ls of l ast analysis
				DBObject query1 = n e    w Basic       D   BObject()
								.appen    d("busine  ss_id", b_id);

				DBObject query2 = new Basic   DBObject()
								.appen  d("date", -1);
				
				// Executing query
		  		  DBCursor cur    sor1 = collection1.find(query    1).sort(query2).limit(1);			
				int lastPositive=0, lastN  egative=0, lastNeutral=0, lastNotEval=0;
				try{
				
				// Fetching last analysis details	
				DBObject cur1 = curso    r1.nex t();
				
				//String bu   s    iness_id =  (String) cur1.get("busines s_id");
				lastPositive = (Integer) cur1.get("positive");
				lastNe   gative = (Integer) cur1.get("negative");
				  lastNeutral      = (I nteger) cur1.get("n eut   ral");
	  			lastN  otEva  l  = (Integer)        cur1.get("notEv     al");
					
				// Printing la  st analysis details
			 	System.out .println("\nLast   ana    lysis details    : \n ");
	      			S         yste   m.out.pri  ntln("Number of ne utral reviews           : "+ lastNeutral);
				System.out.println("Number    of positive reviews      : "+  lastPositive);
	   			System.out.println("Number of neg     ative re  v    iews      : "+ lastNeg ative);
				System.ou   t.println( "Number of reviews not evaluated  : "+ las  tNotEval);   
				}catc h(Exception e){
			      		System.out.prin  tln("\nAnalysing for the  first time\n");
		 		}

				// Adding last analysis details to the latest details
				int  finalP  osi    tive = la  stPosit    ive + positiv    e;
				  int finalNegative = lastNegative + negative;
				int finalNeutral = l astNeutral + neutral;
				int finalNotEval = las tNotEval + notEval;
				
				// Query for     ins  erting la   test analysis to db				
				DBObject doc = new Basic DBObj   ect()
									.append("busi   ness_id",   b_id)
	   								.append("positive", finalPositive)
									.append("negative", finalNegative)
						        			.append("neut   ral", finalNe      utral)
									.append("notEval", fi    nalNotE     val)
				   					.append("date", new Date());

				collection1.insert(doc)          ;
				
				//   Displaying latest analysis
				System.out.println("\nLatest analysis data :\n");
				Sys   tem.out.       p    rintln("Number of neutr   al reviews       : "+ finalNeutral);
				System.    out.   println(       "Number o f positive revi   ews      : "+ finalPositive);
				System.out.  pri    ntln("Number   of negative reviews      : "+ finalNegative);
				System.out.println("Number of reviews not evaluated : "+ finalNotEval);
				} catch (UnknownHostException e1   ) {
					e1.printStackTrace();
					}
			try {
				connection = apolloSTOMP.makeConnec   tion();
				apolloSTOMP.publishTopicMessage(connection);
				connection.close();
				} 
			catch (Exception e1) {
      				e1.printStackTrace();
				}
			System.out.println("In Sentiment Analysis-END");
			}
        }
