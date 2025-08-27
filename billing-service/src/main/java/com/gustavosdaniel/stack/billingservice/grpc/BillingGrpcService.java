package com.gustavosdaniel.stack.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import com.gustavosdaniel.stack.billingservice.commun.BillingValidator;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private final BillingValidator billingValidator;

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    public BillingGrpcService(BillingValidator billingValidator) {
        this.billingValidator = billingValidator;
    }

    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                     StreamObserver<billing.BillingResponse> responseObserver) {

        log.info("createBillingAccount request received for user {}", billingRequest.getPatientId());


        try {

            billingValidator.validateBillingRequest(billingRequest);


            BillingResponse billingResponse = BillingResponse.newBuilder()
                    .setAccountId("12345")
                    .setStatus("ACTIVE")
                    .build();

            responseObserver.onNext(billingResponse);
            responseObserver.onCompleted();

        }catch  (Exception e) {

            throw new RuntimeException("createBillingAccount failed", e);
        }




    }
}
