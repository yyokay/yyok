package com.yyok.share.pki.profile.impl;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.crmf.CertTemplate;
import org.bouncycastle.asn1.crmf.OptionalValidity;
import org.bouncycastle.asn1.microsoft.MicrosoftObjectIdentifiers;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.Time;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import com.yyok.share.pki.profile.ExtensionTemplate;
import com.yyok.share.pki.profile.Profile;
import com.yyok.share.pki.profile.ProfileException;
import com.yyok.share.pki.profile.Result;
import com.yyok.share.pki.profile.jaxb.JAXBDateWithOverRule;
import com.yyok.share.pki.profile.jaxb.JAXBKeyLength;
import com.yyok.share.pki.profile.jaxb.JAXBProfile;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Transformation implementation from JAXB
 */
public class ProfileJAXB implements Profile {

    final Logger logger = LoggerFactory.getLogger(ProfileJAXB.class);

    private final JAXBProfile jaxbProfile;

    final private HashMap<ASN1ObjectIdentifier, ExtensionTemplate> extensionTemplates = new HashMap<ASN1ObjectIdentifier, ExtensionTemplate>();

    public ProfileJAXB(JAXBProfile jaxbProfile, Certificate caCertificate) throws IOException, NoSuchAlgorithmException {

        this.jaxbProfile = jaxbProfile;
        if (jaxbProfile.getCertificateProfile().getExtensions().getAuthorityKeyIdentifier() != null) {
            extensionTemplates.put(Extension.authorityKeyIdentifier, new AuthorityKeyIdentifierJAXB(jaxbProfile.getCertificateProfile().getExtensions().getAuthorityKeyIdentifier(), caCertificate));
        }
        if (jaxbProfile.getCertificateProfile().getExtensions().getSubjectKeyIdentifier() != null) {
            extensionTemplates.put(Extension.subjectKeyIdentifier, new SubjectKeyIdentifierJAXB(jaxbProfile.getCertificateProfile().getExtensions().getSubjectKeyIdentifier()));
        }
        if (jaxbProfile.getCertificateProfile().getExtensions().getKeyUsage() != null) {
            extensionTemplates.put(Extension.keyUsage, new KeyUsageJAXB(jaxbProfile.getCertificateProfile().getExtensions().getKeyUsage()));
        }
        if (jaxbProfile.getCertificateProfile().getExtensions().getCertificatePolicies() != null) {
            extensionTemplates.put(Extension.certificatePolicies, new CertificatePoliciesJAXB(jaxbProfile.getCertificateProfile().getExtensions().getCertificatePolicies()));
        }
        if (jaxbProfile.getCertificateProfile().getExtensions().getSubjectAlternativeName() != null) {
            extensionTemplates.put(Extension.subjectAlternativeName, new SubjectAlternativeNameJAXB(jaxbProfile.getCertificateProfile().getExtensions().getSubjectAlternativeName()));
        }
        if (jaxbProfile.getCertificateProfile().getExtensions().getIssuerAlternativeName() != null) {
            extensionTemplates.put(Extension.issuerAlternativeName, new IssuerAlternativeNameJAXB(jaxbProfile.getCertificateProfile().getExtensions().getIssuerAlternativeName()));
        }if (jaxbProfile.getCertificateProfile().getExtensions().getExtendedKeyUsage() != null) {
            extensionTemplates.put(Extension.extendedKeyUsage, new ExtendedKeyUsageJAXB(jaxbProfile.getCertificateProfile().getExtensions().getExtendedKeyUsage()));
        }
        if (jaxbProfile.getCertificateProfile().getExtensions().getCrlDistributionPoints() != null) {
            extensionTemplates.put(Extension.cRLDistributionPoints, new CRLDistributionPointJAXB(jaxbProfile.getCertificateProfile().getExtensions().getCrlDistributionPoints(), caCertificate));
        }
        if (jaxbProfile.getCertificateProfile().getExtensions().getBasicConstraints() != null) {
            extensionTemplates.put(Extension.basicConstraints, new BasicConstraintsJAXB(jaxbProfile.getCertificateProfile().getExtensions().getBasicConstraints()));
        }
        if (jaxbProfile.getCertificateProfile().getExtensions().getQualifiedStatements() != null) {
            extensionTemplates.put(Extension.qCStatements, new QualifiedStatementsJAXB(jaxbProfile.getCertificateProfile().getExtensions().getQualifiedStatements()));
        }
        if (jaxbProfile.getCertificateProfile().getExtensions().getCertificateTemplateName() != null) {
            extensionTemplates.put(MicrosoftObjectIdentifiers.microsoftCertTemplateV1, new CertificateTemplateJAXB(jaxbProfile.getCertificateProfile().getExtensions().getCertificateTemplateName()));
        }
        if (jaxbProfile.getCertificateProfile().getExtensions().getAuthorityInfoAccess() != null) {
            extensionTemplates.put(Extension.authorityInfoAccess, new AuthorityInfoAccessJAXB(jaxbProfile.getCertificateProfile().getExtensions().getAuthorityInfoAccess()));
        }
    }

