package com.springapp.dao;

import com.springapp.model.Employee;
import com.springapp.model.EmployeeImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EmployeeDao implements DaoInterface<Employee, String> {

	private Session currentSession;
	
	private Transaction currentTransaction;

	public EmployeeDao() {
	}

	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
	
	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	public void persist(Employee entity) {
		getCurrentSession().save(entity);
	}

	public void update(Employee entity) {
		getCurrentSession().update(entity);
	}

	public Employee findById(String id) {
		Employee book = (Employee) getCurrentSession().get(Employee.class, id);
		return book; 
	}

	public void delete(Employee entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Employee> findAll() {
		List<Employee> books = (List<Employee>) getCurrentSession().createQuery("from Employee").list();
		return books;
	}

	public void deleteAll() {
		List<Employee> entityList = findAll();
		for (Employee entity : entityList) {
			delete(entity);
		}
	}

}
