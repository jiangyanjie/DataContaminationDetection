package Application.UnusedSources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import com.csvreader.CsvReader;

import Domain.Company;
import Domain.CompanyCollection;
import Domain.CompanyType;
import Domain.DnBData;
import Domain.ICompanyRepository;

public class ABWCSVCompanyRepository implements ICompanyRepository 
{
	private static Logger logger = Logger.getLogger(ABWCSVCompanyRepository.class.getName());

	private String sourceFile;
	
	public ABWCSVCompanyRepository(String sourceFile)
	{
		this.sourceFile = sourceFile;
	}
	
	@Override
	public Company getCompanyById(String id)
	{
		return getAllCompanies().getCompanyFromId(id);
	}
	
	@Override
	public CompanyCollection getUnregisteredDunsCompanies() 
	{
		// If it's abw then they're all unregistered ???
		// this method probably not used here 
		return getAllCompanies();
	}

	@Override
	public CompanyCollection getAllCompanies() 
	{
		return readAllCompaniesFromFile();
	}

	@Override
	public CompanyCollection getCompanyByDuns(int dunsNumber) 
	{
		// No need to do anything here
		// This is abw data and they don't have duns numbers !
		return getAllCompanies().getCompaniesFromDunsNumber(dunsNumber);
	}

	@Override
	public void saveCompany(Company c) 
	{
		// Won't implement as far as I can see, this i a source repository only.
		// only reason to save I can see is if I need to write back to source ?? or manage Last Changed Dates ?
	}

	@Override
	public void commitAllChanges() 
	{
		// Won't implement as far as I can see, this i a source repository only.
		// only reason to save I can see is if I need to write back to source ?? or manage Last Changed Dates ?
	}

	@Override
	public CompanyCollection getUnregisteredExperianCompanies() {
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
	
	private CompanyCollection readAllCompaniesFromFile()
	{
		CompanyCollection allCompanies = new CompanyCollection();
		try
		{
			CsvReader csvReader = new CsvReader(sourceFile);
			if(csvReader.readHeaders() == true)
			{
				while(csvReader.readRecord()==true)
				{	
					//apar_id,apar_name,address,place,province,Country_code,zip_code,telephone_1,comp_reg_no,acctype
					// TODO : constants for each header ?
					String id = csvReader.get("apar_id");
					String name = csvReader.get("apar_name");
					String companyRegNo = csvReader.get("comp_reg_no");
					

					CompanyType type = CompanyType.getCompanyTypeFromDescription(csvReader.get("acctype"));
					Company c = new Company(id, name, type);
					c.setCompanyRegistrationNumber(companyRegNo);
					
					String dunsString = csvReader.get("DUNS_NO");
					if( dunsString.length()>0)
					{
						int dunsNumber = Integer.parseInt(dunsString);
						DnBData data = new DnBData(dunsNumber);
						
						c.setDunnBradstreetData(data);
					}
					allCompanies.add(c);
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
		return allCompanies;
	}

}
