import { Type } from "./type.model";
import { Account } from "./account.model";


export class Transaction {

/** transactionId: The ID of the transaction. This is a unique ID for the transaction, valid to identify a transaction across all of your accounts provided by Banca Sella. */
transactionId?: number;

/** operationId: The ID of the accounting operation. This ID matches multiple logically connected transactions (e.g., the money transfer with its fees). */
operationId?: number;

/** accountingDate: The date on which the transaction was accounted on the account. */
accountingDate?: Date;

/** valueDate: The value date of the transaction. */
valueDate?: Date;

/** amount: The amount of the transaction. */
amount?: number;

/** currency: The currency of the transaction. */
currency?: string;

/** description: The description of the transaction. */
description?: string;

/** Type Relation:  */
type?: Type;

/** Account Relation:  */
account?: Account;
 	
  }
