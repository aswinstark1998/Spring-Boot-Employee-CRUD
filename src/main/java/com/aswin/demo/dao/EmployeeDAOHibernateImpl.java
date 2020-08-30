package com.aswin.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aswin.demo.entity.Employee;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

	// define field for entity manager
	private EntityManager entityManager;

	// setup constructor injection
	@Autowired
	public EmployeeDAOHibernateImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Employee> findAll() {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		//JPA Alternative: Session is not required, if JPA is used
		
		//Create a query 
		Query<Employee> theQuery = currentSession.createQuery("from Employee", Employee.class); 
			//JPA Alternative for Query: import from javax persistence
			// Query query = entityManager.createQuery("from Employee");
		
		//execute query and get result list
		List<Employee> employees = theQuery.getResultList();
			//JPA Alternative: employees = query.getResults();
		
		//return the results
		return employees;
	}
	
	@Override
	public Employee findById(int theId) {
		// get the current Hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		//JPA Alternative: Session is not required, if JPA is used
		
		
		// Create the query to get the employee
		Employee employee = currentSession.get(Employee.class, theId);
		
		//JPA Alternative
		/*
		 * Employee employee = entityManager.find(Employee.class, theId);
		 */
		
		//Return the results
		return employee;
	}

	@Override
	public void save(Employee theEmployee) {
		// get the current Hibernate Session
		Session currentSession = entityManager.unwrap(Session.class);
		
		/*
		 * JPA Alternative
		 * Employee dbEmployee = entityManager.merge(theEmployee)
		 * ====== NOTE: if(id==0) then insert/save else update
		 * theEMployee.setId(dbEmployee.getId())
		 */
		
		// Save the employee using hibernate
		currentSession.saveOrUpdate(theEmployee);
	}

	@Override
	public void deleteById(int theId) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		Employee employeeToBeDeleted = findById(theId);
		currentSession.delete(employeeToBeDeleted);
		
//		Query theQuery = currentSession.createQuery("from Employee where id=:employeeId");
//		theQuery.setParameter("employeeId", theId);
//		theQuery.executeUpdate();
		
		/*
		 * JPA Alternative
		 * Query theQuery = entityManager.createQuery("delete from Employee where id=:employeeId");
		 * theQuery.setParameter("employeeId", theId);
		 * theQuery.executeUpdate();
		 */
	}
}
