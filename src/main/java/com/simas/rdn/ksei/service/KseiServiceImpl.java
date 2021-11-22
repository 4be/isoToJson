package com.simas.rdn.ksei.service;

import com.google.gson.Gson;
import com.simas.rdn.ksei.models.entity.KseiSystem;
import com.simas.rdn.ksei.models.entity.RequestQueue;
import com.simas.rdn.ksei.models.request.KseiReqBatch;
import com.simas.rdn.ksei.models.request.KseiReqBatchStatic;
import com.simas.rdn.ksei.models.request.KseiRequest;
import com.simas.rdn.ksei.models.request.KseiRequestStatic;
import com.simas.rdn.ksei.models.response.ReceiveStatus;
import com.simas.rdn.ksei.models.response.ResponseAck;
import com.simas.rdn.ksei.models.response.ResponseAckStatic;
import com.simas.rdn.ksei.models.response.payload.KseiResponse;
import com.simas.rdn.ksei.models.response.payload.KseiResponseFailed;
import com.simas.rdn.ksei.models.response.payload.KseiResponseStatic;
import com.simas.rdn.ksei.repository.KseiRepo;
import com.simas.rdn.ksei.repository.RequestRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class KseiServiceImpl implements KseiService {

    @Autowired
    KseiRepo kseiRepo;

    @Autowired
    RequestRepo requestRepo;

    Logger logger = LoggerFactory.getLogger(KseiServiceImpl.class);

    Gson JsonGas = new Gson();

    public Object sendValidate(KseiReqBatch kseiReqBatch) {
        Integer tmp = 0;
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyyMMddhhmm");
        String datez = DateFor.format(date);
        Integer status = 0;

        String totalData = kseiReqBatch.getTotalData();
        Map<String, KseiRequest> queryData = kseiReqBatch.getQueryData();

        if (totalData == null || queryData == null) {
            logger.error("ServiceValidation : Failed Receive RequestData, Response Code = " + status);
            logger.error("ServiceValidation : Incorrect Body Request");
            return new ReceiveStatus(String.valueOf(status));
        } else {
            status += 1;
            logger.info("ServiceValidation : Successfully Received RequestData, Response Code = " + status);
            Map<String, KseiRequest> kseiReqlist = new TreeMap();
            Map<String, Object> recordDetail = new TreeMap<>();
            for (int i = 1; i <= kseiReqBatch.getQueryData().size(); i++) {
                String Part = "NotValid";
                String Sid = "NotValid";
                String Acc = "NotValid";

                KseiSystem kseiSystem = new KseiSystem();

                if (kseiReqBatch.getQueryData().get(String.valueOf(i)).getExternalReference() == null || kseiReqBatch.getQueryData().get(String.valueOf(i)).getParticipantID() == null || kseiReqBatch.getQueryData().get(String.valueOf(i)).getSidNumber() == null || kseiReqBatch.getQueryData().get(String.valueOf(i)).getAccountNumber() == null) {
                    KseiRequest kseiRequest = kseiReqBatch.getQueryData().get(String.valueOf(i));
                    kseiSystem.setId(kseiRequest.getExternalReference());
                    kseiSystem.setParticipantId(Part);
                    kseiSystem.setSid(Sid);
                    kseiSystem.setAccountNumber(Acc);
                    KseiResponseFailed res = getKseiResponFailed(kseiSystem);
                    tmp = tmp + 1;
                    recordDetail.put("detailData" + i, res);

                } else {

                    Long ExternalReference = Long.valueOf(validatealphabet(String.valueOf(kseiReqBatch.getQueryData().get(String.valueOf(i)).getExternalReference())));
                    String participantID = validatealphabet(kseiReqBatch.getQueryData().get(String.valueOf(i)).getParticipantID());
                    String sidNumber = validatealphabet(kseiReqBatch.getQueryData().get(String.valueOf(i)).getSidNumber());
                    String accountNumber = validatealphabet(kseiReqBatch.getQueryData().get(String.valueOf(i)).getAccountNumber());
                    KseiRequest kseiRequestadd = new KseiRequest();
                    kseiRequestadd.setExternalReference(ExternalReference);
                    kseiRequestadd.setParticipantID(participantID);
                    kseiRequestadd.setSidNumber(sidNumber);
                    kseiRequestadd.setAccountNumber(accountNumber);
                    kseiReqlist.put("detailData" + i, kseiRequestadd);

                    List<String> ksei = kseiRepo.findAckData(sidNumber, participantID, accountNumber);

                    if (kseiRepo.existsKseiSystemByParticipantId(participantID)) {
                        Part = "Valid";
                    }
                    if (kseiRepo.existsKseiSystemsBySid(sidNumber)) {
                        Sid = "Valid";
                    }
                    if (kseiRepo.existsKseiSystemByAccountNumber(accountNumber)) {
                        Acc = "Valid";
                    }

                    if (Part.equals("Valid") && Sid.equals("Valid") && Acc.equals("Valid")) {
                        KseiRequest kseiRequest = kseiReqBatch.getQueryData().get(String.valueOf(i));
                        kseiSystem.setId(kseiRequest.getExternalReference());
                        kseiSystem.setParticipantId("Valid");
                        kseiSystem.setSid("Valid");
                        kseiSystem.setAccountNumber("Valid");

                        if (ksei.isEmpty()) {
                            logger.info("DetailData" + i + " = 'NotValid' | ExternalReference : " + ExternalReference + " | ParticipantId : " + participantID + " | SidNumber : " + sidNumber + " | AccountNumber : " + accountNumber);
                            kseiSystem.setParticipantId("NotValid");
                            kseiSystem.setSid("NotValid");
                            kseiSystem.setAccountNumber("NotValid");
                            KseiResponseFailed res = getKseiResponFailed(kseiSystem);
                            tmp = tmp + 1;
                            recordDetail.put("detailData" + i, res);
                        } else {
                            logger.info("DetailData" + i + " = 'InvestorValid' | ExternalReference : " + ExternalReference + " | ParticipantId : " + participantID + " | SidNumber : " + sidNumber + " | AccountNumber : " + accountNumber);
                            kseiSystem.setInvestorName(ksei.get(0).split(",")[0]);
                            kseiSystem.setInvestorId(ksei.get(0).split(",")[1]);
                            kseiSystem.setNpwp(ksei.get(0).split(",")[2]);
                            kseiSystem.setPassport(ksei.get(0).split(",")[3]);
                            KseiResponse res = getKseiResponse(kseiSystem);
                            recordDetail.put("detailData" + i, res);
                        }

                    } else if (Sid.equals("NotValid")) {
                        KseiRequest kseiRequest = kseiReqBatch.getQueryData().get(String.valueOf(i));
                        kseiSystem.setId(kseiRequest.getExternalReference());
                        kseiSystem.setParticipantId(Part);
                        kseiSystem.setSid(Sid);
                        kseiSystem.setAccountNumber(Acc);
                        logger.info("DetailData" + i + " = 'NotValid' | ExternalReference : " + ExternalReference + " | ParticipantId : " + participantID + " | SidNumber : " + sidNumber + " | AccountNumber : " + accountNumber);
                        KseiResponseFailed res = getKseiResponFailed(kseiSystem);
                        tmp = tmp + 1;
                        recordDetail.put("detailData" + i, res);

                    } else {
                        KseiRequest kseiResponse = kseiReqBatch.getQueryData().get(String.valueOf(i));
                        kseiSystem.setId(kseiResponse.getExternalReference());
                        kseiSystem.setParticipantId(Part);
                        kseiSystem.setSid(Sid);
                        kseiSystem.setAccountNumber(Acc);
                        logger.info("DetailData" + i + " = 'NotValid' | ExternalReference : " + ExternalReference + " | ParticipantId : " + participantID + " | SidNumber : " + sidNumber + " | AccountNumber : " + accountNumber);
                        KseiResponseFailed res = getKseiResponFailed(kseiSystem);
                        tmp = tmp + 1;
                        recordDetail.put("detailData" + i, res);
                    }

                }
            }
            String valtotalData = validatealphabet(kseiReqBatch.getTotalData());
            KseiReqBatch logreq = new KseiReqBatch(valtotalData, kseiReqlist);
            logger.info("Request => " + logreq);

            Integer totalQue = kseiReqBatch.getQueryData().size();
            ResponseAck responseAcklog = new ResponseAck("BSIM", datez, totalQue.toString(), tmp.toString(), recordDetail);
            logger.info("Response =>  " + responseAcklog);

//            Buat Callback langsung
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            try {
//                HttpEntity<?> request = new HttpEntity<>(responseAcklog, headers);
//                ResponseEntity<String> response = new RestTemplate()
//                        .postForEntity(rdn_ip, request, String.class);
//                if (response.getStatusCode().toString().equals("200 OK")) {
//                    logger.info("Successfully sent Ack!");
//                }
//
//            } catch (Exception e) {
//                logger.error("Failed to Sent Ack!");
//            }


            RequestQueue requestQueue = new RequestQueue();
            requestQueue.setRequest(JsonGas.toJson(responseAcklog));
            requestQueue.setType("Validation");
            requestQueue.setValidationSent(false);
            requestRepo.save(requestQueue);
            logger.info("ServiceValidation : New Request AckValidation Added to Queue\n");

            return new ReceiveStatus(String.valueOf(status));

        }


    }


    public Object sendValidateStatic(KseiReqBatchStatic kseiReqBatchStatic) {
        Integer tmp = 0;
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyyMMddhhmm");
        SimpleDateFormat convertDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String datez = DateFor.format(date);
        String batchreference = kseiReqBatchStatic.getBatchReference();
        Integer status = 0;
        Map<String, KseiRequestStatic> queryData = kseiReqBatchStatic.getQueryData();

        if (queryData == null || batchreference == null) {
            status += 1;
            logger.error("ServiceStatic : Failed receive RequestData, Response Code = " + status);
            logger.error("ServiceStatic : Incorrect Body Request");
            return new ReceiveStatus(String.valueOf(status));
        } else {
            logger.info("ServiceStatic : Successfully received RequestData, Response Code = " + status);
            try {
                List<KseiSystem> kseiList = new ArrayList<>();
                Map<String, KseiRequestStatic> kseiRequestMap = new TreeMap<>();
                Map<String, KseiResponseStatic> recordDetail = new TreeMap<>();
                for (int i = 1; i <= kseiReqBatchStatic.getQueryData().size(); i++) {

                    KseiResponseStatic kseiSystem = new KseiResponseStatic();
                    Long ExternalReference = Long.valueOf(validatealphabet(String.valueOf(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getExternalReference())));
                    String participantID = validatealphabet(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getParticipantID());
                    String sidNumber = validatealphabet(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getSidNumber());
                    String accountNumber = validatealphabet(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getAccountNumber());
                    String participantName = validatealphabet(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getParticipantName());
                    String investorName = validatealphabet(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getInvestorName());
                    String bankAccountNumber = validatealphabet(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getBankAccountNumber());
                    String bankCode = validatealphabet(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getBankCode());
                    String activity = validatealphabet(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getActivity());
                    String activitydate = kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getActivityDate();
                    KseiRequestStatic kseiRequestadd = new KseiRequestStatic();
                    kseiRequestadd.setExternalReference(ExternalReference);
                    kseiRequestadd.setParticipantID(participantID);
                    kseiRequestadd.setParticipantName(participantName);
                    kseiRequestadd.setInvestorName(investorName);
                    kseiRequestadd.setSidNumber(sidNumber);
                    kseiRequestadd.setAccountNumber(accountNumber);
                    kseiRequestadd.setBankAccountNumber(bankAccountNumber);
                    kseiRequestadd.setBankCode(bankCode);
                    kseiRequestadd.setActivityDate(activitydate);
                    kseiRequestadd.setActivity(activity);
                    kseiRequestMap.put("detailData" + i, kseiRequestadd);

                    List<String> findksei = kseiRepo.findAckData(sidNumber, participantID, accountNumber);
                    KseiSystem kseiSystemUp = kseiRepo.findAckData2(sidNumber, participantID, accountNumber);
                    SimpleDateFormat ae = new SimpleDateFormat("EEE LLL dd HH:mm:ss Z yyyy");

                    if (findksei.isEmpty()) {
                        kseiSystem.setExternalReference(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getExternalReference());
                        kseiSystem.setParticipantID(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getParticipantID());
                        kseiSystem.setSidNumberData(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getSidNumber());
                        kseiSystem.setAccountNumberData(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getAccountNumber());
                        kseiSystem.setActivityData(kseiReqBatchStatic.getQueryData().get(String.valueOf(i)).getActivity());
                        logger.info("DetailData" + i + " = 'InvalidDataStatic' | ExternalReference : " + ExternalReference + " | ParticipantId : " + participantID + " | SidNumber : " + sidNumber + " | AccountNumber : " + accountNumber);
                        tmp = tmp + 1;
                        recordDetail.put("detailData" + tmp, kseiSystem);

                    } else {

                        logger.info("DetailData" + i + " = 'DataValid' | ExternalReference : " + ExternalReference + " | ParticipantId : " + participantID + " | SidNumber : " + sidNumber + " | AccountNumber : " + accountNumber + " | bankAccountNumber : " + bankAccountNumber + " | participantName : " + participantName + " | bankCode : " + bankCode + " | activity : " + activity + " | activitydate : " + activitydate);
                        Date dateUp = ae.parse(activitydate);
                        kseiSystemUp.setParticipantName(participantName);
                        kseiSystemUp.setBankAccountNumber(bankAccountNumber);
                        kseiSystemUp.setBankCode(bankCode);
                        kseiSystemUp.setActivity(activity);
                        kseiSystemUp.setActivityDate(dateUp);
                        kseiList.add(kseiSystemUp);

                    }

                }
                KseiReqBatchStatic logreq = new KseiReqBatchStatic(batchreference, kseiRequestMap);
                logger.info("Request => " + logreq);

                Integer totalQue = kseiReqBatchStatic.getQueryData().size();
                ResponseAckStatic responseAckStaticlog = new ResponseAckStatic("BSIM3", batchreference, datez, totalQue.toString(), tmp.toString(), recordDetail);
                logger.info("Response =>  " + responseAckStaticlog);

                if(!kseiRepo.saveAll(kseiList).isEmpty()){
                    logger.info("ServiceStatic : Successfully update data from DataValid");
                }


                if (!responseAckStaticlog.getInvalidRecordDetail().isEmpty()) {
                    RequestQueue requeststatic = new RequestQueue();
                    requeststatic.setRequest(JsonGas.toJson(responseAckStaticlog));
                    requeststatic.setType("Static");
                    requeststatic.setValidationSent(false);
                    requestRepo.save(requeststatic);
                    logger.info("ServiceStatic : New Request AckStatic Added to Queue\n");
                } else {
                    logger.info("ServiceStatic : All Data Valid in Ksei, No Request added\n");
                }


            } catch (Exception e) {
                logger.error("Something Wrong, Failed to validateACK");

            }


//            Buat Callback langsung
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            try {
//                HttpEntity<?> request = new HttpEntity<>(responseAckStaticlog, headers);
//                ResponseEntity<String> response = new RestTemplate()
//                        .postForEntity(rdn_ip2, request, String.class);
//                if (response.getStatusCode().toString().equals("200 OK")) {
//                    logger.info("AckStatic Sent Successfully!");
//                }
//
//            } catch (Exception e) {
//                logger.error("Failed to Sent AckStatic!");
//            }
            return new ReceiveStatus(String.valueOf(status));

        }


    }

    public String validatealphabet(String input) {
        return input.replaceAll("[^a-zA-Z0-9 ]", "");
    }

    public KseiResponseFailed getKseiResponFailed(KseiSystem kseiSystem) {
        KseiResponseFailed kseiResponseFailed = new KseiResponseFailed(
                kseiSystem.getId(),
                kseiSystem.getParticipantId(),
                kseiSystem.getSid(),
                kseiSystem.getAccountNumber()
        );
        return kseiResponseFailed;
    }

    public KseiResponse getKseiResponse(KseiSystem kseiSystem) {
        KseiResponse kseiResponse = new KseiResponse(
                kseiSystem.getId(),
                kseiSystem.getParticipantId(),
                kseiSystem.getSid(),
                kseiSystem.getAccountNumber(),
                kseiSystem.getInvestorName(),
                kseiSystem.getInvestorId(),
                kseiSystem.getNpwp(),
                kseiSystem.getPassport()
        );
        return kseiResponse;
    }


}
