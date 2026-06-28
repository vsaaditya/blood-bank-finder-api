package com.adi.blood_bank;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class BloodStockController {

    @Autowired
    BloodStockService service;

    @GetMapping("/all")
    public List<BloodStock> getAll() {
        return service.getAll();
    }

    @GetMapping("/bank/{bankId}")
    public List<BloodStock> getByBloodBankId(@PathVariable Integer bankId) {
        return service.getByBloodBankId(bankId);
    }

    @GetMapping("/bloodgroup")
    public List<BloodStock> getByBloodGroup(@RequestParam String type) {
        return service.getByBloodGroup(type);
    }

    @GetMapping("/critical")
    public List<BloodStock> getCriticalStock() {
        return service.getCriticalStock();
    }

    @PostMapping("/add")
    public String addStock(@Valid @RequestBody BloodStock stock) {
        return service.addStock(stock);
    }

    @PatchMapping("/{id}/update")
    public String updateUnits(@PathVariable Integer id,
                              @RequestParam Integer units) {
        return service.updateUnits(id, units);
    }

    @DeleteMapping("/{id}")
    public String deleteStock(@PathVariable Integer id) {
        return service.deleteStock(id);
    }

    @GetMapping("/search")
    public List<BloodAvailabilityDTO> search(
            @RequestParam String bloodGroup,
            @RequestParam String city) {
        return service.searchByBloodGroupAndCity(bloodGroup, city);
    }
}