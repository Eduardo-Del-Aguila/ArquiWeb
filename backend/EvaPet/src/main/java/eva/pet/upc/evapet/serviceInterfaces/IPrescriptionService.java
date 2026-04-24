package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.Prescription;

import java.util.List;
import java.util.Optional;


public interface IPrescriptionService {
    public List<Prescription> list();

    Prescription insert(Prescription pre);

    Optional<Prescription> listId(int id);

    void update(Prescription p);

    void delete(int id);

    List<Object[]> recetasPorPaciente();
}
