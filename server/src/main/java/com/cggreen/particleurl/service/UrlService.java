package com.cggreen.particleurl.service;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.cggreen.particleurl.utils.ApiRequest;
import com.cggreen.particleurl.utils.ApiResponse;
import com.cggreen.particleurl.utils.Config;
import com.soundicly.jnanoidenhanced.jnanoid.NanoIdUtils;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.regions.Region;

public class UrlService {

    public static APIGatewayProxyResponseEvent getLongUrl(APIGatewayProxyRequestEvent request) {
        try {

            String shortUrl = ApiRequest.getShortUrlFromPath(request);

            DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                    .region(Region.US_EAST_1)
                    .build();

            Map<String, AttributeValue> key = Map.of(
                    "ShortUrl", AttributeValue.builder().s(shortUrl).build());

            GetItemRequest itemRequest = GetItemRequest.builder()
                    .tableName(Config.getDynamoTableName())
                    .key(key)
                    .build();

            Map<String, AttributeValue> item = dynamoDbClient.getItem(itemRequest).item();

            String longUrl = null;
            if (item != null && item.containsKey("LongUrl")) {
                longUrl = item.get("LongUrl").s();
            }

            return ApiResponse.buildRedirect(longUrl);
        } catch (Exception e) {
            return ApiResponse.buildResponse(500, "{\"message\":\"Fail!\"}");
        }
    }

    public static APIGatewayProxyResponseEvent createShortUrl(APIGatewayProxyRequestEvent request) {
        try {
            DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                    .region(Region.US_EAST_1)
                    .build();

            String shortUrl = NanoIdUtils.randomNanoId(7);
            String longUrl = ApiRequest.getLongUrlFromBody(request);

            Map<String, AttributeValue> item = new HashMap<>();
            item.put("ShortUrl", AttributeValue.builder().s(shortUrl).build());
            item.put("LongUrl", AttributeValue.builder().s(longUrl).build());

            PutItemRequest itemRequest = PutItemRequest.builder()
                    .tableName(Config.getDynamoTableName())
                    .item(item)
                    .conditionExpression("attribute_not_exists(ShortUrl)")
                    .build();

            dynamoDbClient.putItem(itemRequest);

            return ApiResponse.buildResponse(200, "{\"message\":\"Success!\"}");
        } catch (Exception e) {
            return ApiResponse.buildResponse(500, "{\"message\":\"Fail!\"}");

        }
    }
}
