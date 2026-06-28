package com.adi.blood_bank;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bloodbank")
public class BloodBankController {
    @Autowired
    BloodBankService service;

    @GetMapping("/all")
    public List<BloodBank> getAllBloodBank(){

        return service.getAll();
    }
    @GetMapping("/{id}")
    public BloodBank getById(@PathVariable Integer id){

        return service.getById(id);
    }
    @GetMapping("/filter")
    public List<BloodBank> getByCity(@RequestParam String city){
        return service.findBankByCity(city);
    }
    @PostMapping("/add")
    public String add(@Valid @RequestBody BloodBank bb){

        return service.addBank(bb);
    }
    @PutMapping("/{id}")
    public String updateBloodBank (@PathVariable Integer id,@RequestBody BloodBank bb){
        return service.updateBank(id,bb);
    }
    @DeleteMapping("/{id}")
    public String deleteBloodBank(@PathVariable Integer id){
        return service.deleteBank(id);
    }


}

