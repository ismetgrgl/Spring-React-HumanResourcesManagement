package com.humanresources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.humanresources.Vw.EmployeeView;
import com.humanresources.entity.Employee;
import com.humanresources.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser(User user);

    @Query("select new com.humanresources.Vw.EmployeeView(e.id,e.user.firstName,e.user.lastName,e.annualLeave) from Employee e where e.company.id=?1")
    List<EmployeeView> findAllBycompanyId(Long companyId);

    @Query("select e from Employee e where e.company.id in ?1")
    List<Employee> findAllByCompanyIdIn(List<Long> companyIds);


    Optional<Employee> findByUserId(Long userId);


}





