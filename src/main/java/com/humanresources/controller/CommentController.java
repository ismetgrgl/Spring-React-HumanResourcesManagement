package com.humanresources.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.humanresources.dto.request.CommentSaveRequestDto;
import com.humanresources.dto.response.CommentResponseDto;
import com.humanresources.dto.response.ResponseDTO;
import com.humanresources.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")

public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save-comment")
    public ResponseEntity<ResponseDTO<Boolean>> saveComment(@RequestBody CommentSaveRequestDto dto) {
        commentService.saveComment(dto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Yorum Başarıyla Eklendi.\n Teşekkür Ederiz.").data(Boolean.TRUE).build());
    }

    @GetMapping("/get-comment-list")
    public ResponseEntity<ResponseDTO<List<CommentResponseDto>>> getCommentList(
            @RequestParam int page,
            @RequestParam int size) {
        List<CommentResponseDto> comments = commentService.getAllComment(page, size);
        return ResponseEntity.ok(ResponseDTO.<List<CommentResponseDto>>builder()
                .code(200)
                .message("Yorumlar Listelendi")
                .data(comments)
                .build());
    }
}
