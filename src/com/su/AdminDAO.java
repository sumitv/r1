package com.su;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class AdminDAO {

	static Configuration config;
	static SessionFactory sessionFactory;
	static Session session;
	static Transaction transaction;

	public static void initAdminDAO() {
		config = new Configuration();
		config.configure("com/su/hibernate.cfg.xml");
		sessionFactory = config.buildSessionFactory();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	public static void commit() {
		transaction.commit();
		session.close();
	}

	public static Admin authenticate(Admin a) {
		initAdminDAO();
		System.out.print(getAll().size());
		if (getAll().size() == 0) {
			register(a);
			return a;
		}

		Criteria criteria = session.createCriteria(Admin.class);
		criteria.add(Restrictions.eq("username", a.getUsername()));
		criteria.add(Restrictions.eq("password", a.getPassword()));

		Admin admin = (Admin) criteria.uniqueResult();

		session.close();
		return admin;
	}

	public static void update(String newUsername, String newPassword) {
		initAdminDAO();
		Admin admin = (Admin) session.get(Admin.class, 1);
		admin.setUsername(newUsername);
		admin.setPassword(newPassword);
		session.update(admin);
		commit();
	}

	public static List<Admin> getAll() {
		List<Admin> list = new ArrayList<Admin>();
		list = session.createCriteria(Admin.class).list();
		return list;
	}

	public static void register(Admin admin) {
		session.save(admin);
		session.flush();
		commit();
	}

	public static boolean firstTime() {
		initAdminDAO();
		System.out.print(getAll().size());
		if (getAll().size() == 0) {
			session.close();
			return true;
		}
		session.close();
		return false;
	}
}