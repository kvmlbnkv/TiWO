import {Injectable} from "@angular/core";
import {CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree,} from "@angular/router";
import {AuthService} from "./services/AuthService";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})

export class AuthGuard implements CanActivate {
  constructor(public authService: AuthService, public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.isLogged()){
      return true;
    }
    else{
      this.router.navigate(['/login']);
    }
    return false;
  }
}
