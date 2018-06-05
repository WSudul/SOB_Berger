package report;

import berger.BergerCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
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
        Path file = Paths.get(fileName + "_" + timestamp.toString());
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
        sb.append("\n recordMap=").append(recordMap);
        sb.append("\n}");
        return sb.toString();
    }
}
