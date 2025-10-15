package co.edu.uniquindio.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "examenes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Examen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoExamen tipoExamen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resultado_id")
    @JsonIgnoreProperties("examenes")
    private ResultadoMedico resultadoMedico;
}
