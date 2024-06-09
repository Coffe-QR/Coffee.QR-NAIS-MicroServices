package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.ItemService;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.PDFService;

import java.util.List;

@RestController
@RequestMapping("/items.json")
public class ItemController {

    private final ItemService itemService;
    private final PDFService pdfService;


    public ItemController(ItemService itemService,PDFService pdfService) {
        this.itemService = itemService;
        this.pdfService = pdfService;
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
    public ResponseEntity<List<Item>> searchFoodsByDescription(@RequestParam String description,@RequestParam String localId) {
        return ResponseEntity.ok(itemService.findFoodsByDescription(localId,description));
    }

    @GetMapping("/searchByPriceBetween")
    public List<Item> getByCapacityBetween(@RequestParam int minPrice, @RequestParam int maxPrice) {
        return itemService.findByPriceBetween(minPrice,maxPrice);
    }

    @GetMapping("/pdf/by-price")
    public ResponseEntity<byte[]> getPDFByCapacityRange(@RequestParam int minPrice, @RequestParam int maxPrice) {
        try {
            byte[] pdfContent = pdfService.generatePDFForPrice(minPrice, maxPrice);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "items_price_range_" + minPrice + "_to_" + maxPrice + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/findPriceOfCheapestVisit")
    public double findPriceOfCheapestVisit(@RequestParam String localId) {
        return itemService.findPriceOfCheapestVisit(localId);
    }

    @GetMapping("/search/cheapFood")
    public ResponseEntity<List<Item>> searchCheapFood(@RequestParam String localId) {
        return ResponseEntity.ok(itemService.searchCheapFood(localId));
    }

}
