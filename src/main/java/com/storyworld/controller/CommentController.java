package com.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.sql.Comment;
import com.storyworld.service.AuthorizationService;
import com.storyworld.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private AuthorizationService authorizationService;

	@GetMapping
	public ResponseEntity<Response<CommentContent>> getCommetsByUser(@RequestHeader("Token") String token) {
		return new ResponseEntity<Response<CommentContent>>(commentService.getByUser(token), HttpStatus.OK);
	}

	@GetMapping("/{idStory}/{page}/{pageSize}")
	public ResponseEntity<Response<CommentContent>> getCommetsByStory(@PathVariable(value = "idStory") Long idStory,
			@PathVariable(value = "page") int page, @PathVariable(value = "pageSize") int pageSize) {
		return new ResponseEntity<Response<CommentContent>>(commentService.get(idStory, page, pageSize), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Response<CommentContent>> saveCommet(@RequestBody Request request) {
		return authorizationService.checkAccess(request)
				? new ResponseEntity<Response<CommentContent>>(commentService.save(request), HttpStatus.OK)
				: new ResponseEntity<Response<CommentContent>>(new Response<CommentContent>(), HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/like")
	public ResponseEntity<Response<CommentContent>> like(@RequestBody Request request) {
		return authorizationService.checkAccess(request)
				? new ResponseEntity<Response<CommentContent>>(commentService.like(request), HttpStatus.OK)
				: new ResponseEntity<Response<CommentContent>>(new Response<CommentContent>(), HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/dislike")
	public ResponseEntity<Response<CommentContent>> dislike(@RequestBody Request request) {
		return authorizationService.checkAccess(request)
				? new ResponseEntity<Response<CommentContent>>(commentService.dislike(request), HttpStatus.OK)
				: new ResponseEntity<Response<CommentContent>>(new Response<CommentContent>(), HttpStatus.UNAUTHORIZED);
	}

	@PutMapping
	public ResponseEntity<Response<CommentContent>> updateCommet(@RequestBody Request request) {
		return authorizationService.checkAccessToComment(request)
				? new ResponseEntity<Response<CommentContent>>(commentService.update(request), HttpStatus.OK)
				: new ResponseEntity<Response<CommentContent>>(new Response<CommentContent>(), HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<CommentContent>> deleteCommet(@PathVariable(value = "id") String _id,
			@RequestHeader("Token") String token) {
		return authorizationService.checkAccessToComment(new Request(token, new Comment(_id)))
				? new ResponseEntity<Response<CommentContent>>(
						commentService.delete(new Request(token, new Comment(_id))), HttpStatus.OK)
				: new ResponseEntity<Response<CommentContent>>(new Response<CommentContent>(), HttpStatus.UNAUTHORIZED);

	}
}
