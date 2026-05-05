package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.Hospital;
import eva.pet.upc.evapet.repositories.IHospitalRepository;
import eva.pet.upc.evapet.serviceInterfaces.IHospialServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalServiceImplements implements IHospialServiceInterface {
    @Autowired
    private IHospitalRepository hR;

    @Override
    public List<Hospital> listALL() {
        return hR.findAll();
    }

    @Override
    public Optional<Hospital> listById(Long id) {
        return hR.findById(id);
    }

    @Override
    public Hospital update(Hospital h) {
        return hR.save(h);
    }

    @Override
    public void deleteById(Long id) {
        hR.deleteById(id);
    }
}
