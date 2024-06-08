package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;

import java.util.List;

@Repository
public interface LocalRepository extends ElasticsearchRepository<Local, String> {
    List<Local> findByNameOrDescription(String name, String description);

    //ML #1
    // This query can fetch locals by a range of capacities,
    // filter them by a specific country, and then sort them by capacity in descending order:
    @Query("{\"bool\": {\"must\": [{\"term\": {\"country\": \"?0\"}}, {\"range\": {\"capacity\": {\"gte\": ?1, \"lte\": ?2}}}]}}")
    Page<Local> findByCountryAndCapacityRangeSorted(String country, int minCapacity, int maxCapacity, Pageable pageable);


    //ML #2
    // This query performs a full-text search on the description and name fields,
    // filters by city, and aggregates the results based on country:
    @Query("{\"bool\": {\"must\": [{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"name\", \"description\"]}}, {\"term\": {\"city\": \"?1\"}}]}}")
    List<Local> findByTextAndCity(String text, String city);



    //ML #3
    //This complex query combines multiple conditions (AND, OR)
    // on various fields such as name, country, and capacity:

    @Query("{\"bool\": {\"must\": [{\"term\": {\"country\": ?0}}, {\"range\": {\"capacity\": {\"gte\": ?2}}}], \"should\": [{\"match\": {\"name\": ?1}}], \"minimum_should_match\": 1}}")
    List<Local> findByCountryAndNameOrMinimumCapacity(String country, String name, int capacity, Pageable pageable);

    //ML #PROBA
    @Query("{\"match_all\": {}}")
    Page<Local> findAllLocals(Pageable pageable);

    @Query("{\"bool\": {\"must\": {\"term\": {\"country\": \"?0\"}}}}")
    Page<Local> findByCountry(String country, Pageable pageable);


}
