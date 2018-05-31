package io;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DataReaderTest {

    @Test
    public void readJsonData() throws Exception {
        List<DataInput> expectedData = Arrays.asList(
                new DataInput(InputType.INT, "4321"),
                new DataInput(InputType.LONG, "1234567890"),
                new DataInput(InputType.STRING, "FooBar"),
                new DataInput(InputType.BYTES, "010101110001"));

        List<DataInput> dataInputs = DataReader.readJsonData("src/test/resources/test.json");

        assertFalse(dataInputs.isEmpty());
        assertEquals(expectedData.size(), dataInputs.size());
        assertEquals(expectedData, dataInputs);
    }

    @Test(expected = IOException.class)
    public void readJsonDataThrowsExceptionOnMissingFile() throws Exception {
        List<DataInput> dataInputs = DataReader.readJsonData("invalidfile.json");

    }

}