package com.app.controllers;

import com.app.entity.Department;
import com.app.entity.Employee;
import com.app.repos.DepartmentRepo;
import com.app.repos.EmployeeRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@Controller
@RequestMapping("/xml")
public class XmlController {

    private final DepartmentRepo departmentRepo;
    private final EmployeeRepo employeeRepo;

    @Autowired
    public XmlController(DepartmentRepo departmentRepo, EmployeeRepo employeeRepo) {
        this.departmentRepo = departmentRepo;
        this.employeeRepo = employeeRepo;
    }

    @ResponseBody
    @GetMapping(path = "/employees", produces = MediaType.APPLICATION_XML_VALUE)
    private ModelAndView getEmployees() throws JsonProcessingException {
        Iterable<Employee> list =  employeeRepo.findAll();
        return getModelAndView(list, "employeeXSL");
    }

    @ResponseBody
    @GetMapping(path = "/departments", produces = MediaType.APPLICATION_XML_VALUE)
    private ModelAndView getDepartments() throws JsonProcessingException{
        Iterable<Department> list =  departmentRepo.findAll();
        return getModelAndView(list, "departmentXSL");
    }

    private ModelAndView getModelAndView(Iterable<?> list, String viewName) throws JsonProcessingException {
        String str = new XmlMapper().writeValueAsString(list);
        ModelAndView mod = new ModelAndView(viewName);
        Source src = new StreamSource(new StringReader(str));
        mod.addObject("ArrayList", src);
        return mod;
    }
}
