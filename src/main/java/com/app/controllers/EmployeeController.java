package com.app.controllers;

import com.app.entity.Department;
import com.app.entity.Employee;
import com.app.entity.EventType;
import com.app.repos.DepartmentRepo;
import com.app.repos.EmployeeRepo;
import com.app.response.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.notifications.JmsSenderService;
import com.app.response.BadResponse;
import com.app.response.GoodResponse;

@RestController
public class EmployeeController {

    private final EmployeeRepo repo;
    private final DepartmentRepo repo1;
    private final JmsSenderService jmsSenderService;

    @Autowired
    public EmployeeController(EmployeeRepo repo, DepartmentRepo repo1, JmsSenderService jmsSenderService) {
        this.repo = repo;
        this.repo1 = repo1;
        this.jmsSenderService = jmsSenderService;
    }

    @GetMapping(path = "/employees", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private Iterable<Employee> findAll() {
        return repo.findAll();
    }

    @GetMapping(path = "/add/employee", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ServerResponse add(String name, Long salary, String email, Long departmentId) {
        if (!repo.findByEmail(email).isEmpty()) {
            return new BadResponse("Duplicate email");
        }
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSalary(salary);
        employee.setEmail(email);
        Department dep = repo1.findById(departmentId).get();
        employee.setDepartmentByDepartmentId(dep);
        Employee f = repo.save(employee);
        jmsSenderService.sendEmployeeUpdate(f, EventType.CREATE);
        jmsSenderService.sendEvent(Employee.class, f, EventType.CREATE);
        return new GoodResponse(f);
    }

    @GetMapping(path = "/delete/employee", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ServerResponse delete(long id) {
        Employee employee = repo.findById(id).orElse(null);
        if (employee == null) {
            return new BadResponse("Employee not found");
        }
        if (employee.getDepartmentByDepartmentId() != null) {
            return new BadResponse("Employee has departments");
        }
        repo.delete(employee);

        jmsSenderService.sendEmployeeUpdate(employee, EventType.DELETE);
        jmsSenderService.sendEvent(Employee.class, employee, EventType.DELETE);
        return new GoodResponse();
    }
}
