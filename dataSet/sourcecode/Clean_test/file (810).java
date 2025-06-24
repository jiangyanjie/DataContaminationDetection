package com.smartmuni.client;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.smartmuni.services.soap.SaveResult;
import com.smartmuni.services.soap.smartobject.Bond;
import com.smartmuni.services.soap.smartobject.BondDebtSchedule;
import com.smartmuni.services.soap.smartobject.BondType;
import com.smartmuni.services.soap.smartobject.County;
import com.smartmuni.services.soap.smartobject.Lien;
import com.smartmuni.services.soap.smartobject.LienDebtSchedule;
import com.smartmuni.services.soap.smartobject.Parcel;
import com.smartmuni.services.soap.smartobject.SmartObject;

public class CreateBondSample extends BaseSample {

	public static void main(String[] args) throws DatatypeConfigurationException, ParseException {
		
		CreateBondSample createBondSample = new CreateBondSample();
				
		// Login to the system
		System.out.println("Login to smartmuni");
		createBondSample.login(args[0], args[1], args[2]);
		
		// Create the bond
		System.out.println("Create the bond");
		Bond bond = createBondSample.createBond();

		// Continue if the bond was successfully created
		if (bond != null) {
			
			// Create the bond debt schedule
			System.out.println("Create the bond debt schedule");
			List<SaveResult> bondDebtScheduleSaveResults = createBondSample.createBondDebtSchedule(bond);

			// Continue of the bond debt schedule items were successfully added
			boolean success = true;
			for (int index = 0; index < bondDebtScheduleSaveResults.size(); index++) {
				if (!bondDebtScheduleSaveResults.get(index).isSuccess()) {
					success = false;
				}
			}

			// Continue if the debt schedule was added (checking just the first one here, but you might check them all)
			if (success) {
				
				// Retrieve the parcel if it already exists in the system
				System.out.println("Retrieve the parcel");
				Parcel parcel = createBondSample.getExistingParcel();
				
				if (parcel == null) {
					System.out.println("Create the parcel");
					createBondSample.createParcel();
				}
				
				// Continue if the parcel was successfully added or retrieve
				if (parcel != null) {
					
					// Create the lien
					System.out.println("Create the lien");
					Lien lien = createBondSample.createLien(bond, parcel);
					
					// Continue if the lien was successfully created
					if (lien != null) {
			
						// Create the lien debt schedule
						System.out.println("Create the lien debt schedule");
						List<SaveResult> lienDebtScheduleSaveResults = createBondSample.createLienDebtSchedule(lien);
						
						// Continue of the lien debt schedule items were successfully added
						success = true;
						for (int index = 0; index < lienDebtScheduleSaveResults.size(); index++) {
							if (!lienDebtScheduleSaveResults.get(index).isSuccess()) {
								success = false;
							}
						}
					}
				}
			}
		}
		
		// Logout of the system
		createBondSample.logout();
		
	}

