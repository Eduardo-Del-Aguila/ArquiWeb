package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.enums.AlertType;
import eva.pet.upc.evapet.models.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAlertsRepository extends JpaRepository<Alerts, Long> {

    // Query 1: Buscar todas las alertas que NO han sido leídas para un paciente
    @Query("SELECT a FROM Alerts a WHERE a.patient.id = :idPaciente AND a.isRead = false")
    List<Alerts> findNoLeidasPorPaciente(@Param("idPaciente") Long idPaciente);

    // Query 2: Contar cuántas alertas no leídas tiene un paciente
    @Query("SELECT COUNT(a) FROM Alerts a WHERE a.patient.id = :idPaciente AND a.isRead = false")
    Long contarAlertasNoLeidasPorPaciente(@Param("idPaciente") Long idPaciente);
}