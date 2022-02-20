package com.javatechnolessons.demo.controller;

import com.javatechnolessons.demo.model.Employee;
import com.javatechnolessons.demo.repository.IEmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
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
            Optional<Employee> employee = Optional.ofNullable(iEmployeeJpaRepository.findByEmployeeid(employeeid));
            if(employee.isPresent()){
                return new ResponseEntity<>(employee.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Crear un nuevo empleado.
     * @param employee Objeto Empleado.
     * @return retorna el objeto empleado creado.
     */
    @PostMapping("/post")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        try{
            Employee employeeAux = iEmployeeJpaRepository.save(new Employee(employee.getFirstName(),
                    employee.getLastName(),employee.getEmployeeid(),employee.getRole()));
            return new ResponseEntity<>(employeeAux, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualizar/Modificar empleado según el ID.
     * @param employeeid Corresponde al ID autoincremental de la base de datos.
     * @param employee Objeto empleado con los campos modificados.
     * @return retorna el Objeto con sus datos modificados.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeid, @RequestBody Employee employee){
        try{
            Optional<Employee> employeeData = iEmployeeJpaRepository.findById(employeeid);
            if(employeeData.isPresent()) {
                Employee employeeAux = employeeData.get();
                employeeAux.setFirstName(employee.getFirstName());
                employeeAux.setLastName(employee.getLastName());
                employeeAux.setRole(employee.getRole());
                return new ResponseEntity<>(iEmployeeJpaRepository.save(employeeAux), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Eliminar un Empleado según su ID.
     * @param id Id del empleado correspondiente al de la DB.
     * @return Retorna un mensaje de éxito(OK: Code 200) o el mensaje de la excepción(Code 500).
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id){
        try{
            iEmployeeJpaRepository.deleteById(id);
            return new ResponseEntity<>("El empleado con id: "+id+" ha sido eliminado exitosamente.",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
