package uz.pdp.lesson1task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson1task2.entity.Company;
import uz.pdp.lesson1task2.entity.Department;
import uz.pdp.lesson1task2.payload.ApiResponse;
import uz.pdp.lesson1task2.payload.DepartmentDto;
import uz.pdp.lesson1task2.repository.CompanyRepository;
import uz.pdp.lesson1task2.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;
    
    /**
     * Bu metod barcha bo'limlar riyxatini qaytaradi
     * This method returns the list of all department.
     * @return departments
     */
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * Bu method departmentni id orqali oladi
     * This method gets the department by id.
     * @param id
     * @return department
     */
    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    /**
     * Bu method orqali departament qo'shiladi.
     * Department is added by this method.
     * @param departmentDto
     * @return ApiResponse
     */
    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        if (departmentRepository.existsByName(departmentDto.getName()))
            return new ApiResponse(false, "deportment already exist");

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty())
            return new ApiResponse(false, "company not found!");
        
        Company company = optionalCompany.get();
        
        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(company);
        departmentRepository.save(department);
        
        return new ApiResponse(true, "department added successfully!");
    }

    /**
     * Bu method department malumotlarini tahrirlaydi.
     * This method edits information of the department 
     * @param id
     * @param departmentDto
     * @return ApiResponse
     */
    public ApiResponse editeDepartment(Integer id, DepartmentDto departmentDto) {
        
        if (departmentRepository.existsByNameAndIdNot(departmentDto.getName(), id))
            return new ApiResponse(false, "department already exist!");

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty())
            return new ApiResponse(false, "department not found!");

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty())
            return new ApiResponse(false ,"company not found!");
        
        Company company = optionalCompany.get();

        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        department.setCompany(company);
        
        departmentRepository.save(department);
        return new ApiResponse(true, "department edited!");
                
    }

    /**
     * Bu method department ni id orqali o'chiadi
     * This method deletes the department by id.
     * @param id
     * @return
     */
    public ApiResponse deleteDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty())
            return new ApiResponse(false, "department not found!");
        departmentRepository.deleteById(id);
        return new ApiResponse(true, "department deleted!");
    }

}



















