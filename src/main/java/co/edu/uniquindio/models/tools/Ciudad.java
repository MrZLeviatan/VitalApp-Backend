package co.edu.uniquindio.models.tools;

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
@Entity
@Table(name = "ciudad")
@Comment("Entidad que representa las ciudades asociados a el paciente.")
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @Column(name = "nombre_ciudad", nullable = false)
    @Comment("Nombre de la ciudad")
    private String ciudad;

}
