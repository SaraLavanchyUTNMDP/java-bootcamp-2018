package com.bootcamp2018.shoppingapi.controller;

import com.bootcamp2018.shoppingapi.Request.UserRequest;
import com.bootcampglobant.userregister.controller.UserService;
import com.bootcampglobant.userregister.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserLogInController {
	@Autowired
	UserService userService;
	@Autowired
	HttpSession session;

	@RequestMapping(value = "{username}/{password}", method = RequestMethod.GET)
	public ResponseEntity<User> Loggin(HttpServletRequest request, @PathVariable("username") String username,
			@PathVariable("password") String passsword) throws Exception {
		session = request.getSession();
		User user = userService.findAnUser(username);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "UserLogInController");
		if(Objects.nonNull(user))
			if(user.getPassword().equals(passsword))
				session.setAttribute("user", user);
			else{
				throw  new  Exception("your password is incorrect");
			}
		else{
			throw  new Exception("you must sign in first");
		}
		return ResponseEntity.accepted().headers(headers).body(user);
	}

	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity SignIn(@RequestBody UserRequest userRequest) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("singIn", "UserLogInController");
		if(Objects.isNull(userService.findAnUser(userRequest.getUsername()))){
			if(userRequest.getPassword().equals(userRequest.getPasswordConfirm())){
				try {
					userService.newUser(userRequest.getName(), userRequest.getUsername(), userRequest.getPassword()
							, userRequest.getEmail(), userRequest.getBirthDate());
					return new ResponseEntity(HttpStatus.CREATED);
				}catch (Exception e){
					e.getMessage();
				}
			}else{
				return  new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity(HttpStatus.IM_USED);

	}
}
