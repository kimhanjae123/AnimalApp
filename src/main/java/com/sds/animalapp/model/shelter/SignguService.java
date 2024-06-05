package com.sds.animalapp.model.shelter;

import java.util.List;

import com.sds.animalapp.domain.Signgu;

public interface SignguService {
	public List selectAll(String currentSidoCode);
	public void insertAll(List<Signgu> signguList );
	public void delete();
}
