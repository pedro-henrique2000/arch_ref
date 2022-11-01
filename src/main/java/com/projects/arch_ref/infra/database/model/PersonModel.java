package com.projects.arch_ref.infra.database.model;

import com.projects.arch_ref.domain.entity.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "TB_PERSON", uniqueConstraints = @UniqueConstraint(name = "unique_email", columnNames = "email"))
@NoArgsConstructor
@Entity
@Getter
@Setter
public class PersonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
