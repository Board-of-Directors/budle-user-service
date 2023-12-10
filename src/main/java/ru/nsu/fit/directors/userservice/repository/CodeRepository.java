package ru.nsu.fit.directors.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.directors.userservice.model.Code;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
    Optional<Code> findByPhoneNumberAndCode(String phoneNumber, String codeString);
}
