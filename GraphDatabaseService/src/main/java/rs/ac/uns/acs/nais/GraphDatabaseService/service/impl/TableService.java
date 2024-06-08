package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Table;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.TableRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ITableService;

import java.util.List;

public class TableService implements ITableService {
    private final TableRepository itemRepository;

    public TableService(TableRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public void save(Table item) {itemRepository.save(item);}

    public List<Table> findAllTables() {
        return itemRepository.findAll();
    }

    public Table findTableById(String id) {
        return itemRepository.findById(id).get();
    }

    public void deleteTable(String id) {
        itemRepository.deleteById(id);
    }

}
