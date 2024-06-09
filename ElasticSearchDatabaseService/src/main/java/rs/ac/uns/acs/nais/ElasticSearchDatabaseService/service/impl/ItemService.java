package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.ItemRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IItemService;

import java.util.*;
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

    public double findPriceOfCheapestVisit(String localId) {
        List<Item> drinks = itemRepository.findAllDrinksByLocalId(localId);
        OptionalDouble minDrinkPriceOptional = drinks.stream()
                .mapToDouble(Item::getPrice)
                .min();

        double minDrinkPrice = minDrinkPriceOptional.isPresent() ? minDrinkPriceOptional.getAsDouble() : 0.0;

        List<Item> foods = itemRepository.findAllFoodsByLocalId(localId);
        OptionalDouble minFoodsPriceOptional = foods.stream()
                .mapToDouble(Item::getPrice)
                .min();

        double minFoodsPrice = minFoodsPriceOptional.isPresent() ? minFoodsPriceOptional.getAsDouble() : 0.0;

        return minDrinkPrice+minFoodsPrice;
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
