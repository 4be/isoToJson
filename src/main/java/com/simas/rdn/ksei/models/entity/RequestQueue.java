package com.simas.rdn.ksei.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "request_ack")
public class RequestQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "request", columnDefinition = "TEXT")
    private String request;

    @Column(name = "type")
    private String type;

    @Column(name = "validationsent")
    private Boolean validationSent;

}
