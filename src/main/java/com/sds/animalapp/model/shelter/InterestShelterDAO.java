package com.sds.animalapp.model.shelter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sds.animalapp.domain.AdoptAnimal;
import com.sds.animalapp.domain.InterestShelter;


@Mapper
public interface InterestShelterDAO {
    
    void insertInterestShelter(InterestShelter interestShelter);
    
    void deleteInterestShelter(int interest_shelter_idx);
    
    InterestShelter duplicatedInterestShelter(@Param("shelter_idx") int shelter_idx, @Param("member_idx") int member_idx);
    
    InterestShelter findInterest(@Param("orgNm") String orgNm, @Param("careNm") String careNm);

    List<InterestShelter> findByMemberIdx(int member_idx);

    List<InterestShelter> findAll();

    InterestShelter findInterestById(int interest_shelter_idx);
    
    InterestShelter findInterestByShelterIdxAndMemberIdx(@Param("shelter_idx") int shelter_idx, @Param("member_idx") int member_idx);
    
    int selectCount(@Param("member_idx") int member_idx, @Param("shelter_idx") int shelter_idx);
}