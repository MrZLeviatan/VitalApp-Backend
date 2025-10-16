package co.edu.uniquindio.repository.users;

import co.edu.uniquindio.models.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long>, JpaSpecificationExecutor<Admin> {


    Optional<Admin> findByUser_Email(String email);

    boolean existsByUser_Email(String nombre);

}
