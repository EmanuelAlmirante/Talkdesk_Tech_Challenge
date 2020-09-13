package webservice.model.callstatisticsjson;

import java.time.LocalDate;
import java.util.List;

public class CallsStatisticsJson {
    private final LocalDate day;
    private final Long totalCallsDurationInbound;
    private final Long totalCallsDurationOutbound;
    private final Long totalNumberOfCalls;
    private final List<TotalNumberOfCallsByCallerNumber> totalNumberOfCallsByCallerNumberList;
    private final List<TotalNumberOfCallsByCalleeNumber> totalNumberOfCallsByCalleeNumberList;
    private final Double totalCallsCost;

    public CallsStatisticsJson(Builder builder) {
        this.day = builder.day;
        this.totalCallsDurationInbound = builder.totalCallsDurationInbound;
        this.totalCallsDurationOutbound = builder.totalCallsDurationOutbound;
        this.totalNumberOfCalls = builder.totalNumberOfCalls;
        this.totalNumberOfCallsByCallerNumberList = builder.totalNumberOfCallsByCallerNumberList;
        this.totalNumberOfCallsByCalleeNumberList = builder.totalNumberOfCallsByCalleeNumberList;
        this.totalCallsCost = builder.totalCallsCost;
    }

    public LocalDate getDay() {
        return day;
    }

    public Long getTotalCallsDurationInbound() {
        return totalCallsDurationInbound;
    }

    public Long getTotalCallsDurationOutbound() {
        return totalCallsDurationOutbound;
    }

    public Long getTotalNumberOfCalls() {
        return totalNumberOfCalls;
    }

    public List<TotalNumberOfCallsByCallerNumber> getTotalNumberOfCallsByCallerNumber() {
        return totalNumberOfCallsByCallerNumberList;
    }

    public List<TotalNumberOfCallsByCalleeNumber> getTotalNumberOfCallsByCalleeNumber() {
        return totalNumberOfCallsByCalleeNumberList;
    }

    public Double getTotalCallsCost() {
        return totalCallsCost;
    }

    public static class Builder {
        private LocalDate day;
        private Long totalCallsDurationInbound;
        private Long totalCallsDurationOutbound;
        private Long totalNumberOfCalls;
        private List<TotalNumberOfCallsByCallerNumber> totalNumberOfCallsByCallerNumberList;
        private List<TotalNumberOfCallsByCalleeNumber> totalNumberOfCallsByCalleeNumberList;
        private Double totalCallsCost;

        public static Builder callStatisticsJsonWith() {
            return new Builder();
        }

        public Builder withDay(LocalDate day) {
            this.day = day;

            return this;
        }

        public Builder withTotalCallsDurationInbound(Long totalCallDurationInbound) {
            this.totalCallsDurationInbound = totalCallDurationInbound;

            return this;
        }

        public Builder withTotalCallsDurationOutbound(Long totalCallDurationOutbound) {
            this.totalCallsDurationOutbound = totalCallDurationOutbound;

            return this;
        }

        public Builder withTotalNumberOfCalls(Long totalNumberOfCalls) {
            this.totalNumberOfCalls = totalNumberOfCalls;

            return this;
        }

        public Builder withTotalNumberOfCallsByCallerNumber(
                List<TotalNumberOfCallsByCallerNumber> totalNumberOfCallsByCallerNumberList) {
            this.totalNumberOfCallsByCallerNumberList = totalNumberOfCallsByCallerNumberList;

            return this;
        }

        public Builder withTotalNumberOfCallsByCalleeNumber(
                List<TotalNumberOfCallsByCalleeNumber> totalNumberOfCallsByCalleeNumberList) {
            this.totalNumberOfCallsByCalleeNumberList = totalNumberOfCallsByCalleeNumberList;

            return this;
        }

        public Builder withTotalCallsCost(Double totalCallCost) {
            this.totalCallsCost = totalCallCost;

            return this;
        }

        public CallsStatisticsJson build() {
            return new CallsStatisticsJson(this);
        }
    }
}
