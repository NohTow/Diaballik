package diaballik.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import javafx.scene.paint.Color;

public class ColorDeserializer extends StdDeserializer<Color> {
	public ColorDeserializer() {
		super(Color.class);
	}

	@Override
	public Color deserialize(final JsonParser parser, final DeserializationContext deserializationContext) throws IOException {
		final JsonNode node = parser.getCodec().readTree(parser);
		return Color.web(node.get("code").textValue());
	}
}
