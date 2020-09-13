package webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import webservice.model.calldto.CallDto;
import webservice.model.calljson.CallJson;
import webservice.model.callstatisticsjson.CallsStatisticsJson;
import webservice.model.mapper.CallMapper;
import webservice.service.call.CallService;
import webservice.service.statistics.StatisticsService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("talkdesk/api/call")
public class CallController {
    @Autowired
    private CallService callService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private CallMapper callMapper;

    @PostMapping("/create-calls")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CallJson> createCalls(@Valid @RequestBody List<CallJson> callJsonList) {
        List<CallDto> callDtoList = new ArrayList<>();

        for (CallJson callJson : callJsonList) {
            CallDto callDto = callMapper.convertCallJsonToCallDto(callJson);

            callDtoList.add(callDto);
        }

        return callService.createCalls(callDtoList).stream().map(callMapper::convertCallDtoToCallJson).collect(
                Collectors.toList());
    }

    @GetMapping("/get-calls")
    @ResponseStatus(HttpStatus.OK)
    public List<CallJson> getAllCalls(@RequestParam int page,
                                      @RequestParam(required = false, value = "type") String callType) {
        return callService.getAllCalls(page, callType).stream().map(callMapper::convertCallDtoToCallJson).collect(
                Collectors.toList());
    }

    @DeleteMapping("/delete-call/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCallById(@PathVariable(name = "id") Long id) {
        callService.deleteCallById(id);
    }

    @GetMapping("/statistics-calls")
    @ResponseStatus(HttpStatus.OK)
    public List<CallsStatisticsJson> getCallsStatistics() {
        return statisticsService.getCallsStatistics();
    }
}
