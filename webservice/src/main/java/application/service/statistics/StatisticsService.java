package application.service.statistics;

import application.model.callstatisticsjson.CallsStatisticsJson;

import java.util.List;

public interface StatisticsService {
    List<CallsStatisticsJson> getCallsStatistics();
}
