package com.simas.rdn.ksei.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class KseiReqBatchStatic {
    private String batchReference;
    private Map<String, KseiRequestStatic> queryData;

    @Override
    public String toString() {
        return "KseiReqBatchStatic{" +
                "batchReference='" + batchReference + '\'' +
                ", queryData=" + queryData +
                '}';
    }
}

