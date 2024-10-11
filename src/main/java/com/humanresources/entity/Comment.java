package com.humanresources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.humanresources.entity.enums.CommentStatus;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private CompanyManager companyManager;
    @OneToOne
    private Company company;
    private String content;
    private CommentStatus commentStatus;

    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
