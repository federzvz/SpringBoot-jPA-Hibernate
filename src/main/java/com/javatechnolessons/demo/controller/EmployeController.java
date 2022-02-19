package com.javatechnolessons.demo.controller;

import com.javatechnolessons.demo.model.Employee;
import com.javatechnolessons.demo.repository.IEmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/employe")
public class EmployeController {
    @Autowired
    IEmployeeJpaRepository iEmployeeJpaRepository;

    /**
     * Obtener todos los empleados
     * @param employeID Identificador del empleado.
     * @return Lista de empleados.
     */
    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getAllEmployes(@RequestParam(required = false) String employeID) {
        try {
            List<Employee> employees = new ArrayList<Employee>();
            if(employeID==null){
                iEmployeeJpaRepository.findAll().forEach(employees::add);
            }else{
                employees.add(iEmployeeJpaRepository.findByEmployeeid(employeID));
            }
            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtener empleado por ID.
     * @param employeeid identificador del empleado.
     * @return retorna un objeto Empleado.
     */
    @GetMapping("/get/{employeeid}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("employeeid") String employeeid){
        try{
            Optional<Employee> employee = Optional.ofNullable(iEmployeeJpaRepository.findByEmployeeid(employeeid);
            if(employee.isPresent()){
                return new ResponseEntity<>(employee.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

}
