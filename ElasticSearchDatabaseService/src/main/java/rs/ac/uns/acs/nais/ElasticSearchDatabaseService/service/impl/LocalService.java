package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Local> getAllLocals(Pageable pageable) {
        return localRepository.findAllLocals(pageable);
    }

    public Page<Local> findByCountry(String country, Pageable pageable) {
        return localRepository.findByCountry(country,pageable);
    }

    public List<Local> findByCity(String city){
        return localRepository.findByCity(city);
    }

    // ML #1
    public Page<Local> getLocalsByCountryAndCapacitySorted(String country, int minCapacity, int maxCapacity, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "capacity");
        Pageable pageable = PageRequest.of(page, size, sort);
        return localRepository.findByCountryAndCapacityRangeSorted(country, minCapacity, maxCapacity, pageable);
    }

    // ML #2
    public List<Local> searchByTextAndCity(String text, String city) {
        return localRepository.findByTextAndCity(text, city);
    }


    // ML #3
    public List<Local> getLocalsByCriteria(String country, String name, int capacity, int page, int size) {
        int minimumShouldMatch = (name == null || name.isEmpty()) ? 0 : 1;
        Sort sort = Sort.by(Sort.Direction.ASC, "capacity");
        Pageable pageable = PageRequest.of(page, size, sort);
        return localRepository.findByCountryAndNameOrMinimumCapacity(country, name, capacity, minimumShouldMatch, pageable);
    }


    public List<Local> getLocalsInCitySortedByAverageItemPrice(String city) {
        return localRepository.findLocalsInCitySortedByAverageItemPrice(city);
    }






}
