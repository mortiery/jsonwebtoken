import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Product} from '../../domain/product';

@Component({
  selector: 'app-display-cart',
  templateUrl: './display-cart.component.html',
  styleUrls: ['./display-cart.component.css']
})
export class DisplayCartComponent implements OnInit {

  @Input() productsInCart: Product[];
  @Input() standalone: boolean;
  @Output() removeProgram: EventEmitter<Product> = new EventEmitter<Product>();

  constructor() { }

  getOrderTotal() {
    if (this.productsInCart == null) {
      return 0.0;
    }
    return this.productsInCart
      .map((product) => product.price)
      .reduce((sum, nextPrice) => sum + nextPrice)
      .toFixed(2);
  }

  removeProductFromCart(product: Product) {
    this.removeProgram.emit(product);
  }

  ngOnInit() {
  }

}
