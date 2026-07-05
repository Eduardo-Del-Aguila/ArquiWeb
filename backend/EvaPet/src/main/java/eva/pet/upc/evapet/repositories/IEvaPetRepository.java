package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.EvaPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//Para crear un repository para una entidad estos son los pasos:
// 1. Agregar repository,
// 2. Poner como interface
// 3. Extender (extends) del padre JpaRepository<nombre de la entidad, tipo de dato del Id>




@Repository
public interface IEvaPetRepository extends JpaRepository<EvaPet, Long> {

    @Query("SELECT e.name FROM EvaPet e")
    List<String> listNames();

    @Query("SELECT COUNT(e.patient) FROM EvaPet e")
    List<Object> listByQuantity(Long id);

    @Query("SELECT p FROM EvaPet p WHERE p.patient.id = :patientId ORDER BY p.level DESC")
    List<EvaPet> findTopByPatientOrderedByLevel(@Param("patientId") Long patientId);

    @Query("SELECT e FROM EvaPet e WHERE e.patient.mail = :email")
    List<EvaPet> listByUserId(@Param("email") String email);

    @Query("SELECT e.name, e.experiencie, e.patient.name FROM EvaPet e ORDER BY e.experiencie DESC")
    List<Object[]> findPetsWithLevelAndOwner();
}
