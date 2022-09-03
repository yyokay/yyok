package com.yyok.share.kpi.service.impl;

import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.kpi.domain.CAIdentityContainer;
import com.yyok.share.kpi.service.ICertificateKeyPairGeneratorService;
import com.yyok.share.kpi.service.mapper.ICAIdentityContainerMapper;
import lombok.AllArgsConstructor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "userAvatar")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CertificateKeyPairGeneratorServiceImpl extends BaseServiceImpl<ICAIdentityContainerMapper, CAIdentityContainer> implements ICertificateKeyPairGeneratorService {

	private static final String KEYS_ALGORITHM = "RSA";
	
	private static final int DEFAULT_KEY_SIZE = 4096;

	@Override
	public KeyPair generateKeyPair() {
		return generateKeyPair(DEFAULT_KEY_SIZE);
	}
	
	@Override
	public KeyPair generateKeyPair(int keySize) {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEYS_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);

			keyGen.initialize(keySize, new SecureRandom());
			KeyPair keyPair = keyGen.generateKeyPair();

			return keyPair;

		} catch (Exception e) {
			throw new RuntimeException("Error generating keypair: " + e.getMessage());
		}
	}

	@Override
	public CAIdentityContainer findByName(String name) {
		return null;
	}

	@Override
	public boolean updateByCode(CAIdentityContainer coder) {
		return false;
	}
}
