package com.humanresources.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.humanresources.entity.enums.EmployeeLimitLevel;
import com.humanresources.entity.enums.SubscriptionPlan;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegisterRequestDto {
    @NotBlank(message = "username boş geçilemez.")
    String firstName;
    @NotBlank(message = "username boş geçilemez.")
    String lastName;
    @Size(min = 5, max = 15, message = "telefon numarası 10 haneli olmak zorundadır.")
    String phone;
    @Email(message = "Geçerli bir eposta adresi giriniz.")
    String email;
    @NotBlank(message = "company ismi boş geçilemez.")
    String companyName;
    private EmployeeLimitLevel employeeLimitLevel;

    SubscriptionPlan subscriptionPlan;
    @NotBlank(message = "password boş geçilemez.")
    @Size(min = 8, max = 20, message = "password min 8 - max 20 karakter olabilir.")
    String password;
    @NotBlank(message = "re-password boş geçilemez.")
    @Size(min = 8, max = 20, message = "re-password min 8 - max 20 karakter olabilir.")
    String rePassword;
}
