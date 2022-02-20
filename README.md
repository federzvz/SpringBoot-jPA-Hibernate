
# JPA Entities with JUnit 5 tests - Support for MySQL & H2 in memory database

***Se han agregado 3 nuevos controladores:***

  - `EmployeeController`
  - `ProjectController`
  - `RoleController`


***Se han creado 15 nuevos servicios CRUD:***

| ENDPOINT CREADO | HTTP METHOD  | DESCRIPCIÓN |
| :------------ |:---------------:| :-----|
| /api/employe/get | GET |  Se obtienen todos los objetos Employe. |
| /api/employe/get/{employeeID} | GET | Se obtiene un objeto Employe según el ID especificado. |
| /api/employe/delete/{id} | DELETE | Se elimina un objeto Employe según el ID especificado. |
| /api/employe/{id} | PUT | Actualziar/modificar los datos de un objeto Employe según el ID especificado. |
| /api/employe/post | POST | Crear un nuevo objeto Employe. |
| /api/project/delete/{id} | DELETE | Eliminar un objeto Project según el ID especificado. |
| /api/project/{id} | PUT | Actualizar/modificar los datos de un objeto Project según el ID especificado. |
| /api/project/post | POST | Crear un nuevo objeto Project. |
| /api/project/get/{id} | GET | Se obtiene un objeto Project según el ID especificado. |
| /api/project/get | GET | Se obtienen todos los objetos Project. |
| /api/role/get | GET | Se obtienen todos los objetos Role. |
| /api/role/get/{id} | GET | Se obtiene un objeto Role según el ID especificado. |
| /api/role/post | POST | Crear un nuevo objeto Role. |
| /api/role/{id} | PUT | Actualizar/modificar los datos de un objeto Role según el ID especificado. |
| /api/role/delete/{id} | DELETE | Eliminar un objeto Role según el ID especificado. |


