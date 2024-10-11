package com.humanresources.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.humanresources.entity.enums.SubscriptionPlan;
import com.humanresources.entity.enums.UserRole;
import com.humanresources.entity.enums.UserStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PendingCompaniesResponseDTO {
    //Company manager bilgileri
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private UserRole userRole;


    //Company manager'a ait şirketin bilgileri (onay sürecinde bakılmak istenebileceğini düşündüm.)
    private Long companyId;
    private String companyName;
    private Integer numberOfEmployees;
    private SubscriptionPlan subscriptionPlan;
}
