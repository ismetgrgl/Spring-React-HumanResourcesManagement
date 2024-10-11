package com.humanresources.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.humanresources.dto.response.CompaniesForLeaveDTO;
import com.humanresources.dto.response.ResponseDTO;
import com.humanresources.service.CompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;



    @GetMapping("/get-companies")
    public ResponseEntity<ResponseDTO<List<CompaniesForLeaveDTO>>> getCompanies(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        return ResponseEntity.ok(ResponseDTO.<List<CompaniesForLeaveDTO>>builder().code(200).message("Company manager'ın şirketleri gönderildi").data(companyService.getCompanies(jwtToken)).build());
    }

}
