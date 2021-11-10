package com.axelor.gst.service;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import java.math.BigDecimal;

public class GstServiceImpl implements GstService {

	@Override
	public InvoiceLine computeInvoiceLine(InvoiceLine invoiceLine, Boolean isStateDiff) {
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal gstRate = BigDecimal.ZERO;
		BigDecimal gstAmount = BigDecimal.ZERO;
		BigDecimal igst = BigDecimal.ZERO;
		BigDecimal cgst = BigDecimal.ZERO;
		BigDecimal sgst = BigDecimal.ZERO;
		BigDecimal grossAmount = BigDecimal.ZERO;

		if (invoiceLine != null) {
			amount = (BigDecimal.valueOf(invoiceLine.getQty())).multiply((invoiceLine.getPrice()));
			gstRate = invoiceLine.getGstRate();
			gstAmount = amount.multiply(gstRate.divide(new BigDecimal(100)));
			BigDecimal t = amount.divide(new BigDecimal(2));

			System.err.println(isStateDiff);
			if (isStateDiff != null) {
				if (isStateDiff) {
					igst = gstAmount;
				} else {
					sgst = t;
					cgst = t;
				}
			}
			grossAmount = amount.add(igst.add(sgst));
		}

		invoiceLine.setAmount(amount);
		invoiceLine.setIgst(igst);
		invoiceLine.setCgst(cgst);
		invoiceLine.setSgst(sgst);
		invoiceLine.setGrossAmount(grossAmount);
		return invoiceLine;
	}

	@Override
	public Invoice addInvoiceAddress(Invoice invoice) {
		Address adrs = null;
		if (invoice != null) {
			for (Address a : invoice.getParty().getAddressList()) {
				if (a != null) {
					if((a.getType() != null)) {
					if ( (a.getType().equalsIgnoreCase("0"))) {
						adrs = a;
					}
					else if(a.getType().equalsIgnoreCase("1")) {
						adrs = a;
					}
					}
				}
			}
			invoice.setInvoiceAddress(adrs);
		}
		return invoice;
	}


	@Override
	public Invoice computeInvoice(Invoice invoice) {
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal netIgst = BigDecimal.ZERO;
		BigDecimal netSgst = BigDecimal.ZERO;
		BigDecimal netCgst = BigDecimal.ZERO;
		BigDecimal grossAmount = BigDecimal.ZERO;

		if (invoice != null) {
			for (InvoiceLine iv : invoice.getInvoiceLineList()) {
				netAmount = netAmount.add(iv.getAmount());
				netIgst = netIgst.add(iv.getIgst());
				netSgst = netSgst.add(iv.getSgst());
				netCgst = netCgst.add(iv.getCgst());
				grossAmount = grossAmount.add(iv.getGrossAmount());
			}
			invoice.setNetAmount(netAmount);
			invoice.setNetIgst(netIgst);
			invoice.setNetSgst(netSgst);
			invoice.setNetCgst(netCgst);
			invoice.setGrossAmount(grossAmount);
		}

		return invoice;
	}

	public Boolean checkState(Invoice invoice) {
		if (invoice != null) {
			if (invoice.getCompany() != null && invoice.getCompany().getAddress() != null
					&& invoice.getCompany().getAddress().getState() != null && invoice.getInvoiceAddress() != null
					&& invoice.getInvoiceAddress().getState() != null) {
				if (!(invoice.getCompany().getAddress().getState()).equals(invoice.getInvoiceAddress().getState()))
					return true;
				else
					return false;
			}
		}
		return null;
	}
	public Boolean checkShippingAddress(Invoice invoice) {
		if(invoice!=null) {
			if(invoice.getIsInvoiceAddress()!=null &&(!invoice.getIsInvoiceAddress()))
				return true;
			else
				return false;
		}
			return null;
	}

	@Override
	public Invoice addShippingAddress(Invoice invoice,Boolean isInvoiceadrs) {
		System.err.println("c-23");
		Address adrs = null;
		if (invoice != null && isInvoiceadrs) {
			for (Address a : invoice.getParty().getAddressList()) {
				if (a != null) {
					if((a.getType() != null)) {
					if ( (a.getType().equalsIgnoreCase("0"))) {
						adrs = a;
					}
					else if(a.getType().equalsIgnoreCase("2")) {
						adrs = a;
					}
					}
				}
			}
			invoice.setShippngAddress(adrs);
		}
		return invoice;
		
		
	}

}
