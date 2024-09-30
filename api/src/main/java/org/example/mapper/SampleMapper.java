package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.SampleEntity;

import java.util.List;

@Mapper
public interface SampleMapper {

    @Select("SELECT * FROM sample_table")
    List<SampleEntity> findAll();
}
