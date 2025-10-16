package co.edu.uniquindio.models.users;

import co.edu.uniquindio.models.objects.Agenda;
import co.edu.uniquindio.models.objects.Cita;
import co.edu.uniquindio.models.tools.Especialidad;
import co.edu.uniquindio.models.tools.Telefono;
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
@Table(name = "medicos")
@Comment("Entidad que representa a un médico que atiende pacientes.")
public class Medico extends Persona {


    // Relación uno a muchos con Teléfono
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Teléfonos asociados al medico")
    private List<Telefono> telefonos;

    @ManyToOne
    @JoinColumn(name = "especialidad_id", nullable = false)
    @Comment("Especialidad del medico")
    private Especialidad especialidad;

    // Relación uno a muchos con Citas
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Citas asociados al medico")
    private List<Cita> citas;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Agenda del medico")
    private List<Agenda> agenda;


    // Método auxiliar para agregar teléfono
    public void agregarTelefono(Telefono telefono) {
        telefonos.add(telefono);
        telefono.setMedico(this);
    }


}
