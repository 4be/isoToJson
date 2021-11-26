package com.simas.rdn.ksei.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@AllArgsConstructor
public class KseiReqBatch {
    private String totalData;
    private Map<String, KseiRequest> queryData;

    @Override
    public String toString() {
        return "KseiReqBatch{" +
                "totalData:'" + totalData + '\'' +
                ", queryData:'" + queryData +
                '}';
    }
}
