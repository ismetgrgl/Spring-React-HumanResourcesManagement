package com.humanresources.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.humanresources.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment , Long> {

    Page<Comment> findAll(Pageable pageable);
}
