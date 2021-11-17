package com.simas.rdn.ksei.models.response.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KseiResponseStatic {
    private Long externalReference;
    private String participantID;
    private String sidNumberData;
    private String accountNumberData;
    private String activityData;

}
