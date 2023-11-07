package ru.nsu.fit.directors.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.directors.userservice.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
