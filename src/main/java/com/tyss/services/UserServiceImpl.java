package com.tyss.services;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.tyss.dao.UserDao;
import com.tyss.dto.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;

	@Override
	public User register(User user) {
		// TODO Auto-generated method stub
		return dao.register(user);
	}

	@Override
	public User login(String email, String password) {
		// TODO Auto-generated method stub
		return dao.login(email, password);
	}

	@Override
	public boolean changePassword(String email, String password) {
		// TODO Auto-generated method stub
		return dao.changePassword(email, password);
	}

	@Override
	public boolean userUpdate(User user) {
		// TODO Auto-generated method stub
		return dao.userUpdate(user);
	}

	@Override
	public boolean userDelete(int Id) {
		// TODO Auto-generated method stub
		return dao.userDelete(Id);
	}

	@Override
	public List<User> userGet() {
		// TODO Auto-generated method stub
		return dao.userGet();
	}

	@Override
	public List<User> searchUser(String name) {
		
		return dao.searchUser(name);
	}

	

}
