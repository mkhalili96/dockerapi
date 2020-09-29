package net.seensin.springdockerswarmmanagementapi.common;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class InputStreamHandler {

    public String inputStreamToString(InputStream inputStream) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, UTF_8);
        return writer.toString();
    }

}
