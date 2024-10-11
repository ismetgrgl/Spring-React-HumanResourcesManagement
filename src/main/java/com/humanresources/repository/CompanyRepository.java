package com.humanresources.repository;

import  org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.humanresources.Vw.CompanyView;
import com.humanresources.Vw.PassiveCompaniesView;
import com.humanresources.entity.Company;
import com.humanresources.entity.CompanyManager;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("select new com.humanresources.Vw.PassiveCompaniesView (c.id,c.companyManager.user.id,c.companyName,c.employeeNumberLimit,c.subscriptionPlan) from Company  c where c.isActive=?1")
    List<PassiveCompaniesView> findByIsActive(boolean isActive);

    @Query("select new com.humanresources.Vw.CompanyView(c.id,c.companyName) from Company c where c.companyManager.user.id =?1")
    List<CompanyView> findCompanyManagersCompanies(Long userId);

    List<Company> findAllByCompanyManager(CompanyManager companyManager);
}
