/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.svefaktura;
import oasis.names.tc.ubl.codelist.countryidentificationcode._1._0.CountryIdentificationCodeContentType;
import oasis.names.tc.ubl.codelist.countryidentificationcode._1._0.CountryIdentificationCodeType;
import oasis.names.tc.ubl.codelist.currencycode._1._0.CurrencyCodeContentType;
import oasis.names.tc.ubl.codelist.currencycode._1._0.CurrencyCodeType;
import oasis.names.tc.ubl.codelist.paymentmeanscode._1._0.PaymentMeansCodeType;
import oasis.names.tc.ubl.commonaggregatecomponents._1._0.ItemIdentificationType;
import oasis.names.tc.ubl.commonaggregatecomponents._1._0.ItemType;
import oasis.names.tc.ubl.commonaggregatecomponents._1._0.LineItemType;
import oasis.names.tc.ubl.commonbasiccomponents._1._0.*;
import oasis.names.tc.ubl.unspecializeddatatypes._1._0.CodeType;
import oasis.names.tc.ubl.unspecializeddatatypes._1._0.IdentifierType;
import sfti.commonaggregatecomponents._1._0.*;
import sfti.documents.basicinvoice._1._0.LineItemCountNumericType;
import sfti.documents.basicinvoice._1._0.ObjectFactory;
import sfti.documents.basicinvoice._1._0.SFTIInvoiceType;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import oasis.names.tc.ubl.specializeddatatypes._1._0.UBLAmountType;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.exceptions.SXEntityNotFoundException;
import se.saljex.sxserver.ServerUtil;
import se.saljex.sxserver.tables.TableFaktura1;
import se.saljex.sxserver.tables.TableFaktura2;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableKund;

/**
 *
 * @author Ulf
 */
public class SveFaktura {

	private EntityManager em;
	private TableFuppg fup;
	private CurrencyCodeContentType  currency;
//	private	XMLGregorianCalendar xmlgregcal = new XMLGregorianCalendarImpl();
	
	public SveFaktura(EntityManager em, String currency) {
		this.em = em;
		this.currency = CurrencyCodeContentType.fromValue(currency);
		fup = ServerUtil.getFuppg(em);
	}

	public void getFakturaXML(int faktnr) throws SXEntityNotFoundException {
		TableFaktura1 fa1=null;
		TableFaktura2 fa2=null;
		
		ObjectFactory of = new ObjectFactory();		
		SFTIInvoiceType invoice = new SFTIInvoiceType();
				
		fa1 = (TableFaktura1)em.find(TableFaktura1.class, faktnr);
		if (fa1==null) throw new SXEntityNotFoundException("Faktura: " + faktnr + " hittades inte.");
		
		Query q = em.createNamedQuery("TableFaktura2.findByFaktnr");
		q.setParameter("faktnr", faktnr);
		List<TableFaktura2> f2List = q.getResultList();
		if (f2List != null) {
			for (TableFaktura2 f2 : f2List) {
				
			}
		}
	}
	
