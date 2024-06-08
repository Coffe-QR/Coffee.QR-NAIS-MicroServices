package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.ItemRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IItemService;

import java.util.List;
import java.util.Optional;

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
}
