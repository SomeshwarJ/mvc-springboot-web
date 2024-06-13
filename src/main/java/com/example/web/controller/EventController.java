package com.example.web.controller;

import com.example.web.dto.ClubDto;
import com.example.web.dto.EventDto;
import com.example.web.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class EventController {
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events/clubs/{clubId}")
    public ResponseEntity<?> getEventsByClubId(@PathVariable("clubId") Long clubId){
        List<EventDto> events = eventService.findByClubId(clubId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<?> getAllEvents(){
        List<EventDto> events = eventService.findAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<?> getEventDetails(@PathVariable("eventId") Long eventId){
        EventDto event = eventService.findByEventId(eventId);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping("/events/{clubId}/new")
    public ResponseEntity<?> createEvent(@PathVariable("clubId") Long clubId, @RequestBody EventDto eventDto){
        eventService.createEvent(clubId, eventDto);
        List<EventDto> events = eventService.findByClubId(clubId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
