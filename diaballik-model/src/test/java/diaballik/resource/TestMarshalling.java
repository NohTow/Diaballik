package diaballik.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.model.*;
import diaballik.serialization.DiabalikJacksonProvider;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMarshalling {

	public TestMarshalling() throws JsonProcessingException {
	}

	@Test
	public void testGameMarshalling() throws IOException {
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		Game g = new Game(false, 5,"Antoine","Adrien",b);
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(g);
		assertEquals(g, mapper.readValue(serializedObject, Game.class));
		System.out.println(serializedObject);
	}

	@Test
	public void testBoardMarshalling() throws IOException{
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(b);
		assertEquals(b, mapper.readValue(serializedObject, Board.class));
	}

	@Test
	public void testMoveBall() throws IOException{
		MoveBall mb = new MoveBall(1,1,1,1);
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(mb);
		assertEquals(mb, mapper.readValue(serializedObject, MoveBall.class));
	}

	@Test
	public void testMovePiece() throws IOException{
		MovePion mp = new MovePion(1,1,1,1);
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(mp);
		assertEquals(mp, mapper.readValue(serializedObject, MovePion.class));
	}

	@Test
	public void testPlayer() throws IOException{
		Humain h = new Humain("Antoine", Color.Yellow);
		IA ia = new IA(Color.Green, new Noob());
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(h);
		final String serializedObject2 = mapper.writeValueAsString(ia);
		assertEquals(h, mapper.readValue(serializedObject, Humain.class));
		assertEquals(ia, mapper.readValue(serializedObject2, IA.class));
	}
}
