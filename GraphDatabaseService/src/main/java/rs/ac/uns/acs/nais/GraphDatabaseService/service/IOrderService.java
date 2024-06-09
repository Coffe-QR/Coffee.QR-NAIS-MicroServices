package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Order;

import java.util.List;

public interface IOrderService {

    public void save(Order order);

    public Iterable<Order> findAllOrders();

    public Order findOrderById(String id);

    public void deleteOrder(String id);
}