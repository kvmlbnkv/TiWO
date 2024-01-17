import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginRegisterComponent} from "./login-register/login-register.component";
import {ListsComponent} from "./lists/lists.component";
import {AddOrderComponent} from "./add-order/add-order.component";
import {AddListComponent} from "./add-list/add-list.component";
import {AuthGuard} from "./auth.guard";
import {UpdateListComponent} from "./update-list/update-list.component";


const routes: Routes = [
  {path:'', redirectTo:'login', pathMatch:'full'},
  {path:'login', component:LoginRegisterComponent},
  {path:'lists', component:ListsComponent, canActivate:[AuthGuard]},
  {path:"add_order", component:AddOrderComponent, canActivate:[AuthGuard]},
  {path:"add_list", component:AddListComponent, canActivate:[AuthGuard]},
  {path:"update_list", component:UpdateListComponent, canActivate:[AuthGuard]}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule{

}
