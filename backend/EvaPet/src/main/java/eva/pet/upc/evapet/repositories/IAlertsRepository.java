package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAlertsRepository extends JpaRepository<Alerts, Long> {

    // -------------------------------------------------------------------
    // 1. QUERY METHODS (Spring arma el SQL por el nombre del método)
    // -------------------------------------------------------------------

    // Buscar todas las alertas de un paciente específico, ordenadas de la más nueva a la más vieja
    List<Alerts> findByIdPatientOrderByCreatedAtDesc(Long idPatient);

    // Buscar todas las alertas que NO han sido leídas (isRead = false) para un paciente
    List<Alerts> findByIsReadFalseAndIdPatient(Long idPatient);

    // Buscar alertas por su tipo (ej. "CRITICA", "INFO")
    List<Alerts> findByType(String type);


    // -------------------------------------------------------------------
    // 2. CONSULTAS JPQL (Para lógicas un poco más específicas)
    // -------------------------------------------------------------------

    // Buscar alertas no leídas de un tipo específico (ej: Mostrar solo críticas no leídas)
    @Query("SELECT a FROM Alerts a WHERE a.type = :tipo AND a.isRead = false")
    List<Alerts> buscarNoLeidasPorTipo(@Param("tipo") String tipo);

    // Contar cuántas alertas no leídas tiene un paciente (Útil para mostrar un globo de notificaciones en el Frontend)
    @Query("SELECT COUNT(a) FROM Alerts a WHERE a.idPatient = :idPatient AND a.isRead = false")
    Long contarAlertasNoLeidasPorPaciente(@Param("idPatient") Long idPatient);
}