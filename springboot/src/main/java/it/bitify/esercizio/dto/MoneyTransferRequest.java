package it.bitify.esercizio.dto;

import java.io.Serializable;
import java.math.BigDecimal;


public class MoneyTransferRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Creditor creditor; 
	private String executionDate;
	private String uri;
	private String description;
	private BigDecimal amount;
	private String currency;
	private Boolean isUrgent;
	private Boolean isInstant;

	private String feeType;
	private String feeAccountId;
	private TaxRelief taxRelief; 

	public MoneyTransferRequest(Creditor creditor, String executionDate, String uri, String description, BigDecimal amount,
			String currency, Boolean isUrgent, Boolean isInstant, String feeType, String feeAccountId,
			TaxRelief taxRelief) {
		super();
		this.creditor = creditor;
		this.executionDate = executionDate;
		this.uri = uri;
		this.description = description;
		this.amount = amount;
		this.currency = currency;
		this.isUrgent = isUrgent;
		this.isInstant = isInstant;
		this.feeType = feeType;
		this.feeAccountId = feeAccountId;
		this.taxRelief = taxRelief;
	}

	public MoneyTransferRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static class Account {
		private String accountCode;
		private String bicCode;
		public String getAccountCode() {
			return accountCode;
		}
		public void setAccountCode(String accountCode) {
			this.accountCode = accountCode;
		}
		public String getBicCode() {
			return bicCode;
		}
		public void setBicCode(String bicCode) {
			this.bicCode = bicCode;
		}
		public Account() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Account(String accountCode, String bicCode) {
			super();
			this.accountCode = accountCode;
			this.bicCode = bicCode;
		}
		
	}

	public static class Address {
		private String address;
		private String city;
		private String countryCode;
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCountryCode() {
			return countryCode;
		}
		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}
		public Address() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Address(String address, String city, String countryCode) {
			super();
			this.address = address;
			this.city = city;
			this.countryCode = countryCode;
		}
		
	}

	public static class Creditor {
		private String name;
		private Account account; 
		private Address address; 
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Account getAccount() {
			return account;
		}
		public void setAccount(Account account) {
			this.account = account;
		}
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
		public Creditor() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Creditor(String name, Account account, Address address) {
			super();
			this.name = name;
			this.account = account;
			this.address = address;
		}
		
	}

	public static class  LegalPersonBeneficiary {
		private String fiscalCode;
		private String legalRepresentativeFiscalCode;
		public String getFiscalCode() {
			return fiscalCode;
		}
		public void setFiscalCode(String fiscalCode) {
			this.fiscalCode = fiscalCode;
		}
		public String getLegalRepresentativeFiscalCode() {
			return legalRepresentativeFiscalCode;
		}
		public void setLegalRepresentativeFiscalCode(String legalRepresentativeFiscalCode) {
			this.legalRepresentativeFiscalCode = legalRepresentativeFiscalCode;
		}
		public LegalPersonBeneficiary() {
			super();
			// TODO Auto-generated constructor stub
		}
		public LegalPersonBeneficiary(String fiscalCode, String legalRepresentativeFiscalCode) {
			super();
			this.fiscalCode = fiscalCode;
			this.legalRepresentativeFiscalCode = legalRepresentativeFiscalCode;
		}
		
	}

	public static class  NaturalPersonBeneficiary {
		private String fiscalCode1;
		private String fiscalCode2;
		private String fiscalCode3;
		private String fiscalCode4;
		private String fiscalCode5;
		public String getFiscalCode1() {
			return fiscalCode1;
		}
		public void setFiscalCode1(String fiscalCode1) {
			this.fiscalCode1 = fiscalCode1;
		}
		public String getFiscalCode2() {
			return fiscalCode2;
		}
		public void setFiscalCode2(String fiscalCode2) {
			this.fiscalCode2 = fiscalCode2;
		}
		public String getFiscalCode3() {
			return fiscalCode3;
		}
		public void setFiscalCode3(String fiscalCode3) {
			this.fiscalCode3 = fiscalCode3;
		}
		public String getFiscalCode4() {
			return fiscalCode4;
		}
		public void setFiscalCode4(String fiscalCode4) {
			this.fiscalCode4 = fiscalCode4;
		}
		public String getFiscalCode5() {
			return fiscalCode5;
		}
		public void setFiscalCode5(String fiscalCode5) {
			this.fiscalCode5 = fiscalCode5;
		}
		public NaturalPersonBeneficiary() {
			super();
			// TODO Auto-generated constructor stub
		}
		public NaturalPersonBeneficiary(String fiscalCode1, String fiscalCode2, String fiscalCode3, String fiscalCode4,
				String fiscalCode5) {
			super();
			this.fiscalCode1 = fiscalCode1;
			this.fiscalCode2 = fiscalCode2;
			this.fiscalCode3 = fiscalCode3;
			this.fiscalCode4 = fiscalCode4;
			this.fiscalCode5 = fiscalCode5;
		}
		
	}

	public static class TaxRelief {
		private String taxReliefId;
		private Boolean isCondoUpgrade;
		private String creditorFiscalCode;
		private String beneficiaryType;
		private NaturalPersonBeneficiary naturalPersonBeneficiary;
		private LegalPersonBeneficiary legalPersonBeneficiary;
		public String getTaxReliefId() {
			return taxReliefId;
		}
		public void setTaxReliefId(String taxReliefId) {
			this.taxReliefId = taxReliefId;
		}
		
		public String getCreditorFiscalCode() {
			return creditorFiscalCode;
		}
		public void setCreditorFiscalCode(String creditorFiscalCode) {
			this.creditorFiscalCode = creditorFiscalCode;
		}
		public String getBeneficiaryType() {
			return beneficiaryType;
		}
		public void setBeneficiaryType(String beneficiaryType) {
			this.beneficiaryType = beneficiaryType;
		}
		public NaturalPersonBeneficiary getNaturalPersonBeneficiary() {
			return naturalPersonBeneficiary;
		}
		public void setNaturalPersonBeneficiary(NaturalPersonBeneficiary naturalPersonBeneficiary) {
			this.naturalPersonBeneficiary = naturalPersonBeneficiary;
		}
		public LegalPersonBeneficiary getLegalPersonBeneficiary() {
			return legalPersonBeneficiary;
		}
		public void setLegalPersonBeneficiary(LegalPersonBeneficiary legalPersonBeneficiary) {
			this.legalPersonBeneficiary = legalPersonBeneficiary;
		}
		public TaxRelief() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Boolean getIsCondoUpgrade() {
			return isCondoUpgrade;
		}
		public void setIsCondoUpgrade(Boolean isCondoUpgrade) {
			this.isCondoUpgrade = isCondoUpgrade;
		}
		public TaxRelief(String taxReliefId, Boolean isCondoUpgrade, String creditorFiscalCode, String beneficiaryType,
				NaturalPersonBeneficiary naturalPersonBeneficiary, LegalPersonBeneficiary legalPersonBeneficiary) {
			super();
			this.taxReliefId = taxReliefId;
			this.isCondoUpgrade = isCondoUpgrade;
			this.creditorFiscalCode = creditorFiscalCode;
			this.beneficiaryType = beneficiaryType;
			this.naturalPersonBeneficiary = naturalPersonBeneficiary;
			this.legalPersonBeneficiary = legalPersonBeneficiary;
		}
		

	}

	public Creditor getCreditor() {
		return creditor;
	}

	public void setCreditor(Creditor creditor) {
		this.creditor = creditor;
	}

	public String getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeAccountId() {
		return feeAccountId;
	}

	public void setFeeAccountId(String feeAccountId) {
		this.feeAccountId = feeAccountId;
	}

	public TaxRelief getTaxRelief() {
		return taxRelief;
	}

	public void setTaxRelief(TaxRelief taxRelief) {
		this.taxRelief = taxRelief;
	}


	public Boolean getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(Boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	public Boolean getIsInstant() {
		return isInstant;
	}

	public void setIsInstant(Boolean isInstant) {
		this.isInstant = isInstant;
	}



	
	
}