    private Result validateCertificateNBefore(CertTemplate certTemplate) throws ProfileException {

        DateTime nBefore = null;
        Result result = new Result(Result.Decisions.INVALID, null);

        if ((certTemplate.getValidity() != null) &&
            (certTemplate.getValidity().getNotBefore() != null)) {
            nBefore = new DateTime(certTemplate.getValidity().getNotBefore().getDate(), DateTimeZone.UTC);
        }

        if ((jaxbProfile.getCertificateProfile() != null) &&
            (jaxbProfile.getCertificateProfile().getCertificateValidityProfile() != null))  {
            JAXBDateWithOverRule date = jaxbProfile.getCertificateProfile().getCertificateValidityProfile().getNotBefore();

            if (date == null ||
                ((nBefore != null) && 
                 (nBefore.getMillis() >= date.getDate().getMillis()) &&
                 !date.getOverrule())) {
                logger.debug("Check Not Before [null|null|" + nBefore.toString() + "]");
                result.setDecision(Result.Decisions.VALID);
                result.setValue(nBefore);
            }
            else if (date.getOverrule()) {
                logger.debug("Check Not Before [" + date.getDate().toString() + "|" + date.getOverrule().toString() + "|" + nBefore.toString() + "]");
                result.setDecision(Result.Decisions.OVERRULED);
                result.setValue(date.getDate());
            }
            else {
                logger.debug("Check Not Before [" + date.getDate().toString() + "|" + date.getOverrule().toString() + "|" + nBefore.toString() + "]");
                result.setDecision(Result.Decisions.INVALID);
                result.setValue(String.valueOf("Invalid Not Before [" + date.getDate().toString() + ":" + nBefore.toString() + "]"));
            }
        }
        else {
            throw new ProfileException("Corrupt profile in validity section: [" + String.valueOf(jaxbProfile.getId()) + ":"
                + jaxbProfile.getName() + "]");
        }

        return result;
    }

    private Result validateCertificateNAfter(CertTemplate certTemplate) throws ProfileException {

        DateTime nAfter = null;
        Result result = new Result(Result.Decisions.INVALID, null);

        if ((certTemplate.getValidity() != null) &&
            (certTemplate.getValidity().getNotAfter() != null)) {
            nAfter = new DateTime(certTemplate.getValidity().getNotAfter().getDate(), DateTimeZone.UTC);
        }

        if ((jaxbProfile.getCertificateProfile() != null) &&
            (jaxbProfile.getCertificateProfile().getCertificateValidityProfile() != null))  {
            JAXBDateWithOverRule date = jaxbProfile.getCertificateProfile().getCertificateValidityProfile().getNotAfter();

            if (date == null ||
                ((nAfter != null) && 
                 (nAfter.getMillis() <= date.getDate().getMillis())
                 && !date.getOverrule())) {
                logger.debug("Check Not After [null|null|" + nAfter.toString() + "]");
                result.setDecision(Result.Decisions.VALID);
                result.setValue(nAfter);
            }
            else if (date.getOverrule()) {
                logger.debug("Check Not After [" + date.getDate().toString() + "|" + date.getOverrule().toString() + "|" + nAfter.toString() + "]");
                result.setDecision(Result.Decisions.OVERRULED);
                result.setValue(date.getDate());
            }
            else {
                logger.debug("Check Not After [" + date.getDate().toString() + "|" + date.getOverrule().toString() + "|" + nAfter.toString() + "]");
                result.setDecision(Result.Decisions.INVALID);
                result.setValue(String.valueOf("Invalid Not After [" + date.getDate().toString() + ":" + nAfter.toString() + "]"));
            }

        }
        else {
            throw new ProfileException("Corrupt profile in validity section: [" + String.valueOf(jaxbProfile.getId()) + ":"
                + jaxbProfile.getName() + "]");
        }

        return result;
    }

