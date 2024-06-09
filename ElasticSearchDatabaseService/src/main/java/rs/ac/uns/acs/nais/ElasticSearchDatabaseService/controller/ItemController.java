package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.controller;

import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items.json")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public void addItem(@RequestBody Item item) {
        itemService.save(item);
    }

    @GetMapping("findByNameOrDescription")
    public List<Item> findByNameOrDescription(@RequestParam(value = "name") String name,
                                              @RequestParam(value = "description") String description) {
        return itemService.findByNameOrDescription(name, description);
    }

    @GetMapping
    public Iterable<Item> getAllItems() {
        return itemService.findAllItems();
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable String id) {
        return itemService.findItemById(id).orElse(null);
    }

//    @GetMapping("getAllItemsForLocalId/{id}")
//    public List<Item> getAllItemsForLocalId(@PathVariable String id) {
//        return itemService.getAllItemsForLocalId(id);
//    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
    }

    @GetMapping("/search/food")
    public List<Item> searchFoodsByDescription(@RequestParam String description,@RequestParam String localId) {
        return itemService.findFoodsByDescription(localId,description);
    }

    @GetMapping("/findPriceOfCheapestVisit")
    public double findPriceOfCheapestVisit(@RequestParam String localId) {
        return itemService.findPriceOfCheapestVisit(localId);
    }

    @GetMapping("/search/cheapFood")
    public List<Item> searchCheapFood(@RequestParam String localId) {
        return itemService.searchCheapFood(localId);
    }

}
