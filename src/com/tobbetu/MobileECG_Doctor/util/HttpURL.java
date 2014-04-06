package com.tobbetu.MobileECG_Doctor.util;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class HttpURL {

    public static final String OP_LOGIN = "/user/doctorLogin";
    public static final String OP_REGISTER = "/user/doctorRegister";
    public static final String OP_PATIENT_LIST = "/patient/patientList";
    public static final String OP_GET_PATIENT_WITH_ID = "/patient/patientWithId";
    public static final String OP_GET_ENROLLED_PATIENTS_LIST = "/doctor/followedPatients";
    public static final String OP_FOLLOW_PATIENT = "/doctor/followPatient";
    public static final String OP_GET_PATIENT_ANOMALIES = "/patient/patientAnomalies";
    public static final String OP_GET_PATIENT_ANOMALY_WITHID = "/patient/patientAnomalyWithId";
    public static final String OP_GET_ECG_DATAS_BETWEEN_DATES = "/ecgdata/raw/bwdates";
    public static final String OP_DEFINE_RULES = "/doctor/defineRule";
}
