package application.service.call;

import application.model.calldto.CallDto;
import application.model.callstatisticsjson.CallsStatisticsJson;

import java.util.List;

public interface CallService {
    List<CallDto> createCall(List<CallDto> callDtoList);

    List<CallDto> getAllCalls(int page, String callType);

    void deleteCallById(Long id);

    List<CallsStatisticsJson> getCallsStatistics();
}
