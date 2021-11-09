package com.axelor.gst.web;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.service.GstService;
import com.axelor.gst.service.GstServiceImpl;
import com.axelor.inject.Beans;

import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class GstController {

	public void currentDate(ActionRequest request, ActionResponse response) {
		System.err.println("calling current date");
	}

	public void computeInvoiceLine(ActionRequest request, ActionResponse response) {
		InvoiceLine ivl = request.getContext().asType(InvoiceLine.class);
		Invoice invoice = request.getContext().getParent().asType(Invoice.class);
		Boolean b = Beans.get(GstService.class).checkState(invoice);
		ivl = Beans.get(GstService.class).computeInvoiceLine(ivl, b);
		response.setValue("netAmount", ivl.getNetAmount());
		response.setValue("netIgst", ivl.getNetIgst());
		response.setValue("netCgst", ivl.getNetCgst());
		response.setValue("netSgst", ivl.getNetSgst());
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

	public void addInvoiceAddress(ActionRequest request, ActionResponse response) {
		Invoice invoice = request.getContext().asType(Invoice.class);

	}

}
