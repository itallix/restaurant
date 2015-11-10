package org.itallix.restaurant.controllers;

import org.itallix.restaurant.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoteController {

    @Autowired
    private VoteService voteService;

    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void vote(@RequestBody String name) {
        voteService.vote(name);
    }
}
