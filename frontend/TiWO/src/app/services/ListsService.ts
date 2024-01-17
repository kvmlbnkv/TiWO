import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "./AuthService";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class ListsService {


  constructor(private http: HttpClient) {
  }

  addList(username: string, name: string, date: string){
    return this.http.post("http://localhost:8080/tiwo/list/add?username=" + username + "&name=" + name + "&date=" + date, {});
  }

  deleteList<T>(id: bigint) : Observable<T>{
    return this.http.delete<T>("http://localhost:8080/tiwo/list/delete?listId=" + id, {});
  }

  updateList(id: bigint, name:string, date:string){
    return this.http.put("http://localhost:8080/tiwo/list/update?listId=" + id + "&name=" + name + "&date=" + date, {});
  }

  showLists(username: string|null) :Observable<any>{
    return this.http.get("http://localhost:8080/tiwo/user/lists?username=" + username);
  }

}
