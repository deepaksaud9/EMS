package com.springboot.controller;

import com.springboot.exception.ResourceNotFoundException;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {


    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    //build Create Employee REST API
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee)
    {
        return employeeRepository.save(employee);
    }

    //build get employee by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        Employee employee =employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee not exist with id: "+ id));
        return ResponseEntity.ok(employee);
    }
    //update employee REST API
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails){
         Employee updateEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee not exist with id:" +id));

         updateEmployee.setFirstName(employeeDetails.getFirstName());
         updateEmployee.setLastName(employeeDetails.getLastName());
         updateEmployee.setEmailId(employeeDetails.getEmailId());

         employeeRepository.save(updateEmployee);

         return ResponseEntity.ok(updateEmployee);
    }

    //built delete employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id)
    {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException( "Employee Not Exist id: "+ id));
        employeeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}


