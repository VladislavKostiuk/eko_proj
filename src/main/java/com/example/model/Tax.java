package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tax")
@Getter
@Setter
@NoArgsConstructor
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "object_id")
    private Object object;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "pollution_id")
    private Pollution pollution;

    @Column(name = "danger_level")
    private int dangerLevel;

    @Column(name = "rate")
    private double rate;

    @Column(name = "tax")
    private double tax;

    public Tax(Object object, Pollution pollution, int dangerLevel, double rate, double tax) {
        this.object = object;
        this.pollution = pollution;
        this.dangerLevel = dangerLevel;
        this.rate = rate;
        this.tax = tax;
    }
}
