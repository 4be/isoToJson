package com.simas.rdn.ksei.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAck {
    private String bankCode = "BSIM";
    private String reportTimestamp;
    private String totalRecord;
    private String invalidRecord;
    private Map<String, Object> recordDetail;

    @Override
    public String toString() {
        return "ResponseAck{" +
                "bankCode:'" + bankCode + '\'' +
                ", reportTimestamp:'" + reportTimestamp + '\'' +
                ", totalRecord:'" + totalRecord + '\'' +
                ", invalidRecord:'" + invalidRecord + '\'' +
                ", recordDetail:" + recordDetail +
                '}';
    }
}
