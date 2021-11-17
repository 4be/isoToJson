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


}
