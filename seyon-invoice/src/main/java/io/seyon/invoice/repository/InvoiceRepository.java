package io.seyon.invoice.repository;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.seyon.invoice.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>,JpaSpecificationExecutor<Invoice> {

   Optional<Invoice> findByPerformaId(String performaId);
   
   @Query("SELECT MAX(inv.performaDate) from Invoice inv where inv.companyId=:companyId")
   Timestamp getLastProformaDate( @Param(value = "companyId") Long companyId);
   
   @Query("SELECT MAX(inv.invoiceDate) from Invoice inv where inv.companyId=:companyId")
   Timestamp getLastInvoiceDate( @Param(value = "companyId") Long companyId);
}
