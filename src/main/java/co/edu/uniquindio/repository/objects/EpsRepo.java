package co.edu.uniquindio.repository.objects;

import co.edu.uniquindio.models.objects.Eps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EpsRepo extends JpaRepository<Eps,Long>, JpaSpecificationExecutor<Eps> {


}
