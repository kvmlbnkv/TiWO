import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/AuthService";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-register',
  templateUrl: './login-register.component.html',
  styleUrls: ['./login-register.component.css']
})
export class LoginRegisterComponent implements OnInit{
  invalidLogin = false;
  loginSucces = false;
  loginErrorMessage = 'Niepoprawne dane logowania';
  loginSuccessMessage: string | undefined;
  usernameLogin: string = "";
  passwordLogin: string = "";

  invalidRegister = false;
  registerSucces = false;
  registerErrorMessage = 'Coś poszło nie tak...';
  registerSuccessMessage = "Zarejestrowany";
  emailRegister: string | undefined;
  usernameRegister: string | undefined;
  passwordRegister: string | undefined;

  constructor(private authService: AuthService, private router: Router){
  }

  ngOnInit(): void{
    localStorage.setItem('user', '');
    localStorage.setItem('token', '');
  }

  handleLogin() {
    this.authService.login(this.usernameLogin, this.passwordLogin).subscribe((result) => {
        this.invalidLogin = false;
        this.router.navigate(['/lists']);
      }, () => {
        this.invalidLogin = true;
        this.loginSucces = false;
      }
    );
  }

  handleRegistration() {
    this.authService.register(this.emailRegister, this.usernameRegister, this.passwordRegister).subscribe((result) => {
        this.invalidRegister = false;
        this.registerSucces = true;
      }, () => {
        this.invalidRegister = true;
        this.registerSucces = false;
      }
    );
  }
}
