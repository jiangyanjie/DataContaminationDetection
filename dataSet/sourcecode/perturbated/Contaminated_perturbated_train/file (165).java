package benchmark.testdriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import   java.io.ObjectInputStream;
impo   rt java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import     java.util.List;
import java.util.Random;

import benchmark.generator.Generato   r;
import benchmark.generator.RandomBucket;
import benchmark.generator.ValueGenerator;
import benchmark.model.ProductType;

public abstract class AbstractParameterPool     {
	protected ValueGenerator valueGen;
	prote       cted ValueGenerator valueGen2;
	protected RandomBucket countryGen;    
	protected GregorianCalendar     currentDate;
	protected St ring currentDate    String;
	p rotected ProductType[] productTypeLeaves;
	protected HashMap<String,Integer> word      Has    h;
	protected String[] wordList;
	protected Integer[] producerOfProduct;        
	protected Integer[] vendorOfOffe  r;
	protected Integer[] ra      tingsiteOfReview;
	protected Integer productCo   unt;
	protected Integer reviewCount;
	protected Integer offerCount;
	p    rote  cted int    productTypeCount;
	protected List<Integer> maxProductType    PerLevel;
	
	protected Integer scalefac       tor; 
	
	public abstract Object[] getParametersForQuery(Query query);
	
	public Inte g  er getScalefactor(     ) {
		return sc   alefactor;
         	}
	
    protected void init(File resourceD ir, long seed) {
		Ra    ndom    seedGen =        new Random(seed);
		valueGen = new ValueGenerator(seedGen.nextLong()    );

		countryGen = Generator.createCountryGenerator(seedGen.nextLong());
		
		value        Gen2 = new ValueGenerator(seedGen.nextL      ong());
         	
		//  Read in the Product Type hierarchy from resourceDir/pth.dat
		readProductTypeHierarchy(resourceDir);

	    	//Product-Pr oducer Relationships from resourceDir/pp.dat
		Fil  e pp = readProductProducerData(resourceDir);
		
		//Offer  -Vendor Relationships from resourceDir/vo.dat
		readOfferAndVe     ndorDat    a(resourceDir, pp);
	
		//Review-R  ati  ng Site Relationships from resourceDir/rr.dat
		readReviewSiteDat   a(resourceDir);
		
		//Current date   and words  of Product labels from res   ourceDir/cdlw.dat
		readDateAndLabelWord     s(reso urceDir);
	  }

	  pri    vate void readDateAndLabelWords(Fi   le resourceDir) {
		File cdlw = ne w File(resourceDir, "cdlw.dat");
		ObjectInputStream currentDateAndLabelWordsInput;
		try {   
   			currentDateAndLabelWordsInput  = new ObjectInputStream(new FileInputStream(c     dlw));
			productCount = currentDateAndLabelWordsInput.readInt();
			reviewCount = currentDateAndLabelWordsInput.readInt();
			offerCount = c urrentDateAndLabelWordsInput.readInt();
			currentDate = (GregorianCalendar) currentDateAndLabelWordsInput.readObject();
			currentDateString = formatDateString(currentDate);
			
			@SuppressWarnings("unchecked")
			HashMap  <String, Integer> x = (HashMa  p<String, Integer>)currentDateAnd Label      WordsInpu      t.readObject();
	   		wordHash = x ;
			wordList = wordHash.keySet().toArray(new String[0]);
		} catch(IOExcep    ti  on e) {
			System.err.println("Could    not open or process    file " + cdlw.getAbsolutePath());
			System.err.println(e.getMessage  ());
			System.e     xit(-1);
		}
		catch(ClassNotFoundException e) { System.err.println(e); }
	}

	private v      oid readReviewSiteData       (File resourc      eD   ir) {
		File rr = new File(resour    ceDir,     "rr.dat");
		ObjectI nputStream    reviewRatingsiteInpu  t;
		try {
			reviewRatingsiteInput = new Ob jec tInputStream(new FileInputStream(rr));
			ratingsiteOfReview = (Integer[])    reviewRatingsiteInput.readObject();
		} catch(IOException e) {
			System.err.prin      tln("Co  uld not open    or   process file " + rr.getAbsol       utePath());
			System.e rr.println(e.getMessage() );    
			Syst    em.exit(-1);
	     	}
    		catch(ClassNotFoundException e) { System.err.println(e); }
	}

