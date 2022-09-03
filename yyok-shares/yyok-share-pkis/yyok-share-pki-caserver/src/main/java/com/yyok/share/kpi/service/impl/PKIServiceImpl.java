package com.yyok.share.kpi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyok.share.kpi.domain.CAIdentityContainer;
import com.yyok.share.kpi.domain.CertificateAuthority;
import com.yyok.share.kpi.domain.IdentityContainer;
import com.yyok.share.kpi.domain.PKI;
import com.yyok.share.kpi.service.IPKIService;
import com.yyok.share.kpi.service.mapper.ICAIdentityContainerMapper;
import com.yyok.share.kpi.service.mapper.ICertificateAuthorityMapper;
import com.yyok.share.kpi.service.mapper.IPKIReMapper;
import lombok.AllArgsConstructor;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSSignedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.List;

@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "userAvatar")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PKIServiceImpl implements IPKIService {

	//@Autowired
	private CertificateServiceImpl certificateServiceImpl;

	//@Autowired
	private CertificateKeyPairGeneratorServiceImpl certificateKeyPairGeneratorServiceImpl;

	//@Autowired
	//@Qualifier("pKIReMapper")
	private IPKIReMapper pKIReMapper;

	//@Autowired
	private ICertificateAuthorityMapper certificateAuthorityMapper;

	//@Autowired
	private ICAIdentityContainerMapper caIdentityContainerRepository;

	//@Autowired
	private P7BServiceImpl p7BServiceImpl;

	private static final String ROOT_CA = "ROOTCA";

	@Override
	public PKI generatePKI(String pkiName) {
		KeyPair keyPair = certificateKeyPairGeneratorServiceImpl.generateKeyPair();

		X509CertificateHolder rootCertificate = this.certificateServiceImpl.generateSelfSignedCertificate(pkiName + ROOT_CA, keyPair);

		CAIdentityContainer identityContainer = new CAIdentityContainer(rootCertificate, keyPair.getPrivate());
		caIdentityContainerRepository.insert(identityContainer);

		CertificateAuthority rootCa = new CertificateAuthority(pkiName + ROOT_CA, identityContainer);
		certificateAuthorityMapper.insert(rootCa);

		PKI pki = new PKI(pkiName, rootCa);
		rootCa.setPki(pki);

		pKIReMapper.insert(pki);

		return pki;
	}

	@Override
	public List<PKI> listPKIs() {
		QueryWrapper qw = new QueryWrapper();
		qw.setEntityClass(PKI.class);
	return pKIReMapper.selectList(qw);
	}

	@Override
	public IdentityContainer generateIdentity(String pkiName, String subjectName) {
		PKI retrievedPKI = pKIReMapper.findOneByName(pkiName);

		if(retrievedPKI == null) {
			throw new RuntimeException("Unable to find PKI with name: " + pkiName);
		}

		CertificateAuthority rootCA = retrievedPKI.getCas().get(0);

		X509CertificateHolder rootCertificate = rootCA.getIdentityContainer().getCertificate();
		KeyPair userKeyPair = certificateKeyPairGeneratorServiceImpl.generateKeyPair();
		KeyPair issuerKeyPair = new KeyPair(rootCA.getIdentityContainer().getPublicKey(), rootCA.getIdentityContainer().getPrivateKey());
		X509CertificateHolder finalUserCertificate = this.certificateServiceImpl.generateCertificate(subjectName, userKeyPair.getPublic(), rootCA.getName(), issuerKeyPair);

		IdentityContainer identifyContainer = new IdentityContainer(rootCertificate, finalUserCertificate, userKeyPair.getPrivate());

		return identifyContainer;
	}

	@Override
	public CMSSignedData getCertificateChain(String subjectName) {
		try {
			CertificateAuthority ca = this.certificateAuthorityMapper.findOneByName(subjectName + ROOT_CA);

			if(ca == null) {
				throw new RuntimeException("Unable to find Certificate Authority with name: " + subjectName);
			}
			
			X509CertificateHolder caCertificate = ca.getIdentityContainer().getCertificate();
			PrivateKey caPrivateKey = ca.getIdentityContainer().getPrivateKey();
			
			return this.p7BServiceImpl.generateP7B(caCertificate, caPrivateKey);

		} catch (Exception e) {
			throw new RuntimeException("Error while getting certiticate chain : " + e.getMessage(), e);
		}
	}
}
