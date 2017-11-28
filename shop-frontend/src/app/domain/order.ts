import {BoughtProduct} from './bought-product';

export interface Order {
  id: string;
  orderTime: string;
  orderTotal: number;
  creditCardDigits: string;
  boughtProducts: BoughtProduct[];
}
