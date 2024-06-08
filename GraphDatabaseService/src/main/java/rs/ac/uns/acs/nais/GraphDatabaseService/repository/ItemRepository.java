package rs.ac.uns.acs.nais.GraphDatabaseService.repository;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Customer;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends Neo4jRepository<Item, String> {
    List<Item> findByNameOrDescription(String name, String description);
}