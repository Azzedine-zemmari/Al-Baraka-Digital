import {Account} from './Account'
export interface Operation{
  id: number;
  type: 'DEPOSIT' | 'WITHDRAWAL' | 'TRANSFER';
  amount: number;
  status: string;
  createdAt: string;
  validatedAt?: string;
  executedAt?: string;
  accountSource?: Account | null;
  accountDestination?: Account | null;
}
