package com.humanresources.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.humanresources.dto.request.ShiftRequestDto;
import com.humanresources.dto.response.ResponseDTO;
import com.humanresources.dto.response.ShiftResponseDto;
import com.humanresources.service.ShiftService;

import java.util.List;

@RestController
@RequestMapping("/shift")
@RequiredArgsConstructor
public class ShiftController {


    private final ShiftService shiftService;

    @PostMapping("/assignShifts")
    public ResponseEntity<ResponseDTO<Boolean>> assignShifts(@RequestBody ShiftRequestDto dto) {
        shiftService.assignShifts(dto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Vardiya Başarıyla Tanımlandı").data(Boolean.TRUE).build());
    }

    @GetMapping("/getAllShifts")
    public ResponseEntity<ResponseDTO<List<ShiftResponseDto>>> getAllShifts() {
        return ResponseEntity.ok(ResponseDTO.<List<ShiftResponseDto>>builder().code(200).message("Tüm Vardiyalar Getirildi").data(shiftService.getAllShifts()).build());
    }

}
