package com.humanresources.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.humanresources.dto.request.SendActivationRequestDTO;
import com.humanresources.dto.response.PendingCompaniesResponseDTO;
import com.humanresources.dto.response.ResponseDTO;
import com.humanresources.service.AdminService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;





    @GetMapping("/get-pending-user")
    public ResponseEntity<ResponseDTO<List<PendingCompaniesResponseDTO>>> getPendingUsers(@RequestHeader("Authorization") String token){
        String jwtToken = token.replace("Bearer ", "");
        return ResponseEntity.ok(ResponseDTO.<List<PendingCompaniesResponseDTO>>builder().code(200).data(adminService.getPendingUsers(jwtToken)).message("Pending User Listesi Gönderildi.").build());
    }




    @PostMapping("/send-activation-mail")
    public ResponseEntity<ResponseDTO<Boolean>> sendActivationMail(@RequestBody SendActivationRequestDTO sendActivationRequestDTO){
        adminService.sendActivationMail(sendActivationRequestDTO);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Aktivasyon Maili Gönderildi").build());
    }


    //önce front'a gönder oradan al, araştır

    @GetMapping("/activate-account")
    public ResponseEntity<ResponseDTO<Boolean>> activateAccount(@RequestParam String token){
        adminService.activateAccount(token);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Hesap Aktive Edildi").build());
    }

}
