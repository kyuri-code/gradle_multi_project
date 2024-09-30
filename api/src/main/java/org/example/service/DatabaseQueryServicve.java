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

@Service
public class DatabaseQueryServicve {

    private final SampleMapper sampleMapper;

    public DatabaseQueryServicve(SampleMapper sampleMapper) {
        this.sampleMapper = sampleMapper;
    }

    public List<SampleEntity> getAllSamples() {
        return sampleMapper.findAll();
    }

}