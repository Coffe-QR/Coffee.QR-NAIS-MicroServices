package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.LocalService;

import java.util.List;

@RestController
@RequestMapping("/locals.json")
public class LocalController {
    private final LocalService localService;

    public LocalController(LocalService localService) {
        this.localService = localService;
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

}
