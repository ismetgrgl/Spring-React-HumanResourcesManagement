package com.humanresources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.humanresources.entity.Shift;

@Repository
public interface ShiftRepository  extends JpaRepository<Shift, Long> {
}
