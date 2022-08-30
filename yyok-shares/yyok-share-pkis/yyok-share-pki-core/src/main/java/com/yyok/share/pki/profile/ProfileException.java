package com.yyok.share.pki.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The exception class when invalid profiles settings are used.
 */
public class ProfileException extends Exception {
    final Logger logger = LoggerFactory.getLogger(ProfileException.class);

    public ProfileException(String s) {
        super(s);
    }
}
