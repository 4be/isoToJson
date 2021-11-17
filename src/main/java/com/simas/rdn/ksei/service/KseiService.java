package com.simas.rdn.ksei.service;

import com.simas.rdn.ksei.models.request.KseiReqBatch;
import com.simas.rdn.ksei.models.request.KseiReqBatchStatic;

public interface KseiService {
    Object sendValidate(KseiReqBatch kseiReqBatch);
    Object sendValidateStatic(KseiReqBatchStatic kseiReqBatchStatic);
}
