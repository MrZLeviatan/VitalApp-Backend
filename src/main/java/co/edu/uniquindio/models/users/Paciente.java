package co.edu.uniquindio.models.users;


import co.edu.uniquindio.models.objects.Alerta;
import co.edu.uniquindio.models.objects.Cita;
import co.edu.uniquindio.models.objects.Formula;
import co.edu.uniquindio.models.tools.Ciudad;
import co.edu.uniquindio.models.objects.Eps;
import co.edu.uniquindio.models.tools.Telefono;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
@Comment("Entidad que representa a un paciente que solicita citas y tienes resultados medicos")
public class Paciente extends Persona {


    // Relación a una eps
    @ManyToOne
    @JoinColumn(name = "eps_id", nullable = false)
    @Comment("Eps asociada al Paciente")
    private Eps eps;

    // Relación a una ciudad
    @ManyToOne
    @JoinColumn(name = "ciudad_id", nullable = false)
    @Comment("Ciudad asociada al paciente")
    private Ciudad ciudad;

    // Relación uno a muchos con Teléfono
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Teléfonos asociados al paciente")
    private List<Telefono> telefonos;

    // Relación uno a muchos con las Citas del Paciente
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Citas asociadas al paciente")
    private List<Cita> citas;

    // Relación uno a muchos con las Citas del Paciente
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Formulas Medicas asociadas al paciente")
    private List<Formula> formulasMedicas;

    // Relación uno a muchos con Teléfono
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Alertas asociados al paciente")
    private List<Alerta> alertas;



    // Método auxiliar para agregar teléfono
    public void agregarTelefono(Telefono telefono) {
        telefonos.add(telefono);
        telefono.setPaciente(this);
    }

}