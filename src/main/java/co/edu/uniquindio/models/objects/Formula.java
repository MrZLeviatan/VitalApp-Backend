package co.edu.uniquindio.models.objects;


import co.edu.uniquindio.models.tools.Telefono;
import co.edu.uniquindio.models.users.Paciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "formula_medica")
@Comment("Entidad que representa la formula medica asociada a cada cita y sus detalles.")
public class Formula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @Column(name = "fecha_formulario",nullable = false)
    @Comment("Fecha registro del formulario")
    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @Comment("Paciente asociado a la formula")
    private Paciente paciente;

    @OneToOne
    @JoinColumn(name = "cita_id", nullable = false)
    @Comment("Cita asociada a la formula")
    private Cita cita;

    // Relación uno a muchos con las Citas del Paciente
    @OneToMany(mappedBy = "formula", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Detalles de la formula.")
    private List<DetalleFormula> detallesFormula;


    // Método auxiliar para agregar teléfono
    public void agregarDetalle(DetalleFormula detalle) {
        detallesFormula.add(detalle);
        detalle.setFormula(this);
    }


}