package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends ElasticsearchRepository<Item, String> {
    List<Item> findByNameOrDescription(String name, String description);

    @Query("{\"range\" : {\"price\" : {\"gte\" : ?0, \"lte\" : ?1}}}")
    List<Item> findByPriceBetween(int minPrice, int maxPrice);
    @Query("{\"bool\": {\"must\": {\"match\": {\"localId\": \"?0\"}}}}")
    List<Item> getAllItemsForLocalId(String localId);

    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"localId\": \"?0\"}}," +
            "{\"match\": {\"type\": \"FOOD\"}}," +
            "{\"match_phrase\": {\"description\": \"?1\"}}" +
            "]}}")
    List<Item> findFoodsByDescription(String localId, String description);

    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"localId\": \"?0\"}}," +
            "{\"match\": {\"type\": \"DRINK\"}}" +
            "]}}")
    List<Item> findAllDrinksByLocalId(String localId);

    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"localId\": \"?0\"}}," +
            "{\"match\": {\"type\": \"FOOD\"}}" +
            "]}}")
    List<Item> findAllFoodsByLocalId(String localId);

    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"localId\": \"?0\"}}," +
            "{\"match\": {\"type\": \"FOOD\"}}," +
            "{\"range\": {\"price\": {\"lt\": \"?1\"}}}" +
            "]}}")
    List<Item> findAllFoodsByLocalIdAndLowerPriceThanAvg(String localId,double price);
}
