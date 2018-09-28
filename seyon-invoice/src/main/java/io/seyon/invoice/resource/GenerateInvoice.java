package io.seyon.invoice.resource;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.seyon.invoice.service.GenerateInvoiceService;

@Controller
@RequestMapping("/api/invoice")
public class GenerateInvoice {

	
	private static final Logger log = LoggerFactory.getLogger(GenerateInvoice.class);

	@Autowired
	private GenerateInvoiceService invoiceService;
	
	@RequestMapping(path="/IhtmlReport",method=RequestMethod.GET,produces= "text/html")
	public @ResponseBody String generateIHtmlInvoice(@RequestParam(required = true) String performaId) throws IOException {
		log.info("Genetrating the html Invoice {}",performaId);
		return invoiceService.processInvoiceReport(performaId);
	}
	
	@RequestMapping(path="/PhtmlReport",method=RequestMethod.GET,produces= "text/html")
	public @ResponseBody String generatePHtmlInvoice(@RequestParam(required = true) String performaId) throws IOException {
		log.info("Genetrating the html Invoice {}",performaId);
		return invoiceService.processPInvoiceReport(performaId);
	}
}
