package rs.ac.uns.acs.nais.GraphDatabaseService.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Item;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Table;

public interface TableRepository extends Neo4jRepository<Table, String> {

    @Query("MATCH (l:Local {id: $localId})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order) " +
            "WHERE t.isSmokingArea = false " +
            "RETURN COUNT(o) AS orderCount")
    Long countOrdersForNonSmokingTables(String localId);


    @Query("MATCH (l:Local {id: $localId})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order) " +
            "WITH t, COUNT(o) AS orderCount " +
            "ORDER BY orderCount ASC " +
            "LIMIT 1 " +
            "DETACH DELETE t " +
            "RETURN t")
    Table deleteTableWithLeastOrders(String localId);
}
