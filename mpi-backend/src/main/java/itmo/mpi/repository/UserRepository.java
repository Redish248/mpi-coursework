package itmo.mpi.repository;

import itmo.mpi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByIsActivated(boolean isActivated);

    User findByNick(String nick);

    List<User> findAllByRegistrationDateBefore(LocalDate date);
}
