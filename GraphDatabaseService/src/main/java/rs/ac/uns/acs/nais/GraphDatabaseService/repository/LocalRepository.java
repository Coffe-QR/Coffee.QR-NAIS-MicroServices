package rs.ac.uns.acs.nais.GraphDatabaseService.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Local;

public interface LocalRepository extends Neo4jRepository<Local, String> {

    @Query("MATCH (l:Local {id: '1'})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order)-[:ORDERED]->(i:Item {type: 'FOOD'}) " +
            "WHERE t.isSmokingArea = true " +
            "RETURN SUM(i.quantity) AS totalFoodQuantity")
    int getTotalFoodQuantityForSmokingTables();


    @Query("MATCH (l:Local {id: $localId})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order)-[:ORDERED]->(i:Item) RETURN AVG(i.price) AS averagePrice")
    Double findAverageItemPriceByLocalId(String localId);

    @Query("MATCH (l:Local {id: $localId})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order) " +
            "WHERE o.date.year = $year " +
            "RETURN SUM(o.price) AS totalEarnings")
    Double findAnnualEarningsByLocalIdAndYear(String localId, int year);
}
