package com.sds.animalapp.model.shelter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.Shelter;
import com.sds.animalapp.domain.ShelterSelectParam;
import com.sds.animalapp.domain.ShelterSidoMappingParam;
import com.sds.animalapp.domain.Sido;
import com.sds.animalapp.domain.Signgu;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShelterServiceImpl implements ShelterService {

	@Autowired
	private ShelterDAO shelterDAO;
	@Autowired
	private SignguDAO signguDAO;
	@Autowired
	private SidoDAO sidoDAO;

	public int selectCount(ShelterSelectParam shelterSelectParam) {
		return shelterDAO.selectCount(shelterSelectParam);
	}
	
	public List<Shelter> getAllRecord() {
		return shelterDAO.getAllRecord();
	}
	
	public List selectAll(ShelterSelectParam shelterSelectParam) {
		return shelterDAO.selectAll(shelterSelectParam);
	}

	public Shelter select(int shelter_idx) {
		return shelterDAO.select(shelter_idx);
	}

	public void insert(List<Shelter> shelterList) {
		shelterDAO.insert(shelterList);

	}

	@Override
	public void delete(List<Shelter> shelterList) {
		shelterDAO.delete(shelterList);
	}

	// 시군구 코드 컬럼에 매핑하는 메서드
	public void mapSigngu(List<Shelter> allShelterList) {

		for (Shelter shelter : allShelterList) {
			Sido sido;
			List<Signgu> signgu;

			String[] words = shelter.getOrgNm().split(" ");

			// 두 단어를 변수에 담기
			if (words.length >= 2) {
				String firstWord = words[0];
				String secondWord = words[1];
				log.info("firstWord: " + firstWord);
				log.info("secondWord: " + secondWord);
				sido = sidoDAO.select(firstWord);
				signgu = signguDAO.select(sido.getOrgCd(), secondWord);

			} else {
				// 두 단어가 아닌 경우
				sido = sidoDAO.select(shelter.getOrgNm());
				signgu = signguDAO.select(sido.getOrgCd(), words[0]);
			}
			ShelterSidoMappingParam shelterSidoMappingParam = new ShelterSidoMappingParam();
			shelterSidoMappingParam.setOrgCd(sido.getOrgCd());
			shelterSidoMappingParam.setShelter_idx(shelter.getShelter_idx());
			shelterDAO.updateSidoCode(shelterSidoMappingParam);
			
			//해당하는 시군구 코드가 하나만 있을경우 시도코드를 포함하여 시군구코드도 추가
			if (signgu.size() == 1) {
				shelterSidoMappingParam.setOrgCd(signgu.get(0).getOrgCd());
				shelterSidoMappingParam.setShelter_idx(shelter.getShelter_idx());
				shelterDAO.updateSignguCode(shelterSidoMappingParam); 
			}
		}
		
	}

	
	@Override
    public int findShelterIdxByCareNm(String careNm) {
        return shelterDAO.findShelterIdxByCareNm(careNm);
    }


}