package ru.nsu.fit.directors.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "company")
@Getter
@Setter
@Accessors(chain = true)
public class Company {
    @Id
    private Long id;

}
