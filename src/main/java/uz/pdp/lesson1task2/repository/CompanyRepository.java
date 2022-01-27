package uz.pdp.lesson1task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.lesson1task2.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
