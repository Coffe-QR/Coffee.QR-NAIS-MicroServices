package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Table;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.TableRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ITableService;

import java.util.List;

@Service
public class TableService implements ITableService {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }
    public void save(Table table) {tableRepository.save(table);}

    public List<Table> findAllTables() {
        return tableRepository.findAll();
    }

    public Table findTableById(String id) {
        return tableRepository.findById(id).get();
    }

    public void deleteTable(String id) {
        tableRepository.deleteById(id);
    }

    public Long countOrdersForNonSmokingTables(String localId) {
        return tableRepository.countOrdersForNonSmokingTables(localId);
    }

    public Table deleteTableWithLeastOrders(String localId) {
        return tableRepository.deleteTableWithLeastOrders(localId);
    }
}
