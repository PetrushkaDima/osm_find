package com.osm.findarea.controller;

import com.osm.findarea.Result;
import com.osm.findarea.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private WorkService workService;


    @GetMapping("/")
    public Result getURL(
            @RequestParam(name = "query", defaultValue = "Самарская область") String query,
            @RequestParam(name = "type", defaultValue = "village") String type
    ) {
        return workService.find(query,type);
    }
}
