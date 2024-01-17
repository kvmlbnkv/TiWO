import {Item} from "./item";

export interface Order {
  id:bigint;
  item:Item;
  amount:number;
  grammage:string;
  realized:boolean;
}
