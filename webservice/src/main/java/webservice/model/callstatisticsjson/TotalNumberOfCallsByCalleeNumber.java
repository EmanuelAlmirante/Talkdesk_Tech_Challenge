package webservice.model.callstatisticsjson;

public class TotalNumberOfCallsByCalleeNumber {
    private final Long calleeNumber;
    private final Long totalNumberOfCalls;

    public TotalNumberOfCallsByCalleeNumber(Builder builder) {
        this.calleeNumber = builder.calleeNumber;
        this.totalNumberOfCalls = builder.totalNumberOfCalls;
    }

    public Long getCalleeNumber() {
        return calleeNumber;
    }

    public Long getTotalNumberOfCalls() {
        return totalNumberOfCalls;
    }

    public static class Builder {
        private Long calleeNumber;
        private Long totalNumberOfCalls;

        public static Builder totalNumberOfCallsByCalleeNumberWith() {
            return new Builder();
        }

        public Builder withCalleeNumber(Long calleeNumber) {
            this.calleeNumber = calleeNumber;

            return this;
        }

        public Builder withTotalNumberOfCalls(Long totalNumberOfCalls) {
            this.totalNumberOfCalls = totalNumberOfCalls;

            return this;
        }

        public TotalNumberOfCallsByCalleeNumber build() {
            return new TotalNumberOfCallsByCalleeNumber(this);
        }
    }
}
