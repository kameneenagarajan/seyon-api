package io.seyon.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class CompanyView {

	@Id
	Long companyId;

	@Column
	String companyName;

	@Column
	String ownerName;

	@Column
	String addressLine1;

	@Column
	String addressLine2;

	@Column
	String city;

	@Column
	String state;

	@Column
	String pinCode;

	@Column
	String phonePrimary;

	@Column
	String phoneSecondary;

	@Column
	String faxNo;

	@Column
	String tanNo;

	@Column
	String gstNo;

	@Column
	String panNo;

	@Column
	String serviceTaxRegNo;

	@Column
	String accountingType;

	@Lob
	String logoImg;

	@Lob
	String signatureImg;

	@Column
	String primaryEmail;

	@Column
	String secondaryEmail;

	@Column
	String bankName;

	@Column
	String branch;

	@Column
	String branchIFSCCode;

	@Column
	String accountNo;

	@Column
	String accountName;

	@Column
	String accountType;

	@Column
	String swiftCode;
	
	@Column
	String termsConditions;
	
	@Column
	String title;

	public String getCompanyName() {
		return companyName;
	}

	protected void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	protected void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	protected void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	protected void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	protected void setState(String state) {
		this.state = state;
	}

	public String getPinCode() {
		return pinCode;
	}

	protected void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getTanNo() {
		return tanNo;
	}

	protected void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}

	public String getGstNo() {
		return gstNo;
	}

	protected void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getPanNo() {
		return panNo;
	}

	protected void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public Long getCompanyId() {
		return companyId;
	}

	protected void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	protected void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getPhonePrimary() {
		return phonePrimary;
	}

	protected void setPhonePrimary(String phonePrimary) {
		this.phonePrimary = phonePrimary;
	}

	public String getPhoneSecondary() {
		return phoneSecondary;
	}

	protected void setPhoneSecondary(String phoneSecondary) {
		this.phoneSecondary = phoneSecondary;
	}

	public String getFaxNo() {
		return faxNo;
	}

	protected void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getServiceTaxRegNo() {
		return serviceTaxRegNo;
	}

	protected void setServiceTaxRegNo(String serviceTaxRegNo) {
		this.serviceTaxRegNo = serviceTaxRegNo;
	}

	public String getAccountingType() {
		return accountingType;
	}

	protected void setAccountingType(String accountingType) {
		this.accountingType = accountingType;
	}

	public String getLogoImg() {
		return logoImg;
	}

	protected void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	public String getSignatureImg() {
		return signatureImg;
	}

	protected void setSignatureImg(String signatureImg) {
		this.signatureImg = signatureImg;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	protected void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	protected void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public String getBankName() {
		return bankName;
	}

	protected void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranch() {
		return branch;
	}

	protected void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBranchIFSCCode() {
		return branchIFSCCode;
	}

	protected void setBranchIFSCCode(String branchIFSCCode) {
		this.branchIFSCCode = branchIFSCCode;
	}

	public String getAccountNo() {
		return accountNo;
	}

	protected void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	protected void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	protected void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	protected void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", companyName=" + companyName + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city + ", state=" + state + ", pinCode=" + pinCode
				+ ", tanNo=" + tanNo + ", gstNo=" + gstNo + ", panNo=" + panNo + ", logo= **** ]";
	}

	public String getTermsConditions() {
		return termsConditions;
	}

	protected void setTermsConditions(String termsConditions) {
		this.termsConditions = termsConditions;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// TODO: Need for header and footer

}
