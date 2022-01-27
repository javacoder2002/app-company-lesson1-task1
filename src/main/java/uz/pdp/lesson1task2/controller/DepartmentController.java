package uz.pdp.lesson1task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson1task2.entity.Department;
import uz.pdp.lesson1task2.payload.ApiResponse;
import uz.pdp.lesson1task2.payload.DepartmentDto;
import uz.pdp.lesson1task2.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    /**
     * Bu metod barcha bo'limlar riyxatini qaytaradi
     * This method returns the list of all department.
     * @return departments
     */
    @GetMapping
    public HttpEntity<List<Department>> getDepartments(){
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }

    /**
     * Bu method departmentni id orqali oladi
     * This method gets the department by id.
     * @param id
     * @return department
     */
    @GetMapping("/{id}")
    public HttpEntity<Department> getDepartmentById(@PathVariable Integer id){
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    /**
     * Bu method orqali departament qo'shiladi.
     * Department is added by this method.
     * @param departmentDto
     * @return ApiResponse
     */
    @PostMapping
    public HttpEntity<ApiResponse> addDepartment(@Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * Bu method department malumotlarini tahrirlaydi.
     * This method edits information of the department
     * @param id
     * @param departmentDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editeDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.editeDepartment(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    /**
     * Bu method department ni id orqali o'chiadi
     * This method deletes the department by id.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteDepartment(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }



    /**
     * - - - - - - - - - - - - - - - -
     * */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
