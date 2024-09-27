package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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

    @SuppressWarnings("unchecked")
    public String getDataFromDatabase() {
        
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(REGION)
                .build();
        
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(SECRET_NAME)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }

        String secret = getSecretValueResponse.secretString();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> secretMap;

        try {
            secretMap = objectMapper.readValue(secret, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse secret from secrets manager", e);
        }

        String host = secretMap.get("host");
        String port = String.valueOf(secretMap.get("port"));
        String username = secretMap.get("username");
        String password = secretMap.get("password");

        String url = "jdbc:postgresql://" + host + ":" + port + "/" + DBNAME;

        try(Connection conn = DriverManager.getConnection(url, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL) ) {
            rs.next();
            return rs.getString("name");
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect ot the database or execute query", e);
        }
    }

}