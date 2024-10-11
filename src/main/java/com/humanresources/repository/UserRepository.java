package com.humanresources.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.humanresources.Vw.UserView;
import com.humanresources.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select new com.humanresources.Vw.UserView (u.id,u.firstName,u.lastName,u.email,u.userStatus,u.userRole) from User u where u.id in :companyManagersUserIds")
    List<UserView> findAllByIds(List<Long> companyManagersUserIds);
  
  
    Boolean existsByEmail(String email);
    Optional<User> findOptionalByEmailAndPassword (String username, String password);



    Optional<User> findUserByEmail(String email);

    Optional<User> findByRePasswordCode(String code);



}

