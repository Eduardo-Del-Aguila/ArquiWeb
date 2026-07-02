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
    //  Guardar	save(S entity)	S	Guarda o actualiza una entidad
    // Guardar	saveAll(Iterable<S> entities)	List<S>	Guarda múltiples entidades

    // Buscar	findAll()	List<T>	Obtiene todos los registros
    // Buscar	findById(ID id)	Optional<T>	Busca por ID
    // Buscar	findAllById(Iterable<ID> ids)	List<T>	Busca varios por ID
    // Buscar	existsById(ID id)	boolean	Verifica si existe un registro

    // Eliminar	deleteById(ID id)	void	Elimina por ID
    // Eliminar	delete(T entity)	void	Elimina una entidad
    // Eliminar	deleteAll()	void	Elimina todos los registros
    // Eliminar	deleteAllById(Iterable<? extends ID> ids)	void	Elimina varios por ID

    //  Conteo	count()	long	Cuenta total de registros

    // Paginación	findAll(Pageable pageable)	Page<T>	Obtiene datos paginados
    // Ordenamiento	findAll(Sort sort)	List<T>	Obtiene datos ordenados

    // Avanzado	flush()	void	Sincroniza con la BD
    // Avanzado	saveAndFlush(S entity)	S	Guarda y ejecuta inmediatamente
    // Avanzado	deleteAllInBatch()	void	Elimina en lote (más eficiente)
    // Avanzado	deleteAllInBatch(Iterable<T> entities)	void	Elimina lista en lote
    // Avanzado	deleteAllByIdInBatch(Iterable<ID> ids)
    @Query("SELECT e.name FROM EvaPet e")
    List<String> listNames();

    @Query("SELECT COUNT(e.patient) FROM EvaPet e")
    List<Object> listByQuantity(Long id);

    @Query("SELECT p FROM EvaPet p WHERE p.patient.id = :patientId ORDER BY p.level DESC")
    List<EvaPet> findTopByPatientOrderedByLevel(@Param("patientId") Long patientId);

    @Query("SELECT e FROM EvaPet e WHERE e.patient.id =: idPaciente")
    List<EvaPet> listByUserId(@Param("idPaciente") Long idPaciente);

}
