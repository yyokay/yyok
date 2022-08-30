package com.yyok.share.pki.profile.impl;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.crmf.CertTemplate;
import com.yyok.share.pki.profile.ExtensionTemplate;
import com.yyok.share.pki.profile.Result;
import com.yyok.share.pki.profile.jaxb.JAXBIssuerAlternativeName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.GeneralNamesBuilder;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class IssuerAlternativeNameJAXB implements ExtensionTemplate {

    final private Extension issuerAlternativeName;

    public IssuerAlternativeNameJAXB(JAXBIssuerAlternativeName jaxbIssuerAlternativeName) throws IOException {
        GeneralNamesBuilder generalNamesBuilder = new GeneralNamesBuilder();
        // rfc822Name                      [1]     IA5String,
        if (jaxbIssuerAlternativeName.geteMail() != null) {
            generalNamesBuilder.addName(new GeneralName(GeneralName.rfc822Name, jaxbIssuerAlternativeName.geteMail()));
        }// dNSName                         [2]     IA5String,
        if (jaxbIssuerAlternativeName.getDomainName() != null) {
            generalNamesBuilder.addName(new GeneralName(GeneralName.dNSName, jaxbIssuerAlternativeName.getDomainName()));
        }// directoryName                   [4]     Name,
        if (jaxbIssuerAlternativeName.getdName() != null) {
            generalNamesBuilder.addName(new GeneralName(GeneralName.directoryName, jaxbIssuerAlternativeName.getdName()));
        }// uniformResourceIdentifier       [6]     IA5String,
        if (jaxbIssuerAlternativeName.getUrl() != null) {
            generalNamesBuilder.addName(new GeneralName(GeneralName.uniformResourceIdentifier, jaxbIssuerAlternativeName.getUrl()));
        }// iPAddress                       [7]     OCTET STRING,
        if (jaxbIssuerAlternativeName.getIpAddress() != null) {
            generalNamesBuilder.addName(new GeneralName(GeneralName.iPAddress, jaxbIssuerAlternativeName.getIpAddress()));
        }
        issuerAlternativeName = new Extension(Extension.issuerAlternativeName,
            false,
            new DEROctetString(generalNamesBuilder.build()));
    }

    @Override
    public ASN1ObjectIdentifier getExtensionOID() {
        return Extension.issuerAlternativeName;
    }

    @Override
    public Result validateExtension(Extension extension) throws IOException, NoSuchAlgorithmException {
        Result result = new Result(Result.Decisions.INVALID, null);
        GeneralNames generalNames = GeneralNames.getInstance(extension.getParsedValue());

        // Validate the extension
        if (extension.isCritical() == false &&
            generalNames.equals(GeneralNames.getInstance(issuerAlternativeName.getParsedValue()))) {
            return new Result(Result.Decisions.VALID, extension);
        }

        return new Result(Result.Decisions.OVERRULED, issuerAlternativeName);
    }

    @Override
    public void initialize(CertTemplate certTemplate) {

    }

    @Override
    public Result getExtension() throws IOException, NoSuchAlgorithmException {
        return new Result(Result.Decisions.VALID, issuerAlternativeName);
    }

    @Override
    public Boolean getCriticalility() {
        return false;
    }
}
