package rs.ac.uns.acs.nais.GraphDatabaseService.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.ItemCountPerOrder;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Item;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IItemService;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.impl.ItemService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items.json")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return new ResponseEntity<>(itemService.save(item), HttpStatus.CREATED);
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

    //BRAT MOJ RADI
    @GetMapping("/local/{localId}/most-ordered-items")
    public List<Item> getMostOrderedItemsInLocal(@PathVariable String localId) {
        return itemService.findMostOrderedItemsInLocal(localId);
    }

    @GetMapping("/not-ordered-last-month")
    public List<Item> getItemsNotOrderedInLastMonth() {
        return itemService.getItemsNotOrderedInLastMonth();
    }

    @DeleteMapping("/delete-least-orders")
    public Item deleteItemWithLeastOrders(@RequestParam String localId) {
        return itemService.deleteItemWithLeastOrders(localId);
    }

    @PostMapping("/increase-top-three-prices")
    public List<Item> increasePriceOfTopThreeItems(@RequestParam String localId, @RequestParam double increaseFactor) {
        return itemService.increasePriceOfTopThreeItems(localId, increaseFactor);
    }
}