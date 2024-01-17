import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddListComponent } from './add-list.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {OrdersService} from "../services/OrdersService";
import {of, throwError} from "rxjs";
import {ListsService} from "../services/ListsService";

class MockListService{
  whatToThrow = true;
  username = "test";
  date = "2023-01-25"

  addList(username: string, name: string, date: string){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.whatToThrow);
  }
}

describe('AddListComponent', () => {
  let component: AddListComponent;
  let fixture: ComponentFixture<AddListComponent>;
  let spyRouter = jasmine.createSpyObj<Router>('Router', ['navigate']);
  let mockListsService: MockListService;

  beforeEach(async () => {
    mockListsService = new MockListService();
    await TestBed.configureTestingModule({
      declarations: [ AddListComponent ],
      imports:[HttpClientTestingModule, FormsModule],
      providers: [{provide: Router, useValue: spyRouter},
        {provide: ListsService, useValue: mockListsService},
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to lists on successful addList', () => {
    component.addList();
    fixture.detectChanges();

    expect(component.errorMessageDisplay).toEqual(false);
    expect(spyRouter.navigate).toHaveBeenCalledWith(["/lists"]);
  });

  it('should display error message on unsuccessful addList', () => {
    mockListsService.whatToThrow = false;
    spyOn(mockListsService, "addList").and.returnValue(throwError({status:404}));
    component.addList();
    fixture.detectChanges();

    expect(component.errorMessageDisplay).toEqual(true);
  });

});
