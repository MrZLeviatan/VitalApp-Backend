package co.edu.uniquindio.models.users;


import co.edu.uniquindio.models.tools.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass   // Indica que esta clase es una superclase de entidades, pero no se mapea como tabla.
public abstract class Persona {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @Column(name = "nombre_completo", nullable = false) // El nombre no puede ser nulo.
    @Comment("Nombre completo del usuario (nombre y apellidos)")
    private String nombre;

    // Correo y Password
    @Embedded // Componente que se adhiere a la clase
    private User user;


}
