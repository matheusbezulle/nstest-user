package br.com.nstest.user.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.nstest.user.dto.APIResponse;
import br.com.nstest.user.entity.HeartTeam;
import br.com.nstest.user.entity.User;
import br.com.nstest.user.repository.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@PostMapping
	public ResponseEntity<APIResponse> create(@RequestBody User user) {
		if(Objects.nonNull(user) && Objects.nonNull(user.getUserId())) {
			User existingUser = repository.findByEmail(user.getEmail());
			if(Objects.isNull(existingUser)) {
				try {
					user.setHeartTeam(new RestTemplate().getForEntity("http://localhost:8090/heartTeam?id=" + user.getHeartTeam().getId(), HeartTeam.class).getBody());
				} catch (Exception e) {
					System.out.print(e.getMessage());
				}
				return new ResponseEntity<APIResponse>(new APIResponse(Boolean.TRUE, null, repository.save(user)), HttpStatus.CREATED);
			}
			try {
				return new ResponseEntity<APIResponse>(new APIResponse(Boolean.TRUE, null, new RestTemplate().getForEntity("http://localhost:8090/heartTeam?id=" + user.getHeartTeam().getId(), HeartTeam.class).getBody().getCampaigns()), HttpStatus.OK);
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
