package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.Alerts;
import eva.pet.upc.evapet.repositories.IAlertsRepository;
import eva.pet.upc.evapet.serviceInterfaces.IAlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertsServiceImplement implements IAlertsService {

    @Autowired
    private IAlertsRepository aR;

    @Override
    public List<Alerts> list() {
        return aR.findAll();
    }

    @Override
    public Alerts insert(Alerts a) {
        return aR.save(a);
    }

    @Override
    public Optional<Alerts> listId(Long id) {
        return aR.findById(id);
    }

    @Override
    public void delete(Long id) {
        aR.deleteById(id);
    }

    @Override
    public void update(Alerts a) {
        aR.save(a);
    }
}