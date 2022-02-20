package com.javatechnolessons.demo.controller;

import com.javatechnolessons.demo.model.Role;
import com.javatechnolessons.demo.repository.IRoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    IRoleJpaRepository iRoleJpaRepository;

    /**
     * Obtener todos los roles.
     * @return Se retorna una lista de roles.
     */
    @GetMapping("/get")
    public ResponseEntity<List<Role>> getAllRoles(){
        try {
            List<Role> roles = new ArrayList<Role>();

            iRoleJpaRepository.findAll().forEach(roles::add);

            if (roles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Se obtiene un Rol en específico según su ID.
     * @param roleID Identificador numérico del Rol.
     * @return Si existe se retorna el Objeto Rol del ID especificado.
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Role> getRoleByID(@PathVariable("id") Long roleID){
        try{
            Optional<Role> role = (iRoleJpaRepository.findById(roleID));
            if(role.isPresent()){
                return new ResponseEntity<>(role.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Crear un nuevo rol.
     * @param role Objeto rol.
     * @return retorna el objeto creado.
     */
    @PostMapping("/post")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        try{
            Role roleAux = iRoleJpaRepository.save(new Role(role.getName()));
            return new ResponseEntity<>(roleAux, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualizar un rol.
     * @param roleID Identificador numérico del rol a actualziar.
     * @param role Objeto Rol con sus nuevos atributos a actualizar.
     * @return Retorna el objeto Rol actualziado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") Long roleID, @RequestBody Role role){
        try{
            Optional<Role> roleData = iRoleJpaRepository.findById(roleID);
            if(roleData.isPresent()) {
                Role roelAux = roleData.get();
                roelAux.setName(role.getName());
                return new ResponseEntity<>(iRoleJpaRepository.save(roelAux), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Eliminar un Rol según su ID.
     * @param id Identificador numérico del Rol a eliminar.
     * @return Se retorna un mensaje de éxito si es el caso, caso contrario se retorna el mensaje de la excepción.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") Long id){
        try{
            iRoleJpaRepository.deleteById(id);
            return new ResponseEntity<>("El rol con id: "+id+" ha sido eliminado exitosamente.",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
