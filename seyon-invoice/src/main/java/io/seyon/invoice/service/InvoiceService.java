package io.seyon.invoice.service;

import java.time.Instant;
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
