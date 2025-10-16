package co.edu.uniquindio.models.objects;

import co.edu.uniquindio.models.users.Medico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agenda_medico")
@Comment("Entidad que representa las agendas asociadas a un medico y cita")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @Column(name = "dia_agenda", nullable = false)
    @Comment("Fecha de la agenda")
    private LocalDate dia;

    @Column(name = "hora_inicio",nullable = false)
    @Comment("Hora de inicio de la agenda")
    private LocalTime horaInicio;

    @Column(name = "hora_fin",nullable = false)
    @Comment("Hora fin de la agenda")
    private LocalTime horaFin;

    @Column(nullable = false)
    @Comment("Indica si la agenda está activa")
    private boolean isActivo;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    @Comment("Médico al que pertenece esta agenda")
    private Medico medico;


    @OneToOne(mappedBy = "agenda", cascade = CascadeType.ALL)
    @Comment("Cita asociada a esta agenda")
    private Cita cita;

}
