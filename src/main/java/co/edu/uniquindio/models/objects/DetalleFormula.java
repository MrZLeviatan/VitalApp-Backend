package co.edu.uniquindio.models.objects;

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
@Table(name = "detalle_formula")
@Comment("Entidad que representa el detalle de una formula.")
public class DetalleFormula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "formula_id")
    @Comment("Formula asociada a los detalles")
    private Formula formula;

    @Column(name = "cantidad_medicamento")
    @Comment("Cantidad del medicamento")
    private Integer cantidad;

    @Column(name = "observaciones", nullable = false) // El nombre no puede ser nulo.
    @Comment("Observaciones de la formula médica")
    private String observaciones;

    @Column(name = "dosis", nullable = false) // El nombre no puede ser nulo.
    @Comment("Dosis y detalles de la misma")
    private String dosis;

    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    @Comment("Medicamento asociados al detalle")
    private Medicamento medicamento;

}
