package co.edu.uniquindio.models.objects;

import co.edu.uniquindio.models.enums.EstadoCita;
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
@Table(name = "cita_medica")
@Comment("Entidad que representa las citas medicas asociadas a los pacientes y medicos.")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @Column(name = "observaciones", nullable = false) // El nombre no puede ser nulo.
    @Comment("Observaciones de la cita médica")
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cita")
    @Comment("Estado de la cita: Agendada, En_Revision, Cancelada, Completada")
    private EstadoCita estadoCita;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @Comment("Paciente asociado a la cita")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    @Comment("Medico asociado a la cita")
    private Medico medico;

    @OneToOne(mappedBy = "cita", cascade = CascadeType.ALL)
    @Comment("Formula medica asociada a la cita")
    private Formula formula;

    @OneToOne
    @JoinColumn(name = "agenda_id", nullable = false, unique = true)
    @Comment("Agenda asociada a la cita")
    private Agenda agenda;


}
