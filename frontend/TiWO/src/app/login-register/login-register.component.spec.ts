import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { LoginRegisterComponent } from './login-register.component';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {AuthService} from "../services/AuthService";
import {Router} from "@angular/router";
import {never, Observable, of, throwError} from "rxjs";
import {FormsModule} from "@angular/forms";
import {JwtHelperService} from "@auth0/angular-jwt";
import {HttpClient} from "@angular/common/http";
import {By} from "@angular/platform-browser";

class MockAuthService{
  whatToThrow = true;
  token: string = '';
  user: string = '';
  username = 'test';
  password = 'test';
  email= 'test@test.pl';


  logout(){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.whatToThrow);
  }

  getUsername(){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.user);
  }

  login(username: string, password: string){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.user);
  }

  register(email: string | undefined, username: string | undefined, password: string | undefined){
    if (!this.whatToThrow) throwError({status:404});
    return of(this.user);
  }
}

describe('LoginRegisterComponent', () => {
  let component: LoginRegisterComponent;
  let fixture: ComponentFixture<LoginRegisterComponent>;
  let mockAuthService: MockAuthService
  let router: Router;
  let httpMock: HttpClientTestingModule;
  let spyRouter = jasmine.createSpyObj<Router>('Router', ['navigate']);

  beforeEach(async () => {
    mockAuthService = new MockAuthService();
    await TestBed.configureTestingModule({
      declarations: [ LoginRegisterComponent ],
      imports: [HttpClientTestingModule, FormsModule],
      providers: [{provide: Router, useValue: spyRouter},
        {provide: AuthService, useValue: mockAuthService}]
    })
    .compileComponents();

    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(LoginRegisterComponent);
    httpMock = TestBed.inject(HttpTestingController);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call handleLogin if button was pressed',() => {
    let button = fixture.debugElement.query(By.css('#loginButton'));
    spyOn(component, 'handleLogin')
    expect(button).toBeTruthy();
    button.triggerEventHandler('click', null);

    fixture.detectChanges();

    expect(component.handleLogin).toHaveBeenCalled();
  });

  it('should navigate to lists component if login was a success',() => {
    component.handleLogin();
    fixture.detectChanges();

    expect(spyRouter.navigate).toHaveBeenCalledWith(["/lists"]);
  });

  it('should display error if login was a failure', ()=>{
    mockAuthService.whatToThrow=false;
    spyOn(mockAuthService, "login").and.returnValue(throwError({status:404}));
    component.handleLogin();
    fixture.detectChanges();

    expect(component.invalidLogin).toEqual(true);

  });

  it('should call handleRegister if button was pressed',() => {
    let button = fixture.debugElement.query(By.css('#registerButton'));
    spyOn(component, 'handleRegistration')
    expect(button).toBeTruthy();
    button.triggerEventHandler('click', null);

    fixture.detectChanges();

    expect(component.handleRegistration).toHaveBeenCalled();
  });

  it('should display success message if register was a success',() => {
    component.handleRegistration();
    fixture.detectChanges();

    expect(component.registerSucces).toEqual(true);
  });

  it('should display error if register was a failure', ()=>{
    mockAuthService.whatToThrow=false;
    spyOn(mockAuthService, "register").and.returnValue(throwError({status:404}));
    component.handleRegistration();
    fixture.detectChanges();

    expect(component.invalidRegister).toEqual(true);

  })



});
