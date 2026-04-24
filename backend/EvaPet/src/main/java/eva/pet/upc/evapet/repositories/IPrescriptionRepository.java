package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPrescriptionRepository extends JpaRepository<Prescription, Integer> {

    @Query(value = "SELECT p.id_user_patient, COUNT(p.id_prescription) " +
            "FROM prescription p " +
            "GROUP BY p.id_user_patient ORDER BY COUNT(p.id_prescription) DESC", nativeQuery = true)
    List<Object[]> recetasPorPaciente();

}
