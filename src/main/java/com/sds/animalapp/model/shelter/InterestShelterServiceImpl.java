package com.sds.animalapp.model.shelter;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.InterestShelter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InterestShelterServiceImpl implements InterestShelterService {
    
    @Autowired
    private InterestShelterDAO interestShelterDAO;

    public void insertInterestShelter(InterestShelter interestShelter) {
        log.debug("Inserting interest shelter: {}", interestShelter);
        interestShelterDAO.insertInterestShelter(interestShelter);            
    }

    public void deleteInterestShelter(int interest_shelter_idx) {
        log.debug("Deleting interest shelter with idx: {}", interest_shelter_idx);
        interestShelterDAO.deleteInterestShelter(interest_shelter_idx);
    }

    public boolean duplicatedInterestShelter(int shelter_idx, int member_idx) {
        boolean isDuplicated = interestShelterDAO.duplicatedInterestShelter(shelter_idx, member_idx) != null;
        log.debug("Duplicated interest shelter check for shelter_idx: {}, member_idx: {}: {}", shelter_idx, member_idx, isDuplicated);
        return isDuplicated;
    }

    @Override
    public List<InterestShelter> getAllInterests() {
        return interestShelterDAO.findAll();
    }

    @Override
    public List<InterestShelter> getInterestByMemberIdx(int member_idx) {
        return interestShelterDAO.findByMemberIdx(member_idx);
    }

    @Override
    public InterestShelter findById(int interest_shelter_idx) {
        return interestShelterDAO.findInterestById(interest_shelter_idx);
    }

    @Override
    public InterestShelter findInterestByShelterIdxAndMemberIdx(int shelter_idx, int member_idx) {
        return interestShelterDAO.findInterestByShelterIdxAndMemberIdx(shelter_idx, member_idx);
    }

	@Override
	public int getRecordNum(int member_idx, int shelter_idx) {
		return interestShelterDAO.selectCount(member_idx, shelter_idx);
	}
}