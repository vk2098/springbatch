package com.batch.batch.processing.Controller;

import com.batch.batch.processing.service.BatchService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/job/")
public class BatchController {


    @Autowired
    BatchService batchService;
    @GetMapping("{params}")
    public ResponseEntity<String> executeJob(@PathVariable String params) throws Exception {

            batchService.job(params);
            return new ResponseEntity<>("Started Job", HttpStatus.OK);
        }

    }

