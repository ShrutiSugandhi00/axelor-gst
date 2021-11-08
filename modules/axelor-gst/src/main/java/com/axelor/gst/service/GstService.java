package com.axelor.gst.service;

import com.axelor.gst.db.InvoiceLine;

public interface GstService {
	public InvoiceLine netAmount(InvoiceLine invoiceline);

	public InvoiceLine netIgst(InvoiceLine invoiceLine);

	public InvoiceLine netCgst(InvoiceLine invoiceLine);

	public InvoiceLine netSgst(InvoiceLine invoiceLine);
}
