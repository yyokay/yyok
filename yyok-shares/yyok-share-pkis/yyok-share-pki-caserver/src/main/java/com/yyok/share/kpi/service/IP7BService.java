package com.yyok.share.kpi.service;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSSignedData;

import java.security.PrivateKey;

public interface IP7BService {
    CMSSignedData generateP7B(X509CertificateHolder caCertificate, PrivateKey caPrivateKey);
}
