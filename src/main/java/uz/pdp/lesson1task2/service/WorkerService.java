package uz.pdp.lesson1task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson1task2.entity.Address;
import uz.pdp.lesson1task2.entity.Department;
import uz.pdp.lesson1task2.entity.Worker;
import uz.pdp.lesson1task2.payload.ApiResponse;
import uz.pdp.lesson1task2.payload.WorkerDto;
import uz.pdp.lesson1task2.repository.AddressRepository;
import uz.pdp.lesson1task2.repository.DepartmentRepository;
import uz.pdp.lesson1task2.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AddressRepository addressRepository;

    /**
     * Barcha ishchilar ro'yxati
     * The list of worker.
     * @return workers
     */
    public List<Worker> getWorkers() {
        return workerRepository.findAll();
    }

    /**
     * Bu method bitta ishchini id orqali qaytaradi
     * This method returns one worker by id
     * @param id
     * @return worker
     */
    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    /**
     * This method adds new worker.
     * Bu method yangi ishchi qo'shadi.
     * @param workerDto
     * @return ApiResponse
     */
    public ApiResponse addWorker(WorkerDto workerDto) {

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isEmpty())
            return new ApiResponse(false, "department not found!");

        if (workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber()))
            return new ApiResponse(false, "phone number already exist!");

        Address address = new Address();
        address.setHomeNumber(workerDto.getHomeNumber());
        address.setStreet(workerDto.getStreet());
        Address saveAddress = addressRepository.save(address);

        Department department = optionalDepartment.get();

        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(department);
        worker.setAddress(saveAddress);
        workerRepository.save(worker);
        return new ApiResponse(true, "worker saved successfully!");

    }

    /**
     * Bu metod worker ning malumotlarini tahrirlash uchun
     * This method is for edite information of the worker.
     * @param id
     * @param workerDto
     * @return ApiResponse
     */
    public ApiResponse editeWorker(Integer id, WorkerDto workerDto) {

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty())
            return new ApiResponse(false, "worker not found!");

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isEmpty())
            return new ApiResponse(false, "department not found!");

        if (workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id))
            return new ApiResponse(false, "phone number already exist!");

        Worker worker = optionalWorker.get();

        Address address = worker.getAddress();
        address.setHomeNumber(workerDto.getHomeNumber());
        address.setStreet(workerDto.getStreet());
        Address saveAddress = addressRepository.save(address);

        Department department = optionalDepartment.get();

        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(department);
        worker.setAddress(saveAddress);
        workerRepository.save(worker);
        return new ApiResponse(true, "worker edited!");
    }

    /**
     * This method deletes the worker by id
     * @param id
     * @return ApiResponse
     */
    public ApiResponse deleteWorker(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty())
            return new ApiResponse(false, "worker not found!");
        workerRepository.deleteById(id);
        return new ApiResponse(true, "worker deleted!");
    }

}














