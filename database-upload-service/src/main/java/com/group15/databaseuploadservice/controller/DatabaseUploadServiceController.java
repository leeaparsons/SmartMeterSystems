package com.group15.databaseuploadservice.controller;

import com.group15.databaseuploadservice.service.N3rgyService;
import org.springframework.web.bind.annotation.*;

@RestController
public class DatabaseUploadServiceController {

    private final N3rgyService n3rgyService;

    public DatabaseUploadServiceController(N3rgyService n3rgyService) {
        this.n3rgyService = n3rgyService;
    }

//    @PostMapping (path="/")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void dumpDataToDatabase() {
//       n3rgyService.dumpDataToDatabase();
//    }

    @GetMapping(path="/mock-API")
    @ResponseBody
    public String mockAPI() {
        return n3rgyService.mockAPICall();
    }

    @GetMapping(path="/")
    public void saveToDatabase() {
        n3rgyService.saveAPIData();
    }
}
