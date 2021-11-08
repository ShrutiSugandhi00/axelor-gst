package com.axelor.gst.service;

import java.math.BigDecimal;

import com.axelor.gst.db.InvoiceLine;

public class GstServiceImpl implements GstService {

	@Override
	public InvoiceLine netAmount(InvoiceLine invoiceline) {
		BigDecimal amount = BigDecimal.ZERO;
		if (invoiceline != null) {
			amount = (BigDecimal.valueOf(invoiceline.getQty())).multiply((invoiceline.getPrice())) ;
		}
			invoiceline.setNetAmount(amount);
		return invoiceline;
	}

}
