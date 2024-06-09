package rs.ac.uns.acs.nais.GraphDatabaseService.repository;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.ItemCountPerOrder;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Customer;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Item;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemRepository extends Neo4jRepository<Item, String> {
    List<Item> findByNameOrDescription(String name, String description);


    @Query("MATCH (o:Order)-[r:ORDERED]->(i:Item) WHERE o.id = $orderId SET r.property = $newValue RETURN o, r, i")
    void updateOrderedRelationship(Long orderId, String newValue);

    @Query("MATCH (o:Order)-[:ORDERED]->(i:Item) RETURN o.id as orderId, COUNT(i) AS itemCount")
    List<Object> countItemsPerOrder();

    @Query("MATCH (o:Order)-[r:ORDERED]->(i:Item) WHERE o.price > $minPrice RETURN o, r, i")
    List<Map<String, Object>> findOrdersWithMinPrice(Double minPrice);

    @Query("MATCH (o:Order)-[r:ORDERED]->(i:Item) WHERE o.isActive = $isActive WITH o, COUNT(r) AS relCount RETURN o, relCount")
    List<Map<String, Object>> countRelationshipsForActiveOrders(Boolean isActive);

    @Query("MATCH (l:Local)<-[:IN_LOCAL]-(t:Table)<-[:IN_TABLE]-(o:Order) RETURN l.name, COUNT(o) AS orderCount")
    List<Map<String, Object>> countOrdersPerLocal();

    @Query("MATCH (l:Local {id: $localId})-[:IN_TABLE]->(o:Order)-[:ORDERED]->(i:Item) " +
            "WITH i, count(i) as itemCount " +
            "ORDER BY itemCount DESC " +
            "LIMIT 1 " +
            "RETURN i")
    Item findTopSellingItemInLocal(String localId);

    @Query("MATCH (o:Order)-[:ORDERED]->(i:Item) " +
            "WITH i, COUNT(i) AS itemCount " +
            "ORDER BY itemCount DESC " +
            "LIMIT 1 " +
            "RETURN i")
    Item findMostOrderedItem();


    /*
    Ovaj upit traži sve stavke (Item) koje su bile naručene u kafiću sa ID-jem '1'. Evo kako se koraci upita odvijaju:

        MATCH (l:Local {id: '1'})-[:IN_LOCAL]->(t:Table): Pronalazi kafić sa ID-jem '1' i povezuje ga sa svim stolovima (Table) u tom kafiću.
        -[:ON_TABLE]->(o:Order): Zatim se povezuje svaki sto sa svim porudžbinama (Order) koje su napravljene za taj sto.
        -[:ORDERED]->(i:Item): Dalje se povezuje svaka porudžbina sa svim stavkama (Item) koje su u njoj naručene.
        WITH i, COUNT(*) AS orderCount: Nakon toga se stavke grupišu, i za svaku stavku se računa broj porudžbina u kojima se pojavljuje.
        ORDER BY orderCount DESC: Sortira stavke po broju porudžbina u opadajućem redosledu, tako da stavka sa najvećim brojem porudžbina bude prva.
        RETURN i: Na kraju se vraća samo stavka (Item) sa najvećim brojem porudžbina.
     */
    @Query("MATCH (l:Local {id: $localId})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order)-[:ORDERED]->(i:Item) " +
            "WITH i, COUNT(*) AS orderCount " +
            "ORDER BY orderCount DESC " +
            "RETURN i")
    List<Item> findMostOrderedItemsInLocal(String localId);



    @Query("MATCH (i:Item) WHERE NOT EXISTS { MATCH (i)<-[:ORDERED]-(o:Order) WHERE o.date >= date() - duration('P1M') } RETURN i")
    List<Item> findItemsNotOrderedInLastMonth();

    @Query("MATCH (l:Local {id: $localId})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order)-[:ORDERED]->(i:Item) " +
            "WITH i, COUNT(o) AS orderCount " +
            "ORDER BY orderCount ASC " +
            "LIMIT 1 " +
            "DETACH DELETE i " +
            "RETURN i")
    Item deleteItemWithLeastOrders(String localId);

    @Query("MATCH (l:Local {id: $localId})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order)-[:ORDERED]->(i:Item) " +
            "WITH i, COUNT(o) AS orderCount " +
            "ORDER BY orderCount DESC " +
            "LIMIT 3 " +
            "SET i.price = i.price * $increaseFactor " +
            "RETURN i")
    List<Item> increasePriceOfTopThreeItems(String localId, double increaseFactor);
}