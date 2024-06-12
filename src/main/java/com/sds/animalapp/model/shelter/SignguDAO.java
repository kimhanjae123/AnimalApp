package com.sds.animalapp.model.shelter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sds.animalapp.domain.Signgu;

@Mapper
public interface SignguDAO {
	public List selectAll(String currentSidoCode);
	public List select(@Param("uprCd") String uprCd, @Param("keyword") String keyword);
	public void insertAll(List<Signgu> signguList );
	public void delete();
}
