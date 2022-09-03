package com.yyok.share.kpi.service;

import com.yyok.share.kpi.domain.IdentityContainer;
import com.yyok.share.kpi.domain.PKI;
import org.bouncycastle.cms.CMSSignedData;

import java.util.List;

public interface IPKIService {
    PKI generatePKI(String pkiName);

    List<PKI> listPKIs();

    IdentityContainer generateIdentity(String pkiName, String subjectName);

    CMSSignedData getCertificateChain(String subjectName);
}
