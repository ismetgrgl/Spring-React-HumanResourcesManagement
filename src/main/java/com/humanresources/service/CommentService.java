package com.humanresources.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.humanresources.dto.request.CommentSaveRequestDto;
import com.humanresources.dto.response.CommentResponseDto;
import com.humanresources.entity.Comment;
import com.humanresources.entity.Company;
import com.humanresources.entity.CompanyManager;
import com.humanresources.entity.enums.CommentStatus;
import com.humanresources.exception.ErrorType;
import com.humanresources.exception.HumanResourcesAppException;
import com.humanresources.repository.CommentRepository;
import com.humanresources.repository.CompanyManagerRepository;
import com.humanresources.repository.CompanyRepository;
import com.humanresources.util.JwtTokenManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CompanyManagerRepository companyManagerRepository;
    private final CompanyRepository companyRepository;
    private final JwtTokenManager  jwtTokenManager;

    public void saveComment(CommentSaveRequestDto dto) {
        Long userId = jwtTokenManager.getUserIdFromToken(dto.getToken()).orElseThrow(() -> new HumanResourcesAppException(ErrorType.TOKEN_ARGUMENT_NOTVALID));
        Optional<CompanyManager> companyManager = companyManagerRepository.findByUserId(userId);
        if (companyManager.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND);
        }
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow(() -> new HumanResourcesAppException(ErrorType.COMPANY_NOT_FOUND));
        commentRepository.save(Comment
                .builder()
                .company(company)
                .companyManager(companyManager.get())
                .content(dto.getContent())
                .commentStatus(CommentStatus.APPROVED)
                .build());
    }

    public List<CommentResponseDto> getAllComment(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentPage = commentRepository.findAll(pageable);

        return commentPage.getContent().stream()
                .map(comment -> CommentResponseDto.builder()
                        .companyName(comment.getCompany().getCompanyName())
                        .companyManagerFirstName(comment.getCompanyManager().getUser().getFirstName())
                        .companyManagerLastName(comment.getCompanyManager().getUser().getLastName())
                        .companyManagerAvatar(comment.getCompanyManager().getUser().getAvatar())
                        .content(comment.getContent())
                        .build())
                .collect(Collectors.toList());
    }

}
