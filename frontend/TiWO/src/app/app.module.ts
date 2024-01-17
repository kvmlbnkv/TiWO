import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginRegisterComponent } from './login-register/login-register.component';
import { FormsModule } from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { ListsComponent } from './lists/lists.component';
import { AppRoutingModule } from './app-routing.module';
import {AuthInterceptor} from "./authinterceptor";
import { AddOrderComponent } from './add-order/add-order.component';
import { AddListComponent } from './add-list/add-list.component';
import {JWT_OPTIONS, JwtHelperService} from "@auth0/angular-jwt";
import { UpdateListComponent } from './update-list/update-list.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginRegisterComponent,
    ListsComponent,
    AddOrderComponent,
    AddListComponent,
    UpdateListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    {provide: JWT_OPTIONS, useValue: JWT_OPTIONS}, JwtHelperService],
  bootstrap: [AppComponent]
})
export class AppModule { }
