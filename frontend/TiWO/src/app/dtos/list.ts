import {Order} from "./order";

export interface List {
  id:bigint;
  name:string;
  orders:Array<Order>;
  date:Date;
}
