package com.example.service;

import com.example.constants.TaxRates;
import com.example.model.Pollutant;
import com.example.model.Pollution;
import com.example.model.Tax;
import com.example.repository.ObjectRepository;
import com.example.repository.PollutantRepository;
import com.example.repository.PollutionRepository;
import com.example.repository.TaxRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.example.model.Object;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExcelReader {
    private final ObjectRepository objectRepository;
    private final PollutantRepository pollutantRepository;
    private final PollutionRepository pollutionRepository;
    private final TaxRepository taxRepository;

    public void readObjectData(String fileName) {
        objectRepository.deleteAll();
        String filePath = "D:/" + fileName;
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int i = -1;
            for (Row row : sheet) {
                i++;
                if (i == 0) {
                    continue;
                }

                Cell nameCell = row.getCell(0);
                Cell descriptionCell = row.getCell(1);
                Object object = new Object (
                        nameCell.getStringCellValue(),
                        descriptionCell.getStringCellValue()
                );
                objectRepository.save(object);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readPollutantData(String fileName) {
        pollutantRepository.deleteAll();
        String filePath = "D:/" + fileName;
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int i = -1;
            for (Row row : sheet) {
                i++;
                if (i == 0) {
                    continue;
                }

                Cell nameCell = row.getCell(1);
                Cell gdkCell = row.getCell(2);
                Cell massConsumptionCell = row.getCell(3);
                Cell sfCell = row.getCell(4);
                Cell rfcCell = row.getCell(5);
                Cell dangerLevelCell = row.getCell(6);
                Cell taxRateCell = row.getCell(7);

                double taxRate = taxRateCell.getNumericCellValue();
                int dangerLevel = (int) dangerLevelCell.getNumericCellValue();
                taxRate = getTaxRate(taxRate, dangerLevel);

                Pollutant pollutant = new Pollutant(
                        nameCell.getStringCellValue(),
                        (int) gdkCell.getNumericCellValue(),
                        (int) massConsumptionCell.getNumericCellValue(),
                        sfCell.getNumericCellValue(),
                        rfcCell.getNumericCellValue(),
                        dangerLevel,
                        taxRate
                );
                pollutantRepository.save(pollutant);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readPollutionData(String fileName) {
        pollutionRepository.deleteAll();
        String filePath = "D:/" + fileName;
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int i = -1;
            for (Row row : sheet) {
                i++;
                if (i == 0) {
                    continue;
                }

                Cell objectCell = row.getCell(0);
                Cell pollutantCell = row.getCell(1);
                Cell valueCell = row.getCell(2);
                Cell yearCell = row.getCell(3);
                Cell concentrationCell = row.getCell(4);

                double concentration = concentrationCell.getNumericCellValue();
                double pollutionValue = valueCell.getNumericCellValue();
                Object object = objectRepository.findById((long) objectCell.getNumericCellValue()).get();
                Pollutant pollutant = pollutantRepository.findById((long) pollutantCell.getNumericCellValue()).get();
                double cr = CalculationService.calculateCR(concentration, pollutant.getSf());
                double hq = CalculationService.calculateHQ(concentration, pollutant.getRfc());
                double addLadd = CalculationService.calculateAddLadd(concentration);
                double compensation = CalculationService.calcCompensation(pollutionValue, pollutant.getMassConsumption(), pollutant.getGdk());
                String riskLevel = "Високий";
                if (cr < Math.pow(10, -3)) {
                    riskLevel = "Середній ";
                }
                if (cr < Math.pow(10, -4)) {
                    riskLevel = "Низький  ";
                }
                if (cr < Math.pow(10, -6)) {
                    riskLevel = "Мінімальний   ";
                }

                Pollution pollution = new Pollution(
                        object,
                        pollutant,
                        pollutionValue,
                        (int) yearCell.getNumericCellValue(),
                        concentration,
                        cr,
                        hq,
                        addLadd,
                        compensation,
                        riskLevel
                );
                pollutionRepository.save(pollution);

                double taxSumm = CalculationService.calcTax(pollutionValue, pollutant.getTaxRate());

                Tax tax = new Tax(
                        object,
                        pollution,
                        pollutant.getDangerLevel(),
                        pollutant.getTaxRate(),
                        taxSumm
                );
                taxRepository.save(tax);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private double getTaxRate(double tax_rate, int danger_level){
        if(tax_rate != 0){
            return tax_rate;
        }

        if (danger_level == 1) {
            return TaxRates.FirstDangerLevelTaxRate;
        } else if (danger_level == 2) {
            return TaxRates.SecondDangerLevelTaxRate;
        } else if (danger_level == 3) {
            return TaxRates.ThirdDangerLevelTaxRate;
        } else  {
            return TaxRates.FourthDangerLevelTaxRate;
        }
    }
}
