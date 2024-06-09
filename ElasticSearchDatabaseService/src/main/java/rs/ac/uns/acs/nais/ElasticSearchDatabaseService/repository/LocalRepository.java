package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;

import java.util.List;

@Repository
public interface LocalRepository extends ElasticsearchRepository<Local, String> {
    List<Local> findByNameOrDescription(String name, String description);

    @Query("{\"match_all\": {}}")
    Page<Local> findAllLocals(Pageable pageable);

    @Query("{\"bool\": {\"must\": {\"term\": {\"country\": \"?0\"}}}}")
    Page<Local> findByCountry(String country, Pageable pageable);

    @Query("{\"bool\": {\"must\": {\"match\": {\"city\": \"?0\"}}}}")
    List<Local> findByCity(String city);

    @Query("{\"range\" : {\"capacity\" : {\"gte\" : ?0, \"lte\" : ?1}}}")
    List<Local> findByCapacityBetween(int minCapacity, int maxCapacity);

    /**
     * Pronalazi lokale koji se nalaze u određenoj zemlji i imaju kapacitet u zadanom opsegu (između minCapacity i maxCapacity).
     * Rezultati su sortirani u opadajucem redosledu kapaciteta(implementirano u servisu).
     *
     * @param country     naziv zemlje
     * @param minCapacity minimalni kapacitet
     * @param maxCapacity maksimalni kapacitet
     * @param pageable    informacije o straničenju i sortiranju
     * @return stranica lokalnih objekata koji ispunjavaju uslove pretrage
     */
    @Query("{\"bool\": {\"must\": [{\"term\": {\"country\": \"?0\"}}, {\"range\": {\"capacity\": {\"gte\": ?1, \"lte\": ?2}}}]}}")
    Page<Local> findByCountryAndCapacityRangeSorted(String country, int minCapacity, int maxCapacity, Pageable pageable);


    /**
     * Pronalazi lokale gde se zadati tekst pojavljuje u poljima "name" ili "description" i koji se nalaze u određenom gradu.
     * Upit mora ispuniti oba uslova - tekstualno podudaranje i grad.
     *
     * @param text tekst koji se pretražuje u poljima "name" i "description"
     * @param city naziv grada
     * @return lista lokalnih objekata koji ispunjavaju uslove pretrage
     */
    @Query("{\"bool\": {\"must\": [{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"name\", \"description\"]}}, {\"term\": {\"city\": \"?1\"}}]}}")
    List<Local> findByTextAndCity(String text, String city);


    /**
     * Pronalazi lokale koji se nalaze u određenoj zemlji, imaju kapacitet veći ili jednak zadatom,
     * i gde se naziv lokala podudara sa zadatim tekstom. Upit mora ispuniti uslove za zemlju i kapacitet,
     * dok je uslov za naziv opcionalan ali povećava relevantnost rezultata.Rezultati su sortirani u rastucem
     * redosledu na osnovu capacity-ja
     *
     * @param country  naziv zemlje
     * @param name     naziv lokala
     * @param capacity minimalni kapacitet
     * @return lista lokalnih objekata koji ispunjavaju uslove pretrage
     */
    @Query("{\"bool\": {\"must\": [{\"term\": {\"country\": \"?0\"}}, {\"range\": {\"capacity\": {\"gte\": ?2}}}], \"should\": [{\"match\": {\"name\": \"?1\"}}], \"minimum_should_match\": ?3}}")
    List<Local> findByCountryAndNameOrMinimumCapacity(String country, String name, int capacity, int minimumShouldMatch, Pageable pageable);

    /**
     * Pronalazi sve lokale u određenom gradu i sortira ih po proseku cena itema koje sadrže.
     *
     * @param city naziv grada
     * @return lista lokalnih objekata sortiranih po proseku cena itema
     */
    @Query("{"
            + "\"bool\": {"
            + "  \"must\": [{\"term\": {\"city\": \"?0\"}}]"
            + "},"
            + "\"aggs\": {"
            + "  \"by_local\": {"
            + "    \"terms\": {"
            + "      \"field\": \"localId.keyword\","
            + "      \"size\": 1000"
            + "    },"
            + "    \"aggs\": {"
            + "      \"average_price\": {"
            + "        \"avg\": {"
            + "          \"field\": \"price\""
            + "        }"
            + "      }"
            + "    }"
            + "  }"
            + "},"
            + "\"sort\": [{"
            + "  \"average_price\": {"
            + "    \"order\": \"asc\""
            + "  }"
            + "}]"
            + "}")
    List<Local> findLocalsInCitySortedByAverageItemPrice(String city);



}
