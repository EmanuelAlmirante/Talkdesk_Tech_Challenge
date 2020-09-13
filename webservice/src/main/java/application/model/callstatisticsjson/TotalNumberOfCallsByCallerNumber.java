package application.model.callstatisticsjson;

public class TotalNumberOfCallsByCallerNumber {
    private final Long callerNumber;
    private final Long totalNumberOfCalls;

    public TotalNumberOfCallsByCallerNumber(Builder builder) {
        this.callerNumber = builder.callerNumber;
        this.totalNumberOfCalls = builder.totalNumberOfCalls;
    }

    public Long getCallerNumber() {
        return callerNumber;
    }

    public Long getTotalNumberOfCalls() {
        return totalNumberOfCalls;
    }

    public static class Builder {
        private Long callerNumber;
        private Long totalNumberOfCalls;

        public static Builder totalNumberOfCallsByCallerNumberWith() {
            return new Builder();
        }

        public Builder withCallerNumber(Long callerNumber) {
            this.callerNumber = callerNumber;

            return this;
        }

        public Builder withTotalNumberOfCalls(Long totalNumberOfCalls) {
            this.totalNumberOfCalls = totalNumberOfCalls;

            return this;
        }

        public TotalNumberOfCallsByCallerNumber build() {
            return new TotalNumberOfCallsByCallerNumber(this);
        }
    }
}
