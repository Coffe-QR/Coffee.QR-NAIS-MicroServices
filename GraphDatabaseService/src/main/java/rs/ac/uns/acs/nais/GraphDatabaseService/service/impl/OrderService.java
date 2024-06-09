package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.TableOrderCountDto;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Order;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.OrderRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IOrderService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public void save(Order item) {orderRepository.save(item);}

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(String id) {
        return orderRepository.findById(id).get();
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    public List<TableOrderCountDto>  getTablesWithMoreThanFiveOrders() {
        return orderRepository.findTablesWithMoreThanFiveOrders();
    }


    public Double getTotalSalesForLocal(String localId) {
        return orderRepository.findTotalSalesForLocal(localId);
    }

    public List<Order> getRecentOrdersForLocal(String localId) {
        return orderRepository.findRecentOrdersForLocal(localId);
    }

    public int updateOrderDiscount(String localId, String yearMonth, double discount) {
        String startDate = yearMonth;
        String endDate = LocalDate.parse(startDate).plusMonths(1).toString();
        return orderRepository.updateOrderDiscount(localId, startDate, endDate, discount);
    }
}
