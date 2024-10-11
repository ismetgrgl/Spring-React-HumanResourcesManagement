package com.humanresources.Vw;

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
public class UserView {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private UserRole userRole;
}
