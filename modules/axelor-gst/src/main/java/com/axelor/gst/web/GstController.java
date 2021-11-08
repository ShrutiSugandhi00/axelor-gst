package com.axelor.gst.web;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.service.GstService;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
//import com.google.inject.Inject;

public class GstController {
	// @Inject
	// GstService gstService;

	public void currentDate(ActionRequest request, ActionResponse response) {
		System.err.println("calling current date");

	}

	public void netAmount(ActionRequest request, ActionResponse response) {
		InvoiceLine ivl = request.getContext().asType(InvoiceLine.class);
		ivl = Beans.get(GstService.class).netAmount(ivl);
		// ivl = gstService.netAmount(ivl);
		response.setValue("netAmount", ivl.getNetAmount());

	}

	public void netIgst(ActionRequest request, ActionResponse response) {
		InvoiceLine ivl = request.getContext().asType(InvoiceLine.class);
		ivl = Beans.get(GstService.class).netIgst(ivl);
		// System.err.println(ivl.getNetIgst());
		response.setValue("netIgst", ivl.getNetIgst());
	}

	public void netSgst(ActionRequest request, ActionResponse response) {
		InvoiceLine ivl = request.getContext().asType(InvoiceLine.class);
		ivl = Beans.get(GstService.class).netSgst(ivl);
		// System.err.println(ivl.getNetSgst());
		response.setValue("netSgst", ivl.getNetSgst());
	}

	public void netCgst(ActionRequest request, ActionResponse response) {
		InvoiceLine ivl = request.getContext().asType(InvoiceLine.class);
		ivl = Beans.get(GstService.class).netCgst(ivl);
		// System.err.println(ivl.getNetCgst());
		response.setValue("netCgst", ivl.getNetCgst());
	}

}
