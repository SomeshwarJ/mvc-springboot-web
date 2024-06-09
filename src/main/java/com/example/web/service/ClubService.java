package com.example.web.service;

import com.example.web.dto.ClubDto;
import com.example.web.models.Club;

import java.util.List;

public interface ClubService {
    ClubDto findClubById(long cluId);

    List<ClubDto> findAllClubs();
    Club saveClub(ClubDto clubDto);

    void updateClub(ClubDto club);

    void delete(Long clubId);

    List<ClubDto> searchClubs(String query);
}
