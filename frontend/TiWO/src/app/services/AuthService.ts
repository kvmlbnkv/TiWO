import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {mapTo, Observable, tap} from "rxjs";
import {Token} from "../dtos/token";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthService{

  private token: string = '';
  private user: string = '';

  constructor(private http: HttpClient, private jwtHelper: JwtHelperService) {
  }

  public login(username: string, password: string): Observable<boolean>{
    return this.http.post<any>("http://localhost:8080/login", {username, password})
      .pipe(
        tap(token => this.loginTokens(username, token)),
        mapTo(true)
      );
  }

  logout(){
    localStorage.setItem(this.user, "");
    localStorage.setItem(this.token, "");
    localStorage.setItem('user', "");
    localStorage.setItem('token', "");
  }

  loginTokens(username: string, token: Token){
    console.log(token.accessToken)
    localStorage.setItem(this.user, username);
    localStorage.setItem(this.token, token.accessToken);
    localStorage.setItem('user', username);
    localStorage.setItem('token', token.accessToken);
    console.log(localStorage.getItem('token'))
  }

  isLogged(){
    const token = localStorage.getItem('token');
    return token != '' && token != null && !this.jwtHelper.isTokenExpired(token);
  }

  getUsername(){
    return localStorage.getItem('user');
  }

  register(email: string | undefined, username: string | undefined, password: string | undefined) : Observable<any>{
    return this.http.post("http://localhost:8080/tiwo/user/register", {email, username, password});
  }
}
