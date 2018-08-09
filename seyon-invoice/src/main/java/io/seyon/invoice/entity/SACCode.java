package io.seyon.invoice.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SAC_CODE")
public class SACCode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String sacCode;
	@Column
	private Double cgstPercent;
	@Column
	private Double sgstPercent;
	@Column
	private Double igstPercent;
	@Column
	private LocalDate startDate;
	@Column
	private LocalDate endDate;
	
	public String getSacCode() {
		return sacCode;
	}
	public void setSacCode(String sacCode) {
		this.sacCode = sacCode;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "SACCode [sacCode=" + sacCode + ", cgstPercent=" + cgstPercent + ", sgstPercent=" + sgstPercent
				+ ", igstPercent=" + igstPercent + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getCgstPercent() {
		return cgstPercent;
	}
	public void setCgstPercent(Double cgstPercent) {
		this.cgstPercent = cgstPercent;
	}
	public Double getSgstPercent() {
		return sgstPercent;
	}
	public void setSgstPercent(Double sgstPercent) {
		this.sgstPercent = sgstPercent;
	}
	public Double getIgstPercent() {
		return igstPercent;
	}
	public void setIgstPercent(Double igstPercent) {
		this.igstPercent = igstPercent;
	}
	
	
}
