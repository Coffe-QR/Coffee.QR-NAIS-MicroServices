package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Table;

import java.util.List;

public interface ITableService {

    public void save(Table table);

    public Iterable<Table> findAllTables();

    public Table findTableById(String id);

    public void deleteTable(String id);
}