package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.PrescriptionMedications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPrescriptionMedicationsRepository extends JpaRepository<PrescriptionMedications, Integer> {

    @Query(value = "SELECT m.name, COUNT(pm.id_prescription_medications) " +
            "FROM prescription_medications pm " +
            "JOIN medications m ON pm.id_medication = m.id_medication " +
            "GROUP BY m.name ORDER BY COUNT(pm.id_prescription_medications) DESC",
            nativeQuery = true)
    List<Object[]> medicamentosMasUsados();
}
