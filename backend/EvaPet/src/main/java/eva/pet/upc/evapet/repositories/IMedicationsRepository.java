package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Medications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicationsRepository extends JpaRepository<Medications,Integer> {
    boolean existsByNameIgnoreCase(String name);

    @Query("SELECT m FROM Medications m WHERE m.isActive = true")
    List<Medications> listarMedicamentosActivos();

}
