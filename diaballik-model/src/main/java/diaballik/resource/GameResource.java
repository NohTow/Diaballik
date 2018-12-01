package diaballik.resource;



//import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.model.*;
import diaballik.serialization.DiabalikJacksonProvider;
import io.swagger.annotations.Api;


import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;

//import java.util.logging.Level;
//import java.util.logging.Logger;

/*import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;*/

@Singleton
@Path("game")
@Api(value = "game", description = "Operations on the game")
public class GameResource {

	//static final Logger LOGGER = Logger.getAnonymousLogger();
	Game game;


	@PUT
	@Path("newGamePvP/{idGame}/{nom1}/{nom2}/{typeplateau}")
	@Produces(MediaType.APPLICATION_JSON)
	//public Response newPvPGame(...)
	public Game newPvPGame(@PathParam("idGame") final int idGame, @PathParam("nom1") final String nomJ1, @PathParam("nom2") final String nomJ2, @PathParam("typeplateau") final String typeBoard) throws NoGameCreatedException {
		Board b = this.setUpBoard(typeBoard);
		game = new Game(false, idGame, nomJ1, nomJ2, b);
		if (this.game == null) {
			throw new NoGameCreatedException();
		}
		//Response.ok().build();
		return game;
	}

	@PUT
	@Path("newGamePvIA/{idGame}/{nom1}/{typeplateau}/{strategieIA}")
	@Produces(MediaType.APPLICATION_JSON)
	public Game newPvIAGame(@PathParam("idGame") final int idGame, @PathParam("nom1") final String nomJ1, @PathParam("typeplateau") final String typeBoard, @PathParam("strategieIA") final String strat) throws NoGameCreatedException {
		Board b = this.setUpBoard(typeBoard);
		game = new Game(true, idGame, nomJ1, "", b);
		if (this.game == null) {
			throw new NoGameCreatedException();
		}
		return game;
	}

	/*@POST
	@Path("/{id}")
	public void loadGame(@PathParam("id") final int idGame) throws IOException {
		//final String content = Files.readString(Paths.get("Game " + Integer.toString(idGame)), Charset.forName("utf8"));
		//this.game = new DiabalikJacksonProvider().getMapper().readValue(content, Game.class);
		//ToDo
	}*/

	@POST
	@Path("save/{file}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Game saveGame(final Game game, @PathParam("file") final String file) throws IOException {
		game.saveGame(file);
		return game;
	}

	@GET
	@Path("load/{file}")
	@Produces(MediaType.APPLICATION_JSON)
	public Game loadGame(@PathParam("file") final String file) throws IOException {
		final Game loadedGame = new DiabalikJacksonProvider().getMapper().readValue(new File("./" + file + ".json"), Game.class);
		game = loadedGame;
		return loadedGame;
	}

	@DELETE
	@Path("load/{file}")
	public boolean deleteGame(@PathParam("file") final String file) {
		final File fileToDelete = new File("./" + file + ".json");
		final boolean delete = fileToDelete.delete();
		return delete;
	}

	@PUT
	@Path("/moveBall/{x1}/{x2}/{y1}/{y2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Game playMoveBall(@PathParam("x1") final int oldX, @PathParam("x2") final int newX, @PathParam("y1") final int oldY, @PathParam("y2") final int newY) {
		final MoveBall move = new MoveBall(oldX, oldY, newX, newY);
		game.play(move);
		return game;
	}

	@PUT
	@Path("/movePiece/{x1}/{x2}/{y1}/{y2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Game playMovePiece(@PathParam("x1") final int oldX, @PathParam("x2") final int newX, @PathParam("y1") final int oldY, @PathParam("y2") final int newY) {
		final MovePion move = new MovePion(oldX, oldY, newX, newY);
		game.play(move);
		return game;
	}

	/*
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String saveGame() throws IOException {
		//System.getProperty("user.dir"
		//new DiabalikJacksonProvider().getMapper().writeValue(new File("Game " + Integer.toString(this.game.getIdGame())), this.game);
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		return mapper.writeValueAsString(this);
	}
	*/

	@GET
	@Path("currentPlayer")
	@Produces({MediaType.APPLICATION_JSON})
	public Color getCurrentPlayer() {
		return game.getColor();
	}


	/*@PUT
	@Path("/exit")
	public void leaveGame() throws IOException {
		this.saveGame();
	}*/

	@PUT
	@Path("/undo")
	@Produces({MediaType.APPLICATION_JSON})
	public Game undoGame() {
		game.undo();
		return game;
	}

	@PUT
	@Path("redo")
	@Produces({MediaType.APPLICATION_JSON})
	public Game redoGame() {
		game.redo();
		return game;
	}

	public Board setUpBoard(final String typeBoard) {
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

	}

}

