package com.example.web.controller;

import com.example.web.dto.ClubDto;
import com.example.web.dto.ValidationErrorResponse;
import com.example.web.service.ClubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class ClubController {
    private ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/clubs")
    public ResponseEntity<?> listClubs(){
        List<ClubDto> clubs = clubService.findAllClubs();
        return new ResponseEntity<List<ClubDto>>(clubs, HttpStatus.OK);
    }

    @PostMapping("/clubs/new")
    public ResponseEntity<?> saveClub(@Valid @RequestBody ClubDto clubDto, BindingResult result){
        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            ValidationErrorResponse errorResponse = new ValidationErrorResponse("Validation failed", errors);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        ClubDto club1 = new ClubDto();
        club1.setTitle(clubDto.getTitle());
        club1.setPhotoUrl(clubDto.getPhotoUrl());
        club1.setContent(clubDto.getContent());
        club1.setCreatedOn(clubDto.getCreatedOn());
        club1.setUpdatedOn(clubDto.getUpdatedOn());
        clubService.saveClub(club1);
        List<ClubDto> clubs = clubService.findAllClubs();
        return new ResponseEntity<List<ClubDto>>(clubs, HttpStatus.CREATED);
    }

    @GetMapping("/clubs/{clubId}")
    public ResponseEntity<?> clubDetail(@PathVariable("clubId") Long clubId){
        ClubDto clubDto = clubService.findClubById(clubId);
        return new ResponseEntity<>(clubDto, HttpStatus.OK);
    }

    @PostMapping("/clubs/{clubId}/edit")
    public ResponseEntity<?> updateClub(@PathVariable("clubId") Long clubId, @Valid @RequestBody ClubDto club, BindingResult result){
        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            ValidationErrorResponse errorResponse = new ValidationErrorResponse("Validation failed", errors);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        club.setId(clubId);
        clubService.updateClub(club);
        List<ClubDto> clubs = clubService.findAllClubs();
        return new ResponseEntity<List<ClubDto>>(clubs, HttpStatus.OK);
    }

    @GetMapping("/clubs/{clubId}/delete")
    public ResponseEntity<?> deleteClub(@PathVariable("clubId") Long clubId){
        clubService.delete(clubId);
        List<ClubDto> clubs = clubService.findAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }

}
