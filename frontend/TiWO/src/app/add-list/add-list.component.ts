import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {ListsService} from "../services/ListsService";

@Component({
  selector: 'app-add-list',
  templateUrl: './add-list.component.html',
  styleUrls: ['./add-list.component.css']
})
export class AddListComponent {

  name:string = '';
  datestr:string = '';
  errorMessage:string = "Coś poszło nie tak...";
  errorMessageDisplay:boolean = false;


  constructor(private listsService:  ListsService, private router: Router) {
  }

  ngOnInit(): void{
  }

  addList(){
    this.listsService.addList(`${localStorage.getItem("user")}`, this.name, this.datestr).subscribe((result) => {
      this.errorMessageDisplay = false;
      this.router.navigate(['/lists']);
    }, () => {
      this.errorMessageDisplay = true;
    })
  }


}
