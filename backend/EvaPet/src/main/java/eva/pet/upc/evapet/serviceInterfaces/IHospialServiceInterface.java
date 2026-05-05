package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.Hospital;

import java.util.List;
import java.util.Optional;

public interface IHospialServiceInterface {
    public List<Hospital> listALL();
    public Optional<Hospital> listById(Long id);
    public Hospital update(Hospital h);
    public void deleteById(Hospital h);

}
