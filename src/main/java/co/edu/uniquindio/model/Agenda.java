package co.edu.uniquindio.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "agendas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Agenda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DayOfWeek dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean isActivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    @JsonIgnoreProperties("agendas")
    private Medico medico;

    @OneToMany(mappedBy = "agenda", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("agenda")
    private List<Cita> citas;
}
