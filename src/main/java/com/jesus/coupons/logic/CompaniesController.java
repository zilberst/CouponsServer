package com.jesus.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jesus.coupons.dao.ICompaniesDao;
import com.jesus.coupons.entities.Company;
import com.jesus.coupons.enums.ErrorTypes;
import com.jesus.coupons.exceptions.ApplicationException;


@Controller
public class CompaniesController {

	
	@Autowired
	private ICompaniesDao companiesDao;


	public CompaniesController() {
	}


	public void createCompany(Company company) throws ApplicationException {
		companyValidations(company);
		try {
			this.companiesDao.save(company);

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to create new company");
		}
	}


	public void updateCompany(Company company) throws ApplicationException {
		companyValidations(company);
		try {
			this.companiesDao.save(company);

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to update company");
		}
	}


	private void companyValidations(Company company) throws ApplicationException {
		Company	existingCompany = new Company();

		try {

			existingCompany = this.getCompany(company.getId());
		} catch (ApplicationException e) {

			//Allowing the null company through, we handle it later
			if(e.getErrorType().equals(ErrorTypes.INVALID_COMPANY)) {
			}
			else {
				throw e;
			}
		}
		
		//The null check is intended for when a company is created, so we avoid a null pointer exception
		//that wouldv'e occurred on the second check.
		//The second check is intended for updating a company. If the company isn't changing its name,
		//we must allow it to keep its name, and therefore we skip the name check.
		if (existingCompany == null || !company.getName().equals(existingCompany.getName())) {
			if (this.companiesDao.existsByName(company.getName())) {
				throw new ApplicationException(ErrorTypes.INVALID_COMPANY_NAME, "Name already exists.");
			}
		}
	}


	public void deleteCompany(long companyId) throws ApplicationException {
		try {
			this.companiesDao.deleteById(companyId);

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete company");
		}
	}


	public Company getCompany(long id) throws ApplicationException {
		try {
			return this.companiesDao.findById(id).get();
		} catch (Exception e) {
			if (e.getCause() == null) {
				throw new ApplicationException(ErrorTypes.INVALID_COMPANY, "Company not found");
			}
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get company");
		}
		
	}


	public List<Company> getAllCompanies() throws ApplicationException {

		List<Company> companies;

		try {
			companies = (List<Company>) this.companiesDao.findAll();
		} catch (Exception e) {

			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get companies");
		}

		if (companies.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_COMPANY, "Companies not found");
		}
		return companies;	
	}


	public boolean isCompanyExistsById(long id) throws ApplicationException {
		try {
			return this.companiesDao.existsById(id);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to check id");
		}
	}


}