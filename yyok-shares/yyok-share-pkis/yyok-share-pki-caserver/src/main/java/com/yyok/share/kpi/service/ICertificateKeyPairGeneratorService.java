package com.yyok.share.kpi.service;

import com.yyok.share.framework.mapper.common.service.IBaseService;
import com.yyok.share.kpi.domain.CAIdentityContainer;

import java.security.KeyPair;

public interface ICertificateKeyPairGeneratorService extends IBaseService<CAIdentityContainer> {
    KeyPair generateKeyPair();

    KeyPair generateKeyPair(int keySize);
}
