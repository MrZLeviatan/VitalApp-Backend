package co.edu.uniquindio.repository.utils;

import co.edu.uniquindio.models.tools.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefonoRepo extends JpaRepository<Telefono, Integer> {


    boolean existsByNumero(String numero);
}
