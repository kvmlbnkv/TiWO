import { Component } from '@angular/core';
import {ListsService} from "../services/ListsService";
import {Router} from "@angular/router";

@Component({
  selector: 'app-update-list',
  templateUrl: './update-list.component.html',
  styleUrls: ['./update-list.component.css']
})
export class UpdateListComponent {
  name:string = '';
  datestr:string = '';
  errorMessage:string = "Coś poszło nie tak...";
  errorMessageDisplay:boolean = false;


  constructor(private listsService:  ListsService, private router: Router) {
  }

  ngOnInit(): void{
  }

  updateList(){
    this.listsService.updateList(BigInt(`${localStorage.getItem("listId")}`), this.name, this.datestr).subscribe((result) => {
      this.errorMessageDisplay = false;
      this.router.navigate(['/lists']);
    }, () => {
      this.errorMessageDisplay = true;
    })
  }
}
