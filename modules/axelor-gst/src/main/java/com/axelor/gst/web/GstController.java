package com.axelor.gst.web;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.service.GstService;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class GstController {

  public void computeInvoiceLine(ActionRequest request, ActionResponse response) {
    InvoiceLine ivl = request.getContext().asType(InvoiceLine.class);
    Invoice invoice = request.getContext().getParent().asType(Invoice.class);
    Boolean isStateDiff = Beans.get(GstService.class).checkState(invoice);
    ivl = Beans.get(GstService.class).computeInvoiceLine(ivl, isStateDiff);
    response.setValue("amount", ivl.getAmount());
    response.setValue("igst", ivl.getIgst());
    response.setValue("cgst", ivl.getCgst());
    response.setValue("sgst", ivl.getSgst());
    response.setValue("grossAmount", ivl.getGrossAmount());
  }

  public void computeInvoice(ActionRequest request, ActionResponse response) {
    Invoice invoice = request.getContext().asType(Invoice.class);
    invoice = Beans.get(GstService.class).computeInvoice(invoice);
    response.setValue("netAmount", invoice.getNetAmount());
    response.setValue("netIgst", invoice.getNetIgst());
    response.setValue("netCgst", invoice.getNetCgst());
    response.setValue("netSgst", invoice.getNetSgst());
    response.setValue("grossAmount", invoice.getGrossAmount());
  }

  public void addShippingAddress(ActionRequest request, ActionResponse response) {
    Invoice invoice = request.getContext().asType(Invoice.class);
    Boolean isInvoiceAddress = Beans.get(GstService.class).checkShippingAddress(invoice);
    invoice = Beans.get(GstService.class).addShippingAddress(invoice, isInvoiceAddress);
    response.setValue("shippngAddress", invoice.getShippngAddress());
  }

  public void addInvoiceAddress(ActionRequest request, ActionResponse response) {
    Invoice invoice = request.getContext().asType(Invoice.class);
    invoice = Beans.get(GstService.class).addInvoiceAddress(invoice);
    response.setValue("invoiceAddress", invoice.getInvoiceAddress());
  }

  public void addPartyContact(ActionRequest request, ActionResponse response) {
    Invoice invoice = request.getContext().asType(Invoice.class);
    Boolean isPrimary = Beans.get(GstService.class).checkPrimary(invoice);
    invoice = Beans.get(GstService.class).addPartyContact(invoice, isPrimary);
    response.setValue("partyContact", invoice.getPartyContact());
  }

  public void getNextNumber(ActionRequest request, ActionResponse response) {
    Sequence sequence = request.getContext().asType(Sequence.class);
    Beans.get(GstService.class).getNextNumber(sequence);
    response.setValue("nextNumber", sequence.getNextNumber());
  }

  public void getReference(ActionRequest request, ActionResponse response) {
    Party party = request.getContext().asType(Party.class);
    Beans.get(GstService.class).computeReference(party);
    response.setValue("reference", party.getReference());
  }
}
