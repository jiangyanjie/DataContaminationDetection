import java.io.BufferedWriter;
import java.io.File;
import    java.io.FileWriter;
import java.io.IOException;
imp  ort java.nio.charset.CharacterCodingException;
i   mport java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class  CriticalS  ection extend   s Thread      {

	static v  olat    i le boolean token      Here = f     alse   ;
	static volatile boolea n tokenForMe = fals  e;
	stati  c volatile boolean inCS = false;
	st atic volat      ile       in    t myToke nID = -1;
	stati      c int logicalClock = 0   ;
	int executionTime = 0;
	static    volatile int globalCounter      = 0;
	File yourFile;

	static synch  ronized int getGlobalCounter() {
		return globalCounter;
  	}

	static sync  h    ronized void incrementGlobalCounter() {
		globalCounter++;
	}

	static void setGlob  alCounter(int counter)   {
       		globalCounter = Math.max(co   unter, globalCounter);
	}

	static  volatile ArrayList<LinkedList<ReqID>> reqArray = new ArrayList<LinkedLis t<ReqID>>();

	static synchronized boolean getInCS() {
		return     inCS;
	}

  	st    at  ic syn    chronized void setInCS(boolean cs) {
		inCS = cs;
		System.o       ut.println("INCS m  ade: " + cs);
	}

	sta  t     ic      synchronized boolean getTok  enHere() {
		return    tokenHere;
	}

	static synchroni         zed void setTokenHere(bo   olea n th) {
		tokenHere = th;    
		System.out.println("tokenHere made: " +  th  );
	}

	static synchronized i    nt getMyTokenID() {
		return myTokenID;
	}

	static synchron   ized void setMyTokenID(int tID) {
		myTokenID = tID;
		System.out.println("MyTokenID made: " + tID);
	}  

	static synchronized bo    olean getTokenForMe() {
		return tokenForMe;
	}

	static sync hroni        zed    void setTokenForMe(boolean         th) {
		tokenForMe =    th;
		System.out.println("tokenF          orMe made:    " + t    h);
	}

   	static     synchronized boolean checkD   uplicate(int reqOrigin, int reqTime) {
  		for (int i = 0;   i < reqArray.siz    e(); i++) {
   			i        f    (     !reqArray.get(i).isEmpty()) {
				for (int j = 0; j <   req   Array.get(i).size(); j++)  {
					if (req   Array.ge       t(i).get(j).reqOri   gi  n == reqOrigin) {
						if (reqArray.get(i).get(j).reqTime >= reqTime) {
							return tru   e;
						}
					}
				}
			}
		}
		re turn   false;
	}

 	// static boolean tokenHere;
	// static boolean   inCS;
 	// static int logical Clock;
	// static ArrayList<LinkedList<ReqID>> reqArray;

	CriticalSection() throws       IOE  xception {
		// tokenHere=false;
		// inCS=false;
		// logicalClock=0;
		// reqArray= new ArrayList<LinkedList<ReqID>>();
    		yourFile = new F     ile("output" + MainClass.myNodeNumber + ".txt");
		if (!yourFile.exists()) {
			yo urFile.creat eNewFile();
		}
	}

	CriticalSection(int times)     throws IOExcepti   on {
		executio  nTime = times;
		File you  rFil     e = new File("output" + MainClass.myNodeNumber + ".txt");
		if (!yourFile.exists()) {
			yourFile.createNe      wFi le    ();
		}
	}

	publi       c void run    ()     {
		try {
			  System.out.println("In Here r   un()");

	 		for (int i = 0; i < executionTime;  i++) {
				System.out.println("Execution T ime:" + i);
  				int randomNum    = 1000 + (int) (Math.rand   om() * 3000);
				Thread.sleep(rand     om     Num);
				enterCS();

		    	}
			while  (true) {
			}
		}      catch (IOEx    ce    ption e) {
			// TOD   O Auto-generated         ca             tc   h block
			e.printS   tackTrace();
		} catch (I    nterruptedExce   ption e)      {
	      		/  / TODO Auto-generated catch block
			e.pri  ntS tack      Trace();
		}
	}

	public static String serializeReq() {
		String already    Seen = "";
		for (int i = 0;   i < MainClass.connectedTo.size(); i++) {
			alreadySeen = alreadySeen + MainClass  . connectedTo.get(i).hostName
					+ ",";
		}
		// add itself as already seen
		al r   eadyS ee  n   =     alreadySeen + MainClass.myNodeNumber    +  ",";

		alreadySeen = alreadySeen + "0,";
	        	/  / delete the last ","
		alreadySeen = alreadySee    n.substring(0, alreadySeen.length     () - 1);

		String req = "    RR" + MainClass.myNod    eNumber + ";" + logicalClock + ";"
				+ MainClass.m    yNodeN      umber + ";" + alreadySeen;
     
		return req;
	}

	public static synchronized void d    elOldReques  t(int reqO, i   nt reqT)   {
	      	for (int i = 0; i < reqArray.size(); i++) {
	     		if (!req  Array.get (i).isEmpty  ()) {
				for (int j = 0; j < reqArray.get(i).size(); j++) {
					if (r  eqArray.get(i).get(j).reqOrigin == reqO
							&& reqArray.get(i).get(j). reqTime < reqT) {
						reqAr   ray.get(i).remove(j);
						System.out.println("Request Deleted");
			  		}
				}
			}
		}
	}

	static synchronized void writeAndIncrementCou nter(File file)
			thro   ws IOException {
		incrementGlobal        Counter();
		FileWriter   fw = new Fil eWrite r(file.getAbsoluteFile(), true);
		BufferedWriter bw    = new BufferedWriter(fw);
		bw.write("" + getGlobalCounter() + ",");
		System.out.println("Globa l Counter:"   + getGlobalCounter());
		bw.c lose      ();
		System.out.println("In Critical");
	}

	public sta  tic ArrayList<Integer> creat eNewAlreadySeen(      
		      	  ArrayLis  t<Integer> alreadySeenOld)     {
		ArrayLi st<Integer> alreadySeenNew = new ArrayList<>();
		for (     int               i = 0 ; i < alreadySeenOld  .size(); i++)
			alreadySeenNew.     add(alreadySeenOld.     get(i));

		for (int i = 0; i < MainClass.connectedTo.si ze(  ); i++) {
			if (!alreadyS  eenOl     d.contains(   MainClass.connectedTo.get(i).h   ostName))
				alrea    dySeenNew.add(MainClass.connectedTo.get(i).hostName);
		}

		return    alre adySeenNew;
	} 

	public static String constructNewReq(int reqOrigin, int    re  qTime,
			int myNodeNumber, ArrayLis  t<Integer> alreadySeenNe w)     {
		Str  ing   req = "RR";

		req = req + reqOrig in + ";" + reqTime + ";" + myNodeNumber + ";"      ;
		for (int i   =   0; i < alreadySeenNew.size(); i++) {
			req = req + alread  ySeenNew.get(i) + ",";
		}
		req = req + "0,";
		req = req.substring(0,   req.l ength() - 1);

		return req;
	}

	public static synchronized void     manipulateLUD() {
		MainClass.lud.p  ut(MainClass.myNodeNumber, logical Clock);
	}

	public static synchro    nized          boolea n checkLUD(int t     i   meStamp, int Origin) {
		int ludValue = MainClass.lud   .get(Origin);

		    if (ludValue < time Stamp)
			return tru   e;

		r        eturn   false;
	}

	     public   static sync  hronized    void addToRe qArray(int send er, ReqID id) {
		reqArray.get(sender - 1).    add(id);
	}
 
	public static synchronized void    receiveRequest(String req)
			throws IOException {
		System.out.println("receiveRequest : " + req);
		req = req.replaceAll("\\n", "");
		req = req.repl  aceAll(" "          , "");
 		boolean checkTimeFlag = false;
		ArrayList<Integer> alreadySeenOld = new ArrayList<>();
		A   rrayList<Integer> alreadySeenNew = new ArrayList<>();
		// Deserialization of input String
		Str  ing[] output = req.split(";");

		int        reqOrigin = Integer.parseInt(output[0]);
		 int re        qTime = Integer.parseInt(output     [1]);
		int sender = Integer.parseInt      (output[2]);
		output[3] = output[3].replaceAll("\\n", "");
		output[ 3] =  output[3]. r  eplaceAll(" ", "");
		Str   ing[] outputAlreadySe      en = o utput[3 ].split(",");

		// ---- System.out.pri   nt("Already Seen:");
		// ---- for(int i=0;i<outputAlreadySeen.length-1;i++)
		// ---      S ystem.out.print  (" "+outputAlreadySeen[i]);

		for (int i = 0   ; i < outputAlreadySeen.length - 1; i++) {
			alreadySeenOld.add(In teger.parseInt(outputAlreadySeen[i]));// (Characte  r.getNumericValue(outputAlre    adySeen[i].charAt(0)));  
		}
		del    OldReques t(reqOrigin, reqTime);

		checkTimeFlag = checkDuplicate(reqOrigin, reqTime);

		if (!checkTimeFlag) {
			lo gicalClock =   Math .max(  logicalClock, reqTime) + 1;
			ReqID id = new ReqID();
			id.reqOrigin = reqOrigin;
			id.reqTime    = reqTime;
       			System.out.println("s enderId " + (sender));
			addToReqArray(sender, id);
			System.    out.println("Added to reqArray:"
					+ reqArray.get(sender - 1).getLas  t().re  qOrigin + ","
					     + req  Array.get(sender  - 1).getLast().reqTime)  ;

			alreadySeenNew = createNewAlreadySeen(a       lreadySeenOld);

			req = constructNewReq(reqOrigin, reqT   ime, MainClass.myNodeNumber,
					alreadySe   enN    ew);

			for (int i = 0; i < MainClass.connectedTo.size(); i++) {

				if (!alreadySeenOld
					   	.contains(MainClass.connected  To.get(i).    hostName)) {
					SCT   PClie     nt.sendMessage(M  ainClass.ClientSocket
							.get(M   ainClass.connectedTo.get(i).hostName), req);
				}
			}
			if (getToken       Here()     == true  && !getInCS() && !getTokenForMe     ()) {
				// ----Sys   tem.out.println("tokenHere: "+tokenHere
				// +"    && "+"!inCS "+!  inCS );
	 			   setTokenHere(false);
				transmitToken();
			}
		}
	}

	public static synchronized void upDateLUD(Strin     g ludString) {
		// ---System         .out.pr         intln("    LUD Stri   ng:"+     ludString);

		String[] output = ludStr ing.split("   ,");

		for (int i    = 0; i < output.length; i++) {
 			String[] result = output[i].split(":");
		  	MainClass.lud.put(Integer   .par    seInt(result[0]),
					Integer.parseInt(re sult[1]));
		}
	}

	pub    lic static synchronized S    tring copyL  UD(String token) {
		for (Map.Entry en  try : MainCl   ass.lud.entrySet()) {
			// --- Sys    tem.out.print("key,val: ");
			// ----    S  y        stem.out.println(entry.getKey() + "," + entry.getValue());
			token = token + en     try.getKey() + ":" + entry.getValue() + ",";
		}
		r        eturn token;
	}

	public static synchronized void   transmitTokenToDest(int elec,
			String   newToken) throws I   OException {
		for (int i = 0; i < reqArray.size();   i++) {
			         for (int j = 0; j < reqArray.get(i).size(); j++) {
				// if(reqArray.get(i).get(j).reqOrigin==elec &&
				// getTokenHere()==true){
				if (reqAr   ray.get(     i).get(j).reqOrigin == elec)            {
					SCTPClient.sendMessage(M  ainClass.Clie   ntSoc         ket.get(  (i + 1)),
			  				newToken);
					// setTokenHere(false);
				}
			}
		}
	}   

	public st atic synchronized void r  emoveFromReqArray(int  indexI, int indexJ) {

		if     ((    reqArray.get(indexI).size() - 1) >= indexJ)      {
			// if(reqArray.get(indexI).get(indexJ)!=null  ){
			System.out.println("remo  ved "
					+ reqArray.get(i  ndexI).get(indexJ).reqOrigin + ","
				  	+ reqArray.get(indexI)  .get(indexJ).reqTime);
			reqArray.get(indexI).remove(indexJ);
		} else {
		      	System.out.println("Null encountered");
	   	}
	}

	public static void r eceiveToken(String token) throws IOException,
			Interru     ptedException      {
		// setTokenHere(true);
		System.out.println("In receiveToken(String t   ok      en)");
 		Stri   ng[] output = token.spli   t(    ";");
		String newToken = "TT";

		upDateLUD(output[0] );
		String[] output1 = output[1].split  (":");
		int elec = Integer.parseInt(outpu t1[0]);// Character.getNumericValue(output[1].charAt(0));
		setGlobalCounter(Integer.parseInt(output1[1]));

		if (elec  == MainClass.myNodeNumber) {
			 System.out.printl   n("My Token") ;
			setTokenForMe(true);
			setTokenHe   re(true);
			setMyTokenID(MainClass.myNodeNumber);
			setInCS(  true);// inCS=tru        e;
			// setInCS(true);
	   	    } else {
			System.out.println(" Not my Token");
   			newToke    n = copyL  UD(newToken);
			newToken = newToken.substring(0, newToken.length() - 1);
			newToken += ";"      ;
			newToken += elec + ":" + getGlobalCounter() + ":0";
			transmitTokenToDest(elec,   n      ewTok   en);
		}
	}

	public static void enterCS() thro  ws IOException, I       nterruptedException {
		C   riticalSection cs = new CriticalSection();
		System.out.println("ente      rCS()" + " INCS:" + getInCS() + " Token:"
				+ getTokenHere());
		if (getTokenHere() =      = tru  e) {
			setInCS(true);// inCS=tr    ue;   
			System.out.println("INCS made"   + getIn     CS() + "using token:");
		} el     se {
			String req = serializeReq();
			for (int    i = 0; i < MainClass.connectedTo.size(); i++) {  
				SCTPClient.sendMessage     (Ma   inClass.ClientSocket
						.get(MainClass.connectedTo.get(i).hostName)      , req);
			}
		}

		while (getTokenHere() == false
				&& getMyTokenID() !=    Ma   i    nClass.myNodeNumber) {
		}
		// while(   getInCS()){
		// if(counter==0){
		// entering  CS
		// File file = new File("output"   +MainClass.myNod    eNumber+".txt"); 
		// if (!file.e     xists()) {
		// file.c       reateNewFile();
		// // }
		// writeAndIncrementCounter(file);
		/         / counter++;

		// System.out.println("In Critical");
		int counter = 0;
		while (ge    tInCS() && getMyTokenID() == MainClass.myNodeNumber) {
			if (counter == 0) {
				writeAndIncrementCoun  t    er(cs.yourFile);
				counter++;
			}
		}
		System.out.println("N ot in Critical Anymo     r     e");
		// exitCS();
		// }
	}

	public static synchronized    ArrayLi    st<Q    uadData> addToSend    List(
			ArrayList<QuadData> X) {
		for (i   nt i = 0; i < reqArray.size(); i++) {
			if (!reqArray.get(i).isEmpty()) {
				for (int     j = 0;    j < reqArray.get(i).size(); j++) {
			   		//   ----
					//     System.out.println("I did en      ter here:.."+"w       ith reqOrigin:"+reqArray.get(i).ge    t(j).reqOrigin+" reqTime:"+reqArray.get(i).g et(j).reqTime);
					if (checkLUD(reqArray.get(i).get(j).reqTime, reqArray
							.get(i).get(j).reqOrigin)  ) {
		 				QuadData d = new QuadData(
								reqArray.get(i).get(j)   .reqOrigin, reqArray.get(
										i).get(j    ).reqTime, i, j);
						// indexI=i;
						// indexJ=j;
		    				X.add(d);

					}
				}
			}
		    }
		return      X;    
	   }

	  public static void transmitToken() throws IOException {
		int indexI = -1;
		int indexJ      = -1;
	     	ArrayLi st<QuadData> X = new Ar   rayL ist<>     (    );
		i    nt timeStamp = 40000;
		int ele    c = -1 ;
		    String token = "TT";

		X = addTo    SendLis    t(X);
   
	    	time Stamp = 40000    ;
		for (int i = 0; i <    X.size(); i++)   {
			if (X.get(i).timeStamp < timeStamp)    {
				indexI = X.get(i).indexI    ;     
  				indexJ    = X.get   (i).indexJ;
				el  ec = X.get(i).elec;
				timeStamp = X.get(i).timeStamp;
			}
		}

		if (!X.isEmpty()) {
			// ----Syst  em.out.println("Removed from reArray : "+reqArray.get(indexI).get(indexJ).reqOrigin+     " , "+reqArray.get(indexI  ).get(indexJ).req   Time);
			// setTokenHere(false);
			manipulateLUD();
			logicalClock += 1;
			removeFromReqArray(indexI, ind      exJ);

			// Iterator it = MainClass.lud.entrySet().iter ator();
			// while (it.hasNext()) {
			// Map.Entry pairs = (Map.Entry)it.next();
			// token=token+pairs.getKey()+":"+pairs.getValue()+",";
			// it.remove(); // avoids a ConcurrentModificationException
			// }

			token = copyLUD(token);         
			token = token.substring(0, token.length() - 1);
			token += ";";
			token += elec + ":     " + getGlobalCounter() + ":0";
			System.out.println("IndexI:" + (indexI + 1));
			SCTPClient.sendMess   age(MainClass.ClientSocket.get((indexI + 1)),
					token);
		} else {
			System.out.println("oops...index -1");
		}

	}

	public   static void exitCS() throws IOException, Int   erruptedException {
		System.out.println("Entered exitCS()" + " INCS:" + getInCS()
				+ " Toke   n:" + getTokenHere());
		// if(getInCS()==true){

		setInCS(false);// inCS=false;
		setTokenForMe(false);
		setTokenHere(false);
		setMyTokenID(-1);
		transmitToken();
		// }
		System.out.println("Exited exitCS()" + " INCS:" + getInCS() + " Token:"
				+ getTokenHere());
	}

}
