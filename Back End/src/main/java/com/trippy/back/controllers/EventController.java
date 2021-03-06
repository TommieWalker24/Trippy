package com.trippy.back.controllers;

import com.trippy.back.com.trippy.back.util.ImageUtil;
import com.trippy.back.entities.Account;
import com.trippy.back.entities.Event;
import com.trippy.back.entities.EventComment;
import com.trippy.back.repos.EventCommentsRepo;
import com.trippy.back.repos.EventsRepo;
import com.trippy.back.repos.UserRepo;
import com.trippy.back.services.EventCommentsService;
import com.trippy.back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/event")


public class EventController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    EventsRepo eventRepository;

    @Autowired
    EventCommentsService commentsService;

    @PostMapping(value = "/create", consumes = "application/json;charset=UTF-8" )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity create(@RequestBody Event event){
        eventRepository.save(event);
        return new ResponseEntity(event, HttpStatus.OK);
    }

    @PostMapping(value = "/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity edit(@RequestBody Event event){
        Optional<Event> eventFound = eventRepository.findById(event.getId());
        if(eventFound.isPresent()){
            eventRepository.save(event);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/delete/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("eventId") int id){
        Optional<Event> event = eventRepository.findById(id);

        if(event.isPresent()){
            eventRepository.delete(event.get());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/list")
    public ResponseEntity list(){
        return new ResponseEntity<>(eventRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/follow/{eventId}")
    public ResponseEntity follow(@RequestHeader(value = "email") String email, @PathVariable int eventId){
        return new ResponseEntity<>(userService.followEvent(email, eventId), HttpStatus.OK);
    }

    @GetMapping(value = "/userevents")
    public List<Event> listUserEvents(@RequestHeader(value = "email") String email){
        List<Event> events = userService.findEvents(email);
        return events;
    }

    @CrossOrigin
    @PostMapping(value = "/addcomment/{eventId}")
    public ResponseEntity addComment(@RequestHeader(value = "email") String email, @PathVariable int eventId, @RequestBody EventComment eventComment){
        return new ResponseEntity(commentsService.saveComment(eventComment, email,eventId), HttpStatus.OK);
    }

    @GetMapping(value = "/comments/{eventId}")
    public ResponseEntity getComments(@PathVariable int eventId){
        return new ResponseEntity(commentsService.findComments(eventId), HttpStatus.OK);
    }

    @GetMapping(value = "/removecomment/{commentId}")
    public ResponseEntity removeComments(@PathVariable int commentId){
        return new ResponseEntity(commentsService.deleteComment(commentId), HttpStatus.OK);
    }
}
