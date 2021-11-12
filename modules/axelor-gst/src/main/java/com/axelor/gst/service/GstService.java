package com.axelor.gst.service;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Sequence;

public interface GstService {

  public InvoiceLine computeInvoiceLine(InvoiceLine invoiceLine, Boolean isStateDiff);

  public Boolean checkState(Invoice invoice);

  public Invoice computeInvoice(Invoice invoice);

  public Invoice addInvoiceAddress(Invoice invoice);

  public Boolean checkShippingAddress(Invoice invoice);

  public Invoice addShippingAddress(Invoice invoice, Boolean isInvoiceAdrs);

  public Invoice addPartyContact(Invoice invoice, Boolean isPrimary);

  public Boolean checkPrimary(Invoice invoice);

  public Sequence getNextNumber(Sequence sequence);

  public Party computeReference(Party party);
}
