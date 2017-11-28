import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {Product} from '../domain/product';
import {CartService} from '../service/cart.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = null;

  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) { }

  ngOnInit() {
    this.productService.loadProducts().subscribe((products) => this.products = products);
  }

  addProductToCart(product: Product) {
    this.cartService.addProductToCart(product);
  }

}
