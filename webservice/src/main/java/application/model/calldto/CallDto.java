package application.model.calldto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "calls")
public class CallDto {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Long callerNumber;
    @NotNull
    private Long calleeNumber;
    @NotNull
    private Long callStartTimestamp;
    @NotNull
    private Long callEndTimestamp;
    @NotBlank
    private String callType;
    @NotNull
    private LocalDate callStartDay;
    @NotNull
    private LocalDate callEndDay;
    @NotNull
    private LocalTime callStartTime;
    @NotNull
    private LocalTime callEndTime;
    @NotNull
    private Duration callDuration;
    @NotNull
    private Double callCost;

    public CallDto() {
    }

    private CallDto(Builder builder) {
        this.callerNumber = builder.callerNumber;
        this.calleeNumber = builder.calleeNumber;
        this.callStartTimestamp = builder.callStartTimestamp;
        this.callEndTimestamp = builder.callEndTimestamp;
        this.callType = builder.callType;
        this.callStartDay = builder.callStartDay;
        this.callEndDay = builder.callEndDay;
        this.callStartTime = builder.callStartTime;
        this.callEndTime = builder.callEndTime;
        this.callDuration = builder.callDuration;
        this.callCost = builder.callCost;
    }

    public Long getId() {
        return id;
    }

    public Long getCallerNumber() {
        return callerNumber;
    }

    public Long getCalleeNumber() {
        return calleeNumber;
    }

    public Long getCallStartTimestamp() {
        return callStartTimestamp;
    }

    public Long getCallEndTimestamp() {
        return callEndTimestamp;
    }

    public String getCallType() {
        return callType;
    }

    public LocalDate getCallStartDay() {
        return callStartDay;
    }

    public void setCallStartDay(LocalDate callStartDay) {
        this.callStartDay = callStartDay;
    }

    public LocalDate getCallEndDay() {
        return callEndDay;
    }

    public void setCallEndDay(LocalDate callEndDay) {
        this.callEndDay = callEndDay;
    }

    public LocalTime getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(LocalTime callStartTime) {
        this.callStartTime = callStartTime;
    }

    public LocalTime getCallEndTime() {
        return callEndTime;
    }

    public void setCallEndTime(LocalTime callEndTime) {
        this.callEndTime = callEndTime;
    }

    public Duration getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Duration callDuration) {
        this.callDuration = callDuration;
    }

    public Double getCallCost() {
        return callCost;
    }

    public void setCallCost(Double callCost) {
        this.callCost = callCost;
    }

    public static class Builder {
        private Long callerNumber;
        private Long calleeNumber;
        private Long callStartTimestamp;
        private Long callEndTimestamp;
        private String callType;
        private LocalDate callStartDay;
        private LocalDate callEndDay;
        private LocalTime callStartTime;
        private LocalTime callEndTime;
        private Duration callDuration;
        private Double callCost;

        public static Builder callModelWith() {
            return new Builder();
        }

        public Builder withCallerNumber(Long callerNumber) {
            this.callerNumber = callerNumber;

            return this;
        }

        public Builder withCalleeNumber(Long calleeNumber) {
            this.calleeNumber = calleeNumber;

            return this;
        }

        public Builder withStartTimestamp(Long callStartTimestamp) {
            this.callStartTimestamp = callStartTimestamp;

            return this;
        }

        public Builder withEndTimestamp(Long callEndTimestamp) {
            this.callEndTimestamp = callEndTimestamp;

            return this;
        }

        public Builder withCallType(String callType) {
            this.callType = callType;

            return this;
        }

        public Builder withCallStartDay(LocalDate callStartDay) {
            this.callStartDay = callStartDay;

            return this;
        }

        public Builder withCallEndDay(LocalDate callEndDay) {
            this.callEndDay = callEndDay;

            return this;
        }

        public Builder withCallStartTime(LocalTime callStartTime) {
            this.callStartTime = callStartTime;

            return this;
        }

        public Builder withCallEndTime(LocalTime callEndTime) {
            this.callEndTime = callEndTime;

            return this;
        }

        public Builder withCallDuration(Duration callDuration) {
            this.callDuration = callDuration;

            return this;
        }

        public Builder withCallCost(Double callCost) {
            this.callCost = callCost;

            return this;
        }

        public CallDto build() {
            return new CallDto(this);
        }
    }
}
