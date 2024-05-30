package com.sds.animalapp.model.shelter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignguServiceImpl implements SignguService {
	
	@Autowired
	SignguDAO signguDAO;
	
	public List selectAll(String currentSidoCode) {
		return signguDAO.selectAll(currentSidoCode);
	}
}
