package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;

import java.util.List;

@Repository
public interface LocalRepository extends ElasticsearchRepository<Local, String> {
    List<Local> findByNameOrDescription(String name, String description);
}
