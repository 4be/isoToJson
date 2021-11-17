package com.simas.rdn.ksei.models.response.payload;

import lombok.Data;

@Data
public class KseiResponseFailed {
    private Long relatedReferenceData;
    private String participantID;
    private String sidNumberData;
    private String accountNumberData;

    public KseiResponseFailed(Long relatedReferenceData, String participantID, String sidNumberData, String accountNumberData) {
        this.relatedReferenceData = relatedReferenceData;
        this.participantID = participantID;
        this.sidNumberData = sidNumberData;
        this.accountNumberData = accountNumberData;
    }

    @Override
    public String toString() {
        return "KseiResponseFailed{" +
                "relatedReferenceData=" + relatedReferenceData +
                ", participantID='" + participantID + '\'' +
                ", sidNumberData='" + sidNumberData + '\'' +
                ", accountNumberData='" + accountNumberData + '\'' +
                '}';
    }

}
