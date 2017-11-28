import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {OrderService} from '../service/order.service';
import {Order} from '../domain/order';

@Component({
  selector: 'app-display-order',
  templateUrl: './display-order.component.html',
  styleUrls: ['./display-order.component.css']
})
export class DisplayOrderComponent implements OnInit {

  private order: Order = null;

  constructor(
    private activatedRoute: ActivatedRoute,
    private orderService: OrderService
  ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(
      (params: Params) => {
        this.orderService.getOrder(params['orderId']).subscribe((order) => this.order = order);
      }
    );
  }

}
