package diaballik.resource;

import diaballik.model.*;

import com.github.hanleyt.JerseyExtension;
import diaballik.serialization.DiabalikJacksonProvider;

import java.io.File;
import java.net.URI;
import java.io.IOException;
import java.util.stream.IntStream;
//import javafx.scene.paint.Color;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.*;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;

public class TestGameResource {
	@SuppressWarnings("unused") @RegisterExtension JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

	Game gametest;

	private Application configureJersey() {
		return new ResourceConfig(GameResource.class).
				register(MyExceptionMapper.class).
				register(JacksonFeature.class).
				register(DiabalikJacksonProvider.class).
				property("jersey.config.server.tracing.type", "ALL");
	}

	@BeforeEach
	void setUp() {
		Standard builder = new Standard();
		Board b = builder.placerPieces();
		gametest = new Game(false, 1, "Antoine", "Adrien", b);
	}

	@Test
	void testTemplate(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
	}

	@Test
	void testGameCreation(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		//final Game game = client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text("")).readEntity(Game.class);
		//assertEquals(game,gametest);
		final Response res = client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));
		final Game g = res.readEntity(Game.class);
		IntStream.rangeClosed(0,6).forEach(i->{
			assertEquals(Color.Yellow,g.getBoard().getPiece(0,i).getColor());
			assertEquals(Color.Green,g.getBoard().getPiece(6,i).getColor());
			IntStream.rangeClosed(1,5).forEach(j->{
				assertEquals(null,g.getBoard().getPiece(j,i));
			});
		});
		assertEquals("Antoine",g.getJoueur1().getName());
		assertEquals(Color.Yellow,g.getJoueur1().getColor());
		assertEquals(Color.Green, g.getJoueur2().getColor());
		assertEquals("Adrien",g.getJoueur2().getName());
		Assertions.assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	void testMovePiece(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Response res = client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));
		Pawn p = res.readEntity(Game.class).getBoard().getPiece(0,0);
		res = client.target(baseUri).path("game/movePiece/0/1/0/0").request().put(Entity.text(""));
		Game g = res.readEntity(Game.class);
		assertEquals(p.getColor(),g.getBoard().getPiece(1,0).getColor());
		assertEquals(null,g.getBoard().getPiece(0,0));
	}

	@Test
	void testMoveBall(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));
		Response res = client.target(baseUri).path("game/moveBall/0/0/3/4").request().put(Entity.text(""));
		Game g = res.readEntity(Game.class);
		assertTrue(g.getBoard().getPiece(0,4).hasBall());
		assertFalse(g.getBoard().getPiece(0,3).hasBall());
	}


	@Test
	void testSaveGame(final Client client, final URI baseUri) throws IOException {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		final Response res = client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));
		final Game g = res.readEntity(Game.class);
		client.target(baseUri).path("game/save/TestGameSave").request().post(Entity.json(g));
		assertTrue((new File("TestGameSave").exists()));
	}

	@Test
	void testLoadGame(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);

		final Response res = client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));
		final Game g = res.readEntity(Game.class);
		client.target(baseUri).path("game/save/TestGameSave").request().post(Entity.json(g));

		Game game = client.target(baseUri).path("game/load/TestGameSave").request().get().readEntity(Game.class);

		IntStream.rangeClosed(0,6).forEach(i->{
			assertEquals(game.getBoard().getPiece(0,i).getColor(), g.getBoard().getPiece(0,i).getColor());
			assertEquals(game.getBoard().getPiece(6,i).getColor(), g.getBoard().getPiece(6,i).getColor());
			IntStream.rangeClosed(1,5).forEach(j->{
				assertEquals(null,game.getBoard().getPiece(j,i));
			});
		});
		assertEquals(game.getJoueur1().getName(), g.getJoueur1().getName());
		assertEquals(game.getJoueur1().getColor(), g.getJoueur1().getColor());
		assertEquals(game.getJoueur2().getColor(), g.getJoueur2().getColor());
		assertEquals(game.getJoueur2().getName(), g.getJoueur2().getName());
	}

	@Test
	void testDeleteGame(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);

		final Response res = client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));
		final Game g = res.readEntity(Game.class);
		client.target(baseUri).path("game/save/TestGameSave").request().post(Entity.json(g));
		assertTrue((new File("TestGameSave").exists()));

		Response res2 = client.target(baseUri).path("game/load/TestGameSave").request().delete();
		assertFalse((new File("TestGameSave").exists()));
		Assertions.assertEquals(Response.Status.OK.getStatusCode(), res2.getStatus());
	}

	@Test
	void testUndo(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));
		Response res = client.target(baseUri).path("game/moveBall/0/0/3/4").request().put(Entity.text(""));
		Game g = res.readEntity(Game.class);
		assertTrue(g.getBoard().getPiece(0,4).hasBall());
		assertFalse(g.getBoard().getPiece(0,3).hasBall());

		Response res1 = client.target(baseUri).path("game/undo").request().put(Entity.text(""));
		Game g1 = res1.readEntity(Game.class);
		assertTrue(g1.getBoard().getPiece(0,3).hasBall());
		assertFalse(g1.getBoard().getPiece(0,4).hasBall());
	}

	@Test
	void testRedo(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));
		Response res = client.target(baseUri).path("game/moveBall/0/0/3/4").request().put(Entity.text(""));
		Game g = res.readEntity(Game.class);
		assertTrue(g.getBoard().getPiece(0,4).hasBall());
		assertFalse(g.getBoard().getPiece(0,3).hasBall());

		Response res1 = client.target(baseUri).path("game/undo").request().put(Entity.text(""));
		Game g1 = res1.readEntity(Game.class);
		assertTrue(g1.getBoard().getPiece(0,3).hasBall());
		assertFalse(g1.getBoard().getPiece(0,4).hasBall());

		Response res2 = client.target(baseUri).path("game/redo").request().put(Entity.text(""));
		Game g2 = res2.readEntity(Game.class);
		assertTrue(g2.getBoard().getPiece(0,4).hasBall());
		assertFalse(g2.getBoard().getPiece(0,3).hasBall());
	}

	@Test
	void testCurrentPlayer(final Client client, final URI baseUri) throws IOException {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);

		client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));

		Response res1 = client.target(baseUri).path("game/currentPlayer").request().get();
		Color color = res1.readEntity(Color.class);
		//String stringEntity = res1.readEntity(String.class);
		//Color color  = new DiabalikJacksonProvider().getMapper().readValue(stringEntity, Color.class);
		assertEquals(color, Color.Yellow);

		client.target(baseUri).path("game/movePiece/0/1/5/5").request().put(Entity.text(""));
		client.target(baseUri).path("game/movePiece/0/1/6/6").request().put(Entity.text(""));
		client.target(baseUri).path("game/movePiece/1/1/5/4").request().put(Entity.text(""));

		Response res5 = client.target(baseUri).path("game/currentPlayer").request().get();
		Color color1 = res5.readEntity(Color.class);
		assertEquals(color1, Color.Green);
	}


}
