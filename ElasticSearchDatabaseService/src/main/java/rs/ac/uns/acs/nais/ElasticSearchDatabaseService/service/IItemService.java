package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Item;

import java.util.List;

public interface IItemService {
    List<Item> findByNameOrDescription(String name, String description);
}
