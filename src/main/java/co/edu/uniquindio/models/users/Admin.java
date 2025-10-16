package co.edu.uniquindio.models.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Admins")
@Comment("Entidad que representa a un Administrador que gestiona.")
public class Admin extends Persona {
}
