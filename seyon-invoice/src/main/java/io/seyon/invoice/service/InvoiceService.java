package io.seyon.invoice.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.seyon.invoice.config.InvoiceProperties;
import io.seyon.invoice.entity.Invoice;
import io.seyon.invoice.entity.InvoiceStatus;
import io.seyon.invoice.entity.Particulars;
import io.seyon.invoice.model.InvoiceData;
import io.seyon.invoice.repository.InvoiceRepository;
import io.seyon.invoice.repository.ParticularsRepository;

@Service
public class InvoiceService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ParticularsRepository particularsRepository;

	@Autowired
	InvoiceProperties invoiceProperties;


	public Iterable<Invoice> getInvoiceList(Integer pageNumber, Long companyId, Long id, Long clientId,
			Date invoiceStDate, Date invoiceEdDate, InvoiceStatus status, String type, String invoiceId,
			String performaId) {

		log.debug("Getting page {}", pageNumber);

		Pageable page = PageRequest.of(pageNumber, invoiceProperties.getPageSize(),
				Sort.by(Direction.DESC, "invoiceDate"));

		Specification<Invoice> spec = (Root<Invoice> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("companyId"), companyId));

			if (null != id) {
				predicates.add(cb.equal(root.get("id"), id));
			}
			if (null != clientId) {
				predicates.add(cb.equal(root.get("clientId"), clientId));
			}

			if (!StringUtils.isEmpty(invoiceId)) {
				predicates.add(cb.equal(root.get("invoiceId"), invoiceId));
			}

			if (!StringUtils.isEmpty(performaId)) {
				predicates.add(cb.equal(root.get("performaId"), performaId));
			}

			if (!StringUtils.isEmpty(status)) {
				predicates.add(cb.equal(root.get("status"), status));
			}

			if (!StringUtils.isEmpty(type)) {
				predicates.add(cb.equal(root.get("type"), type));
			}

			if (null != invoiceStDate && null != invoiceEdDate) {
				predicates.add(cb.or(cb.between(root.get("invoiceDate"), invoiceStDate, invoiceEdDate),
						cb.between(root.get("performaDate"), invoiceStDate, invoiceEdDate)));
			}
			return cb.and(predicates.toArray(new Predicate[] {}));
		};

		return invoiceRepository.findAll(spec, page);
	}

	public Iterable<Invoice> getInvoiceListNoPage(Long companyId, Long id, Long clientId,
			Date invoiceStDate, Date invoiceEdDate, InvoiceStatus status, String type, String invoiceId,
			String performaId) {

		Specification<Invoice> spec = (Root<Invoice> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("companyId"), companyId));

			if (null != id) {
				predicates.add(cb.equal(root.get("id"), id));
			}
			if (null != clientId) {
				predicates.add(cb.equal(root.get("clientId"), clientId));
			}

			if (!StringUtils.isEmpty(invoiceId)) {
				predicates.add(cb.equal(root.get("invoiceId"), invoiceId));
			}

			if (!StringUtils.isEmpty(performaId)) {
				predicates.add(cb.equal(root.get("performaId"), performaId));
			}

			if (!StringUtils.isEmpty(status)) {
				predicates.add(cb.equal(root.get("status"), status));
			}

			if (!StringUtils.isEmpty(type)) {
				predicates.add(cb.equal(root.get("type"), type));
			}

			if (null != invoiceStDate && null != invoiceEdDate) {
				predicates.add(cb.or(cb.between(root.get("invoiceDate"), invoiceStDate, invoiceEdDate),
						cb.between(root.get("performaDate"), invoiceStDate, invoiceEdDate)));
			}
			return cb.and(predicates.toArray(new Predicate[] {}));
		};

		return invoiceRepository.findAll(spec);
	}
	
	public Long createPerformaInvoice(Invoice invoice, List<Particulars> particulars,Long companyId) {
		log.info("Saving the invoice");
		if (null == invoice) {
			return null;
		}

		String performaId = "PI-" + invoice.getClientId() +"-"+companyId+"-"+Instant.now().getEpochSecond() + "/" + FinancialYear.getFinancialYearOf();
		invoice.setPerformaId(performaId);
		invoice.setType("PERFORMA");
		invoice = invoiceRepository.save(invoice);
		Long invoiceId = invoice.getId();

		if (CollectionUtils.isEmpty(particulars)) {
			log.info("Particulars are empty");
			return invoiceId;
		}
		log.info("Updating the particulars with invoice id {}", invoiceId);
		particulars.forEach(part -> {
			part.setInvoiceTableId(invoiceId);
		});

		log.info("Saving particulars");
		particularsRepository.saveAll(particulars);

		return invoiceId;

	}
	
	
	public LocalDate getMinProfomaDate(Long companyId){
		
		LocalDate minProfomoDate =  LocalDate.now();
		LocalDate currentDate= LocalDate.now();
		
		
		try {
			
			/**
			 * Logic
			 * 0 - Query the repository for the previous profomo date of the company
			 * 1. IF the profomaDate falls within this month and date >= previous profoma date and date =<today
			 * 		Then proforma date is allowed
			 * 2. IF the profomaDate falls previous month and todays date is <= first 5 days of the current month 
 				THen we can enter the previous month profoma date on these conditions
 					Profoma date must be >= previous profoma date(previous month) and proforma date is allowed till the last day of the previous month
			 * 3. IF the profomaDate falls before the previous month or the current month --> Not allowed
			 * 4. ProfomaDate cannot be selected for future date(may be block this in the calendar widget itself)3
			 */
			
			Timestamp lastProformaDateTime = invoiceRepository.getLastProformaDate(companyId);
			
			if(lastProformaDateTime != null)
			{
				minProfomoDate=lastProformaDateTime.toLocalDateTime().toLocalDate();
			}
			else{
				return currentDate.withDayOfMonth(1).minusDays(1);
			}
			
			int currentMonthValue =currentDate.getMonth().getValue();
			int minprofomaMonthValue = minProfomoDate.getMonth().getValue();
			if( currentMonthValue ==  minprofomaMonthValue){ // Month Value starts from 1 
				return minProfomoDate.minusDays(1);
			}
			else if (minprofomaMonthValue == (currentMonthValue-1)  )
			{
				if(currentDate.getDayOfMonth()<5){ //first 5 days of the month
					return minProfomoDate.minusDays(1);
				}
				else
				{
					return currentDate.withDayOfMonth(1).minusDays(1);
				}
			}
			else
			{
				return currentDate.withDayOfMonth(1).minusDays(1);
			}
			
		} catch (Exception e) {
			log.error("Error in getMinProfomaDate {}", e);
		}
		
		return minProfomoDate.minusDays(1);
		
	}
	
	
