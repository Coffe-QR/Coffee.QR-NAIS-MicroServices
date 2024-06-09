package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Local;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.LocalRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ILocalService;

import java.util.List;

@Service
public class LocalService implements ILocalService {
    private final LocalRepository localRepository;

    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }
    public void save(Local item) {localRepository.save(item);}

    public List<Local> findAllLocals() {
        return localRepository.findAll();
    }

    public Local findLocalById(String id) {
        return localRepository.findById(id).get();
    }

    public void deleteLocal(String id) {
        localRepository.deleteById(id);
    }

    public int getTotalFoodQuantityForSmokingTables() {
        return localRepository.getTotalFoodQuantityForSmokingTables();
    }

    public Double getAverageItemPrice(String localId) {
        return localRepository.findAverageItemPriceByLocalId(localId);
    }
    public Double getAnnualEarnings(String localId, int year) {
        return localRepository.findAnnualEarningsByLocalIdAndYear(localId, year);
    }

}
