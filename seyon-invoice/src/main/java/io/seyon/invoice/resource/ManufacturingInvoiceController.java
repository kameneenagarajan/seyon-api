package io.seyon.invoice.resource;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.seyon.invoice.entity.Invoice;
import io.seyon.invoice.entity.ManufacturingInvoice;
import io.seyon.invoice.entity.SACCode;
import io.seyon.invoice.model.InvoiceData;
import io.seyon.invoice.model.InvoiceSearch;
import io.seyon.invoice.repository.SACCodeRepository;
import io.seyon.invoice.service.GenerateManuInvoiceService;
import io.seyon.invoice.service.InvoiceService;
import io.seyon.invoice.service.ManufacturingInvoiceService;

@RestController
@RequestMapping("/api/manuFacturingInvoice")
public class ManufacturingInvoiceController {

	@Autowired
	private ManufacturingInvoiceService invoiceService;
	

	@Autowired
	private GenerateManuInvoiceService generateManuInvoiceService;
	
	@Autowired
	private SACCodeRepository sacRepo;

	private static final Logger log = LoggerFactory.getLogger(ManufacturingInvoiceController.class);

	@PostMapping(path="/performa",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ManufacturingInvoice> savePerformaInvoice(@RequestBody List<ManufacturingInvoice> invoiceData,
			@RequestHeader(name = "x-company-id", required = true) Long companyId,
			@RequestHeader(name = "x-user-name", required = true) String userId) {
		log.info("Invoice details request {}", invoiceData);
		invoiceData.forEach(p -> {
			p.setCompanyId(companyId);
			p.setCreatedBy(userId);
		});

		List<ManufacturingInvoice> invoiceResult= invoiceService.createProformaInvoice(invoiceData,companyId);
		return invoiceResult;
	}
	
	@PostMapping(path="/invoice", produces = MediaType.APPLICATION_JSON_VALUE)
	public ManufacturingInvoice saveInvoice(@RequestBody ManufacturingInvoice invoiceData,
			@RequestHeader(name = "x-company-id", required = true) Long companyId,
			@RequestHeader(name = "x-user-name", required = true) String userId) {
		log.info("Invoice details request {}", invoiceData);
		ManufacturingInvoice invoice = invoiceService.createInvoice(invoiceData);
		return invoice;
	}

	@PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<ManufacturingInvoice> searchInvoice(@RequestParam(required = false) Integer pageNumber,
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

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ManufacturingInvoice getInvoice(@RequestParam(required = true) Long id) {
		log.info("Invoice Search Data invoiceId {}", id);
		return invoiceService.getInvoiceDetails(id);

	}
	
	@GetMapping(path="/byProforma",produces = MediaType.APPLICATION_JSON_VALUE)
	public ManufacturingInvoice getInvoiceByProfromaId(@RequestParam(required = true) String proformaId) {
		log.info("Invoice Search Data invoiceId {}", proformaId);
		return invoiceService.getInvoiceDetails(proformaId);

	}
	
	@PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE,path="/cancel")
	public ManufacturingInvoice cancelInvoice(@RequestParam(required = true) Long id) {
		log.info("Cancelling the Invoice invoiceId {}", id);
		return invoiceService.cancelInvoice(id);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,path="/sac")
	public Iterable<SACCode> getAllSac() {
		log.info("SAC Details");
		return sacRepo.findAll();
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
		

	@RequestMapping(path="/IhtmlReport",method=RequestMethod.GET,produces= "text/html")
	public @ResponseBody String generateIHtmlInvoice(@RequestParam(required = true) String performaId) throws IOException {
		log.info("Genetrating the html Invoice {}",performaId);
		return generateManuInvoiceService.processInvoiceReport(performaId);
	}
	
	@RequestMapping(path="/PhtmlReport",method=RequestMethod.GET,produces= "text/html")
	public @ResponseBody String generatePHtmlInvoice(@RequestParam(required = true) String performaId) throws IOException {
		log.info("Genetrating the html Invoice {}",performaId);
		return generateManuInvoiceService.processPInvoiceReport(performaId);
	}
	
}
