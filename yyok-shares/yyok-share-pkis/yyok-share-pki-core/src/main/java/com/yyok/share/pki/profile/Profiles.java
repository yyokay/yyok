package com.yyok.share.pki.profile;

/**
 * Interface the profile data
 */
public interface Profiles {

    public Profile get(String profileName);

    public Profile get(int profileID);
}
