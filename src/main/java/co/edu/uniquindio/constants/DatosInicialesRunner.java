package co.edu.uniquindio.constants;

import co.edu.uniquindio.repository.users.AdminRepo;
import co.edu.uniquindio.repository.utils.CiudadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatosInicialesRunner implements CommandLineRunner {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private CiudadRepo ciudadRepo;


    @Override
    public void run(String... args) throws Exception {

        /**
        User adminUser = new User("admin@example.com", "password123", EstadoUser.ACTIVO);

        Admin admin = new Admin();
        admin.setNombre("admin");
        admin.setUser(adminUser);

        adminRepo.save(admin);



        Ciudad ciudad = new Ciudad();
        ciudad.setCiudad("Armenia");

        ciudadRepo.save(ciudad);
         **/
    }

}
