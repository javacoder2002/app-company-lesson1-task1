package uz.pdp.lesson1task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.lesson1task2.entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker,Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);

}
