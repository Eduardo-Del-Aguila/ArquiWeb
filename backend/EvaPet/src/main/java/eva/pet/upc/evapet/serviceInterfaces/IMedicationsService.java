package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.Medications;

import java.util.List;
import java.util.Optional;

public interface IMedicationsService {
    public List<Medications> list();

    Medications insert(Medications med);

    Optional<Medications> listId(int id);

    void update(Medications m);

    void delete(int id);

    boolean existsByName(String name);
}