public LocalDate getMinInvoiceDate(Long companyId){
		
		LocalDate minInvoiceDate =  LocalDate.now();
		LocalDate currentDate= LocalDate.now();
		LocalDate minProfomoDate= LocalDate.now();
		
		
		try {
			
			minProfomoDate = getMinProfomaDate(companyId);
			/**
			 * Logic:::
			 * Fetch the minProfomoDate by calling getMinProfomaDate
			 * Fetch the minInvoiceDate from the Invoice table
			 * 1. if minProfomoDate < minInvoiceDate then
			 *  	1.a) Check if minInvoiceDate within this month and date >= minInvoiceDate and date =<today
			 * 				Return the minInvoiceDate
			 * 		1.b) Check if minInvoiceDate falls previous month and todays date is <= first 5 days of the current month 
 							Return the minInvoiceDate
 					1.c) Check if minInvoiceDate falls previous month and todays date is > first 5 days of the current month
 						   Return the current month first day as minInvoiceDate
 					1.d) Check if minInvoiceDate falls before the previous month or the current month
 							 Return the current month first day as minInvoiceDate
 				2. else if  minInvoiceDate <= minProfomoDate then
 					2.a) return the minProfomoDate fetched from getMinProfomaDate
 					
			 *  3. invoiceDate cannot be selected for future date(may be block this in the calendar widget itself)
			 */
			
			Timestamp lastInvoiceDateTime = invoiceRepository.getLastInvoiceDate(companyId);
			
			if(lastInvoiceDateTime != null)
			{
				minInvoiceDate=lastInvoiceDateTime.toLocalDateTime().toLocalDate();
			}
			else{
				return currentDate.withDayOfMonth(1).minusDays(1);
			}
			
			if(minProfomoDate.isBefore(minInvoiceDate)){
				int currentMonthValue =currentDate.getMonth().getValue();
				int minInvoiceMonthValue = minInvoiceDate.getMonth().getValue();
				
				if( currentMonthValue ==  minInvoiceMonthValue){ // Month Value starts from 1 
					return minInvoiceDate.minusDays(1);
				}
				else if (minInvoiceMonthValue == (currentMonthValue-1)  )
				{
					if(currentDate.getDayOfMonth()<5){ //first 5 days of the month
						return minInvoiceDate.minusDays(1);
					}
					else
					{
						return currentDate.withDayOfMonth(1).minusDays(1);
					}
				}
				else
				{
					return currentDate.withDayOfMonth(1).minusDays(1);
				}
				
			}
			else{
				return minProfomoDate.minusDays(1);
			}
			
			
			
		} catch (Exception e) {
			log.error("Error in getMinInvoiceDate {}", e);
		}
		
		return minInvoiceDate.minusDays(1);
		
	}

	@Transactional
	public Invoice createInvoice(Invoice invoice, List<Particulars> particulars) {

		log.info("Saving the invoice");
		if (null == invoice) {
			return null;
		}
		invoice.setInvoiceId(invoice.getPerformaId().replaceAll("PI-", "IN-"));
		invoice.setType("INVOICE");
		invoice = invoiceRepository.save(invoice);

		log.info("Saving particulars");
		particularsRepository.saveAll(particulars);

		return invoice;
	}

	public Invoice createInvoice(Invoice invoice) {
		log.info("Moving the invoice to created status, {}", invoice);
		invoice.setStatus(InvoiceStatus.CREATED);
		return invoiceRepository.save(invoice);
	}

	public Invoice cancelInvoice(Long id) {
		log.info("Moving the invoice to Cancel status, {}", id);
		Invoice invoice = invoiceRepository.getOne(id);
		invoice.setStatus(InvoiceStatus.CANCELED);
		return invoiceRepository.save(invoice);
	}

	public InvoiceData getInvoiceDetails(Long invoiceId) {
		InvoiceData data = new InvoiceData();

		Optional<Invoice> opInv = invoiceRepository.findById(invoiceId);
		if (!opInv.isPresent()) {
			new NoResultException("No Invoice Found");
		}
		data.setInvoice(opInv.get());
		data.setParticulars(particularsRepository.findByInvoiceTableId(invoiceId));
		log.info("Retrieved Data {}", data);
		return data;
	}

	public void deleteParticular(Long particularId) {
		log.debug("deleting particular {}", particularId);
		particularsRepository.deleteById(particularId);
		log.debug("deleted particular");
	}


}
