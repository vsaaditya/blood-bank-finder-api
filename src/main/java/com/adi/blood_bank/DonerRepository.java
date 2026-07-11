package com.adi.blood_bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DonerRepository extends JpaRepository<Doner, Integer> {

    Optional<Doner> findByNameAndPhoneAndBloodGroup(String name, String phone, String bloodGroup);

    List<Doner> findByBloodGroup(String bloodGroup);

    List<Doner> findByBloodBankId(Integer bloodBankId);

    List<Doner> findByGender(String gender);

    List<Doner> findByAgeGreaterThan(Integer age);

    @Query("SELECT d FROM Doner d WHERE d.lastDonationDate < :date")
    List<Doner> findDonorsEligibleToDonate(@Param("date") LocalDate date);

    @Query("SELECT COUNT(d) FROM Doner d WHERE d.bloodGroup = :bloodGroup")
    Long countByBloodGroup(@Param("bloodGroup") String bloodGroup);
}