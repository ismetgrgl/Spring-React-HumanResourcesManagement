package com.humanresources.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserUpdateRequestDto {
    private String token;
    private String avatar;
    private String firstName;
    private String lastName;
}
