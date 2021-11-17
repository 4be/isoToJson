package com.simas.rdn.ksei.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "ksei_system")
public class KseiSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false)
    private Long id;

    @Column(name = "participant_id", length = 30)
    private String participantId;

    @Column(name = "participant_name", length = 30)
    private String participantName;

    @Column(name = "sid", length = 30)
    private String sid;

    @Column(name = "account_number", length = 30)
    private String accountNumber;

    @Column(name = "investor_name", length = 50)
    private String investorName;

    @Column(name = "investor_id", length = 50)
    private String investorId;

    @Column(name = "npwp", length = 50)
    private String npwp;

    @Column(name = "passport", length = 50)
    private String passport;

    @Column(name = "bank_account_number", length = 50)
    private String bankAccountNumber;

    @Column(name = "bank_code", length = 50)
    private String bankCode;

    @Column(name = "activity_date")
    private Date activityDate;

    @Column(name = "activity", length = 30)
    private String activity;

    public KseiSystem(String investorName, String investorId, String npwp, String passport) {
        this.investorName = investorName;
        this.investorId = investorId;
        this.npwp = npwp;
        this.passport = passport;
    }
}
