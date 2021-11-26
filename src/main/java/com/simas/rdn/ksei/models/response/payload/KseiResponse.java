package com.simas.rdn.ksei.models.response.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KseiResponse {
    private Long relatedReferenceData;
    private String participantID;
    private String sidNumberData;
    private String accountNumberData;
    private String investorName;
    private String investorID;
    private String investorNPWP;
    private String investorPassport;

    @Override
    public String toString() {
        return "KseiResponse{" +
                "relatedReferenceData:" + relatedReferenceData +
                ", participantID:'" + participantID + '\'' +
                ", sidNumberData:'" + sidNumberData + '\'' +
                ", accountNumberData:'" + accountNumberData + '\'' +
                ", investorName:'" + investorName + '\'' +
                ", investorID='" + investorID + '\'' +
                ", investorNPWP:'" + investorNPWP + '\'' +
                ", investorPassport:'" + investorPassport + '\'' +
                '}';
    }
}
