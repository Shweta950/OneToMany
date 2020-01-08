package com.example.demo;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

	@Autowired
	private PostRepository postRepo;
	
	@GetMapping("/posts")
	public Page<Post> getAllTitle(@RequestParam Optional<String> title, @RequestParam Optional<Integer> page,@RequestParam Optional<String> sortBy){
//		return postRepo.findAll(pageable);
		return postRepo.findByTitle(title.orElse("_"),PageRequest.of(page.orElse(0),3, Sort.by("title")));
	}
	

	
	@PostMapping("/addposts")
	public Post createPost(@Valid @RequestBody Post post) {
		return  postRepo.save(post);
	}
	
	 @PutMapping("/posts/{postId}")
	public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
		return postRepo.findById(postId).map(post -> {
			post.setTitle(postRequest.getTitle());
			post.setDescription(postRequest.getDescription());
			post.setContent(postRequest.getContent());
			
			return postRepo.save(post);
		}).orElseThrow(() -> new NullPointerException("PostId " + postId + " not found"));
	}
	 
	 @DeleteMapping("/posts/{postId}")
	 public String deletePost(@PathVariable Long postId){
		 return postRepo.findById(postId).map(post ->{
			 postRepo.delete(post);
			 return "delete post";
		 }).orElseThrow(() -> new NullPointerException("PostId " + postId + " not found"));
	 }
}
