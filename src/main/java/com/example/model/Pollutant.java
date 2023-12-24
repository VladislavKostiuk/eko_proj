package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pollutant")
@Getter
@Setter
@NoArgsConstructor
public class Pollutant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Min(value = 0, message = "Gdk should not be less than 0")
    private int gdk;

    @Min(value = 0, message = "Mass consumption should not be less than 0")
    @Column(name = "mass_consumption")
    private int massConsumption;

    @Column(name = "sf")
    @Min(value = 0, message = "Sf should not be less than 0")
    private double sf;

    @Column(name = "rfc")
    @Min(value = 0, message = "Rfc should not be less than 0")
    private double rfc;

    @Column(name = "danger_level")
    private int dangerLevel;

    @Column(name = "tax_rate")
    private double taxRate;

    public Pollutant(String name, int gdk, int massConsumption, double sf, double rfc, int dangerLevel, double taxRate) {
        this.name = name;
        this.gdk = gdk;
        this.massConsumption = massConsumption;
        this.sf = sf;
        this.rfc = rfc;
        this.dangerLevel = dangerLevel;
        this.taxRate = taxRate;
    }
}
