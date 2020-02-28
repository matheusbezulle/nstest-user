package br.com.nstest.user.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nstest.user.entity.User;
import br.com.nstest.user.repository.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user) {
		if(Objects.nonNull(user) && Objects.nonNull(user.getUserId())) {
			
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
