import { Account } from "./account.model";


export class AuditRegister {

/** auditId: Register Audit Id */
auditId?: number;

/** requestDate: Request Date */
requestDate?: Date;

/** operation: Url or description of operation */
operation?: string;

/** Account Relation:  */
account?: Account;
 	
  }
