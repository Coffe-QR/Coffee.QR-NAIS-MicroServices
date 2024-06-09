package rs.ac.uns.acs.nais.GraphDatabaseService.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.TableOrderCountDto;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderRepository extends Neo4jRepository<Order, String> {

    /*izračunao ukupan broj narudžbina za svaki sto, a zatim filtrira rezultate da bi se prikazali samo stolovi sa više od 5 narudžbina:*/
    @Query("MATCH (t:Table)-[:ON_TABLE]->(o:Order)\n" +
            "WITH t, COUNT(o) AS orderCount\n" +
            "WHERE orderCount > 0\n" +
            "RETURN  t.name AS tableName, orderCount\n" +
            "ORDER BY orderCount DESC")
    List<TableOrderCountDto> findTablesWithMoreThanFiveOrders();

    //UKUPAN PROMET ZA LOKAL
    @Query("MATCH (l:Local {id: $localId})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order) " +
            "WITH SUM(o.price) AS totalSales " +
            "RETURN totalSales")
    Double findTotalSalesForLocal(String localId);

    @Query("MATCH (l:Local {id: $localId})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order) " +
            "RETURN o " +
            "ORDER BY o.date DESC " +
            "LIMIT 10")
    List<Order> findRecentOrdersForLocal(@Param("localId") String localId);

    @Query("MATCH (l:Local {id: $localId})-[:IN_LOCAL]->(t:Table)-[:ON_TABLE]->(o:Order) " +
            "WHERE o.date >= date($startDate) AND o.date < date($endDate) " +
            "SET o.discount = $discount " +
            "RETURN COUNT(o) AS ordersUpdated")
    int updateOrderDiscount(String localId, String startDate, String endDate, double discount);
}
