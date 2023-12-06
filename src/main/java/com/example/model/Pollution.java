package com.example.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "pollution")
@Getter
@Setter
@NoArgsConstructor
public class Pollution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "object_id")
    private Object object;

    @ManyToOne
    @JoinColumn(name = "pollutant_id")
    private Pollutant pollutant;

    @Column(name = "value")
    private double value;

    @Column(name = "year")
    private int year;

    @Column(name = "concentration")
    private double concentration;

    @Column(name = "cr")
    private double cr;

    @Column(name = "hq")
    private double hq;

    @Column(name = "add_ladd")
    private double addLadd;

    @Column(name = "compensation")
    private double compensation;

    @Column(name = "risk_level")
    private String riskLevel;

    public Pollution(Object object,
                     Pollutant pollutant,
                     double value, int year,
                     double concentration,
                     double cr, double hq,
                     double addLadd,
                     double compensation,
                     String riskLevel) {
        this.object = object;
        this.pollutant = pollutant;
        this.value = value;
        this.year = year;
        this.concentration = concentration;
        this.cr = cr;
        this.hq = hq;
        this.addLadd = addLadd;
        this.compensation = compensation;
        this.riskLevel = riskLevel;
    }
}
