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

<<<<<<< HEAD
    public void insertInterestShelter(InterestShelter interestShelter) {
        log.debug("Inserting interest shelter: {}", interestShelter);
        interestShelterDAO.insertInterestShelter(interestShelter);            
    }
=======
	public void insertInterestShelter(InterestShelter interestShelter) {
		if(!duplicatedInterestShelter(interestShelter)) {
			interestShelterDAO.insertInterestShelter(interestShelter);			
		}else {
			throw new IllegalArgumentException("이미 등록된 관심 동물입니다.");
		}
	}
>>>>>>> aafef1acfb92816cd30bb6d72f90720bf08eba05

    public void deleteInterestShelter(int interest_shelter_idx) {
        log.debug("Deleting interest shelter with idx: {}", interest_shelter_idx);
        interestShelterDAO.deleteInterestShelter(interest_shelter_idx);
    }

<<<<<<< HEAD
    public boolean duplicatedInterestShelter(int shelter_idx, int member_idx) {
        boolean isDuplicated = interestShelterDAO.duplicatedInterestShelter(shelter_idx, member_idx) != null;
        log.debug("Duplicated interest shelter check for shelter_idx: {}, member_idx: {}: {}", shelter_idx, member_idx, isDuplicated);
        return isDuplicated;
    }
=======
	@Override
	public boolean duplicatedInterestShelter(InterestShelter interestShelter) {
		int count = interestShelterDAO.duplicatedInterestShelter(interestShelter);
		return count > 0;
	}
>>>>>>> aafef1acfb92816cd30bb6d72f90720bf08eba05

    @Override
    public List<InterestShelter> getAllInterests() {
        return interestShelterDAO.findAll();
    }

<<<<<<< HEAD
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
}
=======


}
>>>>>>> aafef1acfb92816cd30bb6d72f90720bf08eba05
