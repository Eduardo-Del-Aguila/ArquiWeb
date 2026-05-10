package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.EvaPet;

import java.util.List;
import java.util.Optional;

public interface IEvaPetService {

    public List<EvaPet> listAll();
    public Optional<EvaPet> listById(Long id);
    public EvaPet insert(EvaPet e);
    public void update( EvaPet e);
    public void delete(EvaPet e);

    public List<String> listByName();

}
