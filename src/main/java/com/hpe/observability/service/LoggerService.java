package com.hpe.observability.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoggerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerService.class);

    private static final String LOG_LEVEL[] = {"TRACE", "INFO", "DEBUG", "WARN", "ERROR"};
    private static Map<String, String> nfa = new HashMap<>();
    private static Map<String, String> nfb = new HashMap<>();
    private static Map<String, String> nfc = new HashMap<>();
    private static Map<String, String> nfd = new HashMap<>();
    private static Map<String, String> nfe = new HashMap<>();
    private static Map<String, String> nff = new HashMap<>();
    private static List<Map<String, String>> nfList = new ArrayList<>();

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private static final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Map<String, Object> dataObj = new HashMap<>();

    //Create list to put values for NF's args
    List<String> argsList = new ArrayList<>();

    public LoggerService() {

        nfList.add(nfa);
        nfList.add(nfb);
        nfList.add(nfc);
        nfList.add(nfd);
        nfList.add(nfe);
        nfList.add(nff);

        nfa.put("catalog", "Nnef_EE");
        nfa.put("subsystem", "Nnef_EventExposure_Subscribe");
        nfa.put("subTag", "2a50e778412f");
        nfa.put("hostname", "dev");
        nfa.put("service", "Nnef_EventExposure");
        nfa.put("id", "99d443e2-76b4-4aac-a13d-38ca16a05881");
        nfa.put("message", "Parameter Out Of Range for subscription : 4cbaeb2d-5878-4ca5-a8cf-"
            + nfa.get("subTag") + " for parameters: monitoringType");
        nfa.put("runid", "8c75d1d3-a1f5-4464-ba9d-4a07c93b59");

        //////////////////////
        nfb.put("catalog", "Nnef_ED");
        nfb.put("subsystem", "Nnef_EventDebugger_Subscribe");
        nfb.put("subTag", "2a50e778413d");
        nfb.put("hostname", "qa");
        nfb.put("service", "Nnef_EventDebugger");
        nfb.put("id", "99d443e2-76b4-4aac-a13d-38ca16a05882");
        nfb.put("message", "Parameter Out Of Range for subscription : 4cbaeb2d-5878-4ca5-a8cf-"
            + nfa.get("subTag") + " for parameters: monitoringType");
        nfb.put("runid", "8c75d1d3-a1f5-4464-ba9d-4a07c93b60");

        /////////////////
        nfc.put("catalog", "Nnef_ER");
        nfc.put("subsystem", "Nnef_EventError_Subscribe");
        nfc.put("subTag", "2a50e778414r");
        nfc.put("hostname", "uat");
        nfc.put("service", "Nnef_EventError");
        nfc.put("id", "99d443e2-76b4-4aac-a13d-38ca16a05883");
        nfc.put("message", "Parameter Out Of Range for subscription : 4cbaeb2d-5878-4ca5-a8cf-"
            + nfa.get("subTag") + " for parameters: monitoringType");
        nfc.put("runid", "8c75d1d3-a1f5-4464-ba9d-4a07c93b61");

        //////////////////////////
        nfd.put("catalog", "Nnef_EI");
        nfd.put("subsystem", "Nnef_EventIntegration_Subscribe");
        nfd.put("subTag", "2a50e778415i");
        nfd.put("hostname", "int");
        nfd.put("service", "Nnef_EventIntegration");
        nfd.put("id", "99d443e2-76b4-4aac-a13d-38ca16a05884");
        nfd.put("message", "Parameter Out Of Range for subscription : 4cbaeb2d-5878-4ca5-a8cf-"
            + nfa.get("subTag") + " for parameters: monitoringType");
        nfd.put("runid", "8c75d1d3-a1f5-4464-ba9d-4a07c93b62");


        //////////////////////////
        nfe.put("catalog", "Nnef_EL");
        nfe.put("subsystem", "Nnef_EventLocation_Subscribe");
        nfe.put("subTag", "2a50e778416j");
        nfe.put("hostname", "prod1");
        nfe.put("service", "Nnef_EventLocation");
        nfe.put("id", "99d443e2-76b4-4aac-a13d-38ca16a05885");
        nfe.put("message", "Parameter Out Of Range for subscription : 4cbaeb2d-5878-4ca5-a8cf-"
            + nfa.get("subTag") + " for parameters: monitoringType");
        nfe.put("runid", "8c75d1d3-a1f5-4464-ba9d-4a07c93b63");

        /////////////////////////////////
        nff.put("catalog", "Nnef_EG");
        nff.put("subsystem", "Nnef_EventGeo_Subscribe");
        nff.put("subTag", "2a50e778417k");
        nff.put("hostname", "prod2");
        nff.put("service", "Nnef_EventGeo");
        nff.put("id", "99d443e2-76b4-4aac-a13d-38ca16a05885");
        nff.put("message", "Parameter Out Of Range for subscription : 4cbaeb2d-5878-4ca5-a8cf-"
            + nfa.get("subTag") + " for parameters: monitoringType");
        nff.put("runid", "8c75d1d3-a1f5-4464-ba9d-4a07c93b64");
    }

    public void randomLog() throws JsonProcessingException {
        int randomLevel = Integer.parseInt(RandomStringUtils.random(1, "01234"));
        int randomNFNumber = Integer.parseInt(RandomStringUtils.random(1, "012345"));

        dataObj.put("date", SIMPLE_DATE_FORMAT.format(timestamp));
        dataObj.put("hostname", nfList.get(randomNFNumber).get("hostname"));
        dataObj.put("level", LOG_LEVEL[randomLevel]);
        dataObj.put("catalog", nfList.get(randomNFNumber).get("catalog"));
        argsList.clear();
        argsList.add("4cbaeb2d-5878-4ca5-a8cf-" + nfList.get(randomNFNumber).get("subTag"));
        argsList.add("monitoringType");
        dataObj.put("arg", argsList);
        dataObj.put("subsystem", nfList.get(randomNFNumber).get("subsystem"));
        dataObj.put("tag", nfList.get(randomNFNumber).get("catalog")
            + "_4cbaeb2d-5878-4ca5-a8cf-" + nfList.get(randomNFNumber).get("subTag"));
        dataObj.put("message", nfList.get(randomNFNumber).get("message"));
        dataObj.put("key", "MONITORING_SUBSCRIPTION_REQUEST_VALIDATION_ERROR");
        dataObj.put("timestamp", SIMPLE_DATE_FORMAT.format(new Timestamp(System.currentTimeMillis())));


        /*dataObj.put("service", nfList.get(randomNFNumber).get("service"));
        dataObj.put("id", nfList.get(randomNFNumber).get("id"));
        dataObj.put("runid", nfList.get(randomNFNumber).get("runid"));*/

        ObjectMapper mapper = new ObjectMapper();
        String logText = mapper.writeValueAsString(dataObj);

        switch (LOG_LEVEL[randomLevel]) {
            case "TRACE" : {
                LOGGER.trace(logText);
                break;
            }case "INFO" : {
                LOGGER.info(logText);
                break;
            }case "DEBUG" : {
                LOGGER.debug(logText);
                break;
            }case "WARN" : {
                LOGGER.warn(logText);
                break;
            }case "ERROR" : {
                LOGGER.error(logText);
                break;
            }
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        LoggerService service = new LoggerService();
        service.randomLog();
    }
}

