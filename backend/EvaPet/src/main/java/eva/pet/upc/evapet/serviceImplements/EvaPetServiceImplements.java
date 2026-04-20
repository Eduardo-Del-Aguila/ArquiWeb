package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.EvaPet;
import eva.pet.upc.evapet.repositories.IEvaPetRepository;
import eva.pet.upc.evapet.serviceInterfaces.IEvaPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaPetServiceImplements implements IEvaPetService {
    //Llamamos al repository
    @Autowired
    private IEvaPetRepository eR;

    //Ejecutamos todos los métodos de mi IEvaPetInterface ya que lo estamos ejecutanto con impleents
    @Override
    public List<EvaPet> getAll() {
        return eR.findAll();
    }

    @Override
    public Optional<EvaPet> getById(Long id) {
        return eR.findById(id);
    }

    @Override
    public EvaPet create(EvaPet e) {
        return eR.save(e);
    }

    @Override
    public void update(Long id, EvaPet dto) {
        eR.save(dto);
    }

    @Override
    public void delete(Long id, EvaPet e) {
        eR.save(e);
    }

    @Override
    public List<String> getAllNames() {
        return eR.listNames();
    }
}
