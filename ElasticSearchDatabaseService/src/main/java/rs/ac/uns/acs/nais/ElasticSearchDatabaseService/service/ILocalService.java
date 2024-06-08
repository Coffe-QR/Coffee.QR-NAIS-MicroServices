package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;

import java.util.List;

public interface ILocalService {
    List<Local> findByNameOrDescription(String name, String description);
}
