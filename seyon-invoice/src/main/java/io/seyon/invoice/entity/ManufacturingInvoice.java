package io.seyon.invoice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ManufacturingInvoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2295915883765052507L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String invoiceId;

	// PI-number/facialyear
	@Column(unique = true, nullable = false)
	private String proFormaId;

	@Column
	private Long companyId;
	@Column
	private Long clientId;

	@Column
	private String sacCode;

	@Column
	private String invoiceType;

	@Column
	private Date invoiceDate;

	@Column
	private Date performaDate = new Date();

	@Column
	private Double cgstPerfomaPercent;
	@Column
	private Double sgstPerfomaPercent;
	@Column
	private Double igstPerfomaPercent;
	@Column
	private Double cgstInvoicePercent;
	@Column
	private Double sgstInvoicePercent;
	@Column
	private Double igstInvoicePercent;

	@Column
	private Double totalPerfomaBeforeTax;
	@Column
	private Double totalInvoiceBeforeTax;

	@Column
	private Double totalPerfomaAmount;
	@Column
	private Double totalInvoiceAmount;
	@Column
	private Double cgstPerfoma;
	@Column
	private Double sgstPerfoma;
	@Column
	private Double igstPerfoma;
	@Column
	private Double cgstInvoice;
	@Column
	private Double sgstInvoice;
	@Column
	private Double igstInvoice;

	@Column(name = "particular_index")
	private String index;
	@Column
	private String itemDescription;
	@Column
	private Integer quantity;
	@Column
	private String performaRate;
	@Column
	private String invoiceRate;
	@Column
	private Double calculatedInvoiceAmount;
	@Column
	private Double calculatedPerformaAmount;

	@Column
	private Double reimbPerfomaAmount;
	@Column
	private Double reimbInvoiceAmount;
	

	
	@Column
	private Double grossInvoiceAmount;
	@Column
	private Double grossPerformaAmount;


	@Enumerated(EnumType.STRING)
	@Column
	private InvoiceStatus status = InvoiceStatus.NEW;
	@Column
	private String createdBy;
	@Column
	private Date createdDate = new Date();

	@Column
	private String type = "PERFORMA";// or INVOICE

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getSacCode() {
		return sacCode;
	}

	public void setSacCode(String sacCode) {
		this.sacCode = sacCode;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getPerformaDate() {
		return performaDate;
	}

	public void setPerformaDate(Date performaDate) {
		this.performaDate = performaDate;
	}

	public Double getTotalPerfomaAmount() {
		return totalPerfomaAmount;
	}

	public void setTotalPerfomaAmount(Double totalPerfomaAmount) {
		this.totalPerfomaAmount = totalPerfomaAmount;
	}

	public Double getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}

	public void setTotalInvoiceAmount(Double totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public void setStatus(InvoiceStatus status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getTotalPerfomaBeforeTax() {
		return totalPerfomaBeforeTax;
	}

	public void setTotalPerfomaBeforeTax(Double totalPerfomaBeforeTax) {
		this.totalPerfomaBeforeTax = totalPerfomaBeforeTax;
	}

	public Double getTotalInvoiceBeforeTax() {
		return totalInvoiceBeforeTax;
	}

	public void setTotalInvoiceBeforeTax(Double totalInvoiceBeforeTax) {
		this.totalInvoiceBeforeTax = totalInvoiceBeforeTax;
	}

	public Double getCgstPerfomaPercent() {
		return cgstPerfomaPercent;
	}

	public void setCgstPerfomaPercent(Double cgstPerfomaPercent) {
		this.cgstPerfomaPercent = cgstPerfomaPercent;
	}

	public Double getSgstPerfomaPercent() {
		return sgstPerfomaPercent;
	}

	public void setSgstPerfomaPercent(Double sgstPerfomaPercent) {
		this.sgstPerfomaPercent = sgstPerfomaPercent;
	}

	public Double getIgstPerfomaPercent() {
		return igstPerfomaPercent;
	}

	public void setIgstPerfomaPercent(Double igstPerfomaPercent) {
		this.igstPerfomaPercent = igstPerfomaPercent;
	}

	public Double getCgstInvoicePercent() {
		return cgstInvoicePercent;
	}

	public void setCgstInvoicePercent(Double cgstInvoicePercent) {
		this.cgstInvoicePercent = cgstInvoicePercent;
	}

	public Double getSgstInvoicePercent() {
		return sgstInvoicePercent;
	}

	public void setSgstInvoicePercent(Double sgstInvoicePercent) {
		this.sgstInvoicePercent = sgstInvoicePercent;
	}

	public Double getIgstInvoicePercent() {
		return igstInvoicePercent;
	}

	public void setIgstInvoicePercent(Double igstInvoicePercent) {
		this.igstInvoicePercent = igstInvoicePercent;
	}

	public Double getCgstPerfoma() {
		return cgstPerfoma;
	}

	public void setCgstPerfoma(Double cgstPerfoma) {
		this.cgstPerfoma = cgstPerfoma;
	}

	public Double getSgstPerfoma() {
		return sgstPerfoma;
	}

	public void setSgstPerfoma(Double sgstPerfoma) {
		this.sgstPerfoma = sgstPerfoma;
	}

	public Double getIgstPerfoma() {
		return igstPerfoma;
	}

	public void setIgstPerfoma(Double igstPerfoma) {
		this.igstPerfoma = igstPerfoma;
	}

	public Double getCgstInvoice() {
		return cgstInvoice;
	}

	public void setCgstInvoice(Double cgstInvoice) {
		this.cgstInvoice = cgstInvoice;
	}

	public Double getSgstInvoice() {
		return sgstInvoice;
	}

	public void setSgstInvoice(Double sgstInvoice) {
		this.sgstInvoice = sgstInvoice;
	}

	public Double getIgstInvoice() {
		return igstInvoice;
	}

	public void setIgstInvoice(Double igstInvoice) {
		this.igstInvoice = igstInvoice;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getPerformaRate() {
		return performaRate;
	}

	public void setPerformaRate(String performaRate) {
		this.performaRate = performaRate;
	}

	public String getInvoiceRate() {
		return invoiceRate;
	}

	public void setInvoiceRate(String invoiceRate) {
		this.invoiceRate = invoiceRate;
	}

	public Double getCalculatedInvoiceAmount() {
		return calculatedInvoiceAmount;
	}

	public void setCalculatedInvoiceAmount(Double calculatedInvoiceAmount) {
		this.calculatedInvoiceAmount = calculatedInvoiceAmount;
	}

	public Double getCalculatedPerformaAmount() {
		return calculatedPerformaAmount;
	}

	public void setCalculatedPerformaAmount(Double calculatedPerformaAmount) {
		this.calculatedPerformaAmount = calculatedPerformaAmount;
	}

	public String getProFormaId() {
		return proFormaId;
	}

	public void setProFormaId(String proFormaId) {
		this.proFormaId = proFormaId;
	}


	public Double getReimbPerfomaAmount() {
		return reimbPerfomaAmount;
	}

	public void setReimbPerfomaAmount(Double reimbPerfomaAmount) {
		this.reimbPerfomaAmount = reimbPerfomaAmount;
	}

	public Double getReimbInvoiceAmount() {
		return reimbInvoiceAmount;
	}

	public void setReimbInvoiceAmount(Double reimbInvoiceAmount) {
		this.reimbInvoiceAmount = reimbInvoiceAmount;
	}


	public Double getGrossInvoiceAmount() {
		return grossInvoiceAmount;
	}

	public void setGrossInvoiceAmount(Double grossInvoiceAmount) {
		this.grossInvoiceAmount = grossInvoiceAmount;
	}

	public Double getGrossPerformaAmount() {
		return grossPerformaAmount;
	}

	public void setGrossPerformaAmount(Double grossPerformaAmount) {
		this.grossPerformaAmount = grossPerformaAmount;
	}

	@Override
	public String toString() {
		return "ManufacturingInvoice [id=" + id + ", invoiceId=" + invoiceId + ", proFormaId=" + proFormaId
				+ ", companyId=" + companyId + ", clientId=" + clientId + ", sacCode=" + sacCode + ", invoiceType="
				+ invoiceType + ", invoiceDate=" + invoiceDate + ", performaDate=" + performaDate
				+ ", cgstPerfomaPercent=" + cgstPerfomaPercent + ", sgstPerfomaPercent=" + sgstPerfomaPercent
				+ ", igstPerfomaPercent=" + igstPerfomaPercent + ", cgstInvoicePercent=" + cgstInvoicePercent
				+ ", sgstInvoicePercent=" + sgstInvoicePercent + ", igstInvoicePercent=" + igstInvoicePercent
				+ ", totalPerfomaBeforeTax=" + totalPerfomaBeforeTax + ", totalInvoiceBeforeTax="
				+ totalInvoiceBeforeTax + ", totalPerfomaAmount=" + totalPerfomaAmount + ", totalInvoiceAmount="
				+ totalInvoiceAmount + ", cgstPerfoma=" + cgstPerfoma + ", sgstPerfoma=" + sgstPerfoma
				+ ", igstPerfoma=" + igstPerfoma + ", cgstInvoice=" + cgstInvoice + ", sgstInvoice=" + sgstInvoice
				+ ", igstInvoice=" + igstInvoice + ", index=" + index + ", itemDescription=" + itemDescription
				+ ", quantity=" + quantity + ", performaRate=" + performaRate + ", invoiceRate=" + invoiceRate
				+ ", calculatedInvoiceAmount=" + calculatedInvoiceAmount + ", calculatedPerformaAmount="
				+ calculatedPerformaAmount + ", grossInvoiceAmount=" + grossInvoiceAmount + ", grossPerformaAmount="
				+ grossPerformaAmount + ", status=" + status + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", type=" + type + "]";
	}
	
}
