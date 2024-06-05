package com.sds.animalapp.model.shelter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.Signgu;

@Service
public class SignguServiceImpl implements SignguService {
	
	@Autowired
	SignguDAO signguDAO;
	
	public List selectAll(String currentSidoCode) {
		return signguDAO.selectAll(currentSidoCode);
	}
	
	public void insertAll(List<Signgu> signguList ) {
		signguDAO.insertAll(signguList);
	}
	
	public void delete() {
		signguDAO.delete();
	}
}
