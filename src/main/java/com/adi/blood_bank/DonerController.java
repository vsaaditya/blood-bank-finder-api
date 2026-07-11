package com.adi.blood_bank;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonerController {

    @Autowired
    DonerService service;

    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    @GetMapping("/all")
    public Page<Doner> getAll(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return service.getAll(pageable);
    }
    @GetMapping("/{id}")
    public Doner getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping("/bloodgroup")
    public List<Doner> getByBloodGroup(@RequestParam String type) {
        return service.getByBloodGroup(type);
    }

    @GetMapping("/bank/{bankId}")
    public List<Doner> getByBloodBankId(@PathVariable Integer bankId) {
        return service.getByBloodBankId(bankId);
    }
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    @PostMapping("/add")
    public String addDoner(@Valid @RequestBody Doner doner) {
        return service.addDoner(doner);
    }
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    @PutMapping("/{id}")
    public String updateDoner(@PathVariable Integer id,
                              @RequestBody Doner doner) {
        return service.updateDoner(id, doner);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteDoner(@PathVariable Integer id) {
        return service.deleteDoner(id);
    }

    @GetMapping("/whoami")
    public String whoAmI(Authentication authentication) {
        return "User: " + authentication.getName() +
                " Roles: " + authentication.getAuthorities();
    }

    @GetMapping("/gender")
    public List<Doner> getByGender(@RequestParam String gender) {
        return service.getByGender(gender);
    }

    @GetMapping("/age-above")
    public List<Doner> getOlderThan(@RequestParam Integer age) {
        return service.getDonorsOlderThan(age);
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    @GetMapping("/eligible")
    public List<Doner> getEligibleDonors() {
        return service.getEligibleDonors();
    }

    @GetMapping("/count")
    public Long getCount(@RequestParam String bloodGroup) {
        return service.getCountByBloodGroup(bloodGroup);
    }
}