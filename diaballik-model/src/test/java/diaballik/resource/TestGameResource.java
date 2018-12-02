package diaballik.resource;

import diaballik.model.*;

import com.github.hanleyt.JerseyExtension;
import diaballik.serialization.DiabalikJacksonProvider;
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
		Board b = null;
		try {
			b = this.setUpBoard("Standard");
		} catch (NoGameCreatedException e) {
			e.printStackTrace();
		}
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
	void testMovePiece(final Client client, final URI baseUri){
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Response res = client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));
		Pawn p = res.readEntity(Game.class).getBoard().getPiece(0,0);
		res = client.target(baseUri).path("game/movePiece/0/1/0/0").request().put(Entity.text(""));
		Game g = res.readEntity(Game.class);
		assertEquals(p.getColor(),g.getBoard().getPiece(1,0).getColor());
		assertEquals(null,g.getBoard().getPiece(0,0));

	}

	@Test
	void testMoveBall(final Client client, final URI baseUri){
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Response res = client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text(""));
		Pawn p = res.readEntity(Game.class).getBoard().getPiece(0,3);
		res = client.target(baseUri).path("game/moveBall/0/0/3/4").request().put(Entity.text(""));
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

		Response res2 = client.target(baseUri).path("game/load/TestGameSave").request().delete();
		Assertions.assertEquals(Response.Status.OK.getStatusCode(), res2.getStatus());
	}

	/*@Test
	void testMoveBall(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		final Game res1 = client.target(baseUri).path("game/newGamePvP/1/Antoine/Adrien/Standard").request().put(Entity.text("")).readEntity(Game.class);
		final Game res = client.target(baseUri).path("game/moveBall/0/3/0/4").request().put(Entity.text("")).readEntity(Game.class);
		final Game res2 = client.target(baseUri).path("game/moveBall/0/4/0/3").request().put(Entity.text("")).readEntity(Game.class);
		gametest.play(new MoveBall(0, 3, 0, 4));
		//Assertions.assertEquals(res2, res1);
		assertEquals(res, gametest);
	}*/



	/*public Board setUpBoard(final String typeBoard) {
		if (typeBoard.equals("Standard")) {
			final Standard builder = new Standard();
			return builder.placerPieces();
		} else if (typeBoard.equals("Random")) {
			final Random builder = new Random();
			return builder.placerPieces();
		} else {
			final AmongUs builder = new AmongUs();
			return builder.placerPieces();
		}

	}*/
	public Board setUpBoard(final String typeBoard) throws NoGameCreatedException {
		if (typeBoard.equals("Standard")) {
			final Standard builder = new Standard();
			return builder.placerPieces();
		} else if (typeBoard.equals("Random")) {
			final Random builder = new Random();
			return builder.placerPieces();
		} else if (typeBoard.equals("AmongUs")){
			final AmongUs builder = new AmongUs();
			return builder.placerPieces();
		} else {
			throw new NoGameCreatedException("Le type de plateau doit etre Standard, Random ou AmongUs");
		}

	}
}
