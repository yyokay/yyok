package com.yyok.share.pki.profile.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * XMLAdapter Yes/No to true/false
 */
public class BooleanLeaveDeleteAdapter extends XmlAdapter<String, Boolean> {

    @Override
    public Boolean unmarshal(String value) {
        return value.equalsIgnoreCase("LEAVE");
    }

    @Override
    public String marshal(Boolean value) {
        return String.valueOf(value ? "Leave" : "Delete");
    }
}
