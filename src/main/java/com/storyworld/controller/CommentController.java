package com.storyworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.storyworld.domain.elastic.CommentContent;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.sql.Comment;
import com.storyworld.service.AuthorizationService;
import com.storyworld.service.CommentService;

@RestController
@RequestMapping(value = "comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping(value = "/{idStory}/{page}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Response<CommentContent>> getCommetsByStory(@PathVariable(value = "idStory") Long idStory,
			@PathVariable(value = "page") int page, @PathVariable(value = "pageSize") int pageSize) {
		return new ResponseEntity<Response<CommentContent>>(commentService.get(idStory, page, pageSize), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response<CommentContent>> saveCommet(@RequestBody Request request) {
		return authorizationService.checkAccess(request)
				? new ResponseEntity<Response<CommentContent>>(commentService.save(request), HttpStatus.OK)
				: new ResponseEntity<Response<CommentContent>>(new Response<CommentContent>(), HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "like", method = RequestMethod.POST)
	public ResponseEntity<Response<CommentContent>> like(@RequestBody Request request) {
		return authorizationService.checkAccess(request)
				? new ResponseEntity<Response<CommentContent>>(commentService.like(request), HttpStatus.OK)
				: new ResponseEntity<Response<CommentContent>>(new Response<CommentContent>(), HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "dislike", method = RequestMethod.POST)
	public ResponseEntity<Response<CommentContent>> dislike(@RequestBody Request request) {
		return authorizationService.checkAccess(request)
				? new ResponseEntity<Response<CommentContent>>(commentService.dislike(request), HttpStatus.OK)
				: new ResponseEntity<Response<CommentContent>>(new Response<CommentContent>(), HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Response<CommentContent>> updateCommet(@RequestBody Request request) {
		return authorizationService.checkAccessToComment(request)
				? new ResponseEntity<Response<CommentContent>>(commentService.update(request), HttpStatus.OK)
				: new ResponseEntity<Response<CommentContent>>(new Response<CommentContent>(), HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response<CommentContent>> deleteCommet(@PathVariable(value = "id") String _id,
			@RequestHeader("Token") String token) {
		return authorizationService.checkAccessToComment(new Request(token, new Comment(_id)))
				? new ResponseEntity<Response<CommentContent>>(
						commentService.delete(new Request(token, new Comment(_id))), HttpStatus.OK)
				: new ResponseEntity<Response<CommentContent>>(new Response<CommentContent>(), HttpStatus.UNAUTHORIZED);

	}
}
