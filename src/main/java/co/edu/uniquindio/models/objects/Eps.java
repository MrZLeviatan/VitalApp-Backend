package co.edu.uniquindio.models.objects;

import co.edu.uniquindio.models.users.Paciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eps")
@Comment("Entidad que representa una eps asociada a los pacientes.")
public class Eps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @Column(name = "nombre_eps", nullable = false) // El nombre no puede ser nulo.
    @Comment("Nombre completo de la eps.")
    private String nombre;


    @OneToMany(mappedBy = "eps", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @Comment("Pacientes asociados a esta eps.")
    private List<Paciente> pacientes;

}
