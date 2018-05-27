package com.bootcampglobant.userregister.service;

import com.bootcampglobant.userregister.controller.UserService;
import com.bootcampglobant.userregister.models.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping(value = "/users/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	/** the controller that will connect the front with the repository.
	 *
	 */
	@Autowired
	private UserService userService;

	/** to add the user
	 *
	 * @param name String
	 * @param username String
	 * @param password String
	 * @param email String
	 * @param birthDate String
	 */
	@RequestMapping(method = RequestMethod.POST)
	public  void addUser(@RequestParam("name") String name, @RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("birthDate") String birthDate){
		try {
			userService.newUser(name, username, password, email, birthDate);
		}catch(Exception e){
			e.getMessage();
		}
	}

	/** to find all the users register in the system
	 *
	 * @return a List of Users
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<User>getAllUsers(){
		return userService.getAll();
	}

	/**  to find an user by its username
	 *
	 * @param username String
	 * @return an User
	 */
	@RequestMapping(value ="{username}" ,method = RequestMethod.GET)
	public User getByUsername(@PathVariable String username){
		return userService.findAnUser(username);
	}

	/** to find an user by its name
	 *
	 * @param name String
	 * @return an User
	 */
	@RequestMapping(value = "/name",method = RequestMethod.GET)
	public User getByName(@RequestParam("name") String name){
		return userService.findAnUserByName(name);
	}

	/** after find the user by its username, it will delete it
	 *
	 * @param username String
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteAnUser(@RequestParam("username") String username){
		userService.deleteUser(username);
	}

	/** to change information of an user.
	 *
	 * @param name String
	 * @param username String
	 * @param email String
	 * @param password String
	 * @param birthDate String
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public void update(@RequestParam("name") String name, @RequestParam("username") String username, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("birthdate") String birthDate){
		userService.upadateUser(username,name,email,password,birthDate);
	}


}
