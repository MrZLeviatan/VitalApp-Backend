package co.edu.uniquindio.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "medicamentos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Medicamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double cantidad;
    private int frecuenciaHora;
    private int duracionDias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resultado_id")
    @JsonIgnoreProperties("medicamentos")
    private ResultadoMedico resultadoMedico;
}
