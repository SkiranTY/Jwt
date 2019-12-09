package com.tyss.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.tyss.dto.Book;
import com.tyss.dto.LibraryResponse;
import com.tyss.dto.User;
import com.tyss.services.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;

	@PostMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse registerUser(@RequestBody User user) {
		System.out.println(user.getRole());
		// Length of your password as I have choose
		// here to be 8
		int length = 10;
		// System.out.println(AutoGeneratePassword.geek_Password(length));

		char[] ch = AutoGeneratePassword.geek_Password(length);
		String p = new String(ch);
		user.setPassword(p);
		LibraryResponse response = new LibraryResponse();
		User u1 = service.register(user);
		if (u1 != null) {
			response.setStatusCode(201);
			response.setMessage("success");
			response.setDescription("data  successfully stored..");
			response.setUser1(u1);
			return response;
		} else {
			response.setStatusCode(400);
			response.setMessage("failure");
			response.setDescription("data not successfully stored..");
			return response;
		}
	}

	@PostMapping(path = "/user/{email}/{password}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse login(HttpSession session, @PathVariable("email") String email,
			@PathVariable("password") String password) {
		LibraryResponse response = new LibraryResponse();
		User user = service.login(email, password);
		if (user != null) {
			session.setAttribute("user", user);
			response.setStatusCode(201);
			response.setMessage("success");
			response.setDescription("login  successfully ..");
			response.setUser1(user);
			return response;
		} else {
			response.setStatusCode(400);
			response.setMessage("failure");
			response.setDescription("login not successfully ..");
			return response;
		}
	}

	@PutMapping(path = "/user/{email}/{password}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse changePassword(@PathVariable("email") String email,
			@PathVariable("password") String password) {
		LibraryResponse response = new LibraryResponse();
		if (service.changePassword(email, password)) {
			response.setStatusCode(201);
			response.setMessage("success");
			response.setDescription("password changed  successfully ..");
			return response;
		} else {
			response.setStatusCode(400);
			response.setMessage("failure");
			response.setDescription("password changed not successfully ..");
			return response;
		}
	}

	@PutMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse updateRegister(@RequestBody User user) {
		LibraryResponse response = new LibraryResponse();
		if (service.userUpdate(user)) {
			response.setStatusCode(201);
			response.setMessage("success");
			response.setDescription("data  successfully updated..");
		} else {
			response.setStatusCode(400);
			response.setMessage("failure");
			response.setDescription("data not successfully updated..");
		}
		return response;
	}

	@DeleteMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse deleteUser(@PathVariable("id") int uId) {
		LibraryResponse response = new LibraryResponse();
		if (service.userDelete(uId)) {
			response.setStatusCode(201);
			response.setMessage("success");
			response.setDescription("data  successfully deleted..");
		} else {
			response.setStatusCode(400);
			response.setMessage("failure");
			response.setDescription("data not successfully deleted..");
		}
		return response;
	}

	@GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse getAll() {
		LibraryResponse response = new LibraryResponse();
		List<User> list = service.userGet();
		if (list == null) {
			response.setStatusCode(400);
			response.setMessage("failure");
			response.setDescription("data not successfully stored..");
		} else {
			response.setStatusCode(201);
			response.setMessage("success");
			response.setDescription("data  successfully stored..");
			response.setUser(list);
		}
		return response;
	}

	@GetMapping(path = "/user/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse searchBook1(@PathVariable("name") String name) {
		LibraryResponse response = new LibraryResponse();
		List<User> list = service.searchUser(name);
		if (list == null) {
			response.setStatusCode(400);
			response.setMessage("failure");
			response.setDescription("data not successfully access..");
		} else {
			response.setStatusCode(201);
			response.setMessage("success");
			response.setDescription("data  successfully accessed..");
			response.setUser(list);
		}
		return response;
	}
}
