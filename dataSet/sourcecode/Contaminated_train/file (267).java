package Application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import com.csvreader.CsvReader;

import Domain.AccountGroup;
import Domain.Address;
import Domain.Company;
import Domain.CompanyCollection;
import Domain.CompanyType;
import Domain.DnBData;
import Domain.DoubleDatedValue;
import Domain.ExperianData;
import Domain.ExperianLegalStatus;
import Domain.ICompanyRepository;

public class ABWXlsxCompanyRepository implements ICompanyRepository 
{
	private static Logger logger = Logger.getLogger(ABWXlsxCompanyRepository.class.getName());

	private String sourceFileMain;
	private String sourceFileOpenItems;
	private String sourceFileTotalSpend;
	private String sourceFileAvgDays;
	private String clientFilter;
	private int fiscalMonthsOffset = 0;
	
	private CompanyCollection allCompanies;

	public ABWXlsxCompanyRepository(String sourceFolder)
	{
		this(sourceFolder, "", 0);
	}
	public ABWXlsxCompanyRepository(String sourceFolder, String clientFilter)
	{
		this(sourceFolder, clientFilter, 0);
	}
	public ABWXlsxCompanyRepository(String sourceFolder, String clientFilter, int fiscalMonthsOffset)
	{
		if( !sourceFolder.endsWith("/"))
			sourceFolder+="/";
		this.sourceFileMain = sourceFolder + "CurrentBatch-Customer Supplier Listing.CSV";
		this.sourceFileOpenItems = sourceFolder + "CurrentBatch-Customer Supplier Open Items.CSV";
		this.sourceFileTotalSpend = sourceFolder + "CurrentBatch-Customer Supplier Total Spend.CSV";
		this.sourceFileAvgDays = sourceFolder + "CurrentBatch-Customer Supplier Avg Days.CSV";
		this.clientFilter=clientFilter;
		this.fiscalMonthsOffset = fiscalMonthsOffset;
	}
	
	@Override
	public CompanyCollection getUnregisteredDunsCompanies() 
	{
		// If it's abw then they're all unregistered ???
		// this method probably not used here 
		return null;
	}

	@Override
	public CompanyCollection getAllCompanies() 
	{
		if(allCompanies == null)
		{
			readAllCompaniesFromXlsx();
			readOpenItems();
			setDefaultOpenItems();
			readTotalSpend();
			readAvgDays();
		}
		return allCompanies;
	}

	@Override
	public Company getCompanyById(String id) {
		return getAllCompanies().getCompanyFromId(id);
	}

	@Override
	public CompanyCollection getCompanyByDuns(int dunsNumber) {
		return getAllCompanies().getCompaniesFromDunsNumber(dunsNumber);
	}

