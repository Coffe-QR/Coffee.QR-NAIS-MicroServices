package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Item;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.ItemRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IItemService;

import java.util.List;
import java.util.Map;

@Service
public class ItemService implements IItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public class DescriptionTooLongException extends RuntimeException {
        public DescriptionTooLongException(String message) {
            super(message);
        }
    }

    public Item save(Item item) {
        if (item.getDescription() != null && item.getDescription().length() > 50) {
            throw new DescriptionTooLongException("Description cannot be more than 50 characters");
        }
        return itemRepository.save(item);
    }

    public List<Item> findByNameOrDescription(String name, String description) {
        return itemRepository.findByNameOrDescription(name, description);
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(String id) {
        return itemRepository.findById(id).get();
    }

    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }

    public void updateOrderedRelationship(Long orderId, String newValue) {
        itemRepository.updateOrderedRelationship(orderId, newValue);
    }

    public List<Object> countItemsPerOrder() {
        System.out.println(itemRepository.countItemsPerOrder());
        return itemRepository.countItemsPerOrder();
    }

    public List<Map<String, Object>> findOrdersWithMinPrice(Double minPrice) {
        return itemRepository.findOrdersWithMinPrice(minPrice);
    }

    public List<Map<String, Object>> countRelationshipsForActiveOrders(Boolean isActive) {
        return itemRepository.countRelationshipsForActiveOrders(isActive);
    }

    public List<Map<String, Object>> countOrdersPerLocal() {
        return itemRepository.countOrdersPerLocal();
    }

    public Item getTopSellingItemInLocal(String localId) {
        return itemRepository.findTopSellingItemInLocal(localId);
    }

    public Item getMostOrderedItem() {
        return itemRepository.findMostOrderedItem();
    }

    public List<Item> findMostOrderedItemsInLocal(String localId) {
        return itemRepository.findMostOrderedItemsInLocal(localId);
    }

    public List<Item> getItemsNotOrderedInLastMonth() {
        return itemRepository.findItemsNotOrderedInLastMonth();
    }

    public Item deleteItemWithLeastOrders(String localId) {
        return itemRepository.deleteItemWithLeastOrders(localId);
    }

    public List<Item> increasePriceOfTopThreeItems(String localId, double increaseFactor) {
        return itemRepository.increasePriceOfTopThreeItems(localId, increaseFactor);
    }
}