import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CartService} from '../service/cart.service';
import {ProductService} from '../service/product.service';
import {Product} from '../domain/product';
import {CartItem} from '../domain/cart-item';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit, OnDestroy {
  productsInCart: Product[] = null;
  private cartSubscription: Subscription;
  @Input() standalone = true;

  constructor(
    private cartService: CartService,
    private productService: ProductService
  ) { }

  ngOnInit() {
    this.cartSubscription = this.cartService.cartItemSubject.subscribe((productIds) => {
      this.productService.loadProducts().subscribe((products) => {
        this.extractProductsInCart(products, productIds);
      });
    });
  }

  ngOnDestroy(): void {
    this.cartSubscription.unsubscribe();
  }

  removeProductFromCart(product: Product) {
    this.cartService.removeProductFromCart(product);
  }

  private extractProductsInCart(allProducts: Product[], cartItems: CartItem[]) {
    this.productsInCart = allProducts.filter((product) => {
      return cartItems.find((cartItem) => cartItem.productId === product.id) !== undefined;
    });
  }
}
