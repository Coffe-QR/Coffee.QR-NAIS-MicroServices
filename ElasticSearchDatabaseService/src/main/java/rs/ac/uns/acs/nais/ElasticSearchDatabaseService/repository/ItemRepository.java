package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends ElasticsearchRepository<Item, String> {
    List<Item> findByNameOrDescription(String name, String description);
}
