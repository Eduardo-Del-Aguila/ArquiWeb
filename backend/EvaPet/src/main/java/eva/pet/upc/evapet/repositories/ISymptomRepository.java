package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISymptomRepository extends JpaRepository<Symptom,Long> {

    @Query(value = "SELECT s.severity, COUNT(s.id_symptom) " +
            "FROM symptom s " +
            "GROUP BY s.severity " +
            "ORDER BY COUNT(s.id_symptom) DESC", nativeQuery = true)
    List<Object[]> countSymptomsBySeverity();

    @Query(value = "SELECT s.id_medical_history, COUNT(s.id_symptom) " +
            "FROM symptom s " +
            "GROUP BY s.id_medical_history " +
            "ORDER BY COUNT(s.id_symptom) DESC", nativeQuery = true)
    List<Object[]> symptomsPerMedicalHistory();
}
