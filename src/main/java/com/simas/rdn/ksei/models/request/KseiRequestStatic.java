package com.simas.rdn.ksei.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KseiRequestStatic {
    private Long externalReference;
    private String participantID;
    private String participantName;
    private String investorName;
    private String sidNumber;
    private String accountNumber;
    private String bankAccountNumber;
    private String bankCode;
    private String activityDate;
    private String activity;

    @Override
    public String toString() {
        return "KseiRequestStatic{" +
                "externalReference=" + externalReference +
                ", participantID='" + participantID + '\'' +
                ", participantName='" + participantName + '\'' +
                ", investorName='" + investorName + '\'' +
                ", sidNumber='" + sidNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", activityDate='" + activityDate + '\'' +
                ", activity='" + activity + '\'' +
                '}';
    }
}
