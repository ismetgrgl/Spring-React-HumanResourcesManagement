package com.humanresources.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.humanresources.dto.request.*;
import com.humanresources.dto.response.ResponseDTO;
import com.humanresources.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<Boolean>> register(@RequestBody @Valid RegisterRequestDto dto){
        userService.register(dto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Kayıt Başarılı").data(true).build());
    }

    @PostMapping("/edit-profile")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> userUpdate(@RequestBody UserUpdateRequestDto dto){
        userService.userUpdate(dto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().message("Güncelleme Başarılı").code(200).data(true).build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> login(@RequestBody UserLoginRequestDto dto){

        return ResponseEntity.ok(ResponseDTO.<String>builder().message("Giriş Başarılı").code(200).data(userService.Login(dto)).build());
    }

    @PostMapping("/forgot-password") //Sonra
    public ResponseEntity<ResponseDTO<Boolean>> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO){

        return ResponseEntity.ok(userService.forgotPassword(forgotPasswordDTO.getEmail()));
    }

    @PostMapping("/reset-password") //Sonra
    public ResponseEntity<ResponseDTO<Boolean>> resetPasswordCodeControl(@RequestBody ChangePasswordDTO changePasswordDTO){

        return ResponseEntity.ok(userService.resetPasswordCodeControl(changePasswordDTO));
    }

}
