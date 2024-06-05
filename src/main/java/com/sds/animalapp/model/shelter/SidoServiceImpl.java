package com.sds.animalapp.model.shelter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.Sido;

@Service
public class SidoServiceImpl implements SidoService{
	
	@Autowired
	SidoDAO sidoDAO;
	
	public List selectAll() {
		return sidoDAO.selectAll();
	}
	
	public void insertAll(List<Sido> sidoList ) {
		sidoDAO.insertAll(sidoList);
	}
	
	public void delete() {
		sidoDAO.delete();
	}
}
