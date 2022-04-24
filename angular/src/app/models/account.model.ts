import { Transaction } from "./transaction.model";
import { AuditRegister } from "./audit-register.model";


export class Account {

/** accountId: The ID of the account. */
accountId?: number;

/** iban: The IBAN code of the account. */
iban?: string;

/** abiCode: The abiCode code of the account. */
abiCode?: string;

/** cabCode: The cabCode code of the account. */
cabCode?: string;

/** countryCode: The countryCode code of the account. */
countryCode?: string;

/** internationalCin: The internationalCin code of the account. */
internationalCin?: string;

/** nationalCin: The nationalCin code of the account. */
nationalCin?: string;

/** account: The account number. Substring of IBAN code. */
account?: string;

/** alias: The alias of the account, if any. */
alias?: string;

/** productName: The account product name. */
productName?: string;

/** holderName: The full name (or names) of the account holder (or holders). */
holderName?: string;

/** activatedDate: The date on which the account was activated. */
activatedDate?: Date;

/** currency: The native currency of the account. */
currency?: string;

/** Transaction Relation:  */
transactions?: Transaction[];

/** AuditRegister Relation:  */
auditRegisters?: AuditRegister[];
 	
  }
