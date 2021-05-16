package yuki.SpringBootThymeleaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yuki.SpringBootThymeleaf.bean.Employee;
import yuki.SpringBootThymeleaf.service.EmployeeService;

@Controller
@RequestMapping("/employeeController")
public class EmployeeController {

	private EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}

	@GetMapping("/login")
	public String login() {
	  return "fancy-login";
	}

	@GetMapping("/login-error")
	public String loginError() {
	  return "access-denied";
	}

	@GetMapping("/showEmployees")
	public String showEmployees(Model theModel) {

		List<Employee> theEmployees = employeeService.findAll();
		theModel.addAttribute("employees", theEmployees);
		return "employees/list-employees";
	}

	@GetMapping("/showAddForm")
	public String showAddForm(Model theModel) {

		// create model attribute to bind form data
		Employee theEmployee = new Employee();

		theModel.addAttribute("employee", theEmployee);

		return "employees/employee-add-form";
	}

	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {

		// save the employee
		employeeService.save(theEmployee);

		// use a redirect to prevent duplicate submissions
		return "redirect:/employeeController/showEmployees";
	}

	@GetMapping("/showUpdateForm")
	public String showUpdateForm(@RequestParam("employeeId") int theId, Model theModel) {
		// get the employee from the service
		Employee theEmployee = employeeService.findById(theId);

		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);

		// send over to our form
		return "employees/employee-add-form";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {

		// delete the employee
		employeeService.deleteById(theId);

		// redirect to /employees/list
		return "redirect:/employeeController/showEmployees";

	}


}








