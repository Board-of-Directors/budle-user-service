package ru.nsu.fit.directors.userservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
@Slf4j
public class RequestSenderServiceImpl implements RequestSenderService {

    public Map<String, Object> sendRequest(String phoneNumber) {

        if (!phoneNumber.startsWith("7")) {
            phoneNumber = phoneNumber.substring(1);
        }

        String requestString = "https://api.ucaller.ru/v1.0/initCall?" +
            "phone=" + phoneNumber +
            "&voice=" + "false" +
            "&key=1vvjxSFMby9xJx783gk31AT7UDPEHBdI" +
            "&service_id=317622";

        Map<String, Object> map = null;

        try {
            log.info("Request to UCaller API was sent");
            URL request = new URL(requestString);
            HttpURLConnection connection = (HttpURLConnection) request.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            log.info("Response was received");
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(
                response.toString(), new TypeReference<>() {
                });
            log.debug("Response was: " + response);
            connection.disconnect();

        } catch (IOException exception) {
            log.warn(exception.getMessage());
        }
        return map;
    }
}
