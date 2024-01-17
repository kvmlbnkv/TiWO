package com.example.tiwo.Controllers;

import com.example.tiwo.Entities.OrderEntity;
import com.example.tiwo.Exceptions.NoSuchItemException;
import com.example.tiwo.Services.ItemService;
import com.example.tiwo.Services.ListService;
import com.example.tiwo.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tiwo/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    private final OrderService orderService;
    private final ListService listService;
    private final ItemService itemService;

    @PostMapping("/add")
    public ResponseEntity<OrderEntity> addOrder(@RequestParam() Long listId, @RequestParam() String item, @RequestParam() int amount, @RequestParam() String grammage){
        /*final OrderEntity order = new OrderEntity();
        if (itemService.getItem(item).isPresent()) {
            order.setItem(itemService.getItem(item).get());
        }
        else {
            throw new NoSuchItemException();
        }
        order.setAmount(amount);
        order.setGrammage(grammage);
        order.setRealized(false);*/

        OrderEntity order = orderService.createOrder(new OrderEntity(null, null, null, amount, grammage, false));
        orderService.addItem(item, order.getId());
        listService.addOrderToList(order.getId(), listId);

        Optional<OrderEntity> ret = orderService.getOrder(order.getId());
        return ret.map(orderEntity -> new ResponseEntity<>(orderEntity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping("/toggleRealized")
    public ResponseEntity<OrderEntity> toggleRealized(@RequestParam() Long orderId){
        return new ResponseEntity<>(this.orderService.toggleRealized(orderId), HttpStatus.OK);
    }

}
