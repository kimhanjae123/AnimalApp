package com.sds.animalapp.model.shelter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignguDAO {
	public List selectAll(String currentSidoCode);
}
