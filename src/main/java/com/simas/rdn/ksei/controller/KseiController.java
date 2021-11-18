package com.simas.rdn.ksei.controller;

import com.simas.rdn.ksei.models.request.KseiReqBatch;
import com.simas.rdn.ksei.models.request.KseiReqBatchStatic;
import com.simas.rdn.ksei.service.KseiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ksei/")
@Component
public class KseiController {

    @Autowired
    KseiService kseiService;

    Logger logger = LoggerFactory.getLogger(KseiController.class);

    @PostMapping("/validateck/")
    public ResponseEntity<Object> SendKzel(@RequestBody KseiReqBatch kseiReqBatch) {
        Object data = kseiService.sendValidate(kseiReqBatch);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/validateck/static/")
    public ResponseEntity<Object> SendKzeiStatic(@RequestBody KseiReqBatchStatic kseiReqBatchStatic) {
        Object data = kseiService.sendValidateStatic(kseiReqBatchStatic);
        return ResponseEntity.ok(data);
    }


}
