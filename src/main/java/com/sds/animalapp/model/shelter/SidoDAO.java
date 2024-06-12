package com.sds.animalapp.model.shelter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Sido;
import com.sds.animalapp.domain.Signgu;

@Mapper
public interface SidoDAO {
    List<Sido> selectAll();
    public void insertAll(List<Sido> sidoList );
	public Sido select(String keyword);
	public void delete();
}
