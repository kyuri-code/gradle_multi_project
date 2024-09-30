package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.List;

import org.example.entity.SampleEntity;
import org.example.mapper.SampleMapper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
public class DatabaseQueryServicve {

    private final String SECRET_NAME = "dev/si-dev-pgsql153/super-user";

    private final Region REGION = Region.of("ap-northeast-1");

    private final String SQL = "SELECT * FROM sample_table";

    private final String DBNAME = "sample_db";

    private final SampleMapper sampleMapper;

    public DatabaseQueryServicve(SampleMapper sampleMapper) {
        this.sampleMapper = sampleMapper;
    }

    @SuppressWarnings("unchecked")
    public String getDataFromDatabase() {
        
        // SecretsManagerClient client = SecretsManagerClient.builder()
        //         .region(REGION)
        //         .build();
        
        // GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
        //         .secretId(SECRET_NAME)
        //         .build();

        // GetSecretValueResponse getSecretValueResponse;

        // try {
        //     getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        // } catch (Exception e) {
        //     throw e;
        // }

        // String secret = getSecretValueResponse.secretString();

        // ObjectMapper objectMapper = new ObjectMapper();
        // Map<String,String> secretMap;

        // try {
        //     secretMap = objectMapper.readValue(secret, Map.class);
        // } catch (Exception e) {
        //     throw new RuntimeException("Failed to parse secret from secrets manager", e);
        // }

        // String host = secretMap.get("host");
        // String port = String.valueOf(secretMap.get("port"));
        // String username = secretMap.get("username");
        // String password = secretMap.get("password");

        // String url = "jdbc:postgresql://" + host + ":" + port + "/" + DBNAME;
        // String host = "si-dev-pgsql153.cayq42jmc6ys.ap-northeast-1.rds.amazonaws.com";
        // String port = "5432";
        // String url = "jdbc:aws-wrapper:postgresql://" + host + ":" + port + "/" + DBNAME;

        // final Properties properties = new Properties();
        // properties.setProperty("wrapperPlugins", "awsSecretsManager");
        // properties.setProperty("secretsManagerRegion", "ap-northeast-1");
        // properties.setProperty("secretsManagerSecretId", "dev/si-dev-pgsql153/super-user");

        // try(Connection conn = DriverManager.getConnection(url, properties);
        //         Statement stmt = conn.createStatement();
        //         ResultSet rs = stmt.executeQuery(SQL) ) {
        //     rs.next();
        //     return rs.getString("name");
        // } catch (Exception e) {
        //     throw new RuntimeException("Failed to connect ot the database or execute query", e);
        // }

        return "aa";
    }

    public List<SampleEntity> getAllSamples() {
        return sampleMapper.findAll();
    }

}