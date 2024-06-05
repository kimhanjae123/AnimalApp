package com.sds.animalapp.model.shelter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Signgu;

@Mapper
public interface SignguDAO {
	public List selectAll(String currentSidoCode);
	public void insertAll(List<Signgu> signguList );
	public void delete();
}
