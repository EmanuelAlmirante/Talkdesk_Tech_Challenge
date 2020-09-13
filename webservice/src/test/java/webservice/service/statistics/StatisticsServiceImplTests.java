package webservice.service.statistics;

import webservice.model.calldto.CallDto;
import webservice.model.callstatisticsjson.CallsStatisticsJson;
import webservice.repository.CallRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceImplTests {
    @Mock
    private CallRepository callRepository;

    @InjectMocks
    private StatisticsServiceImpl statisticsServiceImpl;

    public static List<CallDto> callDtoList = new ArrayList<>();
    public static CallDto callDtoCallOne;
    public static CallDto callDtoCallTwo;
    public static CallDto callDtoCallThree;

    @BeforeClass
    public static void setup() {
        Long callerNumberCallOne = 123456789L;
        Long calleeNumberCallOne = 987654321L;
        Long callStartTimestampCallOne = 1599909010L;
        Long callEndTimestampCallOne = 1599942944L;
        String callTypeCallOne = "Inbound";
        LocalDate callStartDayCallOne = LocalDate.of(2020, Month.SEPTEMBER, 12);
        LocalDate callEndDayCallOne = LocalDate.of(2020, Month.SEPTEMBER, 12);
        LocalTime callStartTimeCallOne = LocalTime.of(12, 10, 10);
        LocalTime callEndTimeCallOne = LocalTime.of(21, 35, 44);
        Duration callDurationCallOne = Duration.between(callStartTimeCallOne, callEndTimeCallOne);
        Double callCostCallOne = 0.0;

        callDtoCallOne = CallDto.Builder.callModelWith()
                                        .withCallerNumber(callerNumberCallOne)
                                        .withCalleeNumber(calleeNumberCallOne)
                                        .withStartTimestamp(callStartTimestampCallOne)
                                        .withEndTimestamp(callEndTimestampCallOne)
                                        .withCallType(callTypeCallOne)
                                        .withCallStartDay(callStartDayCallOne)
                                        .withCallEndDay(callEndDayCallOne)
                                        .withCallStartTime(callStartTimeCallOne)
                                        .withCallEndTime(callEndTimeCallOne)
                                        .withCallDuration(callDurationCallOne)
                                        .withCallCost(callCostCallOne)
                                        .build();

        Long callerNumberCallTwo = 987654321L;
        Long calleeNumberCallTwo = 123456789L;
        Long callStartTimestampCallTwo = 1599909010L;
        Long callEndTimestampCallTwo = 1599942944L;
        String callTypeCallTwo = "Outbound";
        LocalDate callStartDayCallTwo = callStartDayCallOne;
        LocalDate callEndDayCallTwo = callEndDayCallOne;
        LocalTime callStartTimeCallTwo = LocalTime.of(12, 10, 10);
        LocalTime callEndTimeCallTwo = LocalTime.of(21, 35, 44);
        Duration callDurationCallTwo = Duration.between(callStartTimeCallTwo, callEndTimeCallTwo);
        Double callCostCallTwo = 28.55;

        callDtoCallTwo = CallDto.Builder.callModelWith()
                                        .withCallerNumber(callerNumberCallTwo)
                                        .withCalleeNumber(calleeNumberCallTwo)
                                        .withStartTimestamp(callStartTimestampCallTwo)
                                        .withEndTimestamp(callEndTimestampCallTwo)
                                        .withCallType(callTypeCallTwo)
                                        .withCallStartDay(callStartDayCallTwo)
                                        .withCallEndDay(callEndDayCallTwo)
                                        .withCallStartTime(callStartTimeCallTwo)
                                        .withCallEndTime(callEndTimeCallTwo)
                                        .withCallDuration(callDurationCallTwo)
                                        .withCallCost(callCostCallTwo)
                                        .build();

        Long callerNumberCallThree = 987654321L;
        Long calleeNumberCallThree = 123456789L;
        Long callStartTimestampCallThree = 1599961728L;
        Long callEndTimestampCallThree = 1599965728L;
        String callTypeCallThree = "Outbound";
        LocalDate callStartDayCallThree = LocalDate.of(2020, Month.SEPTEMBER, 13);
        LocalDate callEndDayCallThree = LocalDate.of(2020, Month.SEPTEMBER, 13);
        LocalTime callStartTimeCallThree = LocalTime.of(2, 48, 48);
        LocalTime callEndTimeCallThree = LocalTime.of(3, 55, 28);
        Duration callDurationCallThree = Duration.between(callStartTimeCallThree, callEndTimeCallThree);
        Double callCostCallThree = 3.6;

        callDtoCallThree = CallDto.Builder.callModelWith()
                                          .withCallerNumber(callerNumberCallThree)
                                          .withCalleeNumber(calleeNumberCallThree)
                                          .withStartTimestamp(callStartTimestampCallThree)
                                          .withEndTimestamp(callEndTimestampCallThree)
                                          .withCallType(callTypeCallThree)
                                          .withCallStartDay(callStartDayCallThree)
                                          .withCallEndDay(callEndDayCallThree)
                                          .withCallStartTime(callStartTimeCallThree)
                                          .withCallEndTime(callEndTimeCallThree)
                                          .withCallDuration(callDurationCallThree)
                                          .withCallCost(callCostCallThree)
                                          .build();

        Collections.addAll(callDtoList, callDtoCallOne, callDtoCallTwo, callDtoCallThree);
    }

    @Test
    public void getCallsStatisticsAssertGroupedByDaySuccessfully() {
        // Act
        when(callRepository.findAll()).thenReturn(callDtoList);

        List<CallsStatisticsJson> callsStatisticsJsonList = statisticsServiceImpl.getCallsStatistics();

        // Assert
        assertFalse(callsStatisticsJsonList.isEmpty());
        assertEquals(callDtoCallOne.getCallStartDay(), callsStatisticsJsonList.get(0).getDay());
        assertEquals(callDtoCallTwo.getCallStartDay(), callsStatisticsJsonList.get(0).getDay());
        assertEquals(callDtoCallThree.getCallStartDay(), callsStatisticsJsonList.get(1).getDay());
    }

    @Test
    public void getCallsStatisticsAssertTotalDurationByCallTypeSuccessfully() {
        // Act
        when(callRepository.findAll()).thenReturn(callDtoList);

        List<CallsStatisticsJson> callsStatisticsJsonList = statisticsServiceImpl.getCallsStatistics();

        // Assert
        assertFalse(callsStatisticsJsonList.isEmpty());
        assertEquals(Long.valueOf(callDtoCallOne.getCallDuration().getSeconds()),
                     callsStatisticsJsonList.get(0).getTotalCallsDurationInbound());
        assertEquals(Long.valueOf(callDtoCallTwo.getCallDuration().getSeconds()),
                     callsStatisticsJsonList.get(0).getTotalCallsDurationOutbound());
        assertEquals(Long.valueOf(0), callsStatisticsJsonList.get(1).getTotalCallsDurationInbound());
        assertEquals(Long.valueOf(callDtoCallThree.getCallDuration().getSeconds()),
                     callsStatisticsJsonList.get(1).getTotalCallsDurationOutbound());
    }

    @Test
    public void getCallsStatisticsAssertTotalNumberOfCallsSuccessfully() {
        // Act
        when(callRepository.findAll()).thenReturn(callDtoList);

        List<CallsStatisticsJson> callsStatisticsJsonList = statisticsServiceImpl.getCallsStatistics();

        // Assert
        assertFalse(callsStatisticsJsonList.isEmpty());
        assertEquals(Long.valueOf(2), callsStatisticsJsonList.get(0).getTotalNumberOfCalls());
        assertEquals(Long.valueOf(1), callsStatisticsJsonList.get(1).getTotalNumberOfCalls());
    }

    @Test
    public void getCallsStatisticsAssertTotalNumberOfCallsByCallerNumberListSuccessfully() {
        // Act
        when(callRepository.findAll()).thenReturn(callDtoList);

        List<CallsStatisticsJson> callsStatisticsJsonList = statisticsServiceImpl.getCallsStatistics();

        // Assert
        assertFalse(callsStatisticsJsonList.isEmpty());
        assertEquals(callDtoCallOne.getCallerNumber(), callsStatisticsJsonList.get(0)
                                                                              .getTotalNumberOfCallsByCallerNumber()
                                                                              .get(0).getCallerNumber());
        assertEquals(Long.valueOf(1), callsStatisticsJsonList.get(0).getTotalNumberOfCallsByCallerNumber().get(0)
                                                             .getTotalNumberOfCalls());
        assertEquals(callDtoCallTwo.getCallerNumber(), callsStatisticsJsonList.get(0)
                                                                              .getTotalNumberOfCallsByCallerNumber()
                                                                              .get(1).getCallerNumber());
        assertEquals(Long.valueOf(1), callsStatisticsJsonList.get(0).getTotalNumberOfCallsByCallerNumber().get(1)
                                                             .getTotalNumberOfCalls());
        assertEquals(callDtoCallThree.getCallerNumber(), callsStatisticsJsonList.get(1)
                                                                                .getTotalNumberOfCallsByCallerNumber()
                                                                                .get(0).getCallerNumber());
        assertEquals(Long.valueOf(1), callsStatisticsJsonList.get(1).getTotalNumberOfCallsByCallerNumber()
                                                             .get(0).getTotalNumberOfCalls());
    }

    @Test
    public void getCallsStatisticsAssertTotalNumberOfCallsByCalleeNumberListSuccessfully() {
        // Act
        when(callRepository.findAll()).thenReturn(callDtoList);

        List<CallsStatisticsJson> callsStatisticsJsonList = statisticsServiceImpl.getCallsStatistics();

        // Assert
        assertFalse(callsStatisticsJsonList.isEmpty());
        assertEquals(callDtoCallTwo.getCalleeNumber(), callsStatisticsJsonList.get(0)
                                                                              .getTotalNumberOfCallsByCalleeNumber()
                                                                              .get(0).getCalleeNumber());
        assertEquals(Long.valueOf(1), callsStatisticsJsonList.get(0).getTotalNumberOfCallsByCalleeNumber().get(0)
                                                             .getTotalNumberOfCalls());
        assertEquals(callDtoCallOne.getCalleeNumber(), callsStatisticsJsonList.get(0)
                                                                              .getTotalNumberOfCallsByCalleeNumber()
                                                                              .get(1).getCalleeNumber());
        assertEquals(Long.valueOf(1), callsStatisticsJsonList.get(0).getTotalNumberOfCallsByCalleeNumber().get(1)
                                                             .getTotalNumberOfCalls());
        assertEquals(callDtoCallThree.getCalleeNumber(), callsStatisticsJsonList.get(1)
                                                                                .getTotalNumberOfCallsByCalleeNumber()
                                                                                .get(0).getCalleeNumber());
        assertEquals(Long.valueOf(1), callsStatisticsJsonList.get(1).getTotalNumberOfCallsByCalleeNumber()
                                                             .get(0).getTotalNumberOfCalls());
    }

    @Test
    public void getCallsStatisticsAssertTotalCallsCostListSuccessfully() {
        // Act
        when(callRepository.findAll()).thenReturn(callDtoList);

        List<CallsStatisticsJson> callsStatisticsJsonList = statisticsServiceImpl.getCallsStatistics();

        // Assert
        assertFalse(callsStatisticsJsonList.isEmpty());
        assertEquals(Double.valueOf(callDtoCallOne.getCallCost() + callDtoCallTwo.getCallCost()),
                     callsStatisticsJsonList.get(0).getTotalCallsCost());
        assertEquals(callDtoCallThree.getCallCost(), callsStatisticsJsonList.get(1).getTotalCallsCost());
    }
}
