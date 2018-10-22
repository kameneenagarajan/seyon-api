package io.seyon.invoice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import io.seyon.common.util.ConvertNumberToWords;
import io.seyon.invoice.entity.ClientEntityView;
import io.seyon.invoice.entity.CompanyView;
import io.seyon.invoice.entity.Invoice;
import io.seyon.invoice.entity.ManufacturingInvoice;
import io.seyon.invoice.entity.Particulars;
import io.seyon.invoice.repository.ClientViewRepository;
import io.seyon.invoice.repository.CompanyViewRepository;
import io.seyon.invoice.repository.ManufacturingInvoiceRepository;
import io.seyon.invoice.repository.ParticularsRepository;

@Service
public class GenerateManuInvoiceService {

	
	private static final Logger log = LoggerFactory.getLogger(GenerateManuInvoiceService.class);
	
	@Autowired
	TemplateEngine templateEngine;
	
	@Autowired
	CompanyViewRepository cr;
	
	@Autowired
	private ManufacturingInvoiceRepository manufacturingInvoiceRepository;
	
	@Autowired
	private ClientViewRepository clientViewRepository;
	
	@Autowired
	private ParticularsRepository particularsRepository;
	
	public String processInvoiceReport(String performaId) {	
		final Context ctx=getContext(performaId);
		return templateEngine.process("mInvoice.html",ctx);
	}
	
	
	
	public String processPInvoiceReport(String performaId) {

		final Context ctx=getContext(performaId);
		return templateEngine.process("P-mInvoice.html",ctx);
	}
	
	private Context getContext(String performaId) {
		Optional<ManufacturingInvoice> opInv = manufacturingInvoiceRepository.findByProFormaId(performaId);
		String totalAmtInWords="";
		if (!opInv.isPresent()) {
			new NoResultException("No Invoice Found");
		}
		ManufacturingInvoice inv=opInv.get();
		if(inv!=null){
			if(inv.getCalculatedInvoiceAmount()==null && inv.getCalculatedPerformaAmount()!=null ){
				totalAmtInWords=new ConvertNumberToWords().convertNumber(inv.getCalculatedPerformaAmount().longValue()).toUpperCase();
			}
			else
			{
				totalAmtInWords=new ConvertNumberToWords().convertNumber(inv.getCalculatedInvoiceAmount().longValue()).toUpperCase();
				
			}
		}
		log.info("Retrieved totalAmtInWords  {}", totalAmtInWords);
		
		List<Particulars> parti=particularsRepository.findByInvoiceTableId(inv.getId());
		log.info("Retrieved Invoice {}, Particulars {}", inv,parti);
		
		Optional<CompanyView> opsCmy=cr.findById(inv.getCompanyId());
		if(!opsCmy.isPresent()) {
			new NoResultException("No Invoice Found");
		}
		CompanyView cmp=opsCmy.get();
		log.info("Retrieved Company {}", cmp);
		Optional<ClientEntityView> opcev=clientViewRepository.findById(inv.getClientId());
		if(!opcev.isPresent()) {
			new NoResultException("No Invoice Found");
		}
		ClientEntityView cev=opcev.get();
		log.info("Retrieved Client {}", cev);
		final Context ctx=new Context();
		Map<String, Object> variables= new HashMap<>();
		variables.put("invoice", inv);
		variables.put("totalAmtInWords", totalAmtInWords);
		variables.put("particulars", parti);
		variables.put("company", cmp);
		variables.put("client", cev);
		variables.put("additionPDetails",additionPDetails(inv));
		variables.put("additionDetails",additionDetails(inv));
		ctx.setVariables(variables);
		
		return ctx;
	}
	
	private String additionDetails(ManufacturingInvoice inv) {
		String ret="A";
		
		if((null!=inv.getIgstInvoice() && !inv.getIgstInvoice().equals(0d))
				||(null!=inv.getCgstInvoice() && !inv.getCgstInvoice().equals(0d))
				||(null!=inv.getSgstInvoice() && !inv.getSgstInvoice().equals(0d))) {
			ret=ret+" + GST";
		}
		if(null!=inv.getReimbInvoiceAmount()&&!inv.getReimbInvoiceAmount().equals(0d)) {
			ret=ret+" + Reimbursement";
		}
		return ret;
	}
	private String additionPDetails(ManufacturingInvoice inv) {
		String ret="A";
		
		if((null!=inv.getIgstPerfoma() && !inv.getIgstPerfoma().equals(0d))
				|| (null!=inv.getCgstPerfoma() && !inv.getCgstPerfoma().equals(0d))
				|| (null!=inv.getSgstPerfoma() && !inv.getSgstPerfoma().equals(0d))) {
			ret=ret+" + GST";
		}
		if(null!=inv.getReimbPerfomaAmount() && !inv.getReimbPerfomaAmount().equals(0d)) {
			ret=ret+" + Reimbursement";
		}
		return ret;
	}
}
