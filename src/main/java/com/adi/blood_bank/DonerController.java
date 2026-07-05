package com.adi.blood_bank;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonerController {

    @Autowired
    DonerService service;

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

    @PostMapping("/add")
    public String addDoner(@Valid @RequestBody Doner doner) {
        return service.addDoner(doner);
    }

    @PutMapping("/{id}")
    public String updateDoner(@PathVariable Integer id,
                              @RequestBody Doner doner) {
        return service.updateDoner(id, doner);
    }

    @DeleteMapping("/{id}")
    public String deleteDoner(@PathVariable Integer id) {
        return service.deleteDoner(id);
    }
}