	public XMLGregorianCalendar getXMLGregorianCalendar(java.util.Date date) {
		GregorianCalendar gregory = new GregorianCalendar();
		gregory.setTime(date);
		
		return new XMLGregorianCalendarImpl(gregory);		
	}
	public XMLGregorianCalendar getXMLGregorianCalendarAddDays(java.util.Date date, int days) {
		GregorianCalendar gregory = new GregorianCalendar();
		gregory.setTime(date);
		gregory.add(Calendar.DATE, days);
		return new XMLGregorianCalendarImpl(gregory);		
	}

	
	public void setHuvud(SFTIInvoiceType invoice, TableFaktura1 f1, TableKund k) {
		//Fakturanummer
		invoice.setID(getSimpleIdentifier(f1.getFaktnr().toString()));
		
		//Valuta
		CurrencyCodeType currencyCode = new CurrencyCodeType();
		currencyCode.setValue(currency);
		invoice.setInvoiceCurrencyCode(currencyCode);
		
		//Fakturadatum
		IssueDateType issueDate =  new IssueDateType();
		issueDate.setValue(getXMLGregorianCalendar(f1.getDatum()));
		invoice.setIssueDate(issueDate);
		
		// Fakturatyp - 380=Faktura, 381 = Kreditfaktura
		CodeType codeType = new CodeType();
			if (f1.getTAttbetala() >= 0)	codeType.setValue("380");
			else codeType.setValue("381");
		invoice.setInvoiceTypeCode(codeType);

		
		SFTILegalTotalType legalTotal = new SFTILegalTotalType();
			legalTotal.setLineExtensionTotalAmount(getExtensionTotalAmount(getBigDecimalRound2(f1.getTNetto())));

			legalTotal.setTaxExclusiveTotalAmount(getTotalAmount(getBigDecimalRound2(f1.getTNetto())));

			legalTotal.setTaxInclusiveTotalAmount(getTotalAmount(getBigDecimalRound2(f1.getTAttbetala())));

			legalTotal.setRoundOffAmount(getAmount(getBigDecimalRound2(f1.getTOrut())));		
		invoice.setLegalTotal(legalTotal);
		
		SFTIBuyerPartyType buyerParty = new SFTIBuyerPartyType();
			SFTIPartyType partyType = new SFTIPartyType();
				SFTIPartyNameType partyName = new SFTIPartyNameType();
					NameType name = new NameType();
					name.setValue(f1.getNamn());
					partyName.getName().add(name);
				partyType.setPartyName(partyName);

				SFTIAddressType adress = new SFTIAddressType();		
					SFTIAddressLineType addressLine = new SFTIAddressLineType();
					LineType addressLine2 = new LineType();
					addressLine2.setValue(f1.getAdr1());
					addressLine.getLine().add(addressLine2);
					addressLine2 = new LineType();
					addressLine2.setValue(f1.getAdr2());
					addressLine.getLine().add(addressLine2);
					addressLine2 = new LineType();
					addressLine2.setValue(f1.getAdr3());
					addressLine.getLine().add(addressLine2);
					adress.setAddressLine(addressLine);
				partyType.setAddress(adress);
				
				SFTIPartyTaxSchemeType partyTaxScheme = new SFTIPartyTaxSchemeType();
					partyTaxScheme.setCompanyID(getIdentifier(k.getRegnr()));
					SFTITaxSchemeType taxScheme = new SFTITaxSchemeType();
						taxScheme.setID(getIdentifier("VAT"));
					partyTaxScheme.setTaxScheme(taxScheme);
				partyType.getPartyTaxScheme().add(partyTaxScheme);
			buyerParty.setParty(partyType);
		invoice.setBuyerParty(buyerParty);
		
		SFTITaxTotalType taxTotal = new SFTITaxTotalType();
			taxTotal.setTotalTaxAmount(getTaxAmount(getBigDecimalRound2(f1.getTMoms())));

			SFTITaxSubTotalType taxSubTotal = new SFTITaxSubTotalType();
				taxSubTotal.setTaxableAmount(getAmount(getBigDecimalRound2(f1.getTNetto())));

				taxSubTotal.setTaxAmount(getTaxAmount(getBigDecimalRound2(f1.getTMoms())));

				SFTITaxCategoryType taxCategory = new SFTITaxCategoryType();
					PercentType percent = new PercentType();
						percent.setValue(getBigDecimalRound2(f1.getMomsproc()));
					taxCategory.setPercent(percent);

					taxCategory.setID(getIdentifier("S")); //Standard

					taxScheme = new SFTITaxSchemeType();
						taxScheme.setID(getIdentifier("VAT"));
					taxCategory.setTaxScheme(taxScheme);
				taxSubTotal.setTaxCategory(taxCategory);
			taxTotal.getTaxSubTotal().add(taxSubTotal);
		invoice.getTaxTotal().add(taxTotal);

		SFTIPaymentMeansType paymentMeans;
		PaymentDateType paymentDate;
		PaymentMeansCodeType paymentMeansCode;
		SFTIFinancialAccountType financialAccount;
		SFTIBranchType branch;
		SFTIFinancialInstitutionType financialInstitute;
		if (!SXUtil.isEmpty(fup.getBankgiro())) {
			paymentMeans = new SFTIPaymentMeansType();
				paymentDate = new PaymentDateType();
					paymentDate.setValue(getXMLGregorianCalendarAddDays(f1.getDatum(),f1.getKtid()));
				paymentMeans.setDuePaymentDate(paymentDate);

				paymentMeansCode = new PaymentMeansCodeType();
					paymentMeansCode.setValue("1"); //Använd konstant 1 (= ej definierat)
				paymentMeans.setPaymentMeansTypeCode(paymentMeansCode);

				financialAccount = new SFTIFinancialAccountType();
					financialAccount.setID(getIdentifier(fup.getBankgiro())); //Bankgiro

					financialAccount.setPaymentInstructionID(getSimpleIdentifier(f1.getFaktnr().toString()));

					branch = new SFTIBranchType();
						financialInstitute  = new SFTIFinancialInstitutionType();
							financialInstitute.setID(getIdentifier("BGABSESS"));
						branch.setFinancialInstitution(financialInstitute);
					financialAccount.setFinancialInstitutionBranch(branch);
				paymentMeans.setPayeeFinancialAccount(financialAccount);
			invoice.getPaymentMeans().add(paymentMeans);
		}
		
		if (!SXUtil.isEmpty(fup.getPostgiro())) {
			paymentMeans = new SFTIPaymentMeansType();
				paymentDate = new PaymentDateType();
					paymentDate.setValue(getXMLGregorianCalendarAddDays(f1.getDatum(),f1.getKtid()));
				paymentMeans.setDuePaymentDate(paymentDate);

				paymentMeansCode = new PaymentMeansCodeType();
					paymentMeansCode.setValue("1"); //Använd konstant 1 (= ej definierat)
				paymentMeans.setPaymentMeansTypeCode(paymentMeansCode);

				financialAccount = new SFTIFinancialAccountType();
					financialAccount.setID(getIdentifier(fup.getPostgiro()));

					financialAccount.setPaymentInstructionID(getSimpleIdentifier(f1.getFaktnr().toString()));

					branch = new SFTIBranchType();
						financialInstitute  = new SFTIFinancialInstitutionType();
							financialInstitute.setID(getIdentifier("PGSISESS"));
						branch.setFinancialInstitution(financialInstitute);
					financialAccount.setFinancialInstitutionBranch(branch);
				paymentMeans.setPayeeFinancialAccount(financialAccount);
			invoice.getPaymentMeans().add(paymentMeans);
		}
		
		//IBAN
		String iban = ServerUtil.getSXReg(em, "IBAN");
		if (!SXUtil.isEmpty(iban)) {
			paymentMeans = new SFTIPaymentMeansType();
				paymentDate = new PaymentDateType();
					paymentDate.setValue(getXMLGregorianCalendarAddDays(f1.getDatum(),f1.getKtid()));
				paymentMeans.setDuePaymentDate(paymentDate);

				paymentMeansCode = new PaymentMeansCodeType();
					paymentMeansCode.setValue("1");
				paymentMeans.setPaymentMeansTypeCode(paymentMeansCode);

				financialAccount = new SFTIFinancialAccountType();
					financialAccount.setID(getIdentifier(iban));

					financialAccount.setPaymentInstructionID(getSimpleIdentifier(f1.getFaktnr().toString()));

					branch = new SFTIBranchType();
						financialInstitute  = new SFTIFinancialInstitutionType();
							financialInstitute.setID(getIdentifier("IBAN"));
						branch.setFinancialInstitution(financialInstitute);
					financialAccount.setFinancialInstitutionBranch(branch);
				paymentMeans.setPayeeFinancialAccount(financialAccount);
			invoice.getPaymentMeans().add(paymentMeans);
		}

		
		SFTIDocumentReferenceType documentReference = new SFTIDocumentReferenceType();
			IdentifierType refIdentifierType = new IdentifierType();
				refIdentifierType.setValue(f1.getMarke());
			documentReference.setID(refIdentifierType);
		invoice.getRequisitionistDocumentReference().add(documentReference);
		
		
		SFTISellerPartyType sellerParty = new SFTISellerPartyType();
			SFTIPartyType sellerParty2 = new SFTIPartyType();
				partyName  = new SFTIPartyNameType();
					name = new NameType();
					name.setValue(fup.getNamn());
					partyName.getName().add(name);
				sellerParty2.setPartyName(partyName);

				adress = new SFTIAddressType();
					addressLine = new SFTIAddressLineType();
						addressLine2 = new LineType();
							addressLine2.setValue(fup.getAdr1());
						addressLine.getLine().add(addressLine2);
						addressLine2 = new LineType();
							addressLine2.setValue(fup.getAdr2());
						addressLine.getLine().add(addressLine2);
						addressLine2 = new LineType();
							addressLine2.setValue(fup.getAdr3());
						addressLine.getLine().add(addressLine2);
					adress.setAddressLine(addressLine);

					CountryIdentificationCodeContentType countryCode = CountryIdentificationCodeContentType.fromValue(ServerUtil.getSXReg(em, "Landskod", "SE"));
					SFTICountryType country = new SFTICountryType();
					
						CountryIdentificationCodeType countryIdCode = new CountryIdentificationCodeType();
							countryIdCode.setValue(countryCode);
						country.setIdentificationCode(countryIdCode);
					adress.setCountry(country);
				sellerParty2.setAddress(adress);

				partyTaxScheme = new SFTIPartyTaxSchemeType();
					partyTaxScheme.setCompanyID(getIdentifier("SE556409006501"));

					taxScheme = new SFTITaxSchemeType();
						taxScheme.setID(getIdentifier("VAT"));
					partyTaxScheme.setTaxScheme(taxScheme);			
				sellerParty2.getPartyTaxScheme().add(partyTaxScheme);

				partyTaxScheme = new SFTIPartyTaxSchemeType();
					partyTaxScheme.setCompanyID(getIdentifier("SE556409006501"));

					ReasonType reason = new ReasonType();
						reason.setValue("F-skattebevis finns");
					partyTaxScheme.setExemptionReason(reason);

					taxScheme = new SFTITaxSchemeType();
						taxScheme.setID(getIdentifier("SWT"));

						adress = new SFTIAddressType();
						CityNameType cityName = new CityNameType();
						cityName.setValue("Grums");
						adress.setCityName(cityName);
						country = new SFTICountryType();
							countryIdCode = new CountryIdentificationCodeType();
							countryIdCode.setValue(CountryIdentificationCodeContentType.SE);
							country.setIdentificationCode(countryIdCode);
						adress.setCountry(country);
						partyTaxScheme.setRegistrationAddress(adress);
					partyTaxScheme.setTaxScheme(taxScheme);
				sellerParty2.getPartyTaxScheme().add(partyTaxScheme);

				SFTIContactType contact = new SFTIContactType();
					TelephoneType telephone = new TelephoneType();
						telephone.setValue("055561610");
					contact.setTelephone(telephone);

					TelefaxType telefax = new TelefaxType();
						telefax.setValue("055512376");
					contact.setTelefax(telefax);

					MailType mail = new MailType();
						mail.setValue("info@saljex.se");
					contact.setElectronicMail(mail);
				sellerParty2.setContact(contact);
			sellerParty.setParty(sellerParty2);
		invoice.setSellerParty(sellerParty);
		
		SFTIDeliveryType delivery = new SFTIDeliveryType();
			addressLine = new SFTIAddressLineType();
				addressLine2 = new LineType();
					addressLine2.setValue("Leveransgatan");
				addressLine.getLine().add(addressLine2);
				
				addressLine2 = new LineType();
					addressLine2.setValue("Stockholm");
				addressLine.getLine().add(addressLine2);
			adress.setAddressLine(addressLine);			
		delivery.setDeliveryAddress(adress);
			
		invoice.setDelivery(delivery);
		
	}
	
	
	public String getXML() throws JAXBException {
		
		ObjectFactory of = new ObjectFactory();
		
		SFTIInvoiceType invoice = new SFTIInvoiceType();
	

		//Fakturarader
		SFTIInvoiceLineType line = new SFTIInvoiceLineType();
			SFTIItemType itemType = new SFTIItemType();
				DescriptionType descTyp = new DescriptionType();
					descTyp.setValue("Grej 1010");		
				itemType.setDescription(descTyp);

				SFTIItemIdentificationType itemIdentication = new SFTIItemIdentificationType();
					itemIdentication.setID(getIdentifier("EC1010"));		
				itemType.setSellersItemIdentification(itemIdentication);

				SFTIBasePriceType basePrice = new SFTIBasePriceType();
					basePrice.setPriceAmount(getPriceAmount(getBigDecimalRound2(100.0)));
				itemType.setBasePrice(basePrice);
			line.setItem(itemType);

			line.setLineExtensionAmount(getExtensionAmount(getBigDecimalRound2(120.0)));

			SFTIInvoiceLineAllowanceCharge invoiceLineAllowanceCharge = new SFTIInvoiceLineAllowanceCharge();
				invoiceLineAllowanceCharge.setAllowanceChargeBaseAmount(getAmount(getBigDecimalRound2(200.0)));

				ChargeIndicatorType chargeIndicator = new ChargeIndicatorType();
					chargeIndicator.setValue(false);
				invoiceLineAllowanceCharge.setChargeIndicator(chargeIndicator);

				invoiceLineAllowanceCharge.setAmount(getAmount(getBigDecimalRound2(80.0)));
			line.setAllowanceCharge(invoiceLineAllowanceCharge);

			QuantityType quantity = new QuantityType();
				quantity.setQuantityUnitCode("ST");
				quantity.setValue(getBigDecimalRound2(2.0));
			line.setInvoicedQuantity(quantity);

			line.setID(getSimpleIdentifier("1"));		
		invoice.getInvoiceLine().add(line);

		//Antal fakturarader
		LineItemCountNumericType lineItemCountNumeric = new LineItemCountNumericType();
			lineItemCountNumeric.setValue(getBigDecimalRound2(1.0));
		invoice.setLineItemCountNumeric(lineItemCountNumeric);
		
		
		
		
		
		
		
		Marshaller m=null;
		
		JAXBContext jc = JAXBContext.newInstance("sfti.documents.basicinvoice._1._0");
		m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		Writer w = new StringWriter();
		m.marshal( of.createInvoice(invoice), w);
		return w.toString();

	}
	
