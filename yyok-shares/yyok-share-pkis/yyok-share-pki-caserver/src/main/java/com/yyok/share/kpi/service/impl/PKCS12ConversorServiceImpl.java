package com.yyok.share.kpi.service.impl;

import com.yyok.share.kpi.domain.IdentityContainer;
import com.yyok.share.kpi.service.IPKCS12ConversorService;
import lombok.AllArgsConstructor;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "userAvatar")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PKCS12ConversorServiceImpl implements IPKCS12ConversorService {

	private static final String P12_EXTENSTION = "PKCS12";
	private static final String X509_EXTENSTION = "X.509";
	
	@Override
	public KeyStore generatePKCS12(IdentityContainer identity) {
		try {
			Security.addProvider(new BouncyCastleProvider());
			
			KeyStore keystore = KeyStore.getInstance(P12_EXTENSTION, BouncyCastleProvider.PROVIDER_NAME);
			keystore.load(null, null);

			String alias = identity.getCertificate().getSubject().toString().replaceFirst("CN=",  "");
			PrivateKey privateKey = identity.getPrivateKey();
			
			CertificateFactory certificateFactory = CertificateFactory.getInstance(X509_EXTENSTION, BouncyCastleProvider.PROVIDER_NAME);

			JcaX509CertificateConverter certificateConverter = new JcaX509CertificateConverter();
			
			X509Certificate certificate = certificateConverter.getCertificate(identity.getCertificate());
			Certificate convertedUserCertificate = certificateFactory.generateCertificate(new ByteArrayInputStream(certificate.getEncoded()));
			
			X509Certificate rootCACertificate = certificateConverter.getCertificate(identity.getRootCertificate());
			Certificate convertedRootCACertificate = certificateFactory.generateCertificate(new ByteArrayInputStream(rootCACertificate.getEncoded()));;
			
			Certificate[] chain = {convertedUserCertificate, convertedRootCACertificate};
			
			keystore.setKeyEntry(alias, privateKey, null, chain);
			
			return keystore;
			
		} catch (Exception e) {
			throw new RuntimeException("Error while generating PKCS12: " + e.getMessage(), e);
		}
	}
}
