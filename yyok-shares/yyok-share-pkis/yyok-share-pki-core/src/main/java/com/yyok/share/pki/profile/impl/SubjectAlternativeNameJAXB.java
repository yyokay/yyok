package com.yyok.share.pki.profile.impl;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.crmf.CertTemplate;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.GeneralNamesBuilder;
import com.yyok.share.pki.profile.ExtensionTemplate;
import com.yyok.share.pki.profile.ProfileException;
import com.yyok.share.pki.profile.Result;
import com.yyok.share.pki.profile.jaxb.JAXBSubjectAlternativeName;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Adapter for the JAXB implementation
 *
 */
public class SubjectAlternativeNameJAXB implements ExtensionTemplate {

    private boolean[] keep = new boolean[9];

    public SubjectAlternativeNameJAXB(JAXBSubjectAlternativeName jaxbSubjectAlternativeName) {
        for (int i=0; i<keep.length; i++) keep[i] = false;
        if (jaxbSubjectAlternativeName.getOtherName() == null || jaxbSubjectAlternativeName.getKeepDName())
            keep[GeneralName.directoryName] = true;
        if (jaxbSubjectAlternativeName.getOtherName() == null || jaxbSubjectAlternativeName.getKeepDomainName())
            keep[GeneralName.dNSName] = true;
        if (jaxbSubjectAlternativeName.getOtherName() == null || jaxbSubjectAlternativeName.getKeepEmail())
            keep[GeneralName.rfc822Name] = true;
        if (jaxbSubjectAlternativeName.getOtherName() == null || jaxbSubjectAlternativeName.getKeepIPAdress())
            keep[GeneralName.iPAddress] = true;
        if (jaxbSubjectAlternativeName.getOtherName() == null || jaxbSubjectAlternativeName.getKeepURL())
            keep[GeneralName.uniformResourceIdentifier] = true;
        if (jaxbSubjectAlternativeName.getOtherName() == null || !jaxbSubjectAlternativeName.getOtherName().isEmpty()) {
            keep[GeneralName.otherName] = true;
        }
    }

    @Override
    public ASN1ObjectIdentifier getExtensionOID() {
        return Extension.subjectAlternativeName;
    }

    @Override
    public Result validateExtension(Extension extension) throws IOException, NoSuchAlgorithmException {
        Result result = new Result(Result.Decisions.VALID, null);

        GeneralNamesBuilder generalNamesBuilder = new GeneralNamesBuilder();
        GeneralNames generalNames = GeneralNames.getInstance(extension.getParsedValue());
        for (GeneralName generalName : generalNames.getNames()) {
            if ((generalName.getTagNo() < keep.length) && keep[generalName.getTagNo()]) {
                generalNamesBuilder.addName(generalName);
            }
            else {
                result.setDecision(Result.Decisions.OVERRULED);
            }
        }
        if (extension.isCritical())
            result.setDecision(Result.Decisions.OVERRULED);

        GeneralNames temp = generalNamesBuilder.build();
        Extension tempExtension = new Extension(Extension.subjectAlternativeName,
            false,
            new DEROctetString(temp));

        result.setValue(tempExtension);

        return result;
    }

    @Override
    public void initialize(CertTemplate certTemplate) throws ProfileException {

    }

    @Override
    public Result getExtension() throws IOException, NoSuchAlgorithmException {
        return null;
    }

    @Override
    public Boolean getCriticalility() {
        return false;
    }
}
