package co.edu.uniquindio.models.tools;


import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.models.users.Paciente;
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
@Table(name = "telefonos")
@Comment("Entidad que representa los teléfonos asociados a los pacientes y medicos.")
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @Column(name = "número_telefónico", nullable = false)
    @Comment("Número telefónico")
    private String numero;

    // Relación con Paciente (muchos teléfonos pueden pertenecer a un paciente)
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @Comment("Paciente asociado a este teléfono")
    private Paciente paciente;

    // Relación con Paciente (muchos teléfonos pueden pertenecer a un paciente)
    @ManyToOne
    @JoinColumn(name = "medico_id")
    @Comment("Medico asociado a este teléfono")
    private Medico medico;

}
