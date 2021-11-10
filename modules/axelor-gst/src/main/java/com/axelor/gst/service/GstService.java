package com.axelor.gst.service;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;

public interface GstService {

	public InvoiceLine computeInvoiceLine(InvoiceLine invoiceLine, Boolean isStateDiff);

	public Boolean checkState(Invoice invoice);

	public Invoice computeInvoice(Invoice invoice);

	public Invoice addInvoiceAddress(Invoice invoice);

	public Boolean checkShippingAddress(Invoice invoice);

	public Invoice addShippingAddress(Invoice invoice,Boolean isInvoiceAdrs);
}
