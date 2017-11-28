import {Component} from '@angular/core';
import {OrderService} from '../service/order.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent {

  constructor(
    private orderService: OrderService,
    private router: Router
  ) { }

  placeOrder(order) {
    this.orderService.placeOrder(order).subscribe((confirmedOrder) => {
      this.router.navigate(['/displayOrder', confirmedOrder.id]);
    });
  }

}
