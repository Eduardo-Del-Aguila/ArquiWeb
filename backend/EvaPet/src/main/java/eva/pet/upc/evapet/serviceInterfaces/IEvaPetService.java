package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.EvaPet;

import java.util.List;
import java.util.Optional;

public interface IEvaPetService {

    public List<EvaPet> getAll();
    public Optional<EvaPet> getById(Long id);
    public EvaPet create(EvaPet e);
    public void update(Long id, EvaPet e);
    public void delete(Long id, EvaPet e);

    public List<String> getAllNames();

}
