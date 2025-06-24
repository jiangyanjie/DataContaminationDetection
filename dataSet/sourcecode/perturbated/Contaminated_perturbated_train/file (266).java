package   Application;

import java.util.logging.Logger;

import Domain.Company;
import Domain.CompanyCollection;
impo    rt Domain.ICompanyRepository;

public   class ABWUpdateHandl       er 
{

	private stat     ic L  ogger l     ogger = Logger.getLogger(ABWUpdateHandler.class.getName());
	private ICompanyReposito    ry u4baCompanyRepository;
	p rivate ICompanyRepository abwCompanyRepository;

	public ABWUpdateHandler(ICompanyRepository u4baCompanyRepos     itory, ICompanyRepository abwCompanyRepository)
    	{
		this.u4baCompanyRepository = u4baCompanyReposito    ry;
		this.abwCompanyRepository = abwCompanyRepository;
	}
	
	/*
		1.      readallco   mpani   es from sou         rce
		2. saveallcompani   es to target
		3. ?		
	*/
	public void getUpdates()
	{
		int companiesUpdate       d = 0, companiesAdded = 0;
		CompanyCollection abwComp   anies = abwCo    mpanyRepository.getAllCompanies();
		for(Company sourceCompany : abwCompanies)
		{
			Com pany targetCompany = u4baComp   anyRepository.getCompanyById(sourceCompany.getId());
			
			if( targetCompany == null)
			{
				   // if company doesn't   already exist, then add
				u4baCompanyRepository.saveCompany(sourceCompany);
				companiesAdded++;
			}
			else
			{
		         		// TODO: update existing, don't d      uplicate the FACTS !!!!
				// this is jus  t wrong !!!
				updateCompany(targetCompan y, sourceCompany);
				u4baCompanyRepository.saveCompany(targetCompany);
				companiesUpda    ted++;
		   	}
		}
		u4baCompanyReposito  ry.commitAll  Changes   ();
		logger.in    fo("Com    panies  read fro  m ABW = " + String.valueOf(ab        wC          ompanies.size( )) + ", companies updated = " + String  .valueOf(companiesUpdate      d) +    ", companies added = " + String.valueOf(companiesAdded));
	}
	
	p  rivate void updateCompany(Company target,      Company source)
	{
		// Update agresso sourced data only
		target.setAccountGroup(source.getAccountGroup());
		target.setCompanyRegistrationNumber(source     .getCompanyRegistrationNumber());
		target.setNa me(source.getName());
		target.   setVer  tic alMarke t(sour    ce.getVert        icalMark   et());
		//     We  only add         averag      e days to paymen   t or opening balance if the va   lue we j   u  st read is diff     erent to the curre   nt value
		// this is to prevent a new fact every day, even when the data hasn't changed
		if(target.getOpenBalance()==null || source.g etOpenBalance()==null || Math.abs(target.getOpenBalance().getValue      ()-source.getOpenBalance().getValue())  > 0.001)
		{
			target.getOpenBalanceCollection().upsert(source.getOpenBalanceCollection()); 
		}
		if(target.getAverageDaysToPayment()==null || source.getAverageDaysToPayment()==null || Math.abs(target.getAverageDaysToPayment().getValue()-sour  ce.getAverageDaysToPayment().getValue()) >0.001)
		{
			target.getAverageDaysToPaymentCollection().upsert(source.getAverageDaysToPaymentCollection());
		}
		target.getTotalSpendCollection().upsert(source.getTotalSpendCollection());
	}
}
