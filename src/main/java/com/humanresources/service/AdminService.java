package com.humanresources.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.humanresources.Vw.PassiveCompaniesView;
import com.humanresources.Vw.UserView;
import com.humanresources.dto.request.SendActivationRequestDTO;
import com.humanresources.dto.response.PendingCompaniesResponseDTO;
import com.humanresources.entity.enums.UserStatus;
import com.humanresources.exception.ErrorType;
import com.humanresources.exception.HumanResourcesAppException;
import com.humanresources.util.CodeGenerator;
import com.humanresources.util.JwtTokenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;
    private final CompanyService companyService;
    private final JavaMailSender mailSender;
    private final CodeGenerator codeGenerator;
    private final JwtTokenManager jwtTokenManager;



    public List<PendingCompaniesResponseDTO> getPendingUsers(String token) {
        adminCheck(token);

        List<PassiveCompaniesView> pendingCompanies = companyService.getPendingCompanies(false); //Pasif şirket listesi
        List<Long> companyManagersUserIds = pendingCompanies.stream().map(PassiveCompaniesView::getCompanyManagersUserId).distinct().collect(Collectors.toList()); //pasif şirketlerin manager'larının user id'leri,
        Map<Long, UserView> usersByIds = userService.getUsersByIds(companyManagersUserIds); //pasif şirketlerin manager'larınınn user view'ları

        List<PendingCompaniesResponseDTO> pendingCompaniesResponseDTOList = new ArrayList<>();

        pendingCompanies.forEach(passiveCompaniesView -> {

            pendingCompaniesResponseDTOList.add(PendingCompaniesResponseDTO.builder()
                            .userId(passiveCompaniesView.getCompanyManagersUserId())
                            .firstName(usersByIds.get(passiveCompaniesView.getCompanyManagersUserId()).getFirstName())
                            .lastName(usersByIds.get(passiveCompaniesView.getCompanyManagersUserId()).getLastName())
                            .email(usersByIds.get(passiveCompaniesView.getCompanyManagersUserId()).getEmail())
                            .userStatus(usersByIds.get(passiveCompaniesView.getCompanyManagersUserId()).getUserStatus())
                            .userRole(usersByIds.get(passiveCompaniesView.getCompanyManagersUserId()).getUserRole())
                            .companyId(passiveCompaniesView.getId())
                            .companyName(passiveCompaniesView.getCompanyName())
                            .numberOfEmployees(passiveCompaniesView.getEmployeeNumberLimit())
                            .subscriptionPlan(passiveCompaniesView.getSubscriptionPlan())

                    .build());
        });

        return pendingCompaniesResponseDTOList;
    }

    private void adminCheck(String token) {
        String userRole = jwtTokenManager.getRoleFromToken(token).orElseThrow(() -> new HumanResourcesAppException(ErrorType.INVALID_TOKEN));
        if(!Objects.equals(userRole, "ADMIN")){
            throw new HumanResourcesAppException(ErrorType.UNAUTHORIZED_REQUEST);
        }
    }


    public void sendActivationMail(SendActivationRequestDTO sendActivationRequestDTO) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            String Token = jwtTokenManager.createToken(sendActivationRequestDTO.getUserId(), sendActivationRequestDTO.getEmail(), sendActivationRequestDTO.getCompanyId()).orElseThrow(() -> new HumanResourcesAppException(ErrorType.TOKEN_CREATION_FAILED));
            String activationLink = "http://localhost:9090/admin/activate-account?token=" + Token;

            mailMessage.setTo(sendActivationRequestDTO.getEmail());
            mailMessage.setSubject("Activation Mail");
            mailMessage.setText("Your Activation Link : " + activationLink);
            mailMessage.setBcc("ismetmustafa068@gmail.com");
            mailSender.send(mailMessage);

    }


    public void activateAccount(String token) {
        Long userId = jwtTokenManager.getUserIdFromToken(token).orElseThrow(() -> new HumanResourcesAppException(ErrorType.INVALID_TOKEN));
        String email = jwtTokenManager.getEmailFromToken(token).orElseThrow(() -> new HumanResourcesAppException(ErrorType.INVALID_TOKEN)); //Gerekli mi?
        Long companyId = jwtTokenManager.getCompanyIdFromToken(token).orElseThrow(() -> new HumanResourcesAppException(ErrorType.INVALID_TOKEN));

        userService.updateUserStatus(userId, UserStatus.ACTIVE);
        companyService.updateCompanyStatus(companyId,true);



    }
}
