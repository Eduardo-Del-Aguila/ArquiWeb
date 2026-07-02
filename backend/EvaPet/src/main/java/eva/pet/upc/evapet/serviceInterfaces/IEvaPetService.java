package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.Alerts;
import eva.pet.upc.evapet.models.EvaPet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IEvaPetService {

    public List<EvaPet> listAll();
    public Optional<EvaPet> listById(Long id);
    public EvaPet insert(EvaPet e);
    public void update( EvaPet e);
    public void delete(EvaPet e);

    public List<String> listByName();
    public List<EvaPet> findTopByPatientOrderedByLevel(Long id);

    public List<EvaPet> findByPatientId(Long id);

}
