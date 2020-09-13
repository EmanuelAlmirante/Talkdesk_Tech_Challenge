package webservice.repository;

import webservice.model.calldto.CallDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static webservice.service.ServiceParameters.NUMBER_OF_ELEMENTS_IN_PAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@DataJpaTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CallRepositoryTests {
    @Autowired
    private CallRepository callRepository;

    public List<CallDto> callDtoList = new ArrayList<>();
    public CallDto callDtoCallOne;
    public CallDto callDtoCallTwo;
    public CallDto callDtoCallThree;

    @Before
    public void setup() {
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
    public void createCallsSuccessfully() {
        // Act
        List<CallDto> callDtoReturnedList = callRepository.saveAll(callDtoList);

        // Assert
        assertFalse(callDtoReturnedList.isEmpty());

        assertEquals(callDtoCallOne.getCallerNumber(), callDtoReturnedList.get(0).getCallerNumber());
        assertEquals(callDtoCallOne.getCalleeNumber(), callDtoReturnedList.get(0).getCalleeNumber());
        assertEquals(callDtoCallOne.getCallStartTimestamp(), callDtoReturnedList.get(0).getCallStartTimestamp());
        assertEquals(callDtoCallOne.getCallEndTimestamp(), callDtoReturnedList.get(0).getCallEndTimestamp());
        assertEquals(callDtoCallOne.getCallType(), callDtoReturnedList.get(0).getCallType());
        assertEquals(callDtoCallOne.getCallStartDay(), callDtoReturnedList.get(0).getCallStartDay());
        assertEquals(callDtoCallOne.getCallEndDay(), callDtoReturnedList.get(0).getCallEndDay());
        assertEquals(callDtoCallOne.getCallStartTime(), callDtoReturnedList.get(0).getCallStartTime());
        assertEquals(callDtoCallOne.getCallEndTime(), callDtoReturnedList.get(0).getCallEndTime());
        assertEquals(callDtoCallOne.getCallDuration(), callDtoReturnedList.get(0).getCallDuration());
        assertEquals(callDtoCallOne.getCallCost(), callDtoReturnedList.get(0).getCallCost());

        assertEquals(callDtoCallTwo.getCallerNumber(), callDtoReturnedList.get(1).getCallerNumber());
        assertEquals(callDtoCallTwo.getCalleeNumber(), callDtoReturnedList.get(1).getCalleeNumber());
        assertEquals(callDtoCallTwo.getCallStartTimestamp(), callDtoReturnedList.get(1).getCallStartTimestamp());
        assertEquals(callDtoCallTwo.getCallEndTimestamp(), callDtoReturnedList.get(1).getCallEndTimestamp());
        assertEquals(callDtoCallTwo.getCallType(), callDtoReturnedList.get(1).getCallType());
        assertEquals(callDtoCallTwo.getCallStartDay(), callDtoReturnedList.get(1).getCallStartDay());
        assertEquals(callDtoCallTwo.getCallEndDay(), callDtoReturnedList.get(1).getCallEndDay());
        assertEquals(callDtoCallTwo.getCallStartTime(), callDtoReturnedList.get(1).getCallStartTime());
        assertEquals(callDtoCallTwo.getCallEndTime(), callDtoReturnedList.get(1).getCallEndTime());
        assertEquals(callDtoCallTwo.getCallDuration(), callDtoReturnedList.get(1).getCallDuration());
        assertEquals(callDtoCallTwo.getCallCost(), callDtoReturnedList.get(1).getCallCost());

        assertEquals(callDtoCallThree.getCallerNumber(), callDtoReturnedList.get(2).getCallerNumber());
        assertEquals(callDtoCallThree.getCalleeNumber(), callDtoReturnedList.get(2).getCalleeNumber());
        assertEquals(callDtoCallThree.getCallStartTimestamp(), callDtoReturnedList.get(2).getCallStartTimestamp());
        assertEquals(callDtoCallThree.getCallEndTimestamp(), callDtoReturnedList.get(2).getCallEndTimestamp());
        assertEquals(callDtoCallThree.getCallType(), callDtoReturnedList.get(2).getCallType());
        assertEquals(callDtoCallThree.getCallStartDay(), callDtoReturnedList.get(2).getCallStartDay());
        assertEquals(callDtoCallThree.getCallEndDay(), callDtoReturnedList.get(2).getCallEndDay());
        assertEquals(callDtoCallThree.getCallStartTime(), callDtoReturnedList.get(2).getCallStartTime());
        assertEquals(callDtoCallThree.getCallEndTime(), callDtoReturnedList.get(2).getCallEndTime());
        assertEquals(callDtoCallThree.getCallDuration(), callDtoReturnedList.get(2).getCallDuration());
        assertEquals(callDtoCallThree.getCallCost(), callDtoReturnedList.get(2).getCallCost());
    }

    @Test
    public void getAllCallsWithoutCallTypeSuccessfully() {
        // Arrange
        callRepository.saveAll(callDtoList);

        int page = 1;

        PageRequest pageRequest = PageRequest.of(page - 1, NUMBER_OF_ELEMENTS_IN_PAGE);
        Page<CallDto> callDtoPage;

        // Act
        callDtoPage = callRepository.findAll(pageRequest);

        List<CallDto> callDtoReturnedList = callDtoPage.getContent();

        // Assert
        assertFalse(callDtoReturnedList.isEmpty());

        assertEquals(callDtoCallOne.getCallerNumber(), callDtoReturnedList.get(0).getCallerNumber());
        assertEquals(callDtoCallOne.getCalleeNumber(), callDtoReturnedList.get(0).getCalleeNumber());
        assertEquals(callDtoCallOne.getCallStartTimestamp(), callDtoReturnedList.get(0).getCallStartTimestamp());
        assertEquals(callDtoCallOne.getCallEndTimestamp(), callDtoReturnedList.get(0).getCallEndTimestamp());
        assertEquals(callDtoCallOne.getCallType(), callDtoReturnedList.get(0).getCallType());
        assertEquals(callDtoCallOne.getCallStartDay(), callDtoReturnedList.get(0).getCallStartDay());
        assertEquals(callDtoCallOne.getCallEndDay(), callDtoReturnedList.get(0).getCallEndDay());
        assertEquals(callDtoCallOne.getCallStartTime(), callDtoReturnedList.get(0).getCallStartTime());
        assertEquals(callDtoCallOne.getCallEndTime(), callDtoReturnedList.get(0).getCallEndTime());
        assertEquals(callDtoCallOne.getCallDuration(), callDtoReturnedList.get(0).getCallDuration());
        assertEquals(callDtoCallOne.getCallCost(), callDtoReturnedList.get(0).getCallCost());

        assertEquals(callDtoCallTwo.getCallerNumber(), callDtoReturnedList.get(1).getCallerNumber());
        assertEquals(callDtoCallTwo.getCalleeNumber(), callDtoReturnedList.get(1).getCalleeNumber());
        assertEquals(callDtoCallTwo.getCallStartTimestamp(), callDtoReturnedList.get(1).getCallStartTimestamp());
        assertEquals(callDtoCallTwo.getCallEndTimestamp(), callDtoReturnedList.get(1).getCallEndTimestamp());
        assertEquals(callDtoCallTwo.getCallType(), callDtoReturnedList.get(1).getCallType());
        assertEquals(callDtoCallTwo.getCallStartDay(), callDtoReturnedList.get(1).getCallStartDay());
        assertEquals(callDtoCallTwo.getCallEndDay(), callDtoReturnedList.get(1).getCallEndDay());
        assertEquals(callDtoCallTwo.getCallStartTime(), callDtoReturnedList.get(1).getCallStartTime());
        assertEquals(callDtoCallTwo.getCallEndTime(), callDtoReturnedList.get(1).getCallEndTime());
        assertEquals(callDtoCallTwo.getCallDuration(), callDtoReturnedList.get(1).getCallDuration());
        assertEquals(callDtoCallTwo.getCallCost(), callDtoReturnedList.get(1).getCallCost());

        assertEquals(callDtoCallThree.getCallerNumber(), callDtoReturnedList.get(2).getCallerNumber());
        assertEquals(callDtoCallThree.getCalleeNumber(), callDtoReturnedList.get(2).getCalleeNumber());
        assertEquals(callDtoCallThree.getCallStartTimestamp(), callDtoReturnedList.get(2).getCallStartTimestamp());
        assertEquals(callDtoCallThree.getCallEndTimestamp(), callDtoReturnedList.get(2).getCallEndTimestamp());
        assertEquals(callDtoCallThree.getCallType(), callDtoReturnedList.get(2).getCallType());
        assertEquals(callDtoCallThree.getCallStartDay(), callDtoReturnedList.get(2).getCallStartDay());
        assertEquals(callDtoCallThree.getCallEndDay(), callDtoReturnedList.get(2).getCallEndDay());
        assertEquals(callDtoCallThree.getCallStartTime(), callDtoReturnedList.get(2).getCallStartTime());
        assertEquals(callDtoCallThree.getCallEndTime(), callDtoReturnedList.get(2).getCallEndTime());
        assertEquals(callDtoCallThree.getCallDuration(), callDtoReturnedList.get(2).getCallDuration());
        assertEquals(callDtoCallThree.getCallCost(), callDtoReturnedList.get(2).getCallCost());
    }

    @Test
    public void getAllCallsWithCallTypeInboundSuccessfully() {
        // Arrange
        callRepository.saveAll(callDtoList);

        int page = 1;
        String callTypeQuery = "Inbound";

        PageRequest pageRequest = PageRequest.of(page - 1, NUMBER_OF_ELEMENTS_IN_PAGE);
        Page<CallDto> callDtoPage;

        // Act
        callDtoPage = callRepository.findByCallType(pageRequest, callTypeQuery);

        List<CallDto> callDtoReturnedList = callDtoPage.getContent();

        // Assert
        assertFalse(callDtoReturnedList.isEmpty());
        assertEquals(1, callDtoReturnedList.size());

        assertEquals(callDtoCallOne.getCallerNumber(), callDtoReturnedList.get(0).getCallerNumber());
        assertEquals(callDtoCallOne.getCalleeNumber(), callDtoReturnedList.get(0).getCalleeNumber());
        assertEquals(callDtoCallOne.getCallStartTimestamp(), callDtoReturnedList.get(0).getCallStartTimestamp());
        assertEquals(callDtoCallOne.getCallEndTimestamp(), callDtoReturnedList.get(0).getCallEndTimestamp());
        assertEquals(callDtoCallOne.getCallType(), callDtoReturnedList.get(0).getCallType());
        assertEquals(callDtoCallOne.getCallStartDay(), callDtoReturnedList.get(0).getCallStartDay());
        assertEquals(callDtoCallOne.getCallEndDay(), callDtoReturnedList.get(0).getCallEndDay());
        assertEquals(callDtoCallOne.getCallStartTime(), callDtoReturnedList.get(0).getCallStartTime());
        assertEquals(callDtoCallOne.getCallEndTime(), callDtoReturnedList.get(0).getCallEndTime());
        assertEquals(callDtoCallOne.getCallDuration(), callDtoReturnedList.get(0).getCallDuration());
        assertEquals(callDtoCallOne.getCallCost(), callDtoReturnedList.get(0).getCallCost());
    }

    @Test
    public void getAllCallsWithCallTypeOutboundSuccessfully() {
        // Arrange
        callRepository.saveAll(callDtoList);

        int page = 1;
        String callTypeQuery = "Outbound";

        PageRequest pageRequest = PageRequest.of(page - 1, NUMBER_OF_ELEMENTS_IN_PAGE);
        Page<CallDto> callDtoPage;

        // Act
        callDtoPage = callRepository.findByCallType(pageRequest, callTypeQuery);

        List<CallDto> callDtoReturnedList = callDtoPage.getContent();

        // Assert
        assertFalse(callDtoReturnedList.isEmpty());
        assertEquals(2, callDtoReturnedList.size());

        assertEquals(callDtoCallTwo.getCallerNumber(), callDtoReturnedList.get(0).getCallerNumber());
        assertEquals(callDtoCallTwo.getCalleeNumber(), callDtoReturnedList.get(0).getCalleeNumber());
        assertEquals(callDtoCallTwo.getCallStartTimestamp(), callDtoReturnedList.get(0).getCallStartTimestamp());
        assertEquals(callDtoCallTwo.getCallEndTimestamp(), callDtoReturnedList.get(0).getCallEndTimestamp());
        assertEquals(callDtoCallTwo.getCallType(), callDtoReturnedList.get(0).getCallType());
        assertEquals(callDtoCallTwo.getCallStartDay(), callDtoReturnedList.get(0).getCallStartDay());
        assertEquals(callDtoCallTwo.getCallEndDay(), callDtoReturnedList.get(0).getCallEndDay());
        assertEquals(callDtoCallTwo.getCallStartTime(), callDtoReturnedList.get(0).getCallStartTime());
        assertEquals(callDtoCallTwo.getCallEndTime(), callDtoReturnedList.get(0).getCallEndTime());
        assertEquals(callDtoCallTwo.getCallDuration(), callDtoReturnedList.get(0).getCallDuration());
        assertEquals(callDtoCallTwo.getCallCost(), callDtoReturnedList.get(0).getCallCost());

        assertEquals(callDtoCallThree.getCallerNumber(), callDtoReturnedList.get(1).getCallerNumber());
        assertEquals(callDtoCallThree.getCalleeNumber(), callDtoReturnedList.get(1).getCalleeNumber());
        assertEquals(callDtoCallThree.getCallStartTimestamp(), callDtoReturnedList.get(1).getCallStartTimestamp());
        assertEquals(callDtoCallThree.getCallEndTimestamp(), callDtoReturnedList.get(1).getCallEndTimestamp());
        assertEquals(callDtoCallThree.getCallType(), callDtoReturnedList.get(1).getCallType());
        assertEquals(callDtoCallThree.getCallStartDay(), callDtoReturnedList.get(1).getCallStartDay());
        assertEquals(callDtoCallThree.getCallEndDay(), callDtoReturnedList.get(1).getCallEndDay());
        assertEquals(callDtoCallThree.getCallStartTime(), callDtoReturnedList.get(1).getCallStartTime());
        assertEquals(callDtoCallThree.getCallEndTime(), callDtoReturnedList.get(1).getCallEndTime());
        assertEquals(callDtoCallThree.getCallDuration(), callDtoReturnedList.get(1).getCallDuration());
        assertEquals(callDtoCallThree.getCallCost(), callDtoReturnedList.get(1).getCallCost());
    }

    @Test
    public void deleteCallByIdSuccessfully() {
        // Arrange
        callRepository.saveAll(callDtoList);
        List<CallDto> callDtoListBeforeDelete = callRepository.findAll();

        Long callDtoIdToBeDeleted = callDtoListBeforeDelete.get(2).getId();

        // Act
        callRepository.deleteById(callDtoIdToBeDeleted);

        List<CallDto> callDtoListAfterDelete = callRepository.findAll();

        // Assert
        assertFalse(callDtoListAfterDelete.isEmpty());
        assertEquals(2, callDtoListAfterDelete.size());

        assertEquals(callDtoCallOne.getCallerNumber(), callDtoListAfterDelete.get(0).getCallerNumber());
        assertEquals(callDtoCallOne.getCalleeNumber(), callDtoListAfterDelete.get(0).getCalleeNumber());
        assertEquals(callDtoCallOne.getCallStartTimestamp(), callDtoListAfterDelete.get(0).getCallStartTimestamp());
        assertEquals(callDtoCallOne.getCallEndTimestamp(), callDtoListAfterDelete.get(0).getCallEndTimestamp());
        assertEquals(callDtoCallOne.getCallType(), callDtoListAfterDelete.get(0).getCallType());
        assertEquals(callDtoCallOne.getCallStartDay(), callDtoListAfterDelete.get(0).getCallStartDay());
        assertEquals(callDtoCallOne.getCallEndDay(), callDtoListAfterDelete.get(0).getCallEndDay());
        assertEquals(callDtoCallOne.getCallStartTime(), callDtoListAfterDelete.get(0).getCallStartTime());
        assertEquals(callDtoCallOne.getCallEndTime(), callDtoListAfterDelete.get(0).getCallEndTime());
        assertEquals(callDtoCallOne.getCallDuration(), callDtoListAfterDelete.get(0).getCallDuration());
        assertEquals(callDtoCallOne.getCallCost(), callDtoListAfterDelete.get(0).getCallCost());

        assertEquals(callDtoCallTwo.getCallerNumber(), callDtoListAfterDelete.get(1).getCallerNumber());
        assertEquals(callDtoCallTwo.getCalleeNumber(), callDtoListAfterDelete.get(1).getCalleeNumber());
        assertEquals(callDtoCallTwo.getCallStartTimestamp(), callDtoListAfterDelete.get(1).getCallStartTimestamp());
        assertEquals(callDtoCallTwo.getCallEndTimestamp(), callDtoListAfterDelete.get(1).getCallEndTimestamp());
        assertEquals(callDtoCallTwo.getCallType(), callDtoListAfterDelete.get(1).getCallType());
        assertEquals(callDtoCallTwo.getCallStartDay(), callDtoListAfterDelete.get(1).getCallStartDay());
        assertEquals(callDtoCallTwo.getCallEndDay(), callDtoListAfterDelete.get(1).getCallEndDay());
        assertEquals(callDtoCallTwo.getCallStartTime(), callDtoListAfterDelete.get(1).getCallStartTime());
        assertEquals(callDtoCallTwo.getCallEndTime(), callDtoListAfterDelete.get(1).getCallEndTime());
        assertEquals(callDtoCallTwo.getCallDuration(), callDtoListAfterDelete.get(1).getCallDuration());
        assertEquals(callDtoCallTwo.getCallCost(), callDtoListAfterDelete.get(1).getCallCost());
    }


}
