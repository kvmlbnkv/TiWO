import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ItemsService {


  constructor(private http: HttpClient) {
  }

  getAllItems() :Observable<any>{
    return this.http.get("http://localhost:8080/tiwo/item/getAll");
  }


}
