package application.service.call;

import application.exception.BusinessException;
import application.model.calldto.CallDto;
import application.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static application.service.ServiceParameters.*;


@Service
public class CallServiceImpl implements CallService {
    private final CallRepository callRepository;

    @Autowired
    public CallServiceImpl(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    @Override
    public List<CallDto> createCalls(List<CallDto> callDtoList) {
        for (CallDto callDto : callDtoList) {
            verifyValidityOfCall(callDto);
        }

        for (CallDto callDto : callDtoList) {
            setCallParameters(callDto);
        }

        return callRepository.saveAll(callDtoList);
    }

    @Override
    public List<CallDto> getAllCalls(int page, String callType) {
        PageRequest pageRequest = PageRequest.of(page - 1, NUMBER_OF_ELEMENTS_IN_PAGE);
        Page<CallDto> callDtoPage;

        if (callType == null || callType.isEmpty()) {
            callDtoPage = callRepository.findAll(pageRequest);
        } else {
            callDtoPage = callRepository.findByCallType(pageRequest, callType);
        }

        return callDtoPage.getContent();
    }

    @Override
    public void deleteCallById(Long id) {
        callRepository.deleteById(id);
    }

    private void verifyValidityOfCall(CallDto callDto) {
        validateCallerNumber(callDto);
        validateCalleeNumber(callDto);
        validateStartTimestamp(callDto);
        validateEndTimestamp(callDto);
        validateStartTimestampBeforeEndTimestamp(callDto);
        validateCallType(callDto);
    }

    private void validateCallerNumber(CallDto callDto) {
        if (callDto.getCallerNumber() == null || callDto.getCallerNumber() < 1) {
            throw new BusinessException("Caller number must not be empty and must be a positive number!",
                                        callDto.getCallerNumber() != null ? callDto.getCallerNumber().toString() :
                                        null);
        }
    }

    private void validateCalleeNumber(CallDto callDto) {
        if (callDto.getCalleeNumber() == null || callDto.getCalleeNumber() < 1) {
            throw new BusinessException("Callee number must not be empty and must be a positive number!",
                                        callDto.getCalleeNumber() != null ? callDto.getCalleeNumber().toString() :
                                        null);
        }
    }

    private void validateStartTimestamp(CallDto callDto) {
        if (callDto.getCallStartTimestamp() == null || callDto.getCallStartTimestamp() < 1) {
            throw new BusinessException("Start timestamp must not be empty and must be bigger than 0!",
                                        callDto.getCallStartTimestamp() != null ?
                                        callDto.getCallStartTimestamp().toString() : null);
        }
    }

    private void validateEndTimestamp(CallDto callDto) {
        if (callDto.getCallEndTimestamp() == null || callDto.getCallEndTimestamp() < 1) {
            throw new BusinessException("End timestamp must not be empty and must be bigger than 0!",
                                        callDto.getCallEndTimestamp() != null ?
                                        callDto.getCallEndTimestamp().toString() : null);
        }
    }

    private void validateStartTimestampBeforeEndTimestamp(CallDto callDto) {
        if (callDto.getCallStartTimestamp() > callDto.getCallEndTimestamp()) {
            throw new BusinessException("Start timestamp must be before than end timestamp!",
                                        callDto.getCallStartTimestamp().toString(),
                                        callDto.getCallEndTimestamp().toString());
        }
    }

    private void validateCallType(CallDto callDto) {
        if (!callDto.getCallType().equals("Inbound") && !callDto.getCallType().equals("Outbound")) {
            throw new BusinessException("Call type must be Inbound or Outbound!", callDto.getCallType());
        }
    }


    private void setCallParameters(CallDto callDto) {
        convertCallStartTimestampToStartDay(callDto);
        convertCallEndTimestampToEndDay(callDto);
        convertCallStartTimeStampToStartTime(callDto);
        convertCallEndTimeStampToEndTime(callDto);
        calculateCallDurationInSeconds(callDto);
        calculateCallCost(callDto);
    }

    private void convertCallStartTimestampToStartDay(CallDto callDto) {
        long callStartTimestamp = callDto.getCallStartTimestamp() * 1000L;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate startDate = LocalDate.parse(simpleDateFormat.format(callStartTimestamp), dateTimeFormatter);

        callDto.setCallStartDay(startDate);
    }

    private void convertCallEndTimestampToEndDay(CallDto callDto) {
        long callEndTimestamp = callDto.getCallEndTimestamp() * 1000L;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate endDate = LocalDate.parse(simpleDateFormat.format(callEndTimestamp), dateTimeFormatter);

        callDto.setCallEndDay(endDate);
    }

    private void convertCallStartTimeStampToStartTime(CallDto callDto) {
        long callStartTimestamp = callDto.getCallStartTimestamp() * 1000L;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime startTime = LocalTime.parse(simpleDateFormat.format(callStartTimestamp), dateTimeFormatter);

        callDto.setCallStartTime(startTime);
    }

    private void convertCallEndTimeStampToEndTime(CallDto callDto) {
        long callEndTimestamp = callDto.getCallEndTimestamp() * 1000L;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime endTime = LocalTime.parse(simpleDateFormat.format(callEndTimestamp), dateTimeFormatter);

        callDto.setCallEndTime(endTime);
    }

    private void calculateCallDurationInSeconds(CallDto callDto) {
        LocalDateTime localDateTimeStart = LocalDateTime.of(callDto.getCallStartDay().getYear(),
                                                            callDto.getCallStartDay().getMonth(),
                                                            callDto.getCallStartDay().getDayOfMonth(),
                                                            callDto.getCallStartTime().getHour(),
                                                            callDto.getCallStartTime().getMinute(),
                                                            callDto.getCallStartTime().getSecond());

        LocalDateTime localDateTimeEnd = LocalDateTime.of(callDto.getCallEndDay().getYear(),
                                                          callDto.getCallEndDay().getMonth(),
                                                          callDto.getCallEndDay().getDayOfMonth(),
                                                          callDto.getCallEndTime().getHour(),
                                                          callDto.getCallEndTime().getMinute(),
                                                          callDto.getCallEndTime().getSecond());

        Duration callDuration = Duration.between(localDateTimeStart, localDateTimeEnd);

        callDto.setCallDuration(callDuration);
    }

    private void calculateCallCost(CallDto callDto) {
        long callDurationMinutes = callDto.getCallDuration().toMinutes();
        long callDurationSeconds = callDto.getCallDuration().toSecondsPart();

        double totalCallCost = 0;

        if (callDurationSeconds > 0) {
            callDurationMinutes++;
        }

        if (callDto.getCallType().equals(CALL_TYPE_OUTBOUND)) {
            if (callDurationMinutes > NUMBER_OF_MINUTES_BEFORE_PRICE_REDUCTION) {
                totalCallCost = NUMBER_OF_MINUTES_BEFORE_PRICE_REDUCTION * COST_OF_CALL_BEFORE_PRICE_REDUCTION
                                + (callDurationMinutes - NUMBER_OF_MINUTES_BEFORE_PRICE_REDUCTION)
                                  * COST_OF_CALL_AFTER_PRICE_REDUCTION;
            } else {
                totalCallCost = callDurationMinutes * COST_OF_CALL_BEFORE_PRICE_REDUCTION;
            }

            totalCallCost = new BigDecimal(totalCallCost).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }

        callDto.setCallCost(totalCallCost);
    }
}
