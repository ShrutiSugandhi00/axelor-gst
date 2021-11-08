package com.axelor.gst.service;

import java.math.BigDecimal;

import com.axelor.gst.db.InvoiceLine;

public class GstServiceImpl implements GstService {

	@Override
	public InvoiceLine netAmount(InvoiceLine invoiceline) {
		BigDecimal amount = BigDecimal.ZERO;
		if (invoiceline != null) {
			amount = (BigDecimal.valueOf(invoiceline.getQty())).multiply((invoiceline.getPrice()));
		}
		invoiceline.setNetAmount(amount);
		return invoiceline;
	}

	@Override
	public InvoiceLine netIgst(InvoiceLine invoiceLine) {
		BigDecimal net = BigDecimal.ZERO;
		if (invoiceLine != null) {
			net = invoiceLine.getNetAmount().multiply(invoiceLine.getGstRate());
		}

		invoiceLine.setNetIgst(net);
		return invoiceLine;
	}

	@Override
	public InvoiceLine netCgst(InvoiceLine invoiceLine) {
		BigDecimal net = BigDecimal.ZERO;
		if (invoiceLine != null) {
			net = invoiceLine.getNetAmount().multiply(invoiceLine.getGstRate().divide(new BigDecimal(2)));
		}
			invoiceLine.setNetCgst(net);
		return invoiceLine;
	}

	@Override
	public InvoiceLine netSgst(InvoiceLine invoiceLine) {
		BigDecimal net = BigDecimal.ZERO;
		if (invoiceLine != null) {
			net = invoiceLine.getNetAmount().multiply(invoiceLine.getGstRate().divide(new BigDecimal(2)));
		}
			invoiceLine.setNetSgst(net);
		return invoiceLine;
	}

	
	
}
