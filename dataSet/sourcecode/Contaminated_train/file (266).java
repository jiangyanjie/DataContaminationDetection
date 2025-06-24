package Application;

import java.util.logging.Logger;

import Domain.Company;
import Domain.CompanyCollection;
import Domain.ICompanyRepository;

public class ABWUpdateHandler 
{

	private static Logger logger = Logger.getLogger(ABWUpdateHandler.class.getName());
	private ICompanyRepository u4baCompanyRepository;
	private ICompanyRepository abwCompanyRepository;

	public ABWUpdateHandler(ICompanyRepository u4baCompanyRepository, ICompanyRepository abwCompanyRepository)
	{
		this.u4baCompanyRepository = u4baCompanyRepository;
		this.abwCompanyRepository = abwCompanyRepository;
	}
	
	/*
		1. readallcompanies from source
		2. saveallcompanies to target
		3. ?		
	*/
	public void getUpdates()
	{
		int companiesUpdated = 0, companiesAdded = 0;
		CompanyCollection abwCompanies = abwCompanyRepository.getAllCompanies();
		for(Company sourceCompany : abwCompanies)
		{
			Company targetCompany = u4baCompanyRepository.getCompanyById(sourceCompany.getId());
			
			if( targetCompany == null)
			{
				// if company doesn't already exist, then add
				u4baCompanyRepository.saveCompany(sourceCompany);
				companiesAdded++;
			}
			else
			{
				// TODO: update existing, don't duplicate the FACTS !!!!
				// this is just wrong !!!
				updateCompany(targetCompany, sourceCompany);
				u4baCompanyRepository.saveCompany(targetCompany);
				companiesUpdated++;
			}
		}
		u4baCompanyRepository.commitAllChanges();
		logger.info("Companies read from ABW = " + String.valueOf(abwCompanies.size()) + ", companies updated = " + String.valueOf(companiesUpdated) + ", companies added = " + String.valueOf(companiesAdded));
	}
	
	private void updateCompany(Company target, Company source)
	{
		// Update agresso sourced data only
		target.setAccountGroup(source.getAccountGroup());
		target.setCompanyRegistrationNumber(source.getCompanyRegistrationNumber());
		target.setName(source.getName());
		target.setVerticalMarket(source.getVerticalMarket());
		// We only add average days to payment or opening balance if the value we just read is different to the current value
		// this is to prevent a new fact every day, even when the data hasn't changed
		if(target.getOpenBalance()==null || source.getOpenBalance()==null || Math.abs(target.getOpenBalance().getValue()-source.getOpenBalance().getValue()) > 0.001)
		{
			target.getOpenBalanceCollection().upsert(source.getOpenBalanceCollection()); 
		}
		if(target.getAverageDaysToPayment()==null || source.getAverageDaysToPayment()==null || Math.abs(target.getAverageDaysToPayment().getValue()-source.getAverageDaysToPayment().getValue()) >0.001)
		{
			target.getAverageDaysToPaymentCollection().upsert(source.getAverageDaysToPaymentCollection());
		}
		target.getTotalSpendCollection().upsert(source.getTotalSpendCollection());
	}
}
