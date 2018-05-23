package com.bootcampglobant.userregister;

import com.bootcampglobant.userregister.controller.UserController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserControllerTests {


	UserController controller;

	@Before
	public void creatingController(){
		controller = new UserController();
	}

	@Test
	public void addingAnUser() throws Exception {
		controller.newUser("joe", "Pepe", "1234", "saralavanchy@Gmail.com", "1994-15-04");
		Assert.assertNotNull(controller.getAll());
	}
}
