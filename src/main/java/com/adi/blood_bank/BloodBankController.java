package com.adi.blood_bank;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bloodbank")
public class BloodBankController {
    @Autowired
    BloodBankService service;
    @PreAuthorize("hasAnyRole('USER','DOCTOR','ADMIN')")
    @GetMapping("/all")
    public Page<BloodBank> getAllBloodBank(
            @PageableDefault(size = 5, sort = "name") Pageable pageable) {
        return service.getAll(pageable);
    }


    @GetMapping("/{id}")
    public BloodBank getById(@PathVariable Integer id){

        return service.getById(id);
    }
    @GetMapping("/filter")
    public List<BloodBank> getByCity(@RequestParam String city){
        return service.findBankByCity(city);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String add(@Valid @RequestBody BloodBank bb){

        return service.addBank(bb);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public String updateBloodBank (@PathVariable Integer id,@RequestBody BloodBank bb){
        return service.updateBank(id,bb);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteBloodBank(@PathVariable Integer id){
        return service.deleteBank(id);
    }
    @Autowired
    AppConfig appConfig;

    @GetMapping("/info")
    public String getInfo() {
        return appConfig.getName() + " v" + appConfig.getVersion() + " by " + appConfig.getAuthor();
    }

    @GetMapping("/active")
    public List<BloodBank> getActiveBanks(@RequestParam String city) {
        return service.findActiveBanksByCity(city);
    }

    @GetMapping("/search")
    public List<BloodBank> searchByName(@RequestParam String keyword) {
        return service.searchByName(keyword);
    }

    @GetMapping("/count")
    public Long countBanks(@RequestParam String city) {
        return service.countBanksInCity(city);
    }

}

