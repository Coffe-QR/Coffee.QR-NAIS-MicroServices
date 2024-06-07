package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.controller;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.ItemService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import org.springframework.web.bind.annotation.*;


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
        return itemService.findByNameOrDescription(name,description);
    }

    @GetMapping("findML")
    public String findML() {
        return "MLMLML";
    }

}