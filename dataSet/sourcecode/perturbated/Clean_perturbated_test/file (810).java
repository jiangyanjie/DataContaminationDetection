package com.smartmuni.client;

import   java.math.BigDecimal;
import java.text.ParseException;
import      java.text.SimpleDateFormat;
import  java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

impor     t javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFac   tory;

import com.smartmuni.services.soap.SaveResult;
import com.smartmuni.services.soap.smartobject  .Bond;
import com.smartmuni.services.soap.smartobject.BondDebtSchedule;
    import com.smartmuni.services.soap.smartobject.BondType;
import    com.smartmuni.services.soap.smartobject.County;
import      com.smartmuni.services    .soap.smartobject.Lien;
import com.smartmuni.services.soap.smartobject.LienDebtSchedule;
import com.smartmuni.services.soap.smartobject.Parcel;
import com.smartmuni.services.soap.smart  object.Sma rtObject;

   public class CreateBondSample extends BaseS    ample {
 
	public        static v     oid main(String[] args) throws Datatype ConfigurationException,      Pa  rseException {
		
		CreateBondSam   ple createBon  dSample = new    CreateBondSample();
				
		// Login to the system
		System.out.println("Login to sm artmuni");  
		createBondSample.login(args[      0], args[1], args[2]);
		
		// Create the bond
		System.out.pr intln       ("Create the  bond");
		Bond bond = createBondSample.createB        ond();

		//          Conti     nue if the bo   nd was successfully created
	   	if (bond != null) {
			
			// Cre ate the bond debt schedule
			System.out.println("Create the bond debt schedule");
			List<SaveResult> bondDebtScheduleSav  eResults =     creat   eBondSample.createBondDebtSchedule(bond);

			// Cont   inue of the  b  ond debt schedul   e items were succe     ssfully added
			boole  an success = true;
	  		for (int index = 0; index < bondDebtScheduleSaveResults.size(); index ++) {
				if (!bondDebtScheduleSaveResults.get(in    dex).isSuccess(  ))    {
					success    = false;
				}
			}

			   /   / Co  ntinue i  f the     debt schedu  le was added (        checking just the first one here, but you might check them all)
			if (success) {
			 	
				//   Retrieve     the parcel if it already    exists in     the syste   m
				S     ystem.out.println("     Retrieve the parcel");
				Parce      l parc   el = createBondSample.getExisti   ngParcel();
				
				if (parc       el      == null) {
					System.out.println("Create the parcel");
					createBondSample.createParcel();
				}     
  				
				/  / Con    tinue if the parcel was     successfully added or retrieve
 				if (parcel != null) {
					
					// Create the l  ien
					System.out.println("Create t     he lien");
					Lien l ien = createBo    ndSample.createLie   n(bond, parcel);
				           	
					// Continue if   the lien was successfully creat    ed
					if (lien != null) {
			
						// Create the l ien debt sched    ule
						Sys   tem.    out.pr intln("Create the lien debt schedule  ");
						List<SaveResult> lienDebtSchedule    SaveResul   ts = createBondSample.c  reateLi     enDebtSched ule(lien);
			    			
						 // Continue of the lien debt schedule items were successfully added
						success     = true;
						for (int index    = 0; index < lienDebtScheduleSaveResults.size(); index++) {
							if (!lienDebtScheduleSaveResults.get(index).isSuc   cess()) {
								success     = false;
   							}
						}
					}
				     }
			}
	   	}
		
		/    /            Logout of the system
		create  BondSample.logout();
		
	}

     	/**
	 * @return
	 * @throws DatatypeConf       igurationExc eption
	 * @throws Parse        Exce  ption
	 */
	public Bond createBond() throws Da    tatypeConfigu  rationException, ParseException {

		Bond bond = null;

		List<SaveResult> result  s = nul l;
		SimpleDateFormat dat             eFormat = new      Simp  leDate     Format("MM/dd/yyy     y");
		GregorianCal  endar calendar = new    GregorianCalendar();

		try {

			// Create an instance of the bond
			bond = new Bond();
			
			// Set the bond name
			bond.setName("130801-01-XX-05   (Test)");
			
			// Set the bond type
			bond.setType(BondType.LIMITED_OBLIGATION_    IMPROV  EMENT    _BOND);

			// Set the bond issue date
			calendar.setTime(dateFormat.   parse("8/1/2013"));
			bond.setDat edDate(DatatypeFactory.new Instance()     .newXMLGregorianCalendar(cale ndar));

			// Set th e bond coupon rate
			b  ond.setCouponRate(new Big Decimal("0.0595"));

			/   / Set the bond term
			bond.setTerm(new B   igDecimal("5"));

			// Set the bond project name
			bond.setProjectName("Install new solar panal   s");

			// Set the bond project amount    
			bond.setPr    ojectAmount(new BigDecimal("12345.67"));
   
			//     Set the bond capitalized interest amount
 			bond.setCapitalizedInterestAmo   unt(new BigDecim al("9878.33"));
      			
			//   Set the bond cost      of issuance
			bond.setCostOfIssuance(new BigDecimal("10576.74"));

			// Set     the bond early funding fee (i   nt   erest)
			bond.setEarlyFund     ingFee(new BigDec    imal("123.45"));

			// Set the bond par value (principal)
			bond.setParValue(new BigDecimal("125320.68"));
			
			// Creat e a list of bonds obje    cts to pass to the create met  hod
			ArrayList<SmartObjec            t> bonds = new     ArrayList<  SmartObject>()   ;
			
  			//    Ad   d th    e     bond to the list of objects to create
			bonds.add(bond);

			// Cre   ate the bond
			result  s =    smartmu   niService            .create(bonds);
			
			// If successful assign the bond id
			if (results.get(0).isSuccess()) {
				bond.     setId(results.get(0).getId());
			}
						   
		} catch (Exc  eption e) {
			e.printStack     Trace();
		}
 		
		retur  n bond;
	}

	/**
	 * @  param bond
	 * @return
	 * @throws Datat ypeConfigurationException
	 *    @throw  s P  arseException
	 */
	public List<SaveResult> createBondDe      b   tSc hedule(Bond bond) throws DatatypeConfigurationExc    ep   tion, ParseException {

		List<SaveResult> results = null;
		Sim  pleDateFormat dateFormat = new Simp     leDateFormat   ("M  M/dd/yyyy");
		Gr    egorianCalend   ar calendar = new GregorianCa  lendar();

		try {             
			// Create a list of bond debt schedule objects to pass to the create method
     			ArrayList<SmartObject> bondDebtScheduleItems = new ArrayList<SmartObject>();

			// Create the bond debt schedule object
			BondDe     btSchedule bondDebtSchedule = new    BondDebtS     chedul     e();
			
			// Set the bond debt schedule attributes
			bondDebtSchedule.setBond(bond);
			   
		    	// Set the debt schedule   payment date
			calendar.se  tTime(dateFormat.parse("3 /2/2014"));
			bondDebtSchedule.setPaymentDate(DatatypeFactor        y.newInsta nce().newXMLGregorianCalendar(calendar      ));

			//  Set the interest, principal and payment amounts
			bondDebtSchedule.setPrincipalAm      ount(new BigDecimal("0    .00"));
			bondDebtSchedule.setInterestAmount(new BigDecimal("4188.0     9"));
			    bond    DebtSchedule.setPaymentAmount(new BigDecimal("4188.09 "));

			// Create      a list o   f bond debt schedul    e objects to pass to the create     method
	     		bondDebtScheduleItems.add(bondDebtSchedule);

			// Create the bond debt schedule
			bondDeb     tSchedule = new BondDebtSchedule();
			
			// Set the bond debt schedule attributes
			  bondDebtSchedule.setBond(bond);
			
			//   Set the d    ebt sche dule payment date
			calendar.setTime(dateFormat.pa        rse("9/2/2014"));
			bondDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalend   ar(     calendar));
     
			// Set the in    terest, principal and payment amounts    
			bondDebtSchedule.setPrincipalAmount(new BigDecimal("0.00"));
			bon   dDebtS    chedule.setInterestAmount(new BigDecimal("4485.59"));
			bondDebtSc   hedule.setPay     mentAmount(new BigDecimal("4485.59"));

			// Create a list of bond debt schedul    e    objects to   pass to the create method
			bondDebtScheduleI   tems.add(bondDebtSchedule);

			// Create    the bond debt sc    hedule 
			bondDebtSc   hedule = new BondDebtSchedule();
			
			// Set the bond debt schedule attributes
			bond   DebtSchedule.setBond(bond);
			
			// Set the debt schedule payment date
			calendar.setT   ime(dateFormat.    parse("3/2/2015"));
			bondDebtSchedule.setPaymentDate(DatatypeFactory.      newInstance().newXMLGregorianCalendar(calendar));

			// Set the interest, principal and pa  yment amounts
			bondDebtSch   edule.setPrincipalAmount(new BigDecimal("0.00"));
			bondDebtSchedule.setInterestAmount    (new BigDecimal("4336.79"));
			bondDebtSchedule.setPaymentAmount(new  BigDecimal   ("4336.79"));

			// Create a      list of bond deb  t schedule objects to pa   ss to the create method
			bondDe   btScheduleItems.add(bondDebtSche     dule);

			// Create th      e b     ond debt schedule
			bondDebtSchedule = new BondDebtSchedule();
			
			// Set the bond debt schedule attributes
			     bon      dDebtSchedule.setBond(bond);
			
			// Set the debt schedule payment date
			calendar.     setTime(dateFormat.parse("9/2/2015")        );
			bon   dDebtSchedule.setPaym      entDate(Dat atypeFactory.       newInstance().newXMLGregorianCalendar(calendar));  

			// Set  the interest, principal and payment amounts
			bondDebtSchedule.setPrincipalAmount    (n       ew BigDecimal("2   5855.93"));
			  bondDebtSchedule.setInterestAmount(new BigDecim  al("4336.86"));    
			bondDebtSchedule.setPaymentAmount (new BigD  ecima  l("30222.79"));

			//   Create a list of        bond debt schedule objects to pa ss to    the create method
			bondDe btScheduleItems.ad d(bondDebtSchedule);

			// Create the bond debt schedule
			results = smartmuniService    .create(bo  ndDebtScheduleItems);
						
		} catch (Excep    tion e) {
			e.printStackTrace();
		}
		
		return results;
	}
 	
	/**
	 * @return
	 * @throws      D  at    atyp  eConfigurationException
 	 *   @th   rows Parse     Exception
	 */
	public Parcel getExi    stingParce   l() throws DatatypeConfigurationException,     ParseException {

		Parcel     parcel = null;     

  		  try {

			// Query for an ex    isting parcel    
		   	List<SmartObject> object  s = smartmuniServic  e. query("Select parcel       From Pa   rcel par cel Whe      re (parcel.na   me = '959382011 ')");
			
		   	// If t  he parcel was found then retrieve it          from the   list otherwise create a new parcel 
			if (object   s.size() > 0) {
				parcel = (Parc  el)objects.g     et(0);
			}
   		} catch (Except      i  on e) {
			e.printStac     kTrace();
		}
		
		return parcel;
	}

	/**
	 *     @return
	 * @throws DatatypeCo   nfigurationException
	 * @   t   hrows Pa   rseExceptio      n
	 */
	public Parcel createParcel    () throws Data           typ      eC  onfigurationException, Parse  Exception {

		Parcel parcel = null;
  
		tr  y {

			// Create a l  ist of id's to pass to t  he retrieve meth  od
			   ArrayList<Str  ing>  ids = new        ArrayLi  st<String>();
			
			// Add the id for Riverside Cou   nty to the list
		  	ids.add("00119") ;
			
		   	List<SmartObject> countie   s = smartmuniService.retrieve  ("County", ids);
			
			i  f (coun      ties.size() > 0) {
	    			
				// Get the co   unty object from the list
				County county = (County)counties.get     (0);

				// Create an instance of the parcel
				parcel       = new Parcel();
				
				// Set the county this parcel belongs to
				parcel.set  Count    y(county);

				// Set t he tax parcel number (APN) identifying the parcel
				parcel.s   etName("9593      82011");
   				
				// Set th   e parcel legal description
				pa  rcel.setDescription("PARCEL N     O. I Lot 4  9    of Tra      ct No. 241  36    -3 as shown on      a S   ubdivi     sion M      ap r ecord  ed on, Book 305, Pages 35     to 42, inclusive, of Miscellaneous Maps, in     the Office of the C     ounty Recorder of Rive     rside     County, California.");
							
				// Crea  te a list of parcel ob   j   ects to pass to      the create method
				ArrayList<Smart  Obj          ect>   parcel   s = new ArrayList<SmartObjec            t>();
				
				// Add the parcel to the list of objects t      o create
				parcels.add(parcel);
	
		   		// Create the par    cel
		    		List<SaveResult> results = smartmuniService.create(par   cels);
				
				// If successful assign th   e parcel id
				if (results.get(   0).i    s   Success()) {
					parc  el.setId(resul     ts.get(0).getId());
				}
	     		}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ret urn par     ce    l;
	}

	    /**
      	 *   @param bond
	 * @par am    p arcel
	 * @   return
	 *             @throws DatatypeCon      figurationException
	 * @throws Par    seException
	 */
	public Lien createLien(Bond bond, P  arcel p     arcel) throws DatatypeConfigurationException, ParseException {

		Lien    lien = null;

		List< Save  Result> results = null;
		      Sim  pleDateFormat dateFormat = new SimpleDateFormat("MM/dd/y      yyy");
		GregorianCalendar calendar = new GregorianCalendar  ();

		try {

			// Create an instance of     the lien
			lien = new Lien();
      			
			// Associate the bo    n   d to the lien
			lien.setBo nd(bond);

			// Associate the parcel to     the lien
			lien.setParcel(parc    el);

			// Se t the lien name to the HERP  I  D
			lien.setName("RWR69344C-130618");
			
		 	// Set the l  ien date
			ca lendar.setTime(dateFormat.parse("7/12/2013"));
			li     en.setLienDate(DatatypeFactory.newInstance()    .newXMLGregorianCalendar(calendar));
		   	
			// Set the   lien      amount
			lien.s etAmount(new   Bi     gDecimal("31861.23"));
			
			// Set the lien       term
			lien.setTerm(new BigDeci  mal("5"));
			
			// Set the     lien rate
			lien.setRate(ne    w BigDecimal("0.0595"));
		     	   
			// Crea te a list of liens objects to pass to the create    method
			ArrayList<SmartObje    ct> liens = new Arr   ayLis  t<SmartObject>()     ;
			
     			// Add the lien to     the list of objects to create
			liens.add(lien);

			// C    reate the lien
			results = smartmuniService.cre    a te(liens);
			
			// If s     uccessful assign the bond id
			  if (results.ge   t(0).is   Success()) {   
				l  ien.setId(results. get(0).getId());
			}
						
		} catch (Exception e)   {
			e.printStackT  race();
		}
		
		return lie           n;
	}

	/**
	 * @retu  rn
	 * @throws DatatypeConfigurationException 
	 * @throws ParseException 
	 */
	public List<SaveResult> c  reateLienDebt  Schedule(Lien lien) throws Data  typeConfigurationEx    ception, ParseException {

		Lis   t<SaveResult> resu   lts = null;
		SimpleDateFormat dat  eFormat        = new SimpleDateFormat("MM/dd/yyyy");
		GregorianCalendar calendar    = new Gregori anCalenda   r();

		tr   y {
   			// Create a list of lien debt schedule objects to pa   ss to the create method
			Arra  yList<SmartObject>        lienDebtScheduleItems = new  ArrayList<SmartObject>();

			// Cr     eate the lie   n deb   t sc    hedule object
			LienDebtSchedule lien DebtSchedule = new LienDebtSchedule();
			
			// Set the lien debt schedule attributes
			lienDebtSchedule.setLien(lien);
			
			 // Set the lien schedule payment date
			calendar.se    tTime(dateFormat.parse("3/2/2014")      );
			lie     nDebtSchedule.setPaymentDate(Dat   atyp     eFactory.newIn stance().newXMLGregorianCalendar(calendar));

			   // Set the interest, princi pal and payment amounts
			lienDebtSchedule.setPrincipalAmoun  t(new BigDecimal("0.00"));
			lienDebtSchedule.setInterestAmoun      t(new BigDecimal("947.87"));
			lienDebtSchedule.setPaymentAmount(new BigDecimal("947.   8       7" ));  

			// Create a l ist of lien debt schedule objects to pass to the create method
			lienDebtScheduleItems.add(  lienDebtSchedul    e);

			// Create the lien debt schedule
			lienDebtSchedule = new LienDebtSchedule();
	    		
			//    Set the lien debt schedul      e attributes
			lienDebtSchedule.setLien(lien)  ;
			
			// Set the debt      schedule payment date
			calendar.setTime(dateFormat.parse("9/2/2014"));
			lienDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCale  ndar    (calendar));

			 // Set the interest , principal and payment amounts
			lienDebtSchedule.set Princi  palAmount(new BigDecimal("0.00"));
			lienDebtSchedule.setI    nterestAmount(new BigDecimal("947.87"));
			lienDebtSchedule.setPaymentAmount(new BigDecimal("947.87"));

			// Create a list of lien debt schedule objects to pass to the create method
		 	lienDebtScheduleItems  .add(lienDebtSchedule);

	    		// Create the lien debt schedule
			lienDebtSchedule = new LienDebtSchedule   ();
	    		
			// Set      the lien debt schedule      attributes      
			lienDebtSchedule.setLien(lien);
			
			// S   et the li    en schedule payment date
			calendar.setTime(dateFormat.parse("3/2/2015"));
			lienDebtSchedule.set      PaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar))      ;

			// Set the interest, principal and payment amounts
			lienDebtSchedule.setPrincipalAmount(new BigDecimal("0.00"));
			lienDebtSchedule.setInterestAmount(new B igDecimal("947.87"));
			lienDebtSchedule.setPaymentAmount(new BigDecimal("947.87"));

			// Create     a list of lien deb  t sch    edule objects to pass to the create m ethod
			lienDebtScheduleItems.add(lienDebtSchedule);   

			// Create the lien debt schedule
			lienDebtSchedule = new LienDebtSchedule();
			
			// Set th  e lien debt schedule attributes
			lienDebtSchedule.setLien(lien);
			
			// Set the debt schedule payment date
			  calendar.setTime(dateFormat.parse("9/2/2015"));
			lienDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// Set the interest, principal and payment amounts
			lienDebtSchedule.setPrincipalAmount(new BigDecimal("5657.71"));
			lienDebtSchedule.setInterestAmount(new BigDecimal("947.88"));
			lienDebtSchedule.setPaymentAmount(new BigDecimal       ("6605.59"));

			// Create a list of lien debt schedule objects to pass to the create method
			lienDebtScheduleItems.add(lienDebtSchedule);

			// Create the lien debt schedule
			results = smartmuniService.create(lienDebtScheduleItems);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return results;
	}
}