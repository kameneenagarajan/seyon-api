package io.seyon.invoice.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.seyon.invoice.config.InvoiceProperties;
import io.seyon.invoice.entity.InvoiceStatus;
import io.seyon.invoice.entity.ManufacturingInvoice;
import io.seyon.invoice.repository.ManufacturingInvoiceRepository;

@Service
public class ManufacturingInvoiceService {

	
	private static final Logger log = LoggerFactory.getLogger(ManufacturingInvoiceService.class);

	@Autowired
	ManufacturingInvoiceRepository manufacturingInvoiceRepository;
	
	@Autowired
	InvoiceProperties invoiceProperties;
	
	public List<ManufacturingInvoice> createProformaInvoice(List<ManufacturingInvoice> invoices,final Long companyId) {
		int i=0;
		for(ManufacturingInvoice invoice:invoices) {
			String performaId = "PI-" + invoice.getClientId() +"-"+companyId+"-"+ Instant.now().getEpochSecond() + "/" + FinancialYear.getFinancialYearOf()+"-"+i++;
			invoice.setProFormaId(performaId);
			invoice.setType("PERFORMA");
		}

		log.info(invoices.toString());
		return manufacturingInvoiceRepository.saveAll(invoices);
	}
	
	public ManufacturingInvoice getProformaInvoice(String proformaInvoiceId) {
		return manufacturingInvoiceRepository.findByProFormaId(proformaInvoiceId).get();
	}
	
	public Iterable<ManufacturingInvoice> getInvoiceList(Integer pageNumber, Long companyId, Long id, Long clientId,
			Date invoiceStDate, Date invoiceEdDate, InvoiceStatus status, String type, String invoiceId,
			String performaId) {

		log.debug("ManufacturingInvoice Getting page {}", pageNumber);

		Pageable page = PageRequest.of(pageNumber, invoiceProperties.getPageSize(),
				Sort.by(Direction.DESC, "invoiceDate"));

		Specification<ManufacturingInvoice> spec = (Root<ManufacturingInvoice> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
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

		return manufacturingInvoiceRepository.findAll(spec, page);
	}

	public Iterable<ManufacturingInvoice> getInvoiceList(Long companyId, Long id, Long clientId,
			Date invoiceStDate, Date invoiceEdDate, InvoiceStatus status, String type, String invoiceId,
			String performaId) {


		Specification<ManufacturingInvoice> spec = (Root<ManufacturingInvoice> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
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

		return manufacturingInvoiceRepository.findAll(spec);
	}

	public ManufacturingInvoice createInvoice(ManufacturingInvoice invoiceData) {
		return manufacturingInvoiceRepository.save(invoiceData);
	}

	public ManufacturingInvoice getInvoiceDetails(Long invoiceId) {
		return manufacturingInvoiceRepository.findById(invoiceId).orElse(null);
	}
	
	public ManufacturingInvoice getInvoiceDetails(String proformaId) {
		return manufacturingInvoiceRepository.findByProFormaId(proformaId).get();
	}
	
	public ManufacturingInvoice cancelInvoice(Long id) {
		log.info("Moving the ManufacturingInvoice to Cancel status, {}", id);
		ManufacturingInvoice invoice = manufacturingInvoiceRepository.getOne(id);
		invoice.setStatus(InvoiceStatus.CANCELED);
		return manufacturingInvoiceRepository.save(invoice);
	}
}
