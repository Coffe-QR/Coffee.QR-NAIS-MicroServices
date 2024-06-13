package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Item;

import java.util.List;

public interface IItemService {

    public void save(Item item);

    public List<Item> findByNameOrDescription(String name, String description);

    public Iterable<Item> findAllItems();

    public Item findItemById(String id);

    public boolean deleteItem(String id);
}