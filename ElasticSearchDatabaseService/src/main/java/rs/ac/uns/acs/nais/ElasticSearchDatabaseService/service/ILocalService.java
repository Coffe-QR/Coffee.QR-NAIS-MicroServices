package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;

import java.util.List;

public interface ILocalService {
    List<Local> findByNameOrDescription(String name, String description);

    // ML #1
    Page<Local> getLocalsByCountryAndCapacitySorted(String country, int minCapacity, int maxCapacity, int page, int size);

    // ML #2
    //List<Local> searchByTextAndCity(String text, String city, Pageable pageable);
    List<Local> searchByTextAndCity(String text, String city);

    // ML #3
    List<Local> getLocalsByCriteria(String country, String name, int capacity, Pageable pageable);
}
