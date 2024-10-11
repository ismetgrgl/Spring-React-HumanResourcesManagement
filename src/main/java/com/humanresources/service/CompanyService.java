package com.humanresources.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.humanresources.Vw.CompanyView;
import com.humanresources.Vw.PassiveCompaniesView;
import com.humanresources.dto.response.CompaniesForLeaveDTO;
import com.humanresources.entity.Company;
import com.humanresources.exception.HumanResourcesAppException;
import com.humanresources.mapper.CompanyMapper;
import com.humanresources.repository.CompanyRepository;
import com.humanresources.util.JwtTokenManager;

import java.util.ArrayList;
import java.util.List;

import static com.humanresources.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final JwtTokenManager jwtTokenManager;

    public List<PassiveCompaniesView> getPendingCompanies(boolean isActive) {
        return companyRepository.findByIsActive(isActive);
    }

    public void updateCompanyStatus(Long companyId, boolean isActive) {
        Company company = companyRepository.findById(companyId).orElseThrow(()-> new HumanResourcesAppException(COMPANY_NOT_FOUND));
        company.setActive(isActive);
        companyRepository.save(company);
    }

    public List<CompaniesForLeaveDTO> getCompanies(String jwtToken) {
        Long userId = jwtTokenManager.getUserIdFromToken(jwtToken).orElseThrow(() -> new HumanResourcesAppException(USER_NOT_FOUND));
        List<CompanyView> companyManagersCompanies = companyRepository.findCompanyManagersCompanies(userId);
        if(companyManagersCompanies.isEmpty()) {
            throw new HumanResourcesAppException(COMPANY_NOT_FOUND);
        }

        List<CompaniesForLeaveDTO> companiesForLeaves = new ArrayList<>();
        companyManagersCompanies.forEach(companyView -> {
            companiesForLeaves.add(CompanyMapper.INSTANCE.companyViewToCompaniesForLeaveDTO(companyView));
        });
        return companiesForLeaves;

    }

    public Company findCompanyById(Long companyId) {
        return companyRepository.findById(companyId).orElseThrow(() -> new HumanResourcesAppException(COMPANY_NOT_FOUND));
    }
}

