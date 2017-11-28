import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CartItem} from '../domain/cart-item';
import {Subject} from 'rxjs/Subject';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Product} from '../domain/product';
import 'rxjs/add/operator/map';
import {AuthService} from '../auth/auth.service';

@Injectable()
export class CartService {
  private cartItems: CartItem[] = [];
  cartItemSubject: Subject<CartItem[]> = new ReplaySubject<CartItem[]>(1);

  private static defaultHeaders(): HttpHeaders {
    return new HttpHeaders({'Content-Type' : 'application/json'});
  }

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {
    this.authService.loginState.subscribe((loginState) => {
      if (loginState) {
        this.refreshCartItems();
      }
    });
  }

  public addProductToCart(product: Product) {
    if (this.cartItems.find((cartItem) => cartItem.productId === product.id ) !== undefined) {
      return;
    }

    const headers: HttpHeaders =  CartService.defaultHeaders();
    this.httpClient.post('/cart', { id: product.id }, {headers: headers}).subscribe( () => {
      this.refreshCartItems();
    });
  }

  public removeProductFromCart(product: Product) {
    if (this.cartItems.find((cartItem) => cartItem.productId === product.id ) === undefined) {
      return;
    }

    const headers: HttpHeaders =  CartService.defaultHeaders();
    this.httpClient.delete('/cart/' + product.id, {headers: headers}).subscribe( () => {
      this.refreshCartItems();
    });
  }

  private refreshCartItems(): void {
    const headers: HttpHeaders =  CartService.defaultHeaders();
    this.httpClient.get<CartItem[]>('/cart', {headers: headers}).subscribe((cartItems) =>  {
      this.cartItems = cartItems;
      this.cartItemSubject.next(cartItems);
    });
  }

}
