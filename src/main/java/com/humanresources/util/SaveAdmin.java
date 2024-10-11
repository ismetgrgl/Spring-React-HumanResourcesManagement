package com.humanresources.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.humanresources.entity.User;
import com.humanresources.entity.enums.UserRole;
import com.humanresources.entity.enums.UserStatus;
import com.humanresources.service.UserService;

@Component
@RequiredArgsConstructor
public class SaveAdmin {
    private final UserService userService;

   @PostConstruct
    public void saveAdmin(){
        userService.saveAdmin(User.builder().email("admin@example.com").password("123456789").userRole(UserRole.ADMIN).userStatus(UserStatus.ACTIVE).build()); //mail ve şifresi ortam değişkenlerine eklenerek kodlarda gizlenecek
    }
}
