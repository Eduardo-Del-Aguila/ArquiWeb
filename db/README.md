tabla users: Se ha asignado una auto-referencia a la tabla user, posible mejora sería crear 3 tablas distntas, patient, medic y family con sus propiedades que hagan referencia a usuario.

tabla medical_history: a pensar como se va a relacionar con users o en todo caso si se usa la opcion de las 3 tablas pues solo haría referencia a patient

tablas faltantes: yo diria que alertas tenemos que tener cuidado y con respecto a las alertas asignarles una tabla que quizas una alertá esté ligada a un user/patient

/////////

Ya quedó la división usuarios, pacientes, médicos y familiares para que no se mezcle nada y no queden campos vacíos por las puras.

El historial médico ahora apunta directo al paciente, que es el único que de verdad lo necesita, nada de conectarlo al usuario general.

la tabla de alertas que faltaba para que el sistema mande avisos y recordatorios.

Historial médico solo para el paciente, prohibido mezclarlo con el perfil del doctor.

/// 10/04/2026

1. Se eliminarán las tablas familiares, medicos y pacientes las cuales serán reemplazadas por "rol".
2. Completar el historial medico con mas tablas para especificar.


//21/04/2026

1. eva_pet_responses.severidad
ENUM('leve', 'moderado', 'grave')

2. medical_history.state
ENUM('pendiente', 'revisado', 'cerrado')

3. prescription.status
ENUM('sugerida', 'aprobada', 'activa', 'completada', 'cancelada')

4. alerts.type
ENUM('salud', 'comportamiento', 'medicamento', 'inactividad')

5. symptom.severidad
ENUM('leve', 'moderado', 'grave')

28/04/2026

Considerar una elimicacion de la talba intermedia 

