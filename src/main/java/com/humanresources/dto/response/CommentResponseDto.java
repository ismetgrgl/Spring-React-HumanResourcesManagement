package com.humanresources.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommentResponseDto {
    String companyName;
    String companyManagerFirstName;
    String companyManagerLastName;
    String companyManagerAvatar;
    String content;

}
