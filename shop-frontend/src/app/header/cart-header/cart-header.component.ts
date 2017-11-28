import {Component, OnDestroy, OnInit} from '@angular/core';
import {CartService} from '../../service/cart.service';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-cart-header',
  templateUrl: './cart-header.component.html',
  styleUrls: ['./cart-header.component.css']
})
export class CartHeaderComponent implements OnInit, OnDestroy {
  cartSubscription: Subscription;
  numberOfItems = 0;

  constructor(
    private cartService: CartService
  ) { }

  ngOnInit() {
     this.cartSubscription = this.cartService.cartItemSubject.subscribe((cartItems) => this.numberOfItems = cartItems.length);
  }

  ngOnDestroy(): void {
    this.cartSubscription.unsubscribe();
  }

}
