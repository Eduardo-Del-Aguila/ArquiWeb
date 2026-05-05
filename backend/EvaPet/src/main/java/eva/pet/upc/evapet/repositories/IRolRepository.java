package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.enums.UserRol; // ✅ Importar tu Enum
import eva.pet.upc.evapet.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {

    // 1. Buscar un rol exacto por su nombre (Enum).
    // Devuelve un Optional porque podría no existir en la base de datos aún.
    // 🔥 ESTA ES LA MÁS IMPORTANTE PARA EL REGISTRO DE USUARIOS 🔥
    Optional<Rol> findByNameRol(UserRol nameRol);

    // 2. Comprobar si un rol ya existe (Devuelve true o false)
    // Útil para validaciones antes de intentar insertar un rol duplicado
    boolean existsByNameRol(UserRol nameRol);

    // 3. Buscar roles cuya descripción contenga una palabra específica (ignorando mayúsculas/minúsculas)
    // Útil si en el futuro tienes muchos roles y quieres hacer un buscador en el frontend
    List<Rol> findByDescriptionRolContainingIgnoreCase(String palabraClave);
}