import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit, OnDestroy {

  isLoggedIn = false;
  private loginStateSubscription: Subscription;

  constructor(
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loginStateSubscription = this.authService.loginState.subscribe((isLoggedIn) => this.isLoggedIn = isLoggedIn);
  }

  ngOnDestroy(): void {
    this.loginStateSubscription.unsubscribe();
  }

  onLogin(username: string, password: string): void {
    this.authService.login(username, password);
  }

}