	@Override
	public void saveCompany(Company c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commitAllChanges() {
		// TODO Auto-generated method stub

	}

	@Override
	public CompanyCollection getUnregisteredExperianCompanies() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompanyCollection getCompaniesForInitialUpdateExperian() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompanyCollection getCompaniesForInitialUpdateDnB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompanyCollection getCompaniesByExperianReference(String reference) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void readAllCompaniesFromXlsx()
	{
		this.allCompanies = new CompanyCollection();
		try
		{
			CsvReader csvReader = new CsvReader(sourceFileMain);
			if(csvReader.readHeaders() == true)
			{
				Boolean hasdnbNumber = containsHeader("DUNS_NO", csvReader.getHeaders());
				Boolean hasExperianNumber = containsHeader("EXPERIAN_NO", csvReader.getHeaders());
				while(csvReader.readRecord()==true)
				{	
					//apar_id,apar_name,address,place,province,Country_code,zip_code,telephone_1,comp_reg_no,acctype
					// TODO : constants for each header ?
					String id = csvReader.get("apar_id");
					String name = csvReader.get("apar_name");
					String companyRegNo = csvReader.get("comp_reg_no");
					String groupCode = csvReader.get("apar_gr_id");
					String groupName = csvReader.get("group_name");
					String verticalMarket = csvReader.get("V_market");
					String client = csvReader.get("client");
					String postcode = csvReader.get("zip_code");
					String address1 = csvReader.get("address");
					
					// TODO: only import this client for now
					if(clientFilter.length()==0 || client.equalsIgnoreCase(clientFilter))
					{
						CompanyType type = CompanyType.getCompanyTypeFromDescription(csvReader.get("acctype"));
						Company c = new Company(id, name, type);
						c.setCompanyRegistrationNumber(companyRegNo);
						c.setVerticalMarket(verticalMarket);
						if( groupCode.length()>0)
							c.setAccountGroup(new AccountGroup(groupCode, groupName));
						
						if(postcode.length()>0||address1.length()>0)
						{
							Address address = new Address();
							address.getAddressLines().add(address1);
							address.setPostCode(postcode);
							c.setMainAddress(address);
						}
						
						// TODO : should I delete D&B data if the duns column exists, but data is empty - Dangerous ?
						String dunsString = csvReader.get("DUNS_NO");
						if(hasdnbNumber && dunsString.length()>0)
						{
							int dunsNumber = Integer.parseInt(dunsString);
							DnBData data = new DnBData(dunsNumber);
							c.setDunnBradstreetData(data);
						}

						// TODO : should I delete Experian data if the experian ref column exists, but is empty - Dangerous ?
						String experianRef = csvReader.get("EXPERIAN_NO");
						if( hasExperianNumber && experianRef.length()>0 )
						{
							String legalStatus = csvReader.get("LEGAL_STATUS");
							ExperianLegalStatus ls = ExperianLegalStatus.getExperianLegalStatusFromId(legalStatus);
							
							c.setExperianData(new ExperianData(experianRef, "", ls));
						}
						this.allCompanies.add(c);
					}
				}
			}
			csvReader.close();
		}
		catch(FileNotFoundException fnfe)
		{
			logger.warning(fnfe.getMessage());
		}
		catch(IOException ioe)
		{
			logger.warning(ioe.getMessage());
		}
		catch(Exception e)
		{
			logger.warning(e.getMessage());
		}
	}
	
	private Boolean containsHeader(String header, String[] headers)
	{
		for(int i=0;i<headers.length;i++)
		{
			if(headers[i].equalsIgnoreCase(header))
				return true;
		}
		return false;
	}
	
	private void readOpenItems()
	{
		try
		{
			CsvReader csvReader = new CsvReader(sourceFileOpenItems);
			if(csvReader.readHeaders() == true)
			{
				while(csvReader.readRecord()==true)
				{	
					//apar_id,apar_name,address,place,province,Country_code,zip_code,telephone_1,comp_reg_no,acctype
					// TODO : constants for each header ?
					String id = csvReader.get("apar_id");
					String client = csvReader.get("client");
					String openAmount = csvReader.get("rest_amt");
					
					// if client filter passed in, then use it
					if(clientFilter.length()==0 || client.equalsIgnoreCase(clientFilter))
					{
						Company c = allCompanies.getCompanyFromId(id);
						if(c!=null)
						{
							double val = XmlHelper.getDoubleFromXmlString(openAmount);
							c.getOpenBalanceCollection().upsert(new DoubleDatedValue(Calendar.getInstance().getTime(), val));
						}
					}
				}
			}
			csvReader.close();
		}
		catch(FileNotFoundException fnfe)
		{
			logger.warning(fnfe.getMessage());
		}
		catch(IOException ioe)
		{
			logger.warning(ioe.getMessage());
		}
		catch(Exception e)
		{
			logger.warning(e.getMessage());
		}
	}
	
	private void setDefaultOpenItems()
	{
		for(Company c : allCompanies)
		{
			if(c.getOpenBalance()==null)
			{
				c.getOpenBalanceCollection().upsert(new DoubleDatedValue(Calendar.getInstance().getTime(), 0.0));
			}
		}
	}
	
	private void readTotalSpend()
	{
		try
		{
			CsvReader csvReader = new CsvReader(sourceFileTotalSpend);
			if(csvReader.readHeaders() == true)
			{
				while(csvReader.readRecord()==true)
				{
					//apar_id,apar_name,address,place,province,Country_code,zip_code,telephone_1,comp_reg_no,acctype
					// TODO : constants for each header ?
					String id = csvReader.get("text apar_id");
					String client = csvReader.get("client");
					String amount = csvReader.get("amt");
					String period = csvReader.get("text period");
					period += "01";
					// TODO: only import this client for now
					if(clientFilter.length()==0 || client.equalsIgnoreCase(clientFilter))
					{
						Company c = allCompanies.getCompanyFromId(id);
						if(c!=null)
						{
							try
							{
								Date date = getDateWithOffset(period);
								double val = XmlHelper.getDoubleFromXmlString(amount);
								c.getTotalSpendCollection().upsert(new DoubleDatedValue(date, val));
							}
							catch(ParseException pe)
							{
								logger.warning("Failed to read row - Parse exception : " + csvReader.getRawRecord());
							}
						}
					}
				}
			}
			csvReader.close();
		}
		catch(FileNotFoundException fnfe)
		{
			logger.warning(fnfe.getMessage());
		}
		catch(IOException ioe)
		{
			logger.warning(ioe.getMessage());
		}
		catch(Exception e)
		{
			logger.warning(e.getMessage());
		}
	}
	
	private void readAvgDays()
	{
		try
		{
			CsvReader csvReader = new CsvReader(sourceFileAvgDays);
			if(csvReader.readHeaders() == true)
			{
				while(csvReader.readRecord()==true)
				{	
					//apar_id,apar_name,address,place,province,Country_code,zip_code,telephone_1,comp_reg_no,acctype
					// TODO : constants for each header ?
					String id = csvReader.get("apar_id");
					String client = csvReader.get("client");
					String avg = csvReader.get("avg");
					
					// TODO: only import this client for now
					if(clientFilter.length()==0 || client.equalsIgnoreCase(clientFilter))
					{
						Company c = allCompanies.getCompanyFromId(id);
						if(c!=null)
						{
							double val = XmlHelper.getDoubleFromXmlString(avg);
							c.getAverageDaysToPaymentCollection().upsert(new DoubleDatedValue(Calendar.getInstance().getTime(), val));
						}
					}
				}
			}
			csvReader.close();
		}
		catch(FileNotFoundException fnfe)
		{
			logger.warning(fnfe.getMessage());
		}
		catch(IOException ioe)
		{
			logger.warning(ioe.getMessage());
		}
		catch(Exception e)
		{
			logger.warning(e.getMessage());
		}
	}
	
	private Date getDateWithOffset(String stringDate) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatter.parse(stringDate));
		cal.add(Calendar.MONTH, fiscalMonthsOffset);
		return cal.getTime();
	}
}
