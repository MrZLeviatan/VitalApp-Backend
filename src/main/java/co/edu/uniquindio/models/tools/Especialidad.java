package co.edu.uniquindio.models.tools;

import co.edu.uniquindio.models.users.Medico;
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
@Table(name = "especialidad")
@Comment("Entidad que representa las especialidades de los medicos.")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @Column(name = "nombre_especialidad", nullable = false)
    @Comment("Nombre de la especialidad")
    private String especialidad;


    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Medicos que tienen esta especialidad")
    private List<Medico> medicos;

}
