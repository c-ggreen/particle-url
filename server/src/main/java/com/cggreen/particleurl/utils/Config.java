package com.cggreen.particleurl.utils;

public class Config {
    // Look for 'DYNAMO_TABLE_NAME' in environment variables
    private static final String DYNAMO_TABLE_NAME = System.getenv("DYNAMO_TABLE_NAME");

    public static String getDynamoTableName() {
        if (DYNAMO_TABLE_NAME == null || DYNAMO_TABLE_NAME.isEmpty()) {
            throw new IllegalStateException("Environment variable 'DYNAMO_TABLE_NAME' is not set!");
        }
        return DYNAMO_TABLE_NAME;
    }
}