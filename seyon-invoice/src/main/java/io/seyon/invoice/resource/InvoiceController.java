package io.seyon.invoice.resource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.seyon.invoice.entity.Invoice;
import io.seyon.invoice.entity.SACCode;
import io.seyon.invoice.model.InvoiceData;
import io.seyon.invoice.model.InvoiceSearch;
import io.seyon.invoice.repository.SACCodeRepository;
import io.seyon.invoice.service.InvoiceService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private SACCodeRepository sacRepo;

	private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

	@PostMapping(path="/performa",produces = MediaType.APPLICATION_JSON_VALUE)
	public InvoiceData savePerformaInvoice(@RequestBody InvoiceData invoiceData,
			@RequestHeader(name = "x-company-id", required = true) Long companyId,
			@RequestHeader(name = "x-user-name", required = true) String userId) {
		log.info("Invoice details request {}", invoiceData);

		invoiceData.getInvoice().setCompanyId(companyId);
		invoiceData.getInvoice().setCreatedBy(userId);
		invoiceData.getParticulars().forEach(p -> {
			p.setCompanyId(companyId);
			p.setCreatedBy(userId);
		});

		Long id = invoiceService.createPerformaInvoice(invoiceData.getInvoice(), invoiceData.getParticulars(),companyId);

		invoiceData.getInvoice().setId(id);
		invoiceData.getParticulars().forEach(p -> p.setInvoiceTableId(id));
		log.info("Invoice details response{}", invoiceData);
		return invoiceData;
	}
	
	@PostMapping(path="/invoice", produces = MediaType.APPLICATION_JSON_VALUE)
	public InvoiceData saveInvoice(@RequestBody InvoiceData invoiceData,
			@RequestHeader(name = "x-company-id", required = true) Long companyId,
			@RequestHeader(name = "x-user-name", required = true) String userId) {
		log.info("Invoice details request {}", invoiceData);

		invoiceData.getInvoice().setCompanyId(companyId);
		invoiceData.getInvoice().setCreatedBy(userId);
		invoiceData.getParticulars().forEach(p -> {
			p.setCompanyId(companyId);
			p.setCreatedBy(userId);
		});
		Invoice invoice = invoiceService.createInvoice(invoiceData.getInvoice(), invoiceData.getParticulars());
		invoiceData.setInvoice(invoice);
		log.info("Invoice details response{}", invoiceData);
		return invoiceData;
	}

	@PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Invoice> searchInvoice(@RequestParam(required = false) Integer pageNumber,
			@RequestHeader(name = "x-company-id", required = true) Long companyId,
			@RequestBody InvoiceSearch invoiceSearch) {
		log.info(
				"Invoice Search Data pageNumber {},companyId {},"
						+ "invoiceId {},clientId {}, invoice Start Date {},Invoice end date {},invoice Status {}",
				pageNumber, companyId, invoiceSearch.getId(), invoiceSearch.getClientId(),
				invoiceSearch.getInvoiceStDate(), invoiceSearch.getInvoiceEdDate(), invoiceSearch.getStatus());

		if (null == pageNumber)
			throw new IllegalArgumentException("Page Number is null");
		if (companyId == null)
			throw new IllegalArgumentException("Company Id is null");
		if (null != invoiceSearch.getInvoiceStDate() && null == invoiceSearch.getInvoiceEdDate())
			throw new IllegalArgumentException("End date is null");
		if (null != invoiceSearch.getInvoiceEdDate() && null == invoiceSearch.getInvoiceStDate())
			throw new IllegalArgumentException("Start date is null");
		
		if(null!=invoiceSearch.getInvoiceEdDate() && null!=invoiceSearch.getInvoiceEdDate() && !invoiceSearch.getInvoiceStDate().equals(invoiceSearch.getInvoiceEdDate()) 
				&& invoiceSearch.getInvoiceEdDate().before(invoiceSearch.getInvoiceStDate())) {
			throw new IllegalArgumentException("End date is greater than start date");
		}

		return invoiceService.getInvoiceList(pageNumber, companyId, invoiceSearch.getId(), invoiceSearch.getClientId(),
				invoiceSearch.getInvoiceStDate(), invoiceSearch.getInvoiceEdDate(), invoiceSearch.getStatus(),invoiceSearch.getType(),
				invoiceSearch.getInvoiceId(),invoiceSearch.getPerformaId());

	}

	
	@PostMapping(path = "/getInvoiceReport", produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Invoice> searchInvoiceReport(@RequestHeader(name = "x-company-id", required = true) Long companyId,
			@RequestBody InvoiceSearch invoiceSearch) {
		log.info(
				"Invoice Search Data ,companyId {},"
						+ "invoiceId {},clientId {}, invoice Start Date {},Invoice end date {},invoice Status {}",
				 companyId, invoiceSearch.getId(), invoiceSearch.getClientId(),
				invoiceSearch.getInvoiceStDate(), invoiceSearch.getInvoiceEdDate(), invoiceSearch.getStatus());

		if (companyId == null)
			throw new IllegalArgumentException("Company Id is null");
		if (null != invoiceSearch.getInvoiceStDate() && null == invoiceSearch.getInvoiceEdDate())
			throw new IllegalArgumentException("End date is null");
		if (null != invoiceSearch.getInvoiceEdDate() && null == invoiceSearch.getInvoiceStDate())
			throw new IllegalArgumentException("Start date is null");
		
		if(null!=invoiceSearch.getInvoiceEdDate() && null!=invoiceSearch.getInvoiceEdDate() && !invoiceSearch.getInvoiceStDate().equals(invoiceSearch.getInvoiceEdDate()) 
				&& invoiceSearch.getInvoiceEdDate().before(invoiceSearch.getInvoiceStDate())) {
			throw new IllegalArgumentException("End date is greater than start date");
		}

		return invoiceService.getInvoiceListNoPage(companyId, invoiceSearch.getId(), invoiceSearch.getClientId(),
				invoiceSearch.getInvoiceStDate(), invoiceSearch.getInvoiceEdDate(), invoiceSearch.getStatus(),invoiceSearch.getType(),
				invoiceSearch.getInvoiceId(),invoiceSearch.getPerformaId());

	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public InvoiceData getInvoice(@RequestParam(required = true) Long invoiceId) {
		log.info("Invoice Search Data invoiceId {}", invoiceId);
		return invoiceService.getInvoiceDetails(invoiceId);

	}
	
	@PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE,path="/cancel")
	public Invoice cancelInvoice(@RequestParam(required = true) Long invoiceId) {
		log.info("Cancelling the Invoice invoiceId {}", invoiceId);
		return invoiceService.cancelInvoice(invoiceId);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,path="/sac")
	public Iterable<SACCode> getAllSac() {
		log.info("SAC Details");
		return sacRepo.findAll();
	}
	
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,path="/delParticular")
	public String deleteParticulars(@RequestParam Long particularId) {
		invoiceService.deleteParticular(particularId);
		return "particular is deleted Successfully";
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,path="/sac/byDate")
	public SACCode getSacByDate(@RequestParam LocalDate date,@RequestParam String sacCode) throws Exception {
		log.info("SAC Details");
		
		List<SACCode> sacCodes=sacRepo.findByDate(date,sacCode);
		Optional<SACCode> option=sacCodes.stream().filter(sac->{
			return null!=sac.getEndDate() && sac.getEndDate().isAfter(date);
		 }).findFirst();

		return option.orElseThrow(() -> new Exception("SAC code not found for the give date code :"+sacCode+",Date:"+date)); 
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,path="/minProformaDate")
	public String getMinProfomaDate(@RequestHeader(name = "x-company-id", required = true) Long companyId) {
		log.info("getMinProfomaDate for companyid {}", companyId);
		LocalDate minProformaDate = invoiceService.getMinProfomaDate(companyId);
		return minProformaDate.toString();
		
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,path="/minInvoiceDate")
	public String getMinInvoiceDate(@RequestHeader(name = "x-company-id", required = true) Long companyId) {
		log.info("getMinInvoiceDate for companyid {}", companyId);
		LocalDate minProformaDate = invoiceService.getMinInvoiceDate(companyId);
		return minProformaDate.toString();
		
	}
	
}
