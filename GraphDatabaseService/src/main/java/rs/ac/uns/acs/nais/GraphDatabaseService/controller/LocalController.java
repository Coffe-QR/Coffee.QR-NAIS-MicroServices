package rs.ac.uns.acs.nais.GraphDatabaseService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Local;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ILocalService;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.impl.LocalService;

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

    @GetMapping
    public Iterable<Local> getAllLocals() {
        return localService.findAllLocals();
    }

    @GetMapping("/{id}")
    public Local getLocalById(@PathVariable String id) {
        return localService.findLocalById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteLocal(@PathVariable String id) {
        localService.deleteLocal(id);
    }

    @GetMapping("/total-food-quantity-smoking-tables")
    public int getTotalFoodQuantityForSmokingTables() {
        return localService.getTotalFoodQuantityForSmokingTables();
    }

    @GetMapping("/{localId}/averageItemPrice")
    public Double getAverageItemPrice(@PathVariable String localId) {
        return localService.getAverageItemPrice(localId);
    }

    @GetMapping("/{localId}/annualEarnings/{year}")
    public Double getAnnualEarnings(@PathVariable String localId, @PathVariable int year) {
        return localService.getAnnualEarnings(localId, year);
    }
}