package com.yyok.share.kpi.service;

import java.security.cert.X509CRL;

public interface ICRLService {
    @SuppressWarnings("deprecation")
    X509CRL generateCRL(String caName);
}
