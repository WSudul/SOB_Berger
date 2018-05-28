package report;

import berger.BergerCode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Report {

    private final Instant timestamp;
    private Map<BergerCode, List<Record>> recordMap; /*maps copy of unmodified BerGerCode instance to list of
    generated changes */

    public Report() {
        timestamp = Instant.now();
    }

    public boolean addRecord(BergerCode originalData, Record record) {
        if (null == recordMap.putIfAbsent(originalData, new ArrayList<>(Arrays.asList(record)))) {
            return recordMap.get(originalData).add(record);
        } else
            return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Report{");
        sb.append("\ntimestamp=").append(timestamp);
        sb.append("\n recordMap=").append(recordMap);
        sb.append("\n}");
        return sb.toString();
    }
}
