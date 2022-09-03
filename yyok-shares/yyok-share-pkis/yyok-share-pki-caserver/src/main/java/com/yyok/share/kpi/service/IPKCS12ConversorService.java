package com.yyok.share.kpi.service;

import com.yyok.share.kpi.domain.IdentityContainer;

import java.security.KeyStore;

public interface IPKCS12ConversorService {
    KeyStore generatePKCS12(IdentityContainer identity);
}
