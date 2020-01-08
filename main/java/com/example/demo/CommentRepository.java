package com.example.demo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	Page<Comment> findByPostId(Long post, Pageable pageable);
	Optional<Comment> findByIdAndPostId(Long id, Long post);
	
}
