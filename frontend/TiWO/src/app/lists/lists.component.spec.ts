import {ComponentFixture, fakeAsync, TestBed} from '@angular/core/testing';

import { ListsComponent } from './lists.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {ListsService} from "../services/ListsService";
import {of, throwError} from "rxjs";
import {OrdersService} from "../services/OrdersService";
import {AuthService} from "../services/AuthService";

class MockListService{
  whatToThrow = true;
  username = "test";
  date = "2023-01-25"

  addList(username: string, name: string, date: string){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.whatToThrow);
  }

  showLists(username: string|null){
    if (!this.whatToThrow) throwError({status:404});
    return of([]);
  }

  deleteList<T>(id: bigint) {
    if (!this.whatToThrow) throwError({status:404});
    return of(this.whatToThrow);
  }
}

class MockOrdersService{
  whatToThrow = true;
  listId = BigInt(1);
  item = "test";
  amount = 1;
  grammage = "sztuka";

  addOrder(listId:bigint|null, item: string | undefined, amount: number | undefined, grammage: string){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.whatToThrow);
  }

  toggleOrderRealized<T>(orderId:bigint){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.whatToThrow);
  }
}

class MockAuthService{
  whatToThrow = true;
  token: string = '';
  user: string = '';


  logout(){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.whatToThrow);
  }

  getUsername(){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.user);
  }
}


describe('ListsComponent', () => {
  let component: ListsComponent;
  let fixture: ComponentFixture<ListsComponent>;
  let spyRouter = jasmine.createSpyObj<Router>('Router', ['navigate']);
  let mockListsService: MockListService;
  let mockAuthService: MockAuthService;
  let mockOrdersService: MockOrdersService;

  beforeEach(async () => {
    mockListsService = new MockListService();
    mockOrdersService = new MockOrdersService();
    mockAuthService = new MockAuthService();
    await TestBed.configureTestingModule({
      declarations: [ ListsComponent ],
      imports:[HttpClientTestingModule, FormsModule],
      providers: [{provide: Router, useValue: spyRouter},
        {provide: ListsService, useValue: mockListsService},
        {provide: OrdersService, useValue: mockOrdersService},
        {provide: AuthService, useValue: mockAuthService},
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to login if logout clicked', ()=>{
    component.logout()
    fixture.detectChanges()

    expect(spyRouter.navigate).toHaveBeenCalledWith(["/login"]);

  })

  it('should navigate to add-order on call of addOrder', ()=>{
    component.addOrder(BigInt(1))
    fixture.detectChanges()

    expect(spyRouter.navigate).toHaveBeenCalledWith(["/add_order"]);

  })

  it('should navigate to add-list on call of addList', ()=>{
    component.addList()
    fixture.detectChanges()

    expect(spyRouter.navigate).toHaveBeenCalledWith(["/add_list"]);

  })

  it('should refresh lists on realizeOrder', fakeAsync(()=>{
    component.realizeOrder(BigInt(1))
    spyOn(component, 'showLists')
    component.ngOnInit()
    fixture.detectChanges()

    expect(component.showLists).toHaveBeenCalled();

  }))

  it('should refresh lists on deleteList', fakeAsync(()=>{
    component.deleteList(BigInt(1))
    spyOn(component, 'showLists')
    component.ngOnInit()
    fixture.detectChanges()

    expect(component.showLists).toHaveBeenCalled();

  }))


});
