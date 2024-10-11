package com.humanresources.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.humanresources.dto.request.LeaveRequestDTO;
import com.humanresources.dto.request.LeaveSaveRequestDTO;
import com.humanresources.dto.request.UpdateLeaveStatusRequestDTO;
import com.humanresources.dto.response.PendingLeaveResponseDTO;
import com.humanresources.dto.response.ResponseDTO;
import com.humanresources.service.Leaveservice;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
public class LeaveController {
    private final Leaveservice leaveservice;


    @PostMapping("/save-leave")
    public ResponseEntity<ResponseDTO<Boolean>> saveLeave(@RequestBody LeaveSaveRequestDTO leaveSaveRequestDTO) {
        leaveservice.saveLeave(leaveSaveRequestDTO);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("İzin Başarıyla Tanımlandı").data(Boolean.TRUE).build());
    }


    @PostMapping("/leave-request")
    public ResponseEntity<ResponseDTO<Boolean>> leaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        leaveservice.leaveRequest(leaveRequestDTO);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("İzin isteği gönderildi").build());
    }

    @GetMapping("/get-pending-leaves/{companyId}")
    public ResponseEntity<ResponseDTO<List<PendingLeaveResponseDTO>>> getPendingLeaves(@PathVariable Long companyId) {
        return ResponseEntity.ok(ResponseDTO.<List<PendingLeaveResponseDTO>>builder().code(200).message("Onay bekleyen izinler gönderildi").data(leaveservice.getPendingLeaves(companyId)).build());
    }

    @PutMapping("/update-leave-status")
    public ResponseEntity<ResponseDTO<Boolean>> updateLeaveStatus(@RequestBody UpdateLeaveStatusRequestDTO updateLeaveStatusRequestDTO) {
        leaveservice.updateLeaveStatus(updateLeaveStatusRequestDTO);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("leave status updated").build());
    }



}

