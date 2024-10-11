package com.humanresources.Vw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.humanresources.entity.enums.SubscriptionPlan;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PassiveCompaniesView {
    private Long id; //CompanyId
    private Long companyManagersUserId;
    private String companyName;
    private Integer employeeNumberLimit;
    private SubscriptionPlan subscriptionPlan;

}
