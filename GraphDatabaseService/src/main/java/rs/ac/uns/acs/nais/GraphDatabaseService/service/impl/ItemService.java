package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Item;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.ItemRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IItemService;

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

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(String id) {
        return itemRepository.findById(id).get();
    }

    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }

}