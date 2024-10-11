package com.humanresources.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.humanresources.Vw.UserView;
import com.humanresources.dto.request.ChangePasswordDTO;
import com.humanresources.dto.request.RegisterRequestDto;
import com.humanresources.dto.request.UserLoginRequestDto;
import com.humanresources.dto.request.UserUpdateRequestDto;
import com.humanresources.dto.response.RegisterResponseDto;
import com.humanresources.dto.response.ResponseDTO;
import com.humanresources.entity.Company;
import com.humanresources.entity.CompanyManager;
import com.humanresources.entity.User;
import com.humanresources.entity.enums.UserStatus;
import com.humanresources.exception.ErrorType;
import com.humanresources.exception.HumanResourcesAppException;
import com.humanresources.mapper.CompanyMapper;
import com.humanresources.mapper.UserMapper;
import com.humanresources.repository.CompanyManagerRepository;
import com.humanresources.repository.CompanyRepository;
import com.humanresources.repository.UserRepository;
import com.humanresources.util.CodeGenerator;
import com.humanresources.util.JwtTokenManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JwtTokenManager jwtTokenManager;
    private final CodeGenerator codeGenerator;
    private final CompanyManagerRepository companyManagerRepository;
    private final JavaMailSender mailSender;

    public void saveAdmin(User user) {
        userRepository.save(user);
    }

    public Map<Long,UserView> getUsersByIds(List<Long> companyManagersUserIds) {
        List<UserView> userViewList = userRepository.findAllByIds(companyManagersUserIds);
        Map<Long,UserView> result = userViewList.stream().collect(Collectors.toMap(UserView::getId, userView -> userView));
        return result;
    }
    public void updateUserStatus(Long id, UserStatus userStatus) {
        User user = userRepository.findById(id).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
        user.setUserStatus(userStatus);
        userRepository.save(user);
    }





    public RegisterResponseDto register(RegisterRequestDto dto) {

        //TODO düzenlenecek

        // Password ve rePassword eşitliği kontrol edilir:
        if (!dto.getPassword().equals(dto.getRePassword())) {
            throw new HumanResourcesAppException(ErrorType.PASSWORD_MISMATCH);
        }

        // E-mail daha önce alınmış mı kontrol edilir:
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new HumanResourcesAppException(ErrorType.EMAIL_EXIST);
        }

        //Kontrollerden başarılı bir şekilde geçildiyse dto'dan gelen bilgilerle Company nesnesi oluşturulur.

        Company company = CompanyMapper.INSTANCE.toCompany(dto);
        company.setCompanyName(dto.getCompanyName());
        company.setEmployeeLimitLevel(dto.getEmployeeLimitLevel());
        company.setEmployeeNumberLimit(dto.getEmployeeLimitLevel().getEmployeeLimit());
        company.setSubscriptionPlan(dto.getSubscriptionPlan());

        // RegisterRequestDto'dan User nesnesi oluştur
        User user = UserMapper.INSTANCE.toUser(dto);

        user = userRepository.save(user);
        CompanyManager companyManager = new CompanyManager();
        companyManager.setUser(user);
        companyManager = companyManagerRepository.save(companyManager);
        company.setCompanyManager(companyManager);

        company = companyRepository.save(company);

        RegisterResponseDto response = new RegisterResponseDto();
        response.setMessage("Kayıt Başarılı");
        return response;
    }

    public String Login(UserLoginRequestDto dto){
        Optional<User> optionalByUser = userRepository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if(optionalByUser.isEmpty()){
            throw new HumanResourcesAppException(ErrorType.PASSWORD_MISMATCH);
        }

        if(!optionalByUser.get().getUserStatus().equals(UserStatus.ACTIVE)){
            throw new HumanResourcesAppException(ErrorType.USER_STATUS_NOT_ACTIVE);
        }

        return jwtTokenManager.createToken( optionalByUser.get().getId(), optionalByUser.get().getUserRole() );
    }

    //Şifre Yenileme mail gönderme eklenince düzenlenicek
    public ResponseDTO<Boolean> forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND);
        }
        userOptional.get().setRePasswordCode(codeGenerator.codeGenerator());

        userRepository.save(userOptional.get());

        // Şifre yenileme maili gönderilecek
        String link = "http://localhost:3000/changeMyPassword";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setBcc("bilgeadamworks@gmail.com");
        mailMessage.setSubject("Forgot Password Reset Link");
        mailMessage.setText("Doğrulama Kodunuz :"+ userOptional.get().getRePasswordCode() +" ,şifre yenilemek için linke tıklayın  " +link);
        mailMessage.setTo(email);
        mailSender.send(mailMessage);


        return ResponseDTO.<Boolean>builder()
                .code(200)
                .message("emaile şifre gönderildi. Mailinizi kontrol ediniz.")
                .data(true)
                .build();
    }

    public ResponseDTO<Boolean> resetPasswordCodeControl(ChangePasswordDTO changePasswordDTO){
        Optional<User> userOptional = userRepository.findByRePasswordCode(changePasswordDTO.getCode());
        if (userOptional.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND);
        }

        userOptional.get().setRePasswordCode(null);
        userOptional.get().setPassword(changePasswordDTO.getPassword());
        userRepository.save(userOptional.get());

        return ResponseDTO.<Boolean>builder()
                .code(200)
                .message("Şifre yenileme işlemi başarılı.")
                .data(true)
                .build();
    }
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Boolean userUpdate(UserUpdateRequestDto dto){
        Optional<Long> userId = jwtTokenManager.getUserIdFromToken(dto.getToken());
        //token kontrolü
        if(userId.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.INVALID_TOKEN);
        }
        Optional<User> userOptional = userRepository.findById(userId.get());
        //user var mı kontrolü
        if (userOptional.isPresent()){

            User user = userOptional.get();
            // girilen veriler boş mu kontrolü
            if (!dto.getAvatar().isEmpty()){
                user.setAvatar(dto.getAvatar());
            }
            if(!dto.getFirstName().isEmpty()){
                user.setFirstName(dto.getFirstName());
            }
            if(!dto.getLastName().isEmpty()){
                user.setLastName(dto.getLastName());
            }
            userRepository.save(user);
            return true;
        }else{
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND);
        }
    }
}
