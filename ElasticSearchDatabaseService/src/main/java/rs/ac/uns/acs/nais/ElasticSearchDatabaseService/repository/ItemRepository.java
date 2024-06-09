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


    /**
     * Pronalazi iteme koji pripadaju odredjenom lokalu imaju tip FOOD i u svom opisu sadrze prosledjeni string
     * Rezultati su sortirani u rastucem redosledu cena(implementirano u servisu).
     *
     * @param localId     id lokala
     * @param description string koji se proverava da li je sadrzan u opisu
     * @return lista lokalnih objekata koji ispunjavaju uslove pretrage
     */

    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"localId\": \"?0\"}}," +
            "{\"match\": {\"type\": \"FOOD\"}}," +
            "{\"match_phrase\": {\"description\": \"?1\"}}" +
            "]}}")
    List<Item> findFoodsByDescription(String localId, String description);


    /**
     * Ove dve metode sluze da nadju sva pica i sva jela iz nekog lokala i pomocu njih da izracunamo najmanju cenu u lookalu da se
     * uzme i pice i hrana.
     * Pronadjeni su minimumi cena iz oba querija i sabrani(implementirano u servisu).
     *
     * @param localId     id lokala
     * @return lista lokalnih objekata koji ispunjavaju uslove pretrage
     */

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

    /**
     * Ova metoda sluzi da dobijemo sve artikle koji imaju cenu manju od zadate(izracunata prosecna cena hrane u tom lokalu)
     * Rezultati su sortirani u opadajucem redosledu cena(implementirano u servisu).
     *
     * @param localId     id lokala
     * @param price     izracunata prosecna cena hrane u tom lokalu
     * @return lista lokalnih objekata koji ispunjavaju uslove pretrage
     */

    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"localId\": \"?0\"}}," +
            "{\"match\": {\"type\": \"FOOD\"}}," +
            "{\"range\": {\"price\": {\"lt\": \"?1\"}}}" +
            "]}}")
    List<Item> findAllFoodsByLocalIdAndLowerPriceThanAvg(String localId,double price);

    @Query("{\"match\": {\"description\": {\"query\": \"?0\", \"operator\": \"and\"}}}")
    List<Item> findByDescriptionContaining(String description);
}
