package io;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DataReader {

    public static List<DataInput> readJsonData(String filename) throws IOException {
        Path file = Paths.get(filename);
        BufferedReader reader = Files.newBufferedReader(file);
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(reader, DataInput[].class));
    }
    
}
