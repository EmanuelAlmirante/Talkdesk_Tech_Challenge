package application.service.call;

import application.exception.BusinessException;
import application.model.calldto.CallDto;
import application.model.callstatisticsjson.CallsStatisticsJson;
import application.model.callstatisticsjson.TotalNumberOfCallsByCalleeNumber;
import application.model.callstatisticsjson.TotalNumberOfCallsByCallerNumber;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static application.service.call.CallConstants.*;

@Service
public class CallServiceImpl implements CallService {
    private final CallRepository callRepository;

    @Autowired
    public CallServiceImpl(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    @Override
    public List<CallDto> createCall(List<CallDto> callDtoList) {
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

    @Override
    public List<CallsStatisticsJson> getCallsStatistics() {
        List<CallDto> callDtoList = callRepository.findAll();

        List<CallsStatisticsJson> callsStatisticsJsonList = computeCallsStatistics(callDtoList);

        return callsStatisticsJsonList;
    }

    private void verifyValidityOfCall(CallDto callDto) {
        if (callDto.getCallerNumber() == null || callDto.getCallerNumber() < 1) {
            throw new BusinessException("Caller number must not be null and must be a positive number",
                                        callDto.getCallerNumber() != null ? callDto.getCallerNumber().toString() :
                                        null);
        }

        if (callDto.getCalleeNumber() == null || callDto.getCalleeNumber() < 1) {
            throw new BusinessException("Callee number must not be null and must be a positive number",
                                        callDto.getCalleeNumber() != null ? callDto.getCalleeNumber().toString() :
                                        null);
        }

        if (callDto.getCallStartTimestamp() == null || callDto.getCallStartTimestamp() < 1) {
            throw new BusinessException("Start timestamp must not be null and must be bigger than 0!",
                                        callDto.getCallStartTimestamp() != null ?
                                        callDto.getCallStartTimestamp().toString() : null);
        }

        if (callDto.getCallEndTimestamp() == null || callDto.getCallEndTimestamp() < 1) {
            throw new BusinessException("End timestamp must not be null and must be bigger than 0!",
                                        callDto.getCallEndTimestamp() != null ?
                                        callDto.getCallEndTimestamp().toString() : null);
        }

        if (callDto.getCallStartTimestamp() > callDto.getCallEndTimestamp()) {
            throw new BusinessException("Start timestamp must be bigger than end timestamp!",
                                        callDto.getCallStartTimestamp().toString(),
                                        callDto.getCallEndTimestamp().toString());
        }

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
                totalCallCost = NUMBER_OF_MINUTES_BEFORE_PRICE_REDUCTION * COST_OF_CALL_FIRST_5_MINUTES
                                + (callDurationMinutes - NUMBER_OF_MINUTES_BEFORE_PRICE_REDUCTION)
                                  * COST_OF_CALL_AFTER_5_MINUTES;
            } else {
                totalCallCost = callDurationMinutes * COST_OF_CALL_FIRST_5_MINUTES;

            }

            totalCallCost = new BigDecimal(totalCallCost).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }

        callDto.setCallCost(totalCallCost);
    }

    private List<CallsStatisticsJson> computeCallsStatistics(List<CallDto> callDtoList) {
        Map<LocalDate, List<CallDto>> callDtoListAggregatedByDayMap = callDtoListAggregatedByDay(callDtoList);

        List<CallsStatisticsJson> callsStatisticsJsonList = new ArrayList<>();

        for (Map.Entry<LocalDate, List<CallDto>> callDtoListAggregatedByDayEntry : callDtoListAggregatedByDayMap
                .entrySet()) {
            List<CallDto> callDtoListEntryValues = callDtoListAggregatedByDayEntry.getValue();

            Long totalCallsDurationInboundSum = totalCallsDurationInboundSum(callDtoListEntryValues);
            Long totalCallsDurationOutboundSum = totalCallsDurationOutboundSum(callDtoListEntryValues);

            List<TotalNumberOfCallsByCallerNumber> totalNumberOfCallsByCallerNumberList =
                    callDtoListAggregatedByCallerNumber(callDtoListEntryValues);
            List<TotalNumberOfCallsByCalleeNumber> totalNumberOfCallsByCalleeNumberList =
                    callDtoListAggregatedByCalleeNumber(callDtoListEntryValues);

            Double totalCallsCostSum = totalCallsCostSum(callDtoListEntryValues);

            CallsStatisticsJson callsStatisticsJson = CallsStatisticsJson.Builder
                    .callStatisticsJsonWith()
                    .withDay(callDtoListAggregatedByDayEntry.getKey())
                    .withTotalCallsDurationInbound(totalCallsDurationInboundSum)
                    .withTotalCallsDurationOutbound(totalCallsDurationOutboundSum)
                    .withTotalNumberOfCalls((long) callDtoListEntryValues.size())
                    .withTotalNumberOfCallsByCallerNumber(totalNumberOfCallsByCallerNumberList)
                    .withTotalNumberOfCallsByCalleeNumber(totalNumberOfCallsByCalleeNumberList)
                    .withTotalCallsCost(totalCallsCostSum)
                    .build();

            callsStatisticsJsonList.add(callsStatisticsJson);
        }

        return callsStatisticsJsonList.stream().sorted(Comparator.comparing(CallsStatisticsJson::getDay)).collect(
                Collectors.toList());
    }

