package berger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class BitContainerSerializer extends StdSerializer<BitContainer> {

    public BitContainerSerializer() {
        super(BitContainer.class);
    }

    public BitContainerSerializer(Class<BitContainer> t) {
        super(t);
    }


    @Override
    public void serialize(BitContainer bitContainer, JsonGenerator jsonGenerator, SerializerProvider
            serializerProvider) throws
            IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", bitContainer.getClass().getName());
        StringBuilder stringBuilder = new StringBuilder();
        bitContainer.toList().forEach((e) -> {
            stringBuilder.append(e ? "1" : "0");
        });
        jsonGenerator.writeStringField("data", stringBuilder.toString());
        jsonGenerator.writeNumberField("size", bitContainer.length());
        jsonGenerator.writeEndObject();

    }
}
