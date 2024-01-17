import { Component } from '@angular/core';
import {ListsService} from "../services/ListsService";
import {Router} from "@angular/router";
import {AuthService} from "../services/AuthService";
import {List} from "../dtos/list";
import {OrdersService} from "../services/OrdersService";
import {tick} from "@angular/core/testing";

@Component({
  selector: 'app-lists',
  templateUrl: './lists.component.html',
  styleUrls: ['./lists.component.css']
})
export class ListsComponent {
  lists:Array<List> = [];

  constructor(private authService: AuthService, private listsService: ListsService, private ordersService: OrdersService, private router: Router){
  }

  ngOnInit(): void{
    this.showLists()
  }

  logout(){
    this.authService.logout();
    this.router.navigate(["/login"])
  }

  addOrder(listId: bigint) {
    localStorage.setItem("listId", String(listId));
    this.router.navigate(["/add_order"]);
  }

  showLists(){
    this.listsService.showLists(this.authService.getUsername()).subscribe((response)=>{
      console.log(response)
      this.lists = response;
    });
  }

  async realizeOrder(id: bigint) {
    await this.ordersService.toggleOrderRealized(id).toPromise();
    this.showLists();
  }

  async deleteList(id: bigint){
    await this.listsService.deleteList(id).toPromise();
    this.showLists();
  }

  async updateList(id: bigint){
    localStorage.setItem("listId", String(id));
    this.router.navigate(["/update_list"]);
  }

  addList(){
    this.router.navigate(["/add_list"]);
  }
}
