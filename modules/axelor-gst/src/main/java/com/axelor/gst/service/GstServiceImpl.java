package com.axelor.gst.service;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.repo.InvoiceRepository;
import com.axelor.inject.Beans;

import java.math.BigDecimal;
import java.util.List;

public class GstServiceImpl implements GstService {

	@Override
	public InvoiceLine computeInvoiceLine(InvoiceLine invoiceLine, Boolean b) {
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
			System.err.println(gstAmount);
			BigDecimal t = amount.divide(new BigDecimal(2));

			if (b) {
				igst = gstAmount;
			} else {
				sgst = t;
				cgst = t;
			}
			grossAmount = amount.add(igst.add(sgst));
		}

		invoiceLine.setNetAmount(amount);
		invoiceLine.setNetIgst(igst);
		invoiceLine.setNetCgst(cgst);
		invoiceLine.setNetSgst(sgst);
		invoiceLine.setGrossAmount(grossAmount);

		return invoiceLine;

	}

	public Boolean checkState(Invoice invoice) {
		if (invoice != null) {

			if (invoice.getCompany() != null && invoice.getCompany().getAddress() != null
					&& invoice.getCompany().getAddress().getState() != null && invoice.getInvoiceAddress() != null
					&& invoice.getInvoiceAddress().getState() != null) {
				if (!(invoice.getCompany().getAddress().getState()).equals(invoice.getInvoiceAddress().getState()))
					return true;
			} else
				return false;

		}

		return null;

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
				netAmount = netAmount.add(iv.getNetAmount());
				netIgst = netIgst.add(iv.getNetIgst());
				netSgst = netSgst.add(iv.getNetSgst());
				netCgst = netCgst.add(iv.getNetCgst());
				grossAmount = grossAmount.add(iv.getGrossAmount());
			}
		}
		invoice = Beans.get(InvoiceRepository.class).find(invoice.getId());
		invoice.setNetAmount(netAmount);
		invoice.setNetIgst(netIgst);
		invoice.setNetSgst(netSgst);
		invoice.setNetCgst(netCgst);
		invoice.setGrossAmount(grossAmount);
		return invoice;
	}

	@Override
	public Invoice addInvoiceAddress(Invoice invoice) {
		if(invoice != null) {
			List <Address> address= invoice.getParty().getAddressList();
			for(Address a:address) {
				
			}
			
		
		
		
		
	}
		return invoice;
	}
}
