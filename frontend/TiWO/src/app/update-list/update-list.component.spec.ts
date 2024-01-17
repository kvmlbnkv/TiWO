import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateListComponent } from './update-list.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {ListsService} from "../services/ListsService";
import {of, throwError} from "rxjs";

class MockListService{
  whatToThrow = true;
  id = BigInt(1);
  name = "test";
  date = "2023-01-25"

  updateList(id: bigint, name:string, date:string){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.whatToThrow);
  }
}

describe('UpdateListComponent', () => {
  let component: UpdateListComponent;
  let fixture: ComponentFixture<UpdateListComponent>;
  let spyRouter = jasmine.createSpyObj<Router>('Router', ['navigate']);
  let mockListsService: MockListService;

  beforeEach(async () => {
    mockListsService = new MockListService();
    await TestBed.configureTestingModule({
      declarations: [ UpdateListComponent ],
      imports:[HttpClientTestingModule, FormsModule],
      providers: [{provide: Router, useValue: spyRouter},
        {provide: ListsService, useValue: mockListsService},]

    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to lists on successful addList', () => {
    localStorage.setItem("listId", "1");
    component.updateList();
    fixture.detectChanges();

    expect(component.errorMessageDisplay).toEqual(false);
    expect(spyRouter.navigate).toHaveBeenCalledWith(["/lists"]);
  });

  it('should display error message on unsuccessful addList', () => {
    mockListsService.whatToThrow = false;
    spyOn(mockListsService, "updateList").and.returnValue(throwError({status:404}));
    component.updateList();
    fixture.detectChanges();

    expect(component.errorMessageDisplay).toEqual(true);
  });
});
