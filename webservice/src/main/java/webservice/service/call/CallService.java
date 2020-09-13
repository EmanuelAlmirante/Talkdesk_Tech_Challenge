package webservice.service.call;

import webservice.model.calldto.CallDto;

import java.util.List;

public interface CallService {
    List<CallDto> createCalls(List<CallDto> callDtoList);

    List<CallDto> getAllCalls(int page, String callType);

    void deleteCallById(Long id);
}
