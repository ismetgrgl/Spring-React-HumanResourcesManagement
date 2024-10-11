package com.humanresources.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SendActivationRequestDTO {
    private String token;
    private Long userId;
    private String email;
    private Long companyId;
}
