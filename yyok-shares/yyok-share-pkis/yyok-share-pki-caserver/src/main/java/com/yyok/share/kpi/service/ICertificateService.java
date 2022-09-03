package com.yyok.share.kpi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyok.share.framework.mapper.common.service.IBaseService;
import com.yyok.share.kpi.domain.CAIdentityContainer;
import org.bouncycastle.cert.X509CertificateHolder;

import java.security.KeyPair;
import java.security.PublicKey;

public interface ICertificateService extends IBaseService<CAIdentityContainer>  {
    X509CertificateHolder generateCertificate(String subjectName, PublicKey subjectPublicKey, String issuerName, KeyPair issuerKeyPair);

    X509CertificateHolder generateSelfSignedCertificate(String subjectName, KeyPair keyPair);

    @Override
    CAIdentityContainer findByName(String name);

    @Override
    boolean updateByCode(CAIdentityContainer coder);
}
