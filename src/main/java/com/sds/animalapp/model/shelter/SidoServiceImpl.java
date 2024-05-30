package com.sds.animalapp.model.shelter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SidoServiceImpl implements SidoService{
	
	@Autowired
	SidoDAO sidoDAO;
	
	public List selectAll() {
		return sidoDAO.selectAll();
	}
}
