package webservice.service.statistics;

import webservice.model.callstatisticsjson.CallsStatisticsJson;

import java.util.List;

public interface StatisticsService {
    List<CallsStatisticsJson> getCallsStatistics();
}
