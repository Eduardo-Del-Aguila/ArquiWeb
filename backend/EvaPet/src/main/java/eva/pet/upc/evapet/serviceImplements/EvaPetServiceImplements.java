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

    @Override
    public List<EvaPet> listAll() {
        return eR.findAll();
    }

    @Override
    public Optional<EvaPet> listById(Long id) {
        return eR.findById(id);
    }

    @Override
    public EvaPet insert(EvaPet e) {
        return eR.save(e);
    }

    @Override
    public void update(EvaPet dto) {
        eR.save(dto);
    }

    @Override
    public void delete(EvaPet e) {
        eR.save(e);
    }

    //definir a futuro
    @Override
    public List<String> listByName() {
        return List.of();
    }

//    @Override
//    public List<String> getAllNames() {
//        return eR.listNames();
//    }
}
