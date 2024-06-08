package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Order;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.OrderRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IOrderService;

import java.util.List;

public class OrderService implements IOrderService {
    private final OrderRepository itemRepository;

    public OrderService(OrderRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public void save(Order item) {itemRepository.save(item);}

    public List<Order> findAllOrders() {
        return itemRepository.findAll();
    }

    public Order findOrderById(String id) {
        return itemRepository.findById(id).get();
    }

    public void deleteOrder(String id) {
        itemRepository.deleteById(id);
    }

}
