package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.Medications;
import eva.pet.upc.evapet.repositories.IMedicationsRepository;
import eva.pet.upc.evapet.serviceInterfaces.IMedicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationsServiceImplement implements IMedicationsService {
    @Autowired
    private IMedicationsRepository mRepo;

    @Override
    public List<Medications> list()
    {
        return mRepo.findAll();
    }
}
