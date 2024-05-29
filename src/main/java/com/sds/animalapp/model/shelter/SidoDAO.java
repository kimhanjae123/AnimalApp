package com.sds.animalapp.model.shelter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Sido;

@Mapper
public interface SidoDAO {
    List<Sido> selectAll();
}
