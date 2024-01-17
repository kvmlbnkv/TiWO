import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "./AuthService";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class OrdersService {


  constructor(private http: HttpClient) {
  }

  addOrder(listId:bigint|null, item: string | undefined, amount: number | undefined, grammage: string){
    return this.http.post("http://localhost:8080/tiwo/order/add?listId=" + listId + "&item=" + item + "&amount=" + amount + "&grammage=" + grammage, {});
  }

  toggleOrderRealized<T>(orderId:bigint): Observable<T>{
    return this.http.put<T>("http://localhost:8080/tiwo/order/toggleRealized?orderId=" + orderId, {});
  }


}
