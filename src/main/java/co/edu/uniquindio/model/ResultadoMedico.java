package co.edu.uniquindio.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "resultados_medicos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResultadoMedico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String recomendaciones;

    private LocalDate fecha;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id")
    @JsonIgnoreProperties("resultadoMedico")
    private Cita cita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    @JsonIgnoreProperties({"citas","resultadosMedicos","alertas"})
    private Paciente paciente;

    @OneToMany(mappedBy = "resultadoMedico", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Examen> examenes;

    @OneToMany(mappedBy = "resultadoMedico", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medicamento> medicamentos;
}
