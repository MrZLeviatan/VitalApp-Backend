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
@Table(name = "medicamento")
@Comment("Entidad que representa el medicamento.")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @Column(name = "nombre", nullable = false) // El nombre no puede ser nulo.
    @Comment("Nombre del medicamento")
    private String nombre;

    @Column(name = "precio")
    @Comment("Precio del medicamento")
    private Double precio;

}
