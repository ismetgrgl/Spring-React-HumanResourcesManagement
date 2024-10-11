package com.humanresources.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.humanresources.service.CompanyManagerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company-manager")
public class CompanyManagerController {
    private final CompanyManagerService companyManagerService;

}
