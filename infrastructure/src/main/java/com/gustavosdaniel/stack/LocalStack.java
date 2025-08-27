package com.gustavosdaniel.stack;

import software.amazon.awscdk.*;

public class LocalStack extends Stack {

    public LocalStack(final App scope, final String id, final StackProps stackProps) {
        super(scope, id, stackProps);
    }

    public static void main(String[] args) {

        App app = new App(AppProps.builder().outdir(",/cdl.out").build());

        StackProps stackProps = StackProps.builder()
                .synthesizer(new BootstraplessSynthesizer())
                .build();

        new LocalStack(app, "localstack", stackProps);
        app.synth();

        System.out.println("App synthesizing in progress...");
    }

}