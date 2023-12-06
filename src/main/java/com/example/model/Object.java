package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "object")
@NoArgsConstructor
@Getter
@Setter
public class Object {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "name",unique = true)
//    @NotBlank
    @NotEmpty(message = "Name should not be empty")
    private String name;

//    @Column(name = "description")
    private String description;


    public Object(String name, String description) {
        this.name = name;
        this.description = description;
    }

//    public Object(String name) {
//        this.name = name;
//    }
}