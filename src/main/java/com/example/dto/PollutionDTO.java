package com.example.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PollutionDTO {
    private String objectName;
    private String pollutantName;
    @Min(value = 0, message = "Value should not be less than 0")
    private double value;
    @Min(value = 1950, message = "Year should not be less than 1950")
    @Max(value = 2023, message = "Year should not be more than 2023")
    private int year;
    @Min(value = 0, message = "Concentration should not be less than 0")
    private double concentration;
    @Min(value = 0, message = "Cr should not be less than 0")
    private double cr;
    @Min(value = 0, message = "Hq should not be less than 0")
    private double hq;
    @Min(value = 0, message = "AddLadd should not be less than 0")
    private double addLadd;
    @Min(value = 0, message = "Compensation should not be less than 0")
    private double compensation;

}
