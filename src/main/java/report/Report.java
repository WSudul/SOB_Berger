package report;

import berger.BergerCode;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class Report {

    private final Instant timestamp;
    private Map<BergerCode, List<Record>> recordMap; /*maps copy of unmodified BerGerCode instance to list of
    generated changes */

    public Report() {
        timestamp = Instant.now();
        recordMap = new HashMap<>();
    }

    public boolean addRecord(BergerCode originalData, Record record) {
        recordMap.putIfAbsent(originalData, new ArrayList<>());
        return recordMap.get(originalData).add(record);

    }

    private String serialize(Object obj) {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, true);
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean exportToFile(String fileName) {

        String stringTimestamp=timestamp.truncatedTo(ChronoUnit.SECONDS).toString();
        Path file = Paths.get(fileName + "_" + stringTimestamp.replaceAll("[:-]", "_") + ".json");
        try {
            Files.createFile(file);
            Files.write(file, serialize(this).getBytes());

        } catch (IOException e) {
            System.out.println("Exception caught: " + e.getMessage());
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Report{");
        sb.append("timestamp=").append(timestamp);
        sb.append(", recordMap=").append(recordMap);
        sb.append('}');
        return sb.toString();
    }

    @JsonGetter("timestamp")
    public Instant getTimestamp() {
        return timestamp;
    }

    @JsonAnyGetter
    public Map<BergerCode, List<Record>> getRecordMap() {
        return recordMap;
    }
}
