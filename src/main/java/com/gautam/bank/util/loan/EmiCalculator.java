package com.gautam.bank.util.loan;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component 
public class EmiCalculator {

    public BigDecimal calcuate(BigDecimal principal,BigDecimal annualInterest,Integer months){
        double p=principal.doubleValue();
        double r=annualInterest.doubleValue()/12/100;
        double n=months;
        double emi=(p*r*Math.pow(1+r, n))/(Math.pow(1+r, n) -1);
        return BigDecimal.valueOf(emi).setScale(2,RoundingMode.HALF_UP);
    }
    
}
