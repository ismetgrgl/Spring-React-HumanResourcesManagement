package com.humanresources.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.humanresources.repository.CompanyManagerRepository;

@Service
@RequiredArgsConstructor
public class CompanyManagerService {
    private final CompanyManagerRepository companyManagerRepository;




}
