package com.axelor.gst.web;

import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.service.GstService;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
//import com.google.inject.Inject;

public class GstController {
//	@Inject
//	GstService gstService;

	public void currentDate(ActionRequest request, ActionResponse response) {
		System.err.println("calling current date");

	}

	public void netAmount(ActionRequest request, ActionResponse response) {
		InvoiceLine ivl = request.getContext().asType(InvoiceLine.class);
		ivl=Beans.get(GstService.class).netAmount(ivl);
	//	ivl = gstService.netAmount(ivl);
		response.setValue("netAmount", ivl.getNetAmount());
	

	}

}
