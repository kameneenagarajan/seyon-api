package io.seyon.invoice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.seyon.invoice.entity.ManufacturingInvoice;

public interface ManufacturingInvoiceRepository extends JpaRepository<ManufacturingInvoice, Long>,JpaSpecificationExecutor<ManufacturingInvoice> {

	Optional<ManufacturingInvoice> findByProFormaId(String proformaInvoiceId);
	Optional<ManufacturingInvoice> findByInvoiceId(String invoiceId);

}
