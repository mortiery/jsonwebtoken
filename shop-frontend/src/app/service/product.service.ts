import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Product} from '../domain/product';

@Injectable()
export class ProductService {
  private static defaultHeaders(): HttpHeaders {
    return new HttpHeaders({'Content-Type' : 'application/json'});
  }

  constructor(
    private httpClient: HttpClient
  ) {}

  loadProducts(): Observable<Product[]> {
    const headers: HttpHeaders =  ProductService.defaultHeaders();
    return this.httpClient.get<Product[]>('/products/products', {headers: headers});
  }

}
