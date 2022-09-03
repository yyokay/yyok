package com.yyok.share.pki.profile.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * <Certificate>
 *   <Validity>
 *   ...
 *   </Validity>
 *   <Key_Length>
 *   ...
 *   </Key_Length>
 *   <Algorithm>MD5</Algorithm>
 *   <Keys>Store Private Keys</Keys>
 *   <Publication>00:20</Publication>
 *   <Extensions>
 *   ...
 *   </Extensions>
 * </Certificate>
 *
 * Date: 23/12/13
 * Hour: 23:36
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JAXBCertificate {

    @XmlElement(name="Validity")
    private JAXBCertificateValidity certificateValidityProfile;

    @XmlElement(name="Key_Length")
    private JAXBKeyLength keyLengthProfile;

    @XmlElement(name="Algorithm")
    private String algorithm;

    @XmlElement(name="Keys")
    private String keys;

    @XmlElement(name="Publication")
    private String publication;

    public JAXBCertificateExtensions getExtensions() {
        return extensions;
    }

    public void setExtensions(JAXBCertificateExtensions extensions) {
        this.extensions = extensions;
    }

    @XmlElement(name="Extensions")
    private JAXBCertificateExtensions extensions;

    public JAXBCertificateValidity getCertificateValidityProfile() {
        return certificateValidityProfile;
    }

    public void setCertificateValidityProfile(JAXBCertificateValidity certificateValidityProfile) {
        this.certificateValidityProfile = certificateValidityProfile;
    }

    public JAXBKeyLength getKeyLengthProfile() {
        return keyLengthProfile;
    }

    public void setKeyLengthProfile(JAXBKeyLength keyLengthProfile) {
        this.keyLengthProfile = keyLengthProfile;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }


}