	private BigDecimal getBigDecimalRound2(Double value) {
		if (value == null) return new BigDecimal(0);
		else {
			return new BigDecimal(Math.round(value*100)/100);
		}
	}
	
	private ExtensionAmountType getExtensionAmount(BigDecimal value) {
		ExtensionAmountType amount = new ExtensionAmountType();
		setAmountCurrency(amount);
		return amount;
	}
	private ExtensionTotalAmountType getExtensionTotalAmount(BigDecimal value) {
		ExtensionTotalAmountType amount = new ExtensionTotalAmountType();
		setAmountCurrency(amount);
		return amount;
	}
	private TotalAmountType getTotalAmount(BigDecimal value) {
		TotalAmountType amount = new TotalAmountType();
		setAmountCurrency(amount);
		return amount;
	}
	private PriceAmountType getPriceAmount(BigDecimal value) {
		PriceAmountType amount = new PriceAmountType();
		setAmountCurrency(amount);
		return amount;
	}
	private AmountType getAmount(BigDecimal value) {
		AmountType amount = new AmountType();
		setAmountCurrency(amount);
		return amount;
	}
	private TaxAmountType getTaxAmount(BigDecimal value) {
		TaxAmountType amount = new TaxAmountType();
		setAmountCurrency(amount);
		return amount;
	}
	
	
	private void setAmountCurrency(UBLAmountType amount) {
		amount.setAmountCurrencyID(this.currency.value());
	}
	
	
	private SFTISimpleIdentifierType getSimpleIdentifier(String value) {
		SFTISimpleIdentifierType simpleIdentifier = new SFTISimpleIdentifierType();
		simpleIdentifier.setValue(value);
		return simpleIdentifier;
	}
	private IdentifierType getIdentifier(String value) {
		IdentifierType identifier = new IdentifierType();
		identifier.setValue(value);
		return identifier;
	}
	
	
}
