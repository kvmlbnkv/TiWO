import {ComponentFixture, fakeAsync, TestBed} from '@angular/core/testing';

import { AddOrderComponent } from './add-order.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {Observable, of, throwError} from "rxjs";
import any = jasmine.any;
import {AuthService} from "../services/AuthService";
import {OrdersService} from "../services/OrdersService";
import {ItemsService} from "../services/ItemsService";

class MockOrderService{
  whatToThrow = true;
  listId = BigInt(1);
  item = "test";
  amount = 1;
  grammage = "sztuka";

  addOrder(listId:bigint|null, item: string | undefined, amount: number | undefined, grammage: string){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.whatToThrow);
  }
}

class MockItemsService{
  getAllItems(){
    return of(Observable<any>);
  }
}

describe('AddOrderComponent', () => {
  let component: AddOrderComponent;
  let fixture: ComponentFixture<AddOrderComponent>;
  let mockOrdersService: MockOrderService;
  let mockItemsService: MockItemsService;
  let router: Router;
  let spyRouter = jasmine.createSpyObj<Router>('Router', ['navigate']);

  beforeEach(async () => {
    mockItemsService = new MockItemsService();
    mockOrdersService = new MockOrderService();
    await TestBed.configureTestingModule({
      declarations: [ AddOrderComponent ],
      imports:[HttpClientTestingModule, FormsModule],
      providers: [{provide: Router, useValue: spyRouter},
        {provide: OrdersService, useValue: mockOrdersService},
        ]
    })
    .compileComponents();

    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(AddOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    const mockLocalStorage = {
      getItem: (key: string): string => {
        return "1";
      },
    };
    spyOn(localStorage, 'getItem').and.callFake(mockLocalStorage.getItem);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should get item from list into input', ()=>{
    component.listItem = "test"
    component.itemFromList()
    fixture.detectChanges()

    expect(component.itemName).toEqual("test")

  })

  it('should navigate to lists on successful addOrder', ()=> {
    component.addOrder();
    fixture.detectChanges();

    expect(component.errorMessageDisplay).toEqual(false);
    expect(spyRouter.navigate).toHaveBeenCalledWith(["/lists"]);
  });

  it('should display error message on unsuccessful addOrder', ()=> {
    mockOrdersService.whatToThrow = false;
    spyOn(mockOrdersService, "addOrder").and.returnValue(throwError({status:404}));
    component.addOrder();
    fixture.detectChanges();

    expect(component.errorMessageDisplay).toEqual(true);
  });



});
