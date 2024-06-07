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

    @GetMapping("findML")
    public String findML() {
        return "MLMLML";
    }
}
