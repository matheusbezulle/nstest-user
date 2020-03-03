package br.com.nstest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.nstest.user.controller.UserController;
import br.com.nstest.user.entity.HeartTeam;
import br.com.nstest.user.entity.User;
import br.com.nstest.user.repository.UserRepository;

@SpringBootTest
class UserApplicationTests {
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Test
	void createNullUser() {
		assertEquals(userController.create(null).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createWithId() {
		assertEquals(userController.create(getDefaultUser()).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createWithExistentUser() {
		User user = getDefaultUser();
		user.setUserId(null);
		
		when(userRepository.findByEmail(anyString())).thenReturn(new User());
		when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));
		
		assertEquals(userController.create(user).getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void createWithExistentUserErrorThrowException() {
		User user = getDefaultUser();
		user.setUserId(null);
		
		when(userRepository.findByEmail(anyString())).thenReturn(new User());
		doThrow(new RestClientException("")).when(restTemplate).getForEntity(anyString(), any());
		
		assertEquals(userController.create(user).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	void createUser() {
		User user = getDefaultUser();
		user.setUserId(null);
		
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(userRepository.save(any(User.class))).thenReturn(getDefaultUser());
		when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));
		
		assertEquals(userController.create(user).getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	void createUserErrorThrowException() {
		User user = getDefaultUser();
		user.setUserId(null);
		
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(userRepository.save(any(User.class))).thenReturn(getDefaultUser());
		doThrow(new RestClientException("")).when(restTemplate).getForEntity(anyString(), any());
		
		assertEquals(userController.create(user).getStatusCode(), HttpStatus.CREATED);
	}

	
	private static User getDefaultUser() {
		User user = new User();
		user.setUserId(1);
		user.setName("Matheus");
		user.setBirthday(LocalDate.now());
		user.setEmail("matheusbezulle@gmail.com");
		user.setHeartTeam(new HeartTeam(1));
		return user;
	}

}
