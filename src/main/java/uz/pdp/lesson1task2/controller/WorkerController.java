package uz.pdp.lesson1task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson1task2.entity.Worker;
import uz.pdp.lesson1task2.payload.ApiResponse;
import uz.pdp.lesson1task2.payload.WorkerDto;
import uz.pdp.lesson1task2.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;

    /**
     * Barcha ishchilar ro'yxati
     * The list of worker.
     * @return workers
     */
    @GetMapping
    public HttpEntity<List<Worker>> getWorkers(){
        List<Worker> workers = workerService.getWorkers();
        return ResponseEntity.ok(workers);
    }

    /**
     * Bu method bitta ishchini id orqali qaytaradi
     * This method returns one worker by id
     * @param id
     * @return worker
     */
    @GetMapping("/{id}")
    public HttpEntity<Worker> getWorkerById(@PathVariable Integer id){
        Worker worker = workerService.getWorkerById(id);
        return ResponseEntity.ok(worker);
    }

    /**
     * This method adds new worker.
     * Bu method yangi ishchi qo'shadi.
     * @param workerDto
     * @return ApiResponse
     */
    @PostMapping
    public HttpEntity<ApiResponse> addWorker(@Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * Bu metod worker ning malumotlarini tahrirlash uchun
     * This method is for edite information of the worker.
     * @param id
     * @param workerDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editeWorker(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.editeWorker(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    /**
     * This method deletes the worker by id
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteWorker(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.deleteWorker(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    /*
    * - - - - -
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
