package org.gustavosdaniel.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import com.gustavosdaniel.billingservice.exception.ValidationAcountException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {

    @GrpcClient("billing-service")
    private  BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingResponse createBillingAccount(String patientId, String name, String email) {

        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        BillingResponse response;

        try {

            response = blockingStub.createBillingAccount(request);
            log.info("Received response from billing service via GRPC: {}", response);

        } catch (StatusRuntimeException ex){
            log.error("GRPC call failed: {}: {}", ex.getStatus().getCode(), ex.getStatus().getDescription());

            throw new ValidationAcountException("Failed to create billing account via GRPC", ex);

        }

        log.info("Received response from billing service via GRPC: {}", response);

        return response;
    }
}
