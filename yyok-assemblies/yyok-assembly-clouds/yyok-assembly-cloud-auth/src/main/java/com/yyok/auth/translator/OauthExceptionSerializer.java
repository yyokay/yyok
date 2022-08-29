package com.yyok.auth.translator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OauthExceptionSerializer extends StdSerializer<OauthException> {

    public OauthExceptionSerializer() {
        super(OauthException.class);
    }

    @Override
    public void serialize(OauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        gen.writeStringField("msg", value.getMessage());
        gen.writeBooleanField("success", false);
        gen.writeNumberField("timestamp", System.currentTimeMillis());
        gen.writeEndObject();
    }
}
