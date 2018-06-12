package report;

import berger.BergerCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Report {

    private final Instant timestamp;
    private Map<BergerCode, List<Record>> recordMap; /*maps copy of unmodified BerGerCode instance to list of
    generated changes */

    public Report() {
        timestamp = Instant.now();
        recordMap = new HashMap<>();
    }

    public boolean addRecord(BergerCode originalData, Record record) {
        if (null == recordMap.putIfAbsent(originalData, new ArrayList<>(Arrays.asList(record)))) {
            return recordMap.get(originalData).add(record);
        } else
            return true;
    }

    public boolean exportToFile(String fileName) {

        String stringTimestamp=timestamp.truncatedTo(ChronoUnit.SECONDS).toString();
        Path file = Paths.get(fileName + "_" + stringTimestamp.replaceAll("[:-]","-"));
        try {
            Files.createFile(file);
            Files.write(file, this.toString().getBytes());

        } catch (IOException e) {
            System.out.println("Exception caught: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Report{");
        sb.append("\ntimestamp=").append(timestamp);
        sb.append("\n recordMap=\n");
        for (Map.Entry<BergerCode, List<Record>> entry : recordMap.entrySet()) {
            sb.append(entry.getKey()).append("\n");
            for (Record record : entry.getValue()) {
                sb.append("Record=").append(record.toString()).append("\n");
            }
        }
        sb.append("\n}");
        return sb.toString();
    }
}
