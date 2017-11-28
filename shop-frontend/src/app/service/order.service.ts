import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Order} from '../domain/order';

@Injectable()
export class OrderService {
  private static defaultHeaders(): HttpHeaders {
    return new HttpHeaders({'Content-Type' : 'application/json'});
  }

  constructor(
    private httpClient: HttpClient
  ) {}

  public placeOrder(creditCardData: any): Observable<Order> {
    const headers: HttpHeaders =  OrderService.defaultHeaders();
    return this.httpClient.post<Order>('/order', creditCardData, {headers: headers});
  }

  public getOrder(orderId: String): Observable<Order> {
    const headers: HttpHeaders =  OrderService.defaultHeaders();
    return this.httpClient.get<Order>('/order/' + orderId, {headers: headers});
  }

}
