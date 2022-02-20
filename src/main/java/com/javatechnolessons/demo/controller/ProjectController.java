package com.javatechnolessons.demo.controller;

import com.javatechnolessons.demo.model.Employee;
import com.javatechnolessons.demo.model.Project;
import com.javatechnolessons.demo.repository.IProjectJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    IProjectJpaRepository iProjectJpaRepository;

    /**
     * Obtener todos los proyectos.
     * @return Retorna una lista de todos los proyectos.
     */
    @GetMapping("/get")
    public ResponseEntity<List<Project>> getAllProjects(){
        try {
            List<Project> projects = new ArrayList<Project>();

            iProjectJpaRepository.findAll().forEach(projects::add);

            if (projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtener Proyecto en especifico según su identificador de DB.
     * @param projectID ID único del proyecto.
     * @return retorna si existe el objeto Proyecto.
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Project> getProjectByID(@PathVariable("id") Long projectID){
        try{
            Optional<Project> project = (iProjectJpaRepository.findById(projectID));
            if(project.isPresent()){
                return new ResponseEntity<>(project.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Crear nuevo Proyecto.
     * @param project Objeto proyecto.
     * @return retorna el objeto creado.
     */
    @PostMapping("/post")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        try{
            Project projectAux = iProjectJpaRepository.save(new Project(project.getName()));
            return new ResponseEntity<>(projectAux, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualizar/Modificar proyecto según el ID.
     * @param projectId Corresponde al ID autoincremental de la base de datos.
     * @param project Objeto proyecto con los campos modificados.
     * @return retorna el Objeto con sus datos modificados.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") Long projectId, @RequestBody Project project){
        try{
            Optional<Project> projectData = iProjectJpaRepository.findById(projectId);
            if(projectData.isPresent()) {
                Project projectAux = projectData.get();
                projectAux.setName(project.getName());
                return new ResponseEntity<>(iProjectJpaRepository.save(projectAux), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Eliminar un proyecto según el ID especificado por parámetro.
     * @param id número identificador del proyecto.
     * @return retorna un mensaje de éxito si es el caso, sino retorna un código 500 y la especificación del error.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id){
        try{
            iProjectJpaRepository.deleteById(id);
            return new ResponseEntity<>("El proyecto con id: "+id+" ha sido eliminado exitosamente.",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
