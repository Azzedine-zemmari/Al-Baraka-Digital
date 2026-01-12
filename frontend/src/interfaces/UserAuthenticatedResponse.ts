import {Operation} from './Operation';
import {User} from './User';

  export interface UserAuthenticatedResponse{
    accountNumber : string
    balance : number;
    owner : User ;
    operations: Operation[]
  }
