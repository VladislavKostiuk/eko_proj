package com.example.service;

import com.example.constants.TaxRates;

public class CalculationService {
    private static final double BW = 60;
    private static final double daysInYear = 365;
    private static final double Tout = 1.64;
    private static final double Tin = 19.69;
    private static final double Vout = 0.63;
    private static final double AT = 70;
    private static final double Vin = 0.5;
    private static final double ED = 70;

    private static final double MinWage = 6700;
    private final static double K_population = 1.55;
    private final static double Kf = 1.25;
    private static final double Kzi = 1;
    private static final double time = 365*24;


    public static double calculateHQ(double concentration,double rfc) {
        return concentration/rfc;
    }

    public static double calculateCR(double concentration, double sf) {
        return calculateAddLadd(concentration)*sf;
    }

    public static double calculateAddLadd(double pc) {
        return (((pc * Tout * Vout) + (pc * Tin * Vin)) * daysInYear * ED) / (BW * AT * daysInYear);
    }

    public static double calcCompensation(double pollutionValue, double massConsumption, double gdk){
        double Ai = (gdk > 1) ? (10/gdk) : (1/gdk);
        double Kt = K_population * Kf;
        double convertedPollutionValue = pollutionValue * Math.pow(10,6) / (365*24*60*60);
        double convertedMassConsumption = massConsumption / 3600;

        if(convertedPollutionValue <= convertedMassConsumption) {
            return 0;
        } else {
            return getPollutionMass(convertedPollutionValue, convertedMassConsumption, time) * 1.1 * MinWage * Ai * Kt * Kzi;
        }
    }

    private static double getPollutionMass(double pv, double mfr, double t) {
        return 3.6 * Math.pow(10, -3) * (pv - mfr) * t;
    }

    public static double calcTax(double pollutionValue, double taxRate){
        return pollutionValue * taxRate;
    }
}
