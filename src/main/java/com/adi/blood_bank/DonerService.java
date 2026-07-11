package com.adi.blood_bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonerService {

    @Autowired
    DonerRepository repo;

    // get all
    public Page<Doner> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // get by id
    public Doner getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));
    }

    // add donor
    public String addDoner(Doner doner) {
        // duplicate check — all 3 must match
        if(repo.findByNameAndPhoneAndBloodGroup(
                doner.getName(),
                doner.getPhone(),
                doner.getBloodGroup()).isPresent()){
            return "Donor already exists!";
        }
        // Check 1 — age must be 18+
        if (doner.getAge() < 18) {
            return "You are not eligible to donate — must be 18 or older!";
        }

        // Check 2 — must not have donated within last 3 months
        if (doner.getLastDonationDate() != null) {
            LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
            if (doner.getLastDonationDate().isAfter(threeMonthsAgo)) {
                return "You are not eligible — you donated within the last 3 months!";
            }
        }

        repo.save(doner);
        return "Donor registered successfully!";
    }

    // update donor
    public String updateDoner(Integer id, Doner doner) {
        Doner existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));
        existing.setName(doner.getName());
        existing.setAge(doner.getAge());
        existing.setGender(doner.getGender());
        existing.setBloodGroup(doner.getBloodGroup());
        existing.setPhone(doner.getPhone());
        existing.setEmail(doner.getEmail());
        existing.setLastDonationDate(doner.getLastDonationDate());
        existing.setBloodBank(doner.getBloodBank());
        repo.save(existing);
        return "Donor updated successfully!";
    }

    // delete donor
    public String deleteDoner(Integer id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Donor not found with id: " + id);
        }
        repo.deleteById(id);
        return "Donor deleted successfully!";
    }

    // get by blood group
    public List<Doner> getByBloodGroup(String bloodGroup) {
        return repo.findByBloodGroup(bloodGroup);
    }

    // get by blood bank
    public List<Doner> getByBloodBankId(Integer bloodBankId) {
        return repo.findByBloodBankId(bloodBankId);
    }

    public List<Doner> getByGender(String gender) {
        return repo.findByGender(gender);
    }

    public List<Doner> getDonorsOlderThan(Integer age) {
        return repo.findByAgeGreaterThan(age);
    }

    public List<Doner> getEligibleDonors() {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        return repo.findDonorsEligibleToDonate(threeMonthsAgo);
    }

    public Long getCountByBloodGroup(String bloodGroup) {
        return repo.countByBloodGroup(bloodGroup);
    }
}