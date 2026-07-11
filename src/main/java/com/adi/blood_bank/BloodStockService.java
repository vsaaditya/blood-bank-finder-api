package com.adi.blood_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BloodStockService {

    @Autowired
    BloodStockRepository repo;
    @Autowired
    BloodBankRepository bloodBankRepo;

    public List<BloodAvailabilityDTO> searchByBloodGroupAndCity(
            String bloodGroup, String city) {

        // Step 1 — find all banks in that city
        List<BloodBank> banks = bloodBankRepo.findByCity(city);

        List<BloodAvailabilityDTO> result = new ArrayList<>();

        // Step 2 — for each bank check stock
        for (BloodBank bank : banks) {
            List<BloodStock> stocks = repo.findByBloodBankId(bank.getId());
            for (BloodStock stock : stocks) {
                if (stock.getBloodGroup().equalsIgnoreCase(bloodGroup)
                        && stock.getUnits() > 0) {
                    result.add(new BloodAvailabilityDTO(
                            bank.getName(),
                            bank.getCity(),
                            bank.getPhone(),
                            stock.getBloodGroup(),
                            stock.getUnits()
                    ));
                }
            }
        }
        return result;
    }
    // get all
    public Page<BloodStock> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // get by id
    public BloodStock getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
    }

    // get stock by blood bank
    public List<BloodStock> getByBloodBankId(Integer bloodBankId) {
        return repo.findByBloodBankId(bloodBankId);
    }

    // get stock by blood group
    public List<BloodStock> getByBloodGroup(String bloodGroup) {
        return repo.findByBloodGroup(bloodGroup);
    }

    // add stock
    public String addStock(BloodStock stock) {
        if (stock.getUnits() < 0) {
            return "Units cannot be negative!";
        }
        stock.setLastUpdated(LocalDateTime.now());
        repo.save(stock);
        return "Stock added successfully!";
    }

    // update units
    public String updateUnits(Integer id, Integer units) {
        BloodStock existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));

        // Check 1 — units is zero
        if (existing.getUnits() == 0) {
            return "Blood group not available — stock is empty!";
        }

        // Check 2 — units going below zero
        if (existing.getUnits() - units < 0) {
            return "Insufficient stock — only " + existing.getUnits() + " units available!";
        }

        existing.setUnits(existing.getUnits() - units);
        existing.setLastUpdated(LocalDateTime.now());
        repo.save(existing);
        return "Stock updated! Remaining units: " + existing.getUnits();
    }

    // delete stock
    public String deleteStock(Integer id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Stock not found with id: " + id);
        }
        repo.deleteById(id);
        return "Stock deleted successfully!";
    }

    // get critical stock — less than 2 units
    public List<BloodStock> getCriticalStock() {
        return repo.findAll().stream()
                .filter(s -> s.getUnits() < 2)
                .toList();
    }

    public List<BloodStock> getCriticalStockUnder(Integer units) {
        return repo.findByUnitsLessThan(units);
    }

    public List<BloodStock> getStockInRange(Integer min, Integer max) {
        return repo.findByUnitsBetween(min, max);
    }

    public Long getTotalUnitsByBloodGroup(String bloodGroup) {
        return repo.totalUnitsByBloodGroup(bloodGroup);
    }
}