package com.simas.rdn.ksei.models.response;

import com.simas.rdn.ksei.models.response.payload.KseiResponseStatic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAckStatic {
    private String bankCode;
    private String relatedBatchReference;
    private String reportTimestamp;
    private String totalRecord;
    private String invalidRecord;
    private Map<String, KseiResponseStatic> invalidRecordDetail;

    @Override
    public String toString() {
        return "ResponseAckStatic{" +
                "bankCode='" + bankCode + '\'' +
                ", relatedBatchReference='" + relatedBatchReference + '\'' +
                ", reportTimestamp='" + reportTimestamp + '\'' +
                ", totalRecord='" + totalRecord + '\'' +
                ", invalidRecord='" + invalidRecord + '\'' +
                ", invalidRecordDetail=" + invalidRecordDetail +
                '}';
    }
}
