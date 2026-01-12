import { User } from './User';

export interface Account {
  id: number;
  accountNumber: string;
  balance: number;
  owner: User;
}
