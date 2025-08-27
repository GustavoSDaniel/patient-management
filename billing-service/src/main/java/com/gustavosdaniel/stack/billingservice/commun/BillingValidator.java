package com.gustavosdaniel.stack.billingservice.commun;

import billing.BillingRequest;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class BillingValidator {

    public void validateBillingRequest(BillingRequest billingRequest){

        if (billingRequest.getPatientId() == null || billingRequest.getPatientId().isEmpty()){

            throw new ValidationException("Patient ID is required");
        }

        if (billingRequest.getName() == null || billingRequest.getName().isEmpty()){
            throw new ValidationException("Name is required");
        }

        if (billingRequest.getEmail() == null || billingRequest.getEmail().isEmpty()){
            throw new ValidationException("Email is required");
        }
    }
}