	/**
	 * @return
	 * @throws DatatypeConfigurationException
	 * @throws ParseException
	 */
	public Bond createBond() throws DatatypeConfigurationException, ParseException {

		Bond bond = null;

		List<SaveResult> results = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		GregorianCalendar calendar = new GregorianCalendar();

		try {

			// Create an instance of the bond
			bond = new Bond();
			
			// Set the bond name
			bond.setName("130801-01-XX-05 (Test)");
			
			// Set the bond type
			bond.setType(BondType.LIMITED_OBLIGATION_IMPROVEMENT_BOND);

			// Set the bond issue date
			calendar.setTime(dateFormat.parse("8/1/2013"));
			bond.setDatedDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// Set the bond coupon rate
			bond.setCouponRate(new BigDecimal("0.0595"));

			// Set the bond term
			bond.setTerm(new BigDecimal("5"));

			// Set the bond project name
			bond.setProjectName("Install new solar panals");

			// Set the bond project amount
			bond.setProjectAmount(new BigDecimal("12345.67"));

			// Set the bond capitalized interest amount
			bond.setCapitalizedInterestAmount(new BigDecimal("9878.33"));
			
			// Set the bond cost of issuance
			bond.setCostOfIssuance(new BigDecimal("10576.74"));

			// Set the bond early funding fee (interest)
			bond.setEarlyFundingFee(new BigDecimal("123.45"));

			// Set the bond par value (principal)
			bond.setParValue(new BigDecimal("125320.68"));
			
			// Create a list of bonds objects to pass to the create method
			ArrayList<SmartObject> bonds = new ArrayList<SmartObject>();
			
			// Add the bond to the list of objects to create
			bonds.add(bond);

			// Create the bond
			results = smartmuniService.create(bonds);
			
			// If successful assign the bond id
			if (results.get(0).isSuccess()) {
				bond.setId(results.get(0).getId());
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bond;
	}

	/**
	 * @param bond
	 * @return
	 * @throws DatatypeConfigurationException
	 * @throws ParseException
	 */
	public List<SaveResult> createBondDebtSchedule(Bond bond) throws DatatypeConfigurationException, ParseException {

		List<SaveResult> results = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		GregorianCalendar calendar = new GregorianCalendar();

		try {
			// Create a list of bond debt schedule objects to pass to the create method
			ArrayList<SmartObject> bondDebtScheduleItems = new ArrayList<SmartObject>();

			// Create the bond debt schedule object
			BondDebtSchedule bondDebtSchedule = new BondDebtSchedule();
			
			// Set the bond debt schedule attributes
			bondDebtSchedule.setBond(bond);
			
			// Set the debt schedule payment date
			calendar.setTime(dateFormat.parse("3/2/2014"));
			bondDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// Set the interest, principal and payment amounts
			bondDebtSchedule.setPrincipalAmount(new BigDecimal("0.00"));
			bondDebtSchedule.setInterestAmount(new BigDecimal("4188.09"));
			bondDebtSchedule.setPaymentAmount(new BigDecimal("4188.09"));

			// Create a list of bond debt schedule objects to pass to the create method
			bondDebtScheduleItems.add(bondDebtSchedule);

			// Create the bond debt schedule
			bondDebtSchedule = new BondDebtSchedule();
			
			// Set the bond debt schedule attributes
			bondDebtSchedule.setBond(bond);
			
			// Set the debt schedule payment date
			calendar.setTime(dateFormat.parse("9/2/2014"));
			bondDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// Set the interest, principal and payment amounts
			bondDebtSchedule.setPrincipalAmount(new BigDecimal("0.00"));
			bondDebtSchedule.setInterestAmount(new BigDecimal("4485.59"));
			bondDebtSchedule.setPaymentAmount(new BigDecimal("4485.59"));

			// Create a list of bond debt schedule objects to pass to the create method
			bondDebtScheduleItems.add(bondDebtSchedule);

			// Create the bond debt schedule
			bondDebtSchedule = new BondDebtSchedule();
			
			// Set the bond debt schedule attributes
			bondDebtSchedule.setBond(bond);
			
			// Set the debt schedule payment date
			calendar.setTime(dateFormat.parse("3/2/2015"));
			bondDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// Set the interest, principal and payment amounts
			bondDebtSchedule.setPrincipalAmount(new BigDecimal("0.00"));
			bondDebtSchedule.setInterestAmount(new BigDecimal("4336.79"));
			bondDebtSchedule.setPaymentAmount(new BigDecimal("4336.79"));

			// Create a list of bond debt schedule objects to pass to the create method
			bondDebtScheduleItems.add(bondDebtSchedule);

			// Create the bond debt schedule
			bondDebtSchedule = new BondDebtSchedule();
			
			// Set the bond debt schedule attributes
			bondDebtSchedule.setBond(bond);
			
			// Set the debt schedule payment date
			calendar.setTime(dateFormat.parse("9/2/2015"));
			bondDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// Set the interest, principal and payment amounts
			bondDebtSchedule.setPrincipalAmount(new BigDecimal("25855.93"));
			bondDebtSchedule.setInterestAmount(new BigDecimal("4336.86"));
			bondDebtSchedule.setPaymentAmount(new BigDecimal("30222.79"));

			// Create a list of bond debt schedule objects to pass to the create method
			bondDebtScheduleItems.add(bondDebtSchedule);

			// Create the bond debt schedule
			results = smartmuniService.create(bondDebtScheduleItems);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	/**
	 * @return
	 * @throws DatatypeConfigurationException
	 * @throws ParseException
	 */
	public Parcel getExistingParcel() throws DatatypeConfigurationException, ParseException {

		Parcel parcel = null;

		try {

			// Query for an existing parcel
			List<SmartObject> objects = smartmuniService.query("Select parcel From Parcel parcel Where (parcel.name = '959382011')");
			
			// If the parcel was found then retrieve it from the list otherwise create a new parcel 
			if (objects.size() > 0) {
				parcel = (Parcel)objects.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return parcel;
	}

	/**
	 * @return
	 * @throws DatatypeConfigurationException
	 * @throws ParseException
	 */
	public Parcel createParcel() throws DatatypeConfigurationException, ParseException {

		Parcel parcel = null;

		try {

			// Create a list of id's to pass to the retrieve method
			ArrayList<String> ids = new ArrayList<String>();
			
			// Add the id for Riverside County to the list
			ids.add("00119");
			
			List<SmartObject> counties = smartmuniService.retrieve("County", ids);
			
			if (counties.size() > 0) {
				
				// Get the county object from the list
				County county = (County)counties.get(0);

				// Create an instance of the parcel
				parcel = new Parcel();
				
				// Set the county this parcel belongs to
				parcel.setCounty(county);

				// Set the tax parcel number (APN) identifying the parcel
				parcel.setName("959382011");
				
				// Set the parcel legal description
				parcel.setDescription("PARCEL NO. I Lot 49 of Tract No. 24136-3 as shown on a Subdivision Map recorded on, Book 305, Pages 35 to 42, inclusive, of Miscellaneous Maps, in the Office of the County Recorder of Riverside County, California.");
							
				// Create a list of parcel objects to pass to the create method
				ArrayList<SmartObject> parcels = new ArrayList<SmartObject>();
				
				// Add the parcel to the list of objects to create
				parcels.add(parcel);
	
				// Create the parcel
				List<SaveResult> results = smartmuniService.create(parcels);
				
				// If successful assign the parcel id
				if (results.get(0).isSuccess()) {
					parcel.setId(results.get(0).getId());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return parcel;
	}

	/**
	 * @param bond
	 * @param parcel
	 * @return
	 * @throws DatatypeConfigurationException
	 * @throws ParseException
	 */
	public Lien createLien(Bond bond, Parcel parcel) throws DatatypeConfigurationException, ParseException {

		Lien lien = null;

		List<SaveResult> results = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		GregorianCalendar calendar = new GregorianCalendar();

		try {

			// Create an instance of the lien
			lien = new Lien();
			
			// Associate the bond to the lien
			lien.setBond(bond);

			// Associate the parcel to the lien
			lien.setParcel(parcel);

			// Set the lien name to the HERP ID
			lien.setName("RWR69344C-130618");
			
			// Set the lien date
			calendar.setTime(dateFormat.parse("7/12/2013"));
			lien.setLienDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			
			// Set the lien amount
			lien.setAmount(new BigDecimal("31861.23"));
			
			// Set the lien term
			lien.setTerm(new BigDecimal("5"));
			
			// Set the lien rate
			lien.setRate(new BigDecimal("0.0595"));
			
			// Create a list of liens objects to pass to the create method
			ArrayList<SmartObject> liens = new ArrayList<SmartObject>();
			
			// Add the lien to the list of objects to create
			liens.add(lien);

			// Create the lien
			results = smartmuniService.create(liens);
			
			// If successful assign the bond id
			if (results.get(0).isSuccess()) {
				lien.setId(results.get(0).getId());
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lien;
	}

	/**
	 * @return
	 * @throws DatatypeConfigurationException 
	 * @throws ParseException 
	 */
	public List<SaveResult> createLienDebtSchedule(Lien lien) throws DatatypeConfigurationException, ParseException {

		List<SaveResult> results = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		GregorianCalendar calendar = new GregorianCalendar();

		try {
			// Create a list of lien debt schedule objects to pass to the create method
			ArrayList<SmartObject> lienDebtScheduleItems = new ArrayList<SmartObject>();

			// Create the lien debt schedule object
			LienDebtSchedule lienDebtSchedule = new LienDebtSchedule();
			
			// Set the lien debt schedule attributes
			lienDebtSchedule.setLien(lien);
			
			// Set the lien schedule payment date
			calendar.setTime(dateFormat.parse("3/2/2014"));
			lienDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// Set the interest, principal and payment amounts
			lienDebtSchedule.setPrincipalAmount(new BigDecimal("0.00"));
			lienDebtSchedule.setInterestAmount(new BigDecimal("947.87"));
			lienDebtSchedule.setPaymentAmount(new BigDecimal("947.87"));

			// Create a list of lien debt schedule objects to pass to the create method
			lienDebtScheduleItems.add(lienDebtSchedule);

			// Create the lien debt schedule
			lienDebtSchedule = new LienDebtSchedule();
			
			// Set the lien debt schedule attributes
			lienDebtSchedule.setLien(lien);
			
			// Set the debt schedule payment date
			calendar.setTime(dateFormat.parse("9/2/2014"));
			lienDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// Set the interest, principal and payment amounts
			lienDebtSchedule.setPrincipalAmount(new BigDecimal("0.00"));
			lienDebtSchedule.setInterestAmount(new BigDecimal("947.87"));
			lienDebtSchedule.setPaymentAmount(new BigDecimal("947.87"));

			// Create a list of lien debt schedule objects to pass to the create method
			lienDebtScheduleItems.add(lienDebtSchedule);

			// Create the lien debt schedule
			lienDebtSchedule = new LienDebtSchedule();
			
			// Set the lien debt schedule attributes
			lienDebtSchedule.setLien(lien);
			
			// Set the lien schedule payment date
			calendar.setTime(dateFormat.parse("3/2/2015"));
			lienDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// Set the interest, principal and payment amounts
			lienDebtSchedule.setPrincipalAmount(new BigDecimal("0.00"));
			lienDebtSchedule.setInterestAmount(new BigDecimal("947.87"));
			lienDebtSchedule.setPaymentAmount(new BigDecimal("947.87"));

			// Create a list of lien debt schedule objects to pass to the create method
			lienDebtScheduleItems.add(lienDebtSchedule);

			// Create the lien debt schedule
			lienDebtSchedule = new LienDebtSchedule();
			
			// Set the lien debt schedule attributes
			lienDebtSchedule.setLien(lien);
			
			// Set the debt schedule payment date
			calendar.setTime(dateFormat.parse("9/2/2015"));
			lienDebtSchedule.setPaymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// Set the interest, principal and payment amounts
			lienDebtSchedule.setPrincipalAmount(new BigDecimal("5657.71"));
			lienDebtSchedule.setInterestAmount(new BigDecimal("947.88"));
			lienDebtSchedule.setPaymentAmount(new BigDecimal("6605.59"));

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