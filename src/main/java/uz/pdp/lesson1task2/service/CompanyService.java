package uz.pdp.lesson1task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson1task2.entity.Address;
import uz.pdp.lesson1task2.entity.Company;
import uz.pdp.lesson1task2.payload.ApiResponse;
import uz.pdp.lesson1task2.payload.CompanyDto;
import uz.pdp.lesson1task2.repository.AddressRepository;
import uz.pdp.lesson1task2.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;

    /**
     * Bu metod barcha companiyalar ro'yxatini qaytaradi
     * This method return the list of all company.
     *
     * @return companies
     */
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    /**
     * Bu metod companiyani id orqali qaytaradi
     * Agar topilmasa null qaytaradi.
     * This method returns company by id.
     * if company is not found by id, this method returns null.
     *
     * @param id
     * @return company
     */
    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    /**
     * Bu metod orqali companiya qo'shiladi
     * The company is added by this method.
     *
     * @param companyDto
     * @return ApiResponse
     */
    public ApiResponse addCompany(CompanyDto companyDto) {
        try {
            Address address = new Address();
            address.setStreet(companyDto.getStreet());
            address.setHomeNumber(companyDto.getHomeNumber());
            Address saveAddress = addressRepository.save(address);

            Company company = new Company();
            company.setCorpName(companyDto.getCorpName());
            company.setDirectorName(companyDto.getDirectorName());
            company.setAddress(saveAddress);
            companyRepository.save(company);

            return new ApiResponse(true, "company added successfully!");
        } catch (Exception exception) {
            return new ApiResponse(false, "company not added");
        }
    }

    /**
     * Bu method company ni tahrirlaydi.
     * This method edits company.
     *
     * @param id
     * @param companyDto
     * @return ApiResponse
     */
    public ApiResponse editeCompany(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty())
            return new ApiResponse(false, "company not found!");

        Company company = optionalCompany.get();

        Address address = company.getAddress();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address saveAddress = addressRepository.save(address);

        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(saveAddress);
        companyRepository.save(company);

        return new ApiResponse(true, "company edited!");
    }

    /**
     * Company is deleted by this method.
     * @param id
     * @return ApiResponse
     */
    public ApiResponse deleteCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty())
            return new ApiResponse(false, "company not found!");
        companyRepository.deleteById(id);
        return new ApiResponse(true, "company deleted");
    }

}


















