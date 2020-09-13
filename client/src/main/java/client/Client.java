package client;

import client.json.CallJson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static client.utils.Urls.*;

public final class Client {
    public String createCalls(List<CallJson> callJsonList) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.postForEntity(CREATE_CALLS_URI, callJsonList, String.class);

        return result.getBody();
    }

    public String getAllCalls(int page) throws URISyntaxException {
        URI uriGetAllCallsWithoutCallType = new URI(GET_ALL_CALLS + "page=" + page);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(uriGetAllCallsWithoutCallType, String.class);

        return result.getBody();
    }

    public String getAllCalls(int page, String callType) throws URISyntaxException {
        URI uriGetAllCallsWithCallType = new URI(GET_ALL_CALLS + "page=" + page + "&type=" + callType);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(uriGetAllCallsWithCallType, String.class);

        return result.getBody();
    }

    public void deleteCallById(Long id) throws URISyntaxException {
        URI uriGetAllCallsWithCallType = new URI(DELETE_CALL_BY_ID + id);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(uriGetAllCallsWithCallType);
    }

    public String getCallsStatistics() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(GET_CALLS_STATISTICS, String.class);

        return result.getBody();
    }
}
