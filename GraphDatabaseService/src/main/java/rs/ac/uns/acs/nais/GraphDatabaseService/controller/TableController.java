package rs.ac.uns.acs.nais.GraphDatabaseService.controller;

import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Table;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ITableService;

@RestController
@RequestMapping("/tables.json")
public class TableController {
    private final ITableService tableService;

    public TableController(ITableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping
    public void addTable(@RequestBody Table table) {
        tableService.save(table);
    }

    @GetMapping
    public Iterable<Table> getAllTables() {
        return tableService.findAllTables();
    }

    @GetMapping("/{id}")
    public Table getTableById(@PathVariable String id) {
        return tableService.findTableById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTable(@PathVariable String id) {
        tableService.deleteTable(id);
    }
}