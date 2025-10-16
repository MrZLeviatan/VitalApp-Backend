package co.edu.uniquindio.models.tools;


import co.edu.uniquindio.models.enums.EstadoUser;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable     // Esta clase será embebida en otras entidades, no se convierte en una tabla
public class User {


    @Email
    @Column(name = "email", nullable = false, unique = true)
    @Comment("Dirección de correo electrónico única para autenticación.")
    private String email;

    @Column(name = "password", nullable = false)
    @Comment("Contraseña cifrada del usuario para autenticación.")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_user")
    @Comment("Estado del user: Activo, Eliminado")
    private EstadoUser estadoUser;
}

