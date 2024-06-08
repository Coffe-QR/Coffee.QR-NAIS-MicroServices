package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Item;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public interface IItemService {

    public void save(Item item);

    public List<Item> findByNameOrDescription(String name, String description);

    public Iterable<Item> findAllItems();

    public Item findItemById(String id);

    public void deleteItem(String id);
}