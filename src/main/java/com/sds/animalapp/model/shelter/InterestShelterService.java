package com.sds.animalapp.model.shelter;

import java.util.List;

import com.sds.animalapp.domain.InterestShelter;

public interface InterestShelterService {
	
	// 마음에 드는 보호소 추가
	public void insertInterestShelter(InterestShelter interestShelter);
	
	// 마음에 드는 보호소 삭제
	public void deleteInterestShelter(int interest_shetler_idx);
	
<<<<<<< HEAD
	// 마음에 드는 보호소 중복 확인
	public boolean duplicatedInterestShelter(int shelter_idx,int member_idx);
	
=======
	public boolean duplicatedInterestShelter (InterestShelter interestShelter);
>>>>>>> aafef1acfb92816cd30bb6d72f90720bf08eba05
	
	List<InterestShelter> getAllInterests();
    List<InterestShelter> getInterestByMemberIdx(int member_idx);
    InterestShelter findById(int interest_shelter_idx);
    InterestShelter findInterestByShelterIdxAndMemberIdx(int shelter_idx, int member_idx);
	
	
}
