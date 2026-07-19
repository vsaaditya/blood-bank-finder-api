package com.adi.blood_bank;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodBankService {
    @Autowired
    BloodBankRepository repo;
    //get
    public Page<BloodBank> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
    //get by id
    public BloodBank getById(Integer id){
        return repo.findById(id).orElseThrow(()-> new RuntimeException("E-404"));
    }
    //add blood bank
    public String addBank(BloodBank bb){
        bb.setId(null);  // ← Force null — ignore any client-supplied id!
        if(bb.getName()==null || bb.getName().isBlank()){
            return "name should not empty";
        }
        List<BloodBank> existing = repo.findByCity(bb.getCity());
        for(BloodBank bank : existing){
            if(bank.getName().equalsIgnoreCase(bb.getName())){
                return "Blood Bank with this name already exists in " + bb.getCity() + "!";
            }
        }
        repo.save(bb);
        return "Added successfully !!";
    }

    //get by city
    public List<BloodBank> findBankByCity(String city){
        return repo.findByCity(city);
    }

    //update blood bank
    public String updateBank(Integer id,BloodBank bb){
        BloodBank existing = repo.findById(id).orElseThrow(()->new RuntimeException("e-404"));
        existing.setName(bb.getName());
        existing.setActive(bb.getActive());
        existing.setCity(bb.getCity());
        existing.setEmail(bb.getEmail());
        existing.setAddress(bb.getAddress());
        existing.setPhone(bb.getPhone());
        existing.setPincode(bb.getPincode());
        repo.save(existing);
        return "updated !!";
    }

    //delete
    public String deleteBank(Integer id){
        if(!repo.existsById(id)){
            throw new RuntimeException("e-404");
        }
        repo.deleteById(id);
        return "Deleted !!";
    }

    public List<BloodBank> findActiveBanksByCity(String city) {
        return repo.findByCityAndActive(city, true);
    }

    public List<BloodBank> searchByName(String keyword) {
        return repo.findByNameContaining(keyword);
    }

    public Long countBanksInCity(String city) {
        return repo.countBanksByCity(city);
    }

}
