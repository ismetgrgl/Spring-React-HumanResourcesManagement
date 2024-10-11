package com.humanresources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.humanresources.entity.CompanyManager;

import java.util.Optional;

@Repository
public interface CompanyManagerRepository extends JpaRepository<CompanyManager, Long> {
    Optional<CompanyManager> findByUserId(Long userId);

}
