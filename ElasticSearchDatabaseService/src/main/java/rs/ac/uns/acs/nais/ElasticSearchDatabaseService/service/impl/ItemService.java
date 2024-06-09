package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.ItemRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IItemService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService implements IItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public void save(Item item) {itemRepository.save(item);}

    public List<Item> findByNameOrDescription(String name, String description) {
        return itemRepository.findByNameOrDescription(name, description);
    }

    public Iterable<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> findItemById(String id) {
        return itemRepository.findById(id);
    }

    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }

    public List<Item> getAllItemsForLocalId(String id) {
        return itemRepository.getAllItemsForLocalId(id);
    }

    public List<Item> findFoodsByDescription(String localId, String description) {
        // Fetch items from the repository
        List<Item> items = itemRepository.findFoodsByDescription(localId, description);

        // Sort the list by price in ascending order using Stream API
        return items.stream()
                .sorted(Comparator.comparingDouble(Item::getPrice))
                .collect(Collectors.toList());
    }
    public List<Item> findByPriceBetween(int minPrice, int maxPrice){
        return itemRepository.findByPriceBetween(minPrice,maxPrice);
    }
}
