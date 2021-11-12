package com.axelor.gst.service;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.inject.Beans;
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
          if ((a.getType() != null)) {
            if ((a.getType().equalsIgnoreCase("0"))) {
              adrs = a;
            } else if (a.getType().equalsIgnoreCase("1")) {
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
      if (invoice.getCompany() != null
          && invoice.getCompany().getAddress() != null
          && invoice.getCompany().getAddress().getState() != null
          && invoice.getInvoiceAddress() != null
          && invoice.getInvoiceAddress().getState() != null) {
        if (!(invoice.getCompany().getAddress().getState())
            .equals(invoice.getInvoiceAddress().getState())) return true;
        else return false;
      }
    }
    return null;
  }

  public Boolean checkShippingAddress(Invoice invoice) {
    if (invoice != null) {
      if (invoice.getIsInvoiceAddress() != null && (!invoice.getIsInvoiceAddress())) return true;
      else return false;
    }
    return null;
  }

  @Override
  public Invoice addShippingAddress(Invoice invoice, Boolean isInvoiceadrs) {
    Address adrs = null;
    if (invoice != null && isInvoiceadrs) {
      for (Address a : invoice.getParty().getAddressList()) {
        if (a != null) {
          if ((a.getType() != null)) {
            if ((a.getType().equalsIgnoreCase("0"))) {
              adrs = a;
            } else if (a.getType().equalsIgnoreCase("2")) {
              adrs = a;
            }
          }
        }
      }
      invoice.setShippngAddress(adrs);
    }
    return invoice;
  }

  @Override
  public Invoice addPartyContact(Invoice invoice, Boolean isPrimary) {
    Contact phone = null;
    if (invoice != null && invoice.getParty() != null) {
      if (isPrimary != null) {
        if (invoice.getParty().getContactList() != null) {
          for (Contact c : invoice.getParty().getContactList()) {
            if (c != null) {
              phone = c;
            }
          }
        }
      }
      invoice.setPartyContact(phone);
    }
    return invoice;
  }

  @Override
  public Party computeReference(Party party) {
    if (party != null) {
      if (party.getReference() == null) {
        Sequence seq =
            Beans.get(SequenceRepository.class).all().filter("self.model.name= 'Party'").fetchOne();
        party.setReference(seq.getNextNumber());
        return party;
      }
    }
    return null;
  }

  @Override
  public Sequence getNextNumber(Sequence sequence) {
    if (sequence != null) {
      String nextNumber = null;
      if (sequence.getNextNumber() == null) {
        nextNumber = sequence.getPrefix();
        for (int i = 0; i < sequence.getPadding() - 1; i++) {
          nextNumber += "0";
        }
        nextNumber += "1";
        if (sequence.getSuffix() != null) {
          nextNumber = nextNumber + sequence.getSuffix();
        }

      } else {
        nextNumber = sequence.getNextNumber();
        String number = nextNumber.replaceAll("[^0-9]", "");
        nextNumber = sequence.getPrefix();
        for (int i = 0; i < (sequence.getPadding() - number.length()); i++) {
          nextNumber += "0";
        }
        if (sequence.getSuffix() != null) {
          nextNumber = nextNumber + number + sequence.getSuffix();

        } else {
          nextNumber = nextNumber + number;
        }
      }
      sequence.setNextNumber(nextNumber);
    }

    return null;
  }

  @Override
  public Boolean checkPrimary(Invoice invoice) {
    Boolean isnull = false;
    if (invoice != null) {
      if (invoice.getParty() != null) {
        if (invoice.getParty().getContactList() != null) {
          isnull = true;
          for (Contact c : invoice.getParty().getContactList()) {

            if (c.getType().equalsIgnoreCase("0")) {
              return true;
            }
          }
        } else if (isnull) return false;
      }
    }
    return null;
  }
}
