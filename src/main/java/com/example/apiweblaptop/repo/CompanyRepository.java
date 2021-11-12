package com.example.apiweblaptop.repo;

import com.example.apiweblaptop.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
