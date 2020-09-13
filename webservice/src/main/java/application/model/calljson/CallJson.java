package application.model.calljson;

public class CallJson {
    private Long id;
    private Long callerNumber;
    private Long calleeNumber;
    private Long callStartTimestamp;
    private Long callEndTimestamp;
    private String callType;

    public CallJson() {
    }

    private CallJson(Builder builder) {
        this.id = builder.id;
        this.callerNumber = builder.callerNumber;
        this.calleeNumber = builder.calleeNumber;
        this.callStartTimestamp = builder.callStartTimestamp;
        this.callEndTimestamp = builder.callEndTimestamp;
        this.callType = builder.callType;
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

    public static class Builder {
        private Long id;
        private Long callerNumber;
        private Long calleeNumber;
        private Long callStartTimestamp;
        private Long callEndTimestamp;
        private String callType;

        public static Builder callModelWith() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;

            return this;
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

        public CallJson build() {
            return new CallJson(this);
        }
    }
}
