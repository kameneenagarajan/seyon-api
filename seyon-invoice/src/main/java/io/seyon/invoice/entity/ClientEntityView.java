package io.seyon.invoice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

@Entity
@Table(name="client_entity")
public class ClientEntityView implements Serializable {

	private static final long serialVersionUID = 2658424664266410103L;
	
	@Id
	Long id;
	
	@NonNull
	@Size(min = 3, message = "Please enter the Client name")
	@Column(nullable=false)
	String name;
	
	@NonNull
	@Size(min = 3, message = "Please enter the Address")
	@Column(nullable=false)
	String addrLine1;
	
	@Column
	String addrLine2;
	
	@NonNull
	@Size(min = 3, message = "Please enter the City")
	@Column(nullable=false)
	String city;
	
	@NonNull
	@Size(min = 2, message = "Please enter the State")
	@Column(nullable=false)
	String state;
	
	@NonNull
	@Size(min = 6, max = 6, message = "Please enter the Pincode")
	@Column(name="pin_code", nullable=false)
	String pincode;

	@NonNull
	@Size(min = 10, max = 12, message = "Please enter the Primary phone")
	@Column(nullable=false)
	String phonePrimary;

	@Column
	String phoneSecondary;
	
	@Column
	String faxNo;

	@NonNull
	@Size(min = 10, message = "Please enter the PAN")
	@Column(nullable=false)
	String pan;
	
	@NonNull
	@Size(min = 15,max=20, message = "Please enter the GSTIN")
	@Column(nullable=false)
	String gstin;
	
	@Size(min=15,max=20,message="Please enter the Primary phone" )
	@Column
	String ServiceTaxRegNo;
	

	@Size(min=3,message="Please enter the Primary phone" )
	@Column
	String accountingType;
	

	@Lob
	@Column(nullable=true)
	String logoImg;
	
	@Lob
	@Column
	String signatureImg;
	
	@NonNull
	@Email(message="Please enter the valid email id")
	@Column
	String email;
	
	@NonNull
	@Size(min=3,message="Please enter the Bank Name")
	@Column(nullable=true)
	String bankName;
	
	@NonNull
	@Size(min=5,max=20,message="Please enter the Bank Account Number")
	@Column(nullable=true)
	String bankAcctNumber;
	
	@NonNull
	@Size(min=9,max=15,message="Please enter the IFSC CODE")
	@Column(nullable=true)
	String ifscCode;
	
	@NonNull
	@Size(min=3,max=15,message="Please enter the Bank Branch")
	@Column
	String bankBranch;
	
	
	@Size(min=3,max=15,message="Please enter the Bank Account Type")
	@Column
	String accountType;
	

	@Size(min=3,max=15,message="Please enter the Shift Code")
	@Column
	String swiftCode;
	
	@NonNull
	@Column(nullable=false)
	Long companyId;

	@NonNull
	@Column(nullable=false)
	String active="Y";
	
	@NonNull
	@Column
	Date createDate= new Date();;
	
	@NonNull
	@Column
	String createdBy;
	
	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	protected void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	protected void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
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

	

	public String getPincode() {
		return pincode;
	}

	protected void setPincode(String pincode) {
		this.pincode = pincode;
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

	public String getPan() {
		return pan;
	}

	protected void setPan(String pan) {
		this.pan = pan;
	}

	public String getGstin() {
		return gstin;
	}

	protected void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getServiceTaxRegNo() {
		return ServiceTaxRegNo;
	}

	protected void setServiceTaxRegNo(String serviceTaxRegNo) {
		ServiceTaxRegNo = serviceTaxRegNo;
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

	public String getEmail() {
		return email;
	}

	protected void setEmail(String email) {
		this.email = email;
	}

	public String getBankName() {
		return bankName;
	}

	protected void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAcctNumber() {
		return bankAcctNumber;
	}

	protected void setBankAcctNumber(String bankAcctNumber) {
		this.bankAcctNumber = bankAcctNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	protected void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	protected void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
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

	public Long getCompanyId() {
		return companyId;
	}

	protected void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}



	public String getActive() {
		return active;
	}

	protected void setActive(String active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "ClientEntityView [id=" + id + ", name=" + name + ", addrLine1=" + addrLine1 + ", addrLine2=" + addrLine2
				+ ", city=" + city + ", state=" + state + ", pinCode=" + pincode + ", phonePrimary=" + phonePrimary
				+ ", phoneSecondary=" + phoneSecondary + ", faxNo=" + faxNo + ", pan=" + pan + ", gstin=" + gstin
				+ ", ServiceTaxRegNo=" + ServiceTaxRegNo + ", accountingType=" + accountingType + ", email=" + email
				+ ", bankName=" + bankName + ", bankAcctNumber=" + bankAcctNumber + ", ifscCode=" + ifscCode
				+ ", bankBranch=" + bankBranch + ", accountType=" + accountType + ", swiftCode=" + swiftCode
				+ ", companyId=" + companyId + ", active=" + active + ", createDate=" + createDate + ", createdBy="
				+ createdBy + "]";
	}

	public Date getCreateDate() {
		return createDate;
	}

	protected void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	protected void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
