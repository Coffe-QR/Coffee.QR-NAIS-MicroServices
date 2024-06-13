package rs.ac.uns.acs.nais.GraphDatabaseService.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.ItemCountPerOrder;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Item;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IItemService;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.impl.ItemService;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.impl.PDFService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items.json")
public class ItemController {

    private final ItemService itemService;
    private final PDFService pdfService;

    public ItemController(ItemService itemService, PDFService pdfService) {
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

    /*======================================================*/
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getPDFByCapacityRange(@RequestParam String type) {
        try {
            byte[] pdfContent = pdfService.generatePDFForCapacity(type);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "items_" + type + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pdf-not-sold")
    public ResponseEntity<byte[]> getPDFNotSold(@RequestParam String type) {
        try {
            byte[] pdfContent = pdfService.generatePDFNotSoldItem(type);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "items_not_sold_" + type + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pdf-reccomend")
    public ResponseEntity<byte[]> generatePDFBasedOnItemDescription(@RequestParam double price, @RequestParam String type) {
        try {
            byte[] pdfContent = pdfService.generatePDFBasedOnItemDescription(price, type);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "items_reccomend_" + price + "_"  + type + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pdf-all")
    public ResponseEntity<byte[]> allPdfs(@RequestParam String type, @RequestParam double price) {
        try {
            System.out.println("Ovde");
            byte[] pdfContent = pdfService.allPdfs(type, price);
            HttpHeaders headers = new HttpHeaders();
            System.out.println("Ovde1");
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "items_reccomend_" + price + "_"  + type + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //===========================================================

    @GetMapping("/get-by-id/{itemId}")
    public ResponseEntity<Item> getById(@PathVariable String itemId) {
        return new ResponseEntity<>(itemService.findItemById(itemId), HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> delete(@PathVariable String itemId) {
        return new ResponseEntity<>(itemService.deleteItem(itemId), HttpStatus.OK);
    }
}