    @Override
    public Result validateCertificateValidity(CertTemplate certTemplate) throws ProfileException {

        DateTime nBefore = null;
        DateTime nAfter = null;
        Result result = new Result(Result.Decisions.INVALID, String.valueOf("Invalid validity"));

        Result resultNBefore = validateCertificateNBefore(certTemplate); 
        if (resultNBefore.getDecision() != Result.Decisions.INVALID) {
        	nBefore = (DateTime)resultNBefore.getValue();

        	Result resultNAfter = validateCertificateNAfter(certTemplate);
        	if (resultNAfter.getDecision() != Result.Decisions.INVALID) {
        		nAfter = (DateTime)resultNAfter.getValue();

        		if ((jaxbProfile.getCertificateProfile() != null) &&
			        (jaxbProfile.getCertificateProfile().getCertificateValidityProfile() != null))  {
        			
        			Integer minDays = jaxbProfile.getCertificateProfile().getCertificateValidityProfile().getMinimumDuration();
        			Integer maxDays = jaxbProfile.getCertificateProfile().getCertificateValidityProfile().getMaximumDuration();
        			int numDays = (new Duration(nBefore, nAfter)).toStandardDays().getDays();

	                if (minDays != null && numDays < minDays) {
	                    result.setDecision(Result.Decisions.INVALID);
	                    result.setValue(String.valueOf("Invalid minimum duration [" + String.valueOf(numDays) + "]"));
	                }
	                else if (maxDays != null && numDays > maxDays) {
	                    result.setDecision(Result.Decisions.INVALID);
	                    result.setValue(String.valueOf("Invalid maximum duration [" + String.valueOf(numDays) + "]"));
	                }
	                else {
	                	result = new Result(resultNBefore.getDecision() == Result.Decisions.OVERRULED ? Result.Decisions.OVERRULED : resultNAfter.getDecision(),
	                			new OptionalValidity(
	    	                   		new Time((Date)nBefore.toDate()), 
	    	                   		new Time((Date)nAfter.toDate())));	                	
	                }
        		}
        		else {
        			result = new Result(resultNBefore.getDecision() == Result.Decisions.OVERRULED ? Result.Decisions.OVERRULED : resultNAfter.getDecision(),
        					new OptionalValidity(
    	                   		new Time((Date)nBefore.toDate()), 
    	                   		new Time((Date)nAfter.toDate())));
        		}
        	}
        	else {
        		result = resultNAfter;
        	}
        } else {
    		result = resultNBefore;        	
        }

        return result;
    }

    @Override
    public Result validateCertificateKeyLength(CertTemplate certTemplate) throws ProfileException, IOException {
        Result result = new Result(Result.Decisions.INVALID, null);

        if ((jaxbProfile.getCertificateProfile() != null) &&
            (jaxbProfile.getCertificateProfile().getKeyLengthProfile() != null))  {
            JAXBKeyLength jaxbKeyLength = jaxbProfile.getCertificateProfile().getKeyLengthProfile();
            Integer minKeyLength = jaxbKeyLength.getMinimumKeyLength();
            Integer maxKeyLength = jaxbKeyLength.getMaximumKeyLength();

            if (certTemplate.getPublicKey() == null) {
            	// Empty public key
            	Integer keyLength = jaxbKeyLength.getMaximumKeyLength();
            	if (keyLength == null) {
            		keyLength = jaxbKeyLength.getMinimumKeyLength();
            		if (keyLength == null) {
            			logger.error("Empty public key and no key length defined.");
            			result.setDecision(Result.Decisions.INVALID);
            			result.setValue(null);
            		}
            		else {
                		// Minimum KeyLength configuration
            			logger.info("Minimum KeyLength configuration.");
            			result.setDecision(Result.Decisions.VALID);
                        result.setValue(keyLength);            		
            		}
            	}
            	else {
            		// Maximum KeyLength configuration
        			logger.info("Maximum KeyLength configuration.");
                    result.setDecision(Result.Decisions.VALID);
                    result.setValue(keyLength);            		
            	}
            }
            else {
            	// Public key is defined
                RSAKeyParameters param=(RSAKeyParameters) PublicKeyFactory.createKey(certTemplate.getPublicKey());
                int keyLength = param.getModulus().bitLength();

                logger.debug("Check keylength [" + String.valueOf(minKeyLength) + "|" + String.valueOf(maxKeyLength) + "|" + String.valueOf(keyLength) + "|");
                if (minKeyLength != null && keyLength < minKeyLength ) {
                    result.setDecision(Result.Decisions.INVALID);
                    result.setValue(String.valueOf("Invalid minimum key length [" + String.valueOf(minKeyLength) + ":"
                        + String.valueOf(keyLength) + "]"));
                }
                else if (maxKeyLength != null && keyLength > maxKeyLength) {
                    result.setDecision(Result.Decisions.INVALID);
                    result.setValue(String.valueOf("Invalid maximum key length [" + String.valueOf(maxKeyLength) + ":"
                        + String.valueOf(keyLength) + "]"));
                }
                else {
                    result.setDecision(Result.Decisions.VALID);
                    result.setValue(keyLength);
                }            	
            }
        }
        else {
            throw new ProfileException("Corrupt profile in key length section: [" + String.valueOf(jaxbProfile.getId()) + ":"
                + jaxbProfile.getName() + "]");
        }

        return result;
    }

    @Override
    public String getCertificateSignatureAlgorithm() throws ProfileException {
        String result = "Unknown";

        if (jaxbProfile.getCertificateProfile() != null) {
            String algorithm = jaxbProfile.getCertificateProfile().getAlgorithm();
            if (algorithm == null) {
                result = "SHA256WithRSAEncryption";
            }
            else if (algorithm.equalsIgnoreCase("MD5")) {
                result = "MD5WITHRSAENCRYPTION";
            }
            else if (algorithm.equalsIgnoreCase("SHA-1")) {
                result = "SHA1WithRSAEncryption";
            }
            else {
                result = algorithm;
            }
        }
        else {
            throw new ProfileException("Corrupt profile in key length section: [" + String.valueOf(jaxbProfile.getId()) + ":"
                + jaxbProfile.getName() + "]");
        }

        return result;
    }

    @Override
    public String getKeyAlgorithm() throws ProfileException {
        return "RSA";
    }

    @Override
    public boolean usePrivateKeyEscrow() throws ProfileException {
        boolean result = false;

        if (jaxbProfile.getCertificateProfile() != null) {
            String keys = jaxbProfile.getCertificateProfile().getKeys();

            logger.debug("Keys entry [" + String.valueOf(keys) + "]");
            if (keys != null && keys.equalsIgnoreCase("Store Private Keys")) {
                result = true;
            }
        }
        else {
            throw new ProfileException("Corrupt profile in key length section: [" + String.valueOf(jaxbProfile.getId()) + ":"
                + jaxbProfile.getName() + "]");
        }

        return result;
    }

    @Override
    public long certificatePublicationDelay() throws ProfileException {
        long result = 0;

        if (jaxbProfile.getCertificateProfile() != null) {
            PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
                .printZeroAlways()
                .appendHours()
                .appendSeparator(":")
                .appendMinutes()
                .toFormatter();
            String publication = jaxbProfile.getCertificateProfile().getPublication();
            if (publication != null) {
                Period period = periodFormatter.parsePeriod(publication);
                result = period.toStandardSeconds().getSeconds() * 1000;
                logger.debug("Publication Delay entry [" + period.toString(periodFormatter) + ":" + String.valueOf(result) + "]");
            }
        }
        else {
            throw new ProfileException("Corrupt profile in key length section: [" + String.valueOf(jaxbProfile.getId()) + ":"
                + jaxbProfile.getName() + "]");
        }

        return result;
    }

    @Override
    public List<Result> validateCertificateExtensions(CertTemplate certTemplate) throws IOException, NoSuchAlgorithmException, ProfileException {
        List<Result> results = new ArrayList<Result>();

        Extensions extensions = certTemplate.getExtensions();

        if (extensions != null) {
            // Validate the extensions
            for (ASN1ObjectIdentifier oid : extensions.getExtensionOIDs()) {
                ExtensionTemplate extensionTemplate = extensionTemplates.get(oid);
                if (extensionTemplate != null) {
                    extensionTemplate.initialize(certTemplate);
                    results.add(extensionTemplate.validateExtension(extensions.getExtension(oid)));
                    logger.debug("Validated extension");
                }
                else {
                    // Add unknown extensions
                    results.add(new Result(Result.Decisions.VALID, extensions.getExtension(oid)));
                    logger.debug("Copied original extension validation missing");
                }
            }
        }

        // Add the missing extensions
        for (Map.Entry<ASN1ObjectIdentifier, ExtensionTemplate> entry: extensionTemplates.entrySet()) {
            Extension extension = extensions == null ? null : extensions.getExtension(entry.getKey());

            if (extension == null) {
                entry.getValue().initialize(certTemplate);
                Result temp = entry.getValue().getExtension();
                if (temp != null) {
                    results.add(temp);
                    logger.debug("Adding extension");
                }
            }
        }

        return results;
    }
}
