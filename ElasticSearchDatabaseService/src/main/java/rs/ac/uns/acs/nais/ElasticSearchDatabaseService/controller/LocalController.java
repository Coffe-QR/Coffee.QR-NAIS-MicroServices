package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.LocalService;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.PDFService;

import java.util.List;

@RestController
@RequestMapping("/locals.json")
public class LocalController {
    private final LocalService localService;
    private final PDFService pdfService;

    public LocalController(LocalService localService,PDFService pdfService) {
        this.localService = localService;
        this.pdfService = pdfService;
    }

    @PostMapping
    public void addLocal(@RequestBody Local local) {
        localService.save(local);
    }

    @GetMapping("findByNameOrDescription")
    public List<Local> findByNameOrDescription(@RequestParam(value = "name") String name,
                                              @RequestParam(value = "description") String description) {
        return localService.findByNameOrDescription(name, description);
    }

    @GetMapping
    public Iterable<Local> getAllLocals() {
        return localService.findAllLocals();
    }

    @GetMapping("/{id}")
    public Local getLocalById(@PathVariable String id) {
        return localService.findLocalById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteLocal(@PathVariable String id) {
        localService.deleteLocal(id);
    }

    @GetMapping("/all")
    public Page<Local> getAllLocals(Pageable pageable) {
        return localService.getAllLocals(pageable);
    }

    @GetMapping("/searchByCountry")
    public Page<Local> getByCountry(@RequestParam String country, Pageable pageable) {
        return localService.findByCountry(country, pageable);
    }

    @GetMapping("/searchByCity")
    public List<Local> getByCity(@RequestParam String city) {
        return localService.findByCity(city);
    }


    @GetMapping("/searchByCapacityBetween")
    public List<Local> getByCapacityBetween(@RequestParam int minCapacity, @RequestParam int maxCapacity) {
        return localService.findByCapacityBetween(minCapacity,maxCapacity);
    }

    @GetMapping("/search-by-item-description")
    public ResponseEntity<List<Local>> getLocalsByItemDescription(@RequestParam String description) {
        List<Local> locals = localService.findLocalsByItemDescription(description);
        return ResponseEntity.ok(locals);
    }

    // ML #1
    @GetMapping("/searchByCountryAndCapacitySorted")
    public Page<Local> searchByCountryAndCapacitySorted(
            @RequestParam String country,
            @RequestParam int minCapacity,
            @RequestParam int maxCapacity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return localService.getLocalsByCountryAndCapacitySorted(country, minCapacity, maxCapacity, page, size);
    }

    // ML #2
    @GetMapping("/full-text-search")
    public ResponseEntity<?> performFullTextSearch(@RequestParam String text, @RequestParam String city) {
        try {
            List<Local> results = localService.searchByTextAndCity(text, city);
            if (results.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // ML #3
    @GetMapping("/complex-search")
    public ResponseEntity<?> complexSearch(
            @RequestParam String country,
            @RequestParam(required = false) String name,
            @RequestParam int capacity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            if (name == null) {
                name = "";
            }
            List<Local> results = localService.getLocalsByCriteria(country, name, capacity, page,size);
            if (results.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/city-sorted-by-average-price")
    public ResponseEntity<?> getLocalsInCitySortedByAverageItemPrice(@RequestParam String city) {
        try {
            List<Local> results = localService.getLocalsInCitySortedByAverageItemPrice(city);
            if (results.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/pdf/by-capacity")
    public ResponseEntity<byte[]> getPDFByCapacityRange(@RequestParam int minCapacity, @RequestParam int maxCapacity) {
        try {
            byte[] pdfContent = pdfService.generatePDFForCapacity(minCapacity, maxCapacity);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "locals_capacity_range_" + minCapacity + "_to_" + maxCapacity + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pdf/by-item-description")
    public ResponseEntity<byte[]> getPDFByItemDescription(@RequestParam String desc) {
        try {
            byte[] pdfContent = pdfService.generatePDFBasedOnItemDescription(desc);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "locals_which_have_items_with_desc_" + desc + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
