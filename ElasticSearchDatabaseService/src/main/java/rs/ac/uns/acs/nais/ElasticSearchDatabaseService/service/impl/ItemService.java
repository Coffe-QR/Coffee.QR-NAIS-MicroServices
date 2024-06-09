package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.ItemRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IItemService;

import java.util.Collections;
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
        List<Item> items = itemRepository.findFoodsByDescription(localId, description);

        return items.stream()
                .sorted(Comparator.comparingDouble(Item::getPrice))
                .collect(Collectors.toList());
    }

    public double findAvgPriceOfDrinksByLocalId(String localId) {
        List<Item> items = itemRepository.findAllDrinksByLocalId(localId);

        if (items == null || items.isEmpty()) {
            return 0.0;
        }

        double priceSum = items.stream()
                .mapToDouble(Item::getPrice)
                .sum();
        return priceSum / items.size();
    }

    public List<Item> searchCheapFood(String localId)
    {
        List<Item> items = itemRepository.findAllFoodsByLocalId(localId);
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }

        double priceSum = items.stream()
                .mapToDouble(Item::getPrice)
                .sum();
        double avgPrice = priceSum / items.size();

        return itemRepository.findAllFoodsByLocalIdAndLowerPriceThanAvg(localId,avgPrice);
    }

}