    private Map<LocalDate, List<CallDto>> callDtoListAggregatedByDay(List<CallDto> callDtoList) {
        return callDtoList.stream().collect(Collectors.groupingBy(CallDto::getCallStartDay));
    }

    private Long totalCallsDurationInboundSum(List<CallDto> callDtoListEntryValues) {
        return callDtoListEntryValues.stream().filter(callDto -> callDto.getCallType().equals(CALL_TYPE_INBOUND))
                                     .mapToLong(callDto -> callDto.getCallDuration().toSeconds()).sum();
    }

    private Long totalCallsDurationOutboundSum(List<CallDto> callDtoListEntryValues) {
        return callDtoListEntryValues.stream().filter(callDto -> callDto.getCallType().equals(CALL_TYPE_OUTBOUND))
                                     .mapToLong(callDto -> callDto.getCallDuration().toSeconds()).sum();
    }

    private List<TotalNumberOfCallsByCallerNumber> callDtoListAggregatedByCallerNumber(
            List<CallDto> callDtoListEntryValues) {
        Map<Long, List<CallDto>> callDtoListAggregatedByCallerNumberMap = callDtoListEntryValues.stream().collect(
                Collectors.groupingBy(CallDto::getCallerNumber));

        List<TotalNumberOfCallsByCallerNumber> totalNumberOfCallsByCallerNumberList = new ArrayList<>();

        for (Map.Entry<Long, List<CallDto>> callDtoListAggregatedByCallerNumberEntry :
                callDtoListAggregatedByCallerNumberMap.entrySet()) {
            TotalNumberOfCallsByCallerNumber totalNumberOfCallsByCallerNumber =
                    TotalNumberOfCallsByCallerNumber.Builder
                            .totalNumberOfCallsByCallerNumberWith()
                            .withCallerNumber(callDtoListAggregatedByCallerNumberEntry.getKey())
                            .withTotalNumberOfCalls((long) callDtoListAggregatedByCallerNumberEntry.getValue().size())
                            .build();

            totalNumberOfCallsByCallerNumberList.add(totalNumberOfCallsByCallerNumber);
        }

        return totalNumberOfCallsByCallerNumberList;
    }

    private List<TotalNumberOfCallsByCalleeNumber> callDtoListAggregatedByCalleeNumber(
            List<CallDto> callDtoListEntryValues) {
        Map<Long, List<CallDto>> callDtoListAggregatedByCalleeNumberMap = callDtoListEntryValues.stream().collect(
                Collectors.groupingBy(CallDto::getCalleeNumber));

        List<TotalNumberOfCallsByCalleeNumber> totalNumberOfCallsByCalleeNumberList = new ArrayList<>();

        for (Map.Entry<Long, List<CallDto>> callDtoListAggregatedByCalleeNumberEntry :
                callDtoListAggregatedByCalleeNumberMap
                        .entrySet()) {
            TotalNumberOfCallsByCalleeNumber totalNumberOfCallsByCalleeNumber =
                    TotalNumberOfCallsByCalleeNumber.Builder.totalNumberOfCallsByCalleeNumberWith()
                                                            .withCalleeNumber(
                                                                    callDtoListAggregatedByCalleeNumberEntry.getKey())
                                                            .withTotalNumberOfCalls(
                                                                    (long) callDtoListAggregatedByCalleeNumberEntry
                                                                            .getValue()
                                                                            .size())
                                                            .build();

            totalNumberOfCallsByCalleeNumberList.add(totalNumberOfCallsByCalleeNumber);

        }

        return totalNumberOfCallsByCalleeNumberList;
    }

    private Double totalCallsCostSum(List<CallDto> callDtoListEntryValue) {
        return callDtoListEntryValue.stream().mapToDouble(CallDto::getCallCost).sum();
    }
}
