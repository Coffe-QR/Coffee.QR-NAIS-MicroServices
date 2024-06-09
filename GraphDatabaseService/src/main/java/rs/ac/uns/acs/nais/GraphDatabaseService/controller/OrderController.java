package rs.ac.uns.acs.nais.GraphDatabaseService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.TableOrderCountDto;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Order;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IOrderService;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.impl.OrderService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders.json")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void addOrder(@RequestBody Order order) {
        orderService.save(order);
    }

    @GetMapping
    public Iterable<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        return orderService.findOrderById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/tables-with-more-than-five-orders")
    public List<TableOrderCountDto>  getTablesWithMoreThanFiveOrders() {
        return orderService.getTablesWithMoreThanFiveOrders();
    }

    @GetMapping("/locals/{localId}/total-sales")
    public Double getTotalSalesForLocal(@PathVariable String localId) {
        System.out.println("RACUNAM ZARADU VLJD CU USPEM");
        return orderService.getTotalSalesForLocal(localId);
    }

    @GetMapping("/recent")
    public List<Order> getRecentOrdersForLocal(@RequestParam String localId) {
        return orderService.getRecentOrdersForLocal(localId);
    }

    @PutMapping("/update-discount")
    public int updateOrderDiscount(@RequestParam String localId, @RequestParam String yearMonth, @RequestParam double discount) {
        return orderService.updateOrderDiscount(localId, yearMonth, discount);
    }
}