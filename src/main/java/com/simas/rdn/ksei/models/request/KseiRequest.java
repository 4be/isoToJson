package com.simas.rdn.ksei.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KseiRequest {
    private Long externalReference;
    private String participantID;
    private String sidNumber;
    private String accountNumber;

    @Override
    public String toString() {
        return "KseiRequest{" +
                "externalReference=" + externalReference +
                ", participantID='" + participantID + '\'' +
                ", sidNumber='" + sidNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
