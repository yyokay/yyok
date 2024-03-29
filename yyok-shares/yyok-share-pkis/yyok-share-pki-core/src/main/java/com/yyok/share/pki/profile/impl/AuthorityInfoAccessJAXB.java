package com.yyok.share.pki.profile.impl;

import com.yyok.share.pki.profile.ExtensionTemplate;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.crmf.CertTemplate;
import com.yyok.share.pki.profile.Result;
import com.yyok.share.pki.profile.jaxb.JAXBAccessDescription;
import com.yyok.share.pki.profile.jaxb.JAXBAuthorityInfoAccess;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <Authority_Info_Access>
 *   <Distribution_Point Name="AIA1">
 *     <URL>http://ocsp.cryptable.org</URL>
 *     <Access_Method>1</Access_Method>
 *   </Distribution_Point>
 *   <Distribution_Point Name="AIA2">
 *     <URL>http://www.cryptable.org/rootca.der</URL>
 *     <Access_Method>2</Access_Method>
 *   </Distribution_Point>
 * </Authority_Info_Access>
 *
 */
public class AuthorityInfoAccessJAXB implements ExtensionTemplate {

    private final Logger logger = LoggerFactory.getLogger(AuthorityInfoAccessJAXB.class);

    private Extension authorityInfoAccess;

    public AuthorityInfoAccessJAXB(JAXBAuthorityInfoAccess jaxbAuthorityInfoAccess) throws IOException {

        if (jaxbAuthorityInfoAccess.getAccessDescriptions().size() > 0) {
            ASN1EncodableVector asn1EncodableVector = new ASN1EncodableVector();
            for (JAXBAccessDescription jaxbAccessDescription : jaxbAuthorityInfoAccess.getAccessDescriptions()) {
                if (jaxbAccessDescription.getAccessMethod() == 1) {
                    AccessDescription accessDescription = new AccessDescription(AccessDescription.id_ad_ocsp,
                        new GeneralName(GeneralName.uniformResourceIdentifier, jaxbAccessDescription.getUrl()));
                    asn1EncodableVector.add(accessDescription);
                }
                else if (jaxbAccessDescription.getAccessMethod() == 2) {
                    AccessDescription accessDescription = new AccessDescription(AccessDescription.id_ad_caIssuers,
                        new GeneralName(GeneralName.uniformResourceIdentifier, jaxbAccessDescription.getUrl()));
                    asn1EncodableVector.add(accessDescription);
                }
            }
            ASN1Sequence authorityInformationAccess = new DERSequence(asn1EncodableVector);
            this.authorityInfoAccess = new Extension(Extension.authorityInfoAccess, false, new DEROctetString(authorityInformationAccess));
        }
    }

    @Override
    public ASN1ObjectIdentifier getExtensionOID() {
        return Extension.authorityInfoAccess;
    }

    @Override
    public Result validateExtension(Extension extension) throws IOException {
        return new Result(Result.Decisions.OVERRULED, authorityInfoAccess);
    }

    @Override
    public void initialize(CertTemplate certTemplate) {

    }

    @Override
    public Result getExtension() throws IOException {
        return new Result(Result.Decisions.VALID, authorityInfoAccess);
    }

    @Override
    public Boolean getCriticalility() {
        return false;
    }
}
