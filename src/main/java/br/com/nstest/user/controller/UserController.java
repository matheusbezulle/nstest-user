package br.com.nstest.user.controller;

import java.util.Arrays;
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
import br.com.nstest.user.entity.Campaign;
import br.com.nstest.user.entity.User;
import br.com.nstest.user.repository.UserRepository;
import br.com.nstest.user.utils.Constants;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@PostMapping
	public ResponseEntity<APIResponse> create(@RequestBody User user) {
		if(Objects.nonNull(user) && Objects.isNull(user.getUserId())) {
			if(Objects.isNull(repository.findByEmail(user.getEmail()))) {
				try {
					user.getHeartTeam().setCampaigns(Arrays.asList(new RestTemplate().getForEntity(Constants.campaignAPIURL + "/campaign/findByHeartTeam?heartTeamId=" + user.getHeartTeam().getId(), Campaign[].class).getBody()));
				} catch (Exception e) {
					return new ResponseEntity<APIResponse>(new APIResponse(Boolean.TRUE, null, repository.save(user)), HttpStatus.CREATED);
				}
				return new ResponseEntity<APIResponse>(new APIResponse(Boolean.TRUE, null, repository.save(user)), HttpStatus.CREATED);
			}
			try {
				return new ResponseEntity<APIResponse>(new APIResponse(Boolean.FALSE, Constants.userExistsMessage, new RestTemplate().getForEntity(Constants.campaignAPIURL + "/campaign/findByHeartTeam?heartTeamId=" + user.getHeartTeam().getId(), Campaign[].class).getBody()), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
