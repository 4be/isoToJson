package com.simas.rdn.ksei.repository;

import com.simas.rdn.ksei.models.entity.KseiSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;
import java.util.List;

public interface KseiRepo extends JpaRepository<KseiSystem,Long> {

    Boolean existsKseiSystemByParticipantId(String participantId);

    Boolean existsKseiSystemsBySid(String sid);

    Boolean existsKseiSystemByAccountNumber(String accountNumber);

    KseiSystem findKseiSystemBySid(String sid);

    @Query(value = "SELECT COUNT(*) FROM ksei_system AS tn WHERE tn.account_number=:an", nativeQuery = true)
    public List<String> findByAccountNumber(@PathParam("an") String an);

    @Query(value = "SELECT COUNT(*) FROM ksei_system AS tn WHERE tn.participant_id=:peid", nativeQuery = true)
    public List<String> findByParticipantID(@PathParam("peid") String peid);

    @Query(value = "SELECT COUNT(*) FROM ksei_system AS tn WHERE tn.sid=:sid", nativeQuery = true)
    public List<String> findBySid(@PathParam("sid") String sid);

    @Query(value = "SELECT investor_name,investor_id,npwp,passport,bank_code FROM ksei_system AS tn WHERE tn.sid=:sid AND tn.participant_id=:peid AND tn.account_number=:an", nativeQuery = true)
    public List<String> findAckData(@PathParam("sid") String sid, @PathParam("peid") String peid, @PathParam("an") String an);

}
