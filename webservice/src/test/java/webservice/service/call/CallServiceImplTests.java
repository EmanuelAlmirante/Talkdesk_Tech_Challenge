package webservice.service.call;

import webservice.exception.BusinessException;
import webservice.model.calldto.CallDto;
import webservice.repository.CallRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static webservice.service.ServiceParameters.NUMBER_OF_ELEMENTS_IN_PAGE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CallServiceImplTests {
    @Mock
    private CallRepository callRepository;

    @InjectMocks
    private CallServiceImpl callServiceImpl;

    @Test
    public void createOutboundCallSuccessfully() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Outbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act
        when(callRepository.saveAll(callDtoToBeCreatedList)).thenReturn(callDtoToBeCreatedList);

        List<CallDto> callDtoReturnedList = callServiceImpl.createCalls(callDtoToBeCreatedList);

        // Assert
        assertFalse(callDtoReturnedList.isEmpty());
        assertEquals(callerNumber, callDtoReturnedList.get(0).getCallerNumber());
        assertEquals(calleeNumber, callDtoReturnedList.get(0).getCalleeNumber());
        assertEquals(callStartTimestamp, callDtoReturnedList.get(0).getCallStartTimestamp());
        assertEquals(callEndTimestamp, callDtoReturnedList.get(0).getCallEndTimestamp());
        assertEquals(callType, callDtoReturnedList.get(0).getCallType());
        assertEquals(LocalDate.of(2020, Month.SEPTEMBER, 12), callDtoReturnedList.get(0).getCallStartDay());
        assertEquals(LocalDate.of(2020, Month.SEPTEMBER, 12), callDtoReturnedList.get(0).getCallEndDay());
        assertEquals(LocalTime.of(12, 10, 10), callDtoReturnedList.get(0).getCallStartTime());
        assertEquals(LocalTime.of(21, 35, 44), callDtoReturnedList.get(0).getCallEndTime());
        assertEquals(Duration.between(callDtoReturnedList.get(0).getCallStartTime(),
                                      callDtoReturnedList.get(0).getCallEndTime()),
                     callDtoReturnedList.get(0).getCallDuration());
        assertEquals(Double.valueOf(28.55), callDtoReturnedList.get(0).getCallCost());
    }

    @Test
    public void createInboundCallSuccessfully() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Inbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act
        when(callRepository.saveAll(callDtoToBeCreatedList)).thenReturn(callDtoToBeCreatedList);

        List<CallDto> callDtoReturnedList = callServiceImpl.createCalls(callDtoToBeCreatedList);

        // Assert
        assertFalse(callDtoReturnedList.isEmpty());
        assertEquals(callerNumber, callDtoReturnedList.get(0).getCallerNumber());
        assertEquals(calleeNumber, callDtoReturnedList.get(0).getCalleeNumber());
        assertEquals(callStartTimestamp, callDtoReturnedList.get(0).getCallStartTimestamp());
        assertEquals(callEndTimestamp, callDtoReturnedList.get(0).getCallEndTimestamp());
        assertEquals(callType, callDtoReturnedList.get(0).getCallType());
        assertEquals(LocalDate.of(2020, Month.SEPTEMBER, 12), callDtoReturnedList.get(0).getCallStartDay());
        assertEquals(LocalDate.of(2020, Month.SEPTEMBER, 12), callDtoReturnedList.get(0).getCallEndDay());
        assertEquals(LocalTime.of(12, 10, 10), callDtoReturnedList.get(0).getCallStartTime());
        assertEquals(LocalTime.of(21, 35, 44), callDtoReturnedList.get(0).getCallEndTime());
        assertEquals(Duration.between(callDtoReturnedList.get(0).getCallStartTime(),
                                      callDtoReturnedList.get(0).getCallEndTime()),
                     callDtoReturnedList.get(0).getCallDuration());
        assertEquals(Double.valueOf(0), callDtoReturnedList.get(0).getCallCost());
    }

    @Test
    public void createOutboundCallWithLessThanFiveMinutesDurationCalculateCorrectCostSuccessfully() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 123456789L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = 1599909110L;
        String callType = "Outbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act
        when(callRepository.saveAll(callDtoToBeCreatedList)).thenReturn(callDtoToBeCreatedList);

        List<CallDto> callDtoReturnedList = callServiceImpl.createCalls(callDtoToBeCreatedList);

        // Assert
        assertFalse(callDtoReturnedList.isEmpty());
        assertEquals(callerNumber, callDtoReturnedList.get(0).getCallerNumber());
        assertEquals(calleeNumber, callDtoReturnedList.get(0).getCalleeNumber());
        assertEquals(callStartTimestamp, callDtoReturnedList.get(0).getCallStartTimestamp());
        assertEquals(callEndTimestamp, callDtoReturnedList.get(0).getCallEndTimestamp());
        assertEquals(callType, callDtoReturnedList.get(0).getCallType());
        assertEquals(LocalDate.of(2020, Month.SEPTEMBER, 12), callDtoReturnedList.get(0).getCallStartDay());
        assertEquals(LocalDate.of(2020, Month.SEPTEMBER, 12), callDtoReturnedList.get(0).getCallEndDay());
        assertEquals(LocalTime.of(12, 10, 10), callDtoReturnedList.get(0).getCallStartTime());
        assertEquals(LocalTime.of(12, 11, 50), callDtoReturnedList.get(0).getCallEndTime());
        assertEquals(Duration.between(callDtoReturnedList.get(0).getCallStartTime(),
                                      callDtoReturnedList.get(0).getCallEndTime()),
                     callDtoReturnedList.get(0).getCallDuration());
        assertEquals(Double.valueOf(0.20), callDtoReturnedList.get(0).getCallCost());
    }

    @Test(expected = BusinessException.class)
    public void createCallWithoutCallerNumberFails() {
        // Arrange
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Inbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act && Assert
        try {
            callServiceImpl.createCalls(callDtoToBeCreatedList);
        } catch (BusinessException be) {
            String exceptionMessage = "Caller number must not be empty and must be a positive number!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of call without caller number was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createCallWithNegativeCallerNumberFails() {
        // Arrange
        Long callerNumber = -123456789L;
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Inbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act && Assert
        try {
            callServiceImpl.createCalls(callDtoToBeCreatedList);
        } catch (BusinessException be) {
            String exceptionMessage = "Caller number must not be empty and must be a positive number!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of call with negative caller number was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createCallWithoutCalleeNumberFails() {
        // Arrange
        Long callerNumber = 123456789L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Inbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act && Assert
        try {
            callServiceImpl.createCalls(callDtoToBeCreatedList);
        } catch (BusinessException be) {
            String exceptionMessage = "Callee number must not be empty and must be a positive number!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of call without callee number was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createCallWithNegativeCalleeNumberFails() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = -987654321L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Inbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act && Assert
        try {
            callServiceImpl.createCalls(callDtoToBeCreatedList);
        } catch (BusinessException be) {
            String exceptionMessage = "Callee number must not be empty and must be a positive number!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of call with negative callee number was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createCallWithoutStartTimestampFails() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 987654321L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Inbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act && Assert
        try {
            callServiceImpl.createCalls(callDtoToBeCreatedList);
        } catch (BusinessException be) {
            String exceptionMessage = "Start timestamp must not be empty and must be bigger than 0!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of call without start timestamp was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createCallWithNegativeStartTimestampFails() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = -1599909010L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Inbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act && Assert
        try {
            callServiceImpl.createCalls(callDtoToBeCreatedList);
        } catch (BusinessException be) {
            String exceptionMessage = "Start timestamp must not be empty and must be bigger than 0!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of call with negative start timestamp was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createCallWithoutEndTimestampFails() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = 1599909010L;
        String callType = "Inbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act && Assert
        try {
            callServiceImpl.createCalls(callDtoToBeCreatedList);
        } catch (BusinessException be) {
            String exceptionMessage = "End timestamp must not be empty and must be bigger than 0!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of call without end timestamp was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createCallWithEndTimestampFails() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = -1599942944L;
        String callType = "Inbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act && Assert
        try {
            callServiceImpl.createCalls(callDtoToBeCreatedList);
        } catch (BusinessException be) {
            String exceptionMessage = "End timestamp must not be empty and must be bigger than 0!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of call with negative end timestamp was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createCallWithStartTimestampBiggerThanEndTimestampFails() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = 1599942944L;
        Long callEndTimestamp = 1599909010L;
        String callType = "Inbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act && Assert
        try {
            callServiceImpl.createCalls(callDtoToBeCreatedList);
        } catch (BusinessException be) {
            String exceptionMessage = "Start timestamp must be before than end timestamp!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of call with start timestamp bigger than end timestamp was not thrown!");
    }

    @Test(expected = BusinessException.class)
    public void createCallWithInvalidCallTypeFails() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Invalid_Call_Type";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        // Act && Assert
        try {
            callServiceImpl.createCalls(callDtoToBeCreatedList);
        } catch (BusinessException be) {
            String exceptionMessage = "Call type must be Inbound or Outbound!";
            assertEquals(exceptionMessage, be.getMessage());
            throw be;
        }

        fail("Business exception of call with invalid call type was not thrown!");
    }

    @Test
    public void getAllCallsWithCallTypeNullSuccessfully() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Outbound";

        CallDto callDtoToBeCreated = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoToBeCreatedList = Collections.singletonList(callDtoToBeCreated);

        int page = 1;
        String callTypeQueryParam = null;

        PageRequest pageRequest = PageRequest.of(page - 1, NUMBER_OF_ELEMENTS_IN_PAGE);
        Page<CallDto> callDtoPage = new PageImpl<>(callDtoToBeCreatedList);

        // Act
        when(callRepository.findAll(pageRequest)).thenReturn(callDtoPage);

        List<CallDto> callDtoReturnedList = callServiceImpl.getAllCalls(page, callTypeQueryParam);

        // Assert
        assertFalse(callDtoReturnedList.isEmpty());
        assertEquals(callerNumber, callDtoReturnedList.get(0).getCallerNumber());
        assertEquals(calleeNumber, callDtoReturnedList.get(0).getCalleeNumber());
        assertEquals(callStartTimestamp, callDtoReturnedList.get(0).getCallStartTimestamp());
        assertEquals(callEndTimestamp, callDtoReturnedList.get(0).getCallEndTimestamp());
        assertEquals(callType, callDtoReturnedList.get(0).getCallType());
    }

    @Test
    public void getAllCallsWithCallTypeEmptySuccessfully() {
        // Arrange
        Long callerNumber = 123456789L;
        Long calleeNumber = 987654321L;
        Long callStartTimestamp = 1599909010L;
        Long callEndTimestamp = 1599942944L;
        String callType = "Outbound";

        CallDto callDto = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumber)
                                                    .withCalleeNumber(calleeNumber)
                                                    .withStartTimestamp(callStartTimestamp)
                                                    .withEndTimestamp(callEndTimestamp)
                                                    .withCallType(callType)
                                                    .build();

        List<CallDto> callDtoList = Collections.singletonList(callDto);

        int page = 1;
        String callTypeQueryParam = "";

        PageRequest pageRequest = PageRequest.of(page - 1, NUMBER_OF_ELEMENTS_IN_PAGE);
        Page<CallDto> callDtoPage = new PageImpl<>(callDtoList);

        // Act
        when(callRepository.findAll(pageRequest)).thenReturn(callDtoPage);

        List<CallDto> callDtoReturnedList = callServiceImpl.getAllCalls(page, callTypeQueryParam);

        // Assert
        assertFalse(callDtoReturnedList.isEmpty());
        assertEquals(callerNumber, callDtoReturnedList.get(0).getCallerNumber());
        assertEquals(calleeNumber, callDtoReturnedList.get(0).getCalleeNumber());
        assertEquals(callStartTimestamp, callDtoReturnedList.get(0).getCallStartTimestamp());
        assertEquals(callEndTimestamp, callDtoReturnedList.get(0).getCallEndTimestamp());
        assertEquals(callType, callDtoReturnedList.get(0).getCallType());
    }

    @Test
    public void getAllCallsWithCallTypeOutboundSuccessfully() {
        // Arrange
        Long callerNumberOutbound = 123456789L;
        Long calleeNumberOutbound = 987654321L;
        Long callStartTimestampOutbound = 1599909010L;
        Long callEndTimestampOutbound = 1599942944L;
        String callTypeOutbound = "Outbound";

        CallDto callDtoOutbound = CallDto.Builder.callModelWith()
                                                    .withCallerNumber(callerNumberOutbound)
                                                    .withCalleeNumber(calleeNumberOutbound)
                                                    .withStartTimestamp(callStartTimestampOutbound)
                                                    .withEndTimestamp(callEndTimestampOutbound)
                                                    .withCallType(callTypeOutbound)
                                                    .build();

        List<CallDto> callDtoListToBeReturned = Collections.singletonList(callDtoOutbound);

        int page = 1;

        PageRequest pageRequest = PageRequest.of(page - 1, NUMBER_OF_ELEMENTS_IN_PAGE);
        Page<CallDto> callDtoPage = new PageImpl<>(callDtoListToBeReturned);

        // Act
        when(callRepository.findByCallType(pageRequest, callTypeOutbound)).thenReturn(callDtoPage);

        List<CallDto> callDtoReturnedList = callServiceImpl.getAllCalls(page, callTypeOutbound);

        // Assert
        assertFalse(callDtoReturnedList.isEmpty());
        assertEquals(1, callDtoReturnedList.size());
        assertEquals(callerNumberOutbound, callDtoReturnedList.get(0).getCallerNumber());
        assertEquals(calleeNumberOutbound, callDtoReturnedList.get(0).getCalleeNumber());
        assertEquals(callStartTimestampOutbound, callDtoReturnedList.get(0).getCallStartTimestamp());
        assertEquals(callEndTimestampOutbound, callDtoReturnedList.get(0).getCallEndTimestamp());
        assertEquals(callTypeOutbound, callDtoReturnedList.get(0).getCallType());
    }

    @Test
    public void deleteCallBySuccessfully() {
        // Arrange
        Long id = 1L;

        // Act
        callServiceImpl.deleteCallById(id);

        // Assert
        verify(callRepository, times(1)).deleteById(id);
    }
}
