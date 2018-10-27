package io.seyon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.seyon.client.entity.ClientEntity;
import io.seyon.client.repository.ClientRepository;
import io.seyon.company.entity.Company;
import io.seyon.company.entity.User;
import io.seyon.company.model.CompanyModel;
import io.seyon.company.repository.CompanyRepository;
import io.seyon.company.service.CompanyService;
import io.seyon.invoice.entity.SACCode;
import io.seyon.invoice.repository.SACCodeRepository;

@Component
public class SeyonTestDataLoader implements CommandLineRunner {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CompanyService companyService;
	
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	SACCodeRepository sACCodeRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	 @Override
	 public void run(String... strings) throws Exception {
		 log.info("SeyonTestDataLoader loading test data...");
		 
		 createCompanyAndUser();
		 createSACCodes();
		 createClients();
	     
	    }
	 
	 @Transactional
	 public void createClients(){
		 
		 try {
			List<ClientEntity> clients= clientRepository.findByName("Client1");
			
			if(clients!=null && clients.size()>0){
				log.debug("Client1 already exists");
			}
			else {
				
		
				
				Company company=companyRepository.findByCompanyName("TESTCOMPANY");
				ClientEntity clientEntity = new ClientEntity();
				clientEntity.setActive("Active");
				clientEntity.setAddrLine1("22 client st");
				clientEntity.setAddrLine2("c area");
				clientEntity.setCity("Chennai");
				clientEntity.setState("TN");
				clientEntity.setPincode("600600");
				clientEntity.setCompanyId(company.getCompanyId());
				clientEntity.setCompanyType("Company");
				clientEntity.setContactPerson("TestClient");
				clientEntity.setCreateDate(new Date());
				clientEntity.setCreatedBy("TESTUSR1");
				clientEntity.setEmail("c1@c.com");
				
				clientEntity.setGstin("G12341234123456789");
				clientEntity.setName("Client1");
				clientEntity.setPan("P459459856325");
				/*clientEntity.setPhonePrimary("98045678901");
				clientEntity.setShipToAddrCity("Chennai");
				clientEntity.setShipToAddrLine1("22 client st");
				clientEntity.setShipToAddrLine2("c area");
				clientEntity.setShipToAddrPincode("600600");
				clientEntity.setShipToAddrState("TN");*/
				
			}
			
			
		} catch (Exception e) {
			log.error("Error in createClients test data loader:",e);
		}
		 
		 
	 }
	 
	 @Transactional
	 public void createSACCodes(){
		 
		 try {
			 List saccodes= sACCodeRepository.findBySACCode("SAC001");
			if(saccodes!=null && saccodes.size()>0)
			{
				log.debug("SAC001 already exists");
			}
			else
			{
			
			 SACCode sacCode = new SACCode();
			 
			 sacCode.setSacCode("SAC001");
			 sacCode.setCgstPercent(new Double("2.5"));
			 sacCode.setEndDate(null);
			 sacCode.setIgstPercent(new Double("2.5"));
			 sacCode.setSgstPercent(new Double("2.5"));
			 sacCode.setStartDate(null);
			 //sacCodes.add(sacCode);
			 sACCodeRepository.save(sacCode);
			}
			
			saccodes= sACCodeRepository.findBySACCode("SAC002");
			if(saccodes!=null && saccodes.size()>0)
			{
				 log.debug("SAC002 already exists");
			}
			else{
				
			 SACCode sacCode1 = new SACCode();
			 sacCode1.setSacCode("SAC002");
			 sacCode1.setCgstPercent(new Double("5"));
			 sacCode1.setEndDate(null);
			 sacCode1.setIgstPercent(new Double("5"));
			 sacCode1.setSgstPercent(new Double("5"));
			 sacCode1.setStartDate(null);
			 
			 
			 //sacCodes.add(sacCode1);
			 sACCodeRepository.save(sacCode1);
			}
			 
		} catch (NumberFormatException e) {
			log.error("Error in createSACCode test data loader:",e);
		}
	 }
	 
	 @Transactional
	 public void createCompanyAndUser(){
		 
		 try {
			 
			 Company company = companyRepository.findByCompanyName("TESTCOMPANY");
			 if(company!=null){
				 log.debug("Test company already exists with id - {}", company.getCompanyId());
			 }
			 else{
			 CompanyModel companyModel = new CompanyModel();
			 company = new Company();
			 
			 company.setAccountingType("");
			 company.setAccountName("TESTBANKACCT");
			 company.setAccountNo("TEST1234");
			 company.setAccountType("TESTSAVINGS");
			 company.setAddressLine1("123 Test st");
			 company.setAddressLine2("Test area");
			 company.setBankName("TEST BANK");
			 company.setBranch("TEST BRANCH");
		     company.setBranchIFSCCode("TEST001245");
		     company.setCity("CHENNAI");
		     company.setCompanyName("TESTCOMPANY");
		     company.setFaxNo("13131313133");
		     company.setGstNo("G1234567894564133");
		     company.setLogoImg("");
		     company.setOwnerName("TEST OWNER");
		     company.setPanNo("AFJPTEST1234");
		     company.setPhonePrimary("9874567890");
		     company.setPhoneSecondary("9874567891");
		     company.setPinCode("600500");
		     company.setPrimaryEmail("t1@t.com");
		     company.setSecondaryEmail("test2@test.com");
		     company.setServiceTaxRegNo("S1131616161");
		     company.setSignatureImg("");
		     company.setState("TN");
		     company.setTitle("TEST C");
		     company.setTanNo("T6161616161616");
		     
		     User userInfo = new User();
		     userInfo.setActive(true);
		     userInfo.setEmail("t1@t.com");
		     userInfo.setName("TESTUSR1");
		     userInfo.setPassword("pass");
		 
		     companyModel.setCompany(company);
		     companyModel.setUserInfo(userInfo);
		     
		     companyService.createCompanyAndUser(companyModel);
			 }
			 
		} catch (Exception e) {
			log.error("Error in  createCompanyAndUser test data loader:",e);
		}
		 
	 }

}
