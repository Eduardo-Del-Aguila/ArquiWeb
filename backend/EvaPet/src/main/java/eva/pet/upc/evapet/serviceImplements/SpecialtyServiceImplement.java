package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.Specialty;
import eva.pet.upc.evapet.repositories.ISpecialtyRepository;
import eva.pet.upc.evapet.serviceInterfaces.ISpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyServiceImplement implements ISpecialtyService {
    @Autowired
    private ISpecialtyRepository sR;

    @Override
    public List<Specialty> list() {return sR.findAll();}
}
