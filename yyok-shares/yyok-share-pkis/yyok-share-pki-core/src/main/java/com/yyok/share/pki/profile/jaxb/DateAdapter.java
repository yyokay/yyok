package com.yyok.share.pki.profile.jaxb;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Date parser for usage in profile parsing
 *
 */
public class DateAdapter extends XmlAdapter<String, DateTime> {

    private final DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyyMMddHHmmss").withZoneUTC();

    @Override
    public String marshal(DateTime v) throws Exception {
        return dateFormat.print(v);
    }

    @Override
    public DateTime unmarshal(String v) throws Exception {
        return dateFormat.parseDateTime(v);
    }

}
