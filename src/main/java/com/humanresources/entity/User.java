package com.humanresources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.humanresources.entity.enums.UserRole;
import com.humanresources.entity.enums.UserStatus;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    private String firstName;
    private String lastName;

    private String email;
    private String activationCode;
    private String rePasswordCode;
    private String password;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.PENDING;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.COMPANY_MANAGER;


    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
