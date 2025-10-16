package co.edu.uniquindio.models.objects;

import co.edu.uniquindio.models.enums.TipoAlerta;
import co.edu.uniquindio.models.users.Paciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alerta")
@Comment("Entidad que representa las alertas del sistema")
public class Alerta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID creado automáticamente por Oracle SQL)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_alerta")
    @Comment("Tipo de Alerta: AVISO, WARNING")
    private TipoAlerta tipoAlerta;

    @Column(name = "mensaje_alerta", nullable = false)
    @Comment("mensaje")
    private String mensaje;

    @Column(name = "fecha_registro", nullable = false)
    @Comment("Fecha del registro alerta.")
    private LocalDateTime fechaRegistro;

    @Column(name = "estado_leida")
    @Comment("Indica si la agenda está leida o no")
    private boolean isLeida;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @Comment("Paciente asociado a la alerta")
    private Paciente paciente;

}
