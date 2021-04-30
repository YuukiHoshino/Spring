package yuki.SpringBootThymeleaf.service;

import java.util.List;

import yuki.SpringBootThymeleaf.bean.Employee;

public interface EmployeeService {

	public List<Employee> findAll();

	public Employee findById(int theId);

	public void save(Employee theEmployee);

	public void deleteById(int theId);

}
