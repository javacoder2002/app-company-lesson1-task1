package uz.pdp.lesson1task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson1task2.entity.Company;
import uz.pdp.lesson1task2.payload.ApiResponse;
import uz.pdp.lesson1task2.payload.CompanyDto;
import uz.pdp.lesson1task2.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    /**
     * Bu metod barcha companiyalar ro'yxatini qaytaradi
     * This method returns the list of all company.
     * @return companies
     */
    @GetMapping
    public HttpEntity<List<Company>> getCompanies(){
        List<Company> companies = companyService.getCompanies();
        return ResponseEntity.ok(companies);
    }

    /**
     * Bu metod companiyani id orqali qaytaradi
     * Agar topilmasa null qaytaradi.
     * This method returns company by id.
     * if company is not found by id, this method returns null.
     * @param id
     * @return company
     */
    @GetMapping("/{id}")
    public HttpEntity<Company> getCompanyById(@PathVariable Integer id){
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    /**
     * Bu metod orqali companiya qo'shiladi
     * The company is added by this method.
     * @param companyDto
     * @return ApiResponse
     */
    @PostMapping
    public HttpEntity<ApiResponse> addCompany(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Bu method company ni tahrirlaydi.
     * This method edits company.
     * @param id
     * @param companyDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editeCompany(@PathVariable Integer id,
                                                @Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.editeCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    /**
     * Company is deleted by this method.
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteCompany(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.deleteCompany(id);
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
