package com.sds.animalapp.model.shelter;

import java.util.List;

import com.sds.animalapp.domain.Sido;

public interface SidoService {
	//시, 도 목록을 불러오는 코드
	public List selectAll();
	public void insertAll(List<Sido> sidoList );
	public void delete();
}
