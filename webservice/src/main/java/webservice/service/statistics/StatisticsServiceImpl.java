package webservice.service.statistics;

import webservice.model.calldto.CallDto;
import webservice.model.callstatisticsjson.CallsStatisticsJson;
import webservice.model.callstatisticsjson.TotalNumberOfCallsByCalleeNumber;
import webservice.model.callstatisticsjson.TotalNumberOfCallsByCallerNumber;
import webservice.repository.CallRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static webservice.service.ServiceParameters.CALL_TYPE_INBOUND;
import static webservice.service.ServiceParameters.CALL_TYPE_OUTBOUND;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final CallRepository callRepository;

    public StatisticsServiceImpl(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    @Override
    public List<CallsStatisticsJson> getCallsStatistics() {
        List<CallDto> callDtoList = callRepository.findAll();

        List<CallsStatisticsJson> callsStatisticsJsonList = computeCallsStatisticsJsonList(callDtoList);

        return callsStatisticsJsonList;
    }

    private List<CallsStatisticsJson> computeCallsStatisticsJsonList(List<CallDto> callDtoList) {
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
                    TotalNumberOfCallsByCalleeNumber.Builder
                            .totalNumberOfCallsByCalleeNumberWith()
                            .withCalleeNumber(callDtoListAggregatedByCalleeNumberEntry.getKey())
                            .withTotalNumberOfCalls((long) callDtoListAggregatedByCalleeNumberEntry.getValue().size())
                            .build();

            totalNumberOfCallsByCalleeNumberList.add(totalNumberOfCallsByCalleeNumber);
        }

        return totalNumberOfCallsByCalleeNumberList;
    }

    private Double totalCallsCostSum(List<CallDto> callDtoListEntryValue) {
        return callDtoListEntryValue.stream().mapToDouble(CallDto::getCallCost).sum();
    }
}
