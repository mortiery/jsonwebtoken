import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthService} from './auth/auth.service';
import {AuthTokenService} from './auth/auth-token.service';
import {AuthGuard} from './auth/auth.guard';
import {UserGuard} from './auth/user.guard';
import {AdminGuard} from './auth/admin.guard';
import {AuthInterceptor} from './auth/auth.interceptor';
import {LoginComponent} from './login/login.component';
import {MenuComponent} from './header/menu.component';
import {AppRoutingModule} from './app-routing.module';
import {HomeComponent} from './home/home.component';
import {ProductsComponent} from './products/products.component';
import {ProductService} from './service/product.service';
import {CartService} from './service/cart.service';
import {CartHeaderComponent} from './header/cart-header/cart-header.component';
import {CartComponent} from './cart/cart.component';
import {DisplayCartComponent} from './cart/display-cart/display-cart.component';
import {CheckoutComponent} from './checkout/checkout.component';
import {FormsModule} from '@angular/forms';
import {OrderService} from './service/order.service';
import {DisplayOrderComponent} from './display-order/display-order.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MenuComponent,
    HomeComponent,
    ProductsComponent,
    CartHeaderComponent,
    CartComponent,
    DisplayCartComponent,
    CheckoutComponent,
    DisplayOrderComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [
    AuthService,
    AuthTokenService,
    AuthGuard,
    UserGuard,
    AdminGuard,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    ProductService,
    CartService,
    OrderService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
