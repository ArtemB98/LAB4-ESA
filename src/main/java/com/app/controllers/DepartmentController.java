package com.app.controllers;

import com.app.entity.Department;
import com.app.entity.EventType;
import com.app.repos.DepartmentRepo;
import com.app.response.BadResponse;
import com.app.response.GoodResponse;
import com.app.response.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.notifications.JmsSenderService;

@RestController
public class DepartmentController {

    private final DepartmentRepo repo;
    private final JmsSenderService jmsSenderService;

    @Autowired
    public DepartmentController(DepartmentRepo repo,
                                JmsSenderService jmsSenderService) {
        this.repo = repo;
        this.jmsSenderService = jmsSenderService;
    }

    @GetMapping(path = "/departments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private Iterable<Department> findAll() {
        return repo.findAll();
    }

    @GetMapping(path = "/add/department", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ServerResponse add(String name) {
        Department department = new Department();
        department.setName(name);

        Department newDepartment = repo.save(department);
        jmsSenderService.sendEvent(Department.class, newDepartment, EventType.CREATE);
        return new GoodResponse(newDepartment);
    }

    @GetMapping(path = "/delete/department", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ServerResponse delete(Long id) {
        Department department = repo.findById(id).orElse(null);
        if (department == null) {
            return new BadResponse("Department not found");
        }
        repo.delete(department);
        jmsSenderService.sendEvent(Department.class, department, EventType.DELETE);
        return new GoodResponse();
    }
}