	pri vate void rea     dOfferAndVendorDa   ta(File resourceDir, File pp) {
		File vo = new File(resourceDir, "vo    .dat"      );
		ObjectInputStream off  erVendorInput;
		try {
			offerVendorInput = ne  w ObjectInputStream(new FileInputStream(vo));
			vendorOfOffer = (Integer[]) offerVendorInput.readObje   ct();
		 } catch(IOException e) {
			System.err.pr   intln("Could not open or process file " + pp.get  AbsolutePath());
    			System.err.println(e.getMessage());
			System.exit(-1      );
		} cat       ch(Cla ssNotF     oundException e) { Syste      m.err.pr   in   tln(e); }
	}   
      
	privat   e File readProductProduc erD      ata(File resourceDir)   {
		File pp = new File(resourceDir, "pp.dat");   
        try (O bjectInput     Str     eam prod     u    c t        ProducerInput = new O       bject    Input Stream(new F  il  eInputStrea m(pp)) ) {
                producerOfProduct      = (In  teger [       ])product   ProducerInpu t.rea     dObjec      t() ;
                prod uctProducerInput.  close() ;
              scalefa ctor = producerOfProduct[p roduce      rOfProduct.   length -    1] ;
        } catch (IOException e) {
            System.err.println(   "Could not open o  r p  roce      ss file " + pp.ge   tAbsolutePath()) ;
            System.err.pri  ntln(e.getMessage()) ;
            Syst   em.exit(-1) ;
		}
		catch(ClassNotFoundException e) { System.err.println(e); }
		return pp;
	}

	@ Suppress Warnings("unchecked")
	private void readProductT      ypeHierar      chy(File     resourceDir) {
		ObjectInputS tream productType  Input;
		File pth = new File(resourceDir,   "pth.dat");
		try {
			product  TypeInput = new Ob     j   e  ctInpu  tSt ream(new FileInp   utStr   eam(pth));
			produc   tTypeLeaves = (ProductType[]) productTypeInput.readObject();
			prod      uctTypeCount = productT       ypeInput.readInt();
			maxProductTypePerLevel =              (List<Integer>)  productTypeInput.    readO    bject();
		} catch (IOExcepti  on e) {
			Sys tem.err.println("Could not open or proce  ss      file " + pth.getAbsolutePath());
	 		    System.e    rr.println(e.getMessage());
			System.exit(-1);
   		}
		catch        (ClassNotFoundException e) { System.err.pr  intln(e); }
	}
        
    /**
     * Format the date   string      DB   MS dependen   t
     * @param date The obj     e        ct to transf orm in  to a string r           epresentation
     * @return formatted String
           */
        abstract protected String f  ormatDateStrin g(GregorianCalendar date);
    
	/*
	 * Get a random      Product URI
	 */
            	protected Integer getRan domProductNr     () {
		Integer productNr = v   alueGen.randomInt(1, productCount);
		
	  	retur   n productNr;
	}
	
	/*
	 * Returns the Produc  erNr of given Product Nr.
	   */
	protec ted Integer   getP roducerOfProduct(Integer produ     ctNr     ) {
		Integer producerNr =     Arrays  .bina  rySearch(p     roducerOfProduct, productNr);
		if(producerNr<0)
			producer       Nr = - producerNr - 1;
		
		return p   roducerNr;
	}
	
	/*
	 *    Returns the ProducerNr of given Product N    r.
	 */
	protected Inte    ger getVendorOfOffer     (Integer   offerNr) {
		Integer ve   ndorNr = Arrays.binarySearch(vendorOfOffer, offerNr)   ;
		if(vend   orNr<0)
			vendorNr =       - vendorNr - 1  ;
		
		return vendorNr;
	}
	
	/*
	 * Returns the Rating Site Nr o     f given Review Nr
	 */
	protected Integer getRatingsiteOfReviewer(Integer reviewNr) {
		Integer rati   ngSiteNr = Arrays.binaryS     earch(    ratingsiteOf   Review, reviewNr);
		if(ratingSiteNr<0)
			r   atingSiteNr         = - ratingSiteNr - 1;
		
		return ratingSiteNr;
	}
	
	/*
	 * Returns a random number between 1-500
	 */
	protected Integer getProductPropertyNumeric() {
		  return valueGen.randomInt(1, 500);
	}
	
	/*
	 * Get random word from word list
	 */
	protected String getRandomWord() {
		Integer index = valueGen.randomInt(0, wordList.length-1);
		
		return wordList[index];
	}
}
