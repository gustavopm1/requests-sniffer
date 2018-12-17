package com.gustavopm1.requestssniffer.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavopm1.requestssniffer.models.BufferBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

@Service
@Slf4j
public class FileSystemStorage {

    private String dataPath = "./data";

    //@Async
    public void persistRequest(String urlReceived, Map<String, Object> headers, Object body) throws IOException {

        String url = sanitizedUrl(urlReceived);

        String folderPath = String.format("%s/%s", dataPath, url);
        String headerPath = String.format("%s/%s/headers.json", dataPath, url);
        String bodyPath = String.format("%s/%s/body.json", dataPath, url);
        String paramsPath = String.format("%s/%s/params.json", dataPath, url);

        log.info("Persisting it to {} {} {}", folderPath, headerPath, bodyPath);

        if (!Paths.get(folderPath)
                .normalize()
                .toFile()
                .exists()) {
            Paths.get(folderPath)
                    .normalize()
                    .toFile()
                    .mkdirs();
        }

        new ObjectMapper().writeValue(
                Paths.get(headerPath)
                        .normalize()
                        .toFile(),
                headers);

        new ObjectMapper().writeValue(
                Paths.get(bodyPath)
                        .normalize()
                        .toFile(),
                body);

        new ObjectMapper().writeValue(
                Paths.get(paramsPath)
                        .normalize()
                        .toFile(),
                getQueryStringParams(urlReceived));
    }

    public BufferBody loadRequest(String urlReceived) throws IOException {
        String url = sanitizedUrl(urlReceived);
        String folderPath = String.format("%s/%s", dataPath, url);
        String headerPath = String.format("%s/%s/headers.json", dataPath, url);
        String bodyPath = String.format("%s/%s/body.json", dataPath, url);
        String paramsPath = String.format("%s/%s/params.json", dataPath, url);

        if (!Paths.get(folderPath)
                .normalize()
                .toFile()
                .exists()) {
            throw new FileNotFoundException("No file found for that request");
        }

        BufferBody body = BufferBody.builder().url(url).build();

        ObjectMapper mapper = new ObjectMapper();

        body.setHeaders(mapper.readValue(Paths.get(headerPath).normalize().toFile(), Map.class));
        body.setBody(mapper.readValue(Paths.get(bodyPath).normalize().toFile(), Object.class));

        String requestParams = mapper.readValue(Paths.get(paramsPath).normalize().toFile(), String.class);
        if (requestParams != null && !requestParams.isEmpty()) {
            body.setUrl(String.format("%s?%s", body.getUrl(), requestParams));
        }

        return body;

    }

    private String sanitizedUrl(String url) {
        if (url.contains("?")) {
            return url.split("\\?")[0].replace(":", "");
        }
        return url;
    }

    private String getQueryStringParams(String url) {
        if (url.contains("?")) {
            return url.split("\\?")[1];
        }
        return "";
    }

}
