package webservice.model.mapper;

import webservice.model.calldto.CallDto;
import webservice.model.calljson.CallJson;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CallMapper {
    public CallJson convertCallDtoToCallJson(CallDto callDto) {
        if (Objects.isNull(callDto)) {
            return null;
        }

        return CallJson.Builder.callModelWith()
                               .withId(callDto.getId())
                               .withCallerNumber(callDto.getCallerNumber())
                               .withCalleeNumber(callDto.getCalleeNumber())
                               .withStartTimestamp(callDto.getCallStartTimestamp())
                               .withEndTimestamp(callDto.getCallEndTimestamp())
                               .withCallType(callDto.getCallType())
                               .build();
    }

    public CallDto convertCallJsonToCallDto(CallJson callJson) {
        if (Objects.isNull(callJson)) {
            return null;
        }

        return CallDto.Builder.callModelWith()
                              .withCallerNumber(callJson.getCallerNumber())
                              .withCalleeNumber(callJson.getCalleeNumber())
                              .withStartTimestamp(callJson.getCallStartTimestamp())
                              .withEndTimestamp(callJson.getCallEndTimestamp())
                              .withCallType(callJson.getCallType())
                              .build();
    }
}
