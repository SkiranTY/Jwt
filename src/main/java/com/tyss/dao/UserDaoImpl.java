package com.tyss.dao;

import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tyss.dto.Book;
import com.tyss.dto.User;

@Repository
public class UserDaoImpl implements UserDao {
	@PersistenceUnit
	private EntityManagerFactory factory;

	@Override
	public User register(User user) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		try {
			transaction.begin();
			manager.persist(user);
			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();

		}
		User user1 = manager.find(User.class, user.getId());
		if (user1 == null) {
			return null;
		}
		return user;
	}

	@Override
	public User login(String email, String password) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		String login = "from User where email=:email and password=:password";

		Query query = manager.createQuery(login);
		query.setParameter("email", email);
		query.setParameter("password", password);
		User user = (User) query.getSingleResult();
		if (user == null)
			return null;
		return user;
	}

	@Override
	public boolean changePassword(String email, String password) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		String chgepwd = "from User where email=:email";

		Query query = manager.createQuery(chgepwd);
		query.setParameter("email", email);

		User user = (User) query.getSingleResult();
		if (user != null) {
			transaction.begin();
			user.setPassword(password);
			System.out.println(user.getPassword());
			transaction.commit();
			return true;
		}
		return false;
	}

	@Override
	public boolean userUpdate(User user) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		User user1 = manager.find(User.class, user.getId());
		if (user1 == null) {
			return false;
		}
		transaction.begin();
		user1.setEmail(user.getEmail());
		user1.setName(user.getName());
		user1.setPhno(user.getPhno());
		user1.setRole(user.getRole());
		transaction.commit();
		return true;
	}

	@Override
	public boolean userDelete(int id) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		User user2 = manager.find(User.class, id);
		if (user2 == null) {
			return false;
		}
		transaction.begin();
		manager.remove(user2);
		transaction.commit();
		return true;
	}

	@Override
	public List<User> userGet() {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		String get = "from User";
		Query query = manager.createQuery(get);
		List<User> list = query.getResultList();
		if (list == null) {
			return null;
		}
		return list;
	}

	@Override
	public List<User> searchUser(String name) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		String get = "from User where name like :name";
		Query query = manager.createQuery(get);
		query.setParameter("name", "%" + name + "%");
		List<User> list = query.getResultList();
		if (list == null) {
			return null;
		}
		return list;
	}
}
