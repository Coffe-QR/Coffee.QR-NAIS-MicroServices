package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Local;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.LocalRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.ILocalService;

import java.util.List;
import java.util.Optional;

@Service
public class LocalService implements ILocalService {
    private final LocalRepository localRepository;

    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }
    public void save(Local local) {localRepository.save(local);}

    public List<Local> findByNameOrDescription(String name, String description) {
        return localRepository.findByNameOrDescription(name, description);
    }

    public Iterable<Local> findAllLocals() {
        return localRepository.findAll();
    }

    public Optional<Local> findLocalById(String id) {
        return localRepository.findById(id);
    }

    public void deleteLocal(String id) {
        localRepository.deleteById(id);
    }
}