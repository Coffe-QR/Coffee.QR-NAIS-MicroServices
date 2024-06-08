package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.controller;

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

}
