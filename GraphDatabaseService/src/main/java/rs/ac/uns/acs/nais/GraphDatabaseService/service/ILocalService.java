package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Local;

import java.util.List;

public interface ILocalService {

    public void save(Local local);

    public Iterable<Local> findAllLocals();

    public Local findLocalById(String id);

    public void deleteLocal(String id);
}