import { Component } from '@angular/core';
import {OrdersService} from "../services/OrdersService";
import {Router} from "@angular/router";
import {Item} from "../dtos/item";
import {ItemsService} from "../services/ItemsService";

@Component({
  selector: 'app-add-order',
  templateUrl: './add-order.component.html',
  styleUrls: ['./add-order.component.css']
})
export class AddOrderComponent {
  itemName:string = "";
  listItem:string = "";
  amount_str:string = "";
  grammage:string = "";
  errorMessage:string = "Coś poszło nie tak...";
  errorMessageDisplay:boolean = false;
  items:Array<Item> = [];
  grammages:Array<String> = ["sztuka", "kg", "g"];


  constructor(private ordersService:  OrdersService, private itemsService: ItemsService, private router: Router) {
  }


  ngOnInit(): void{
    this.itemsService.getAllItems().subscribe((response)=>
    {
      this.items = response;
    });
  }

  itemFromList(){
    this.itemName = this.listItem;
  }
  addOrder(){
      this.ordersService.addOrder(BigInt(`${localStorage.getItem("listId")}`), this.itemName, Number(this.amount_str), this.grammage).subscribe((result) => {
        this.errorMessageDisplay = false;
        this.router.navigate(['/lists']);
      }, () => {
        this.errorMessageDisplay = true;
      })
  }


}
