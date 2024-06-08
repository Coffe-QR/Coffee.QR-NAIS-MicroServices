package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Local;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.LocalRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ILocalService;

import java.util.List;

public class LocalService implements ILocalService {
    private final LocalRepository itemRepository;

    public LocalService(LocalRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public void save(Local item) {itemRepository.save(item);}

    public List<Local> findAllLocals() {
        return itemRepository.findAll();
    }

    public Local findLocalById(String id) {
        return itemRepository.findById(id).get();
    }

    public void deleteLocal(String id) {
        itemRepository.deleteById(id);
    }

}
