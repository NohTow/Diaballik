package diaballik.resource;


//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.model.Command;
import diaballik.model.Pawn;
import diaballik.model.IA;
import diaballik.model.Color;
import diaballik.model.Board;
import diaballik.model.Game;
import diaballik.model.MoveBall;
import diaballik.model.Noob;
import diaballik.model.Standard;
import diaballik.model.MovePion;
import diaballik.model.AmongUs;
import diaballik.model.Random;
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

//import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
//import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	static final Logger LOGGER = Logger.getAnonymousLogger();
	Game game;


	@PUT
	@Path("newGamePvP/{idGame}/{nom1}/{nom2}/{typeplateau}")
	@Produces(MediaType.APPLICATION_JSON)
	//public Response newPvPGame(...)
	public Response newPvPGame(@PathParam("idGame") final int idGame, @PathParam("nom1") final String nomJ1, @PathParam("nom2") final String nomJ2, @PathParam("typeplateau") final String typeBoard) throws NoGameCreatedException {
		try {
			final Board b = this.setUpBoard(typeBoard);
			game = new Game(false, idGame, nomJ1, nomJ2, b);
		} catch (NoGameCreatedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return Response.ok().entity(game).build();
		//return game;
	}

	@PUT
	@Path("newGamePvIA/{idGame}/{nom1}/{typeplateau}/{strategieIA}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response newPvIAGame(@PathParam("idGame") final int idGame, @PathParam("nom1") final String nomJ1, @PathParam("typeplateau") final String typeBoard, @PathParam("strategieIA") final String strat) throws NoGameCreatedException {
		try {
			final Board b = this.setUpBoard(typeBoard);
			game = new Game(true, idGame, nomJ1, "", b);
			if ("Noob".equals(strat)) {
				((IA) game.getJoueur2()).changeLevel(new Noob());
			}

		} catch (NoGameCreatedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return Response.ok().entity(game).build();
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
	public Response saveGame(final Game game, @PathParam("file") final String file) throws IOException {
		game.saveGame(file);
		return Response.ok().entity(game).build();
	}

	@GET
	@Path("load/{file}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadGame(@PathParam("file") final String file) throws IOException {
		game = new DiabalikJacksonProvider().getMapper().readValue(new File("./" + file), Game.class);

		return Response.ok().entity(game).build();
	}

	@POST
	@Path("replay/{file}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response replay(@PathParam("file") final String file) throws IOException {
		game = new DiabalikJacksonProvider().getMapper().readValue(new File("./" + file), Game.class);
		while(game.getSave().size() != 0) {
			game.undo();
		}
		return Response.ok().entity(game).build();
	}
	@POST
	@Path("replay/forward")
	@Produces(MediaType.APPLICATION_JSON)
	public Response forwardReplay() {
		game.redo();
		return Response.ok().entity(game).build();
	}

	@POST
	@Path("replay/backward")
	@Produces(MediaType.APPLICATION_JSON)
	public Response backwardReplay() {
		game.undo();
		return Response.ok().entity(game).build();
	}

	@DELETE
	@Path("load/{file}")
	public Response deleteGame(@PathParam("file") final String file) {
		final File fileToDelete = new File("./" + file);
		final boolean delete = fileToDelete.delete();
		if (delete) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_MODIFIED).entity("La suppression n'a pas eu lieu").build();
		}
	}

	@PUT
	@Path("/moveBall/{x1}/{x2}/{y1}/{y2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response playMoveBall(@PathParam("x1") final int oldX, @PathParam("x2") final int newX, @PathParam("y1") final int oldY, @PathParam("y2") final int newY) {
		final MoveBall move = new MoveBall(oldX, oldY, newX, newY);
		game.play(move);
		//return Response.ok().entity(game).build();
		if (game.isFinished().isPresent()) {
			return Response.ok().entity(game.isFinished().get()).build();
		}
		return Response.ok().entity(game).build();
	}

	@PUT
	@Path("/movePiece/{x1}/{x2}/{y1}/{y2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response playMovePiece(@PathParam("x1") final int oldX, @PathParam("x2") final int newX, @PathParam("y1") final int oldY, @PathParam("y2") final int newY) {
		final MovePion move = new MovePion(oldX, oldY, newX, newY);
		game.play(move);
		if (game.isFinished().isPresent()) {
			return Response.ok().entity(game.isFinished().get()).build();
		}
		return Response.ok().entity(game).build();
	}

	@GET
	@Path("/currentPlayer")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCurrentPlayer() {
		final Color color = game.getColor();
		return Response.ok().entity(color).build();
	}

	@GET
	@Path("/moovePlayable/{x}/{y}")
	public Response moovePlayable(@PathParam("x") final int x, @PathParam("y") final int y) throws JsonProcessingException {
		final Pawn p = game.getBoard().getPiece(x, y);
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();

		if(p != null) {
			final ArrayList<Command> list = p.movePlayable(game);
			final String serializedObject = mapper.writeValueAsString(list);
			return Response.ok().entity(serializedObject).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("Il n'y a pas de pièce ici !").build();
	}

	/*@PUT
	@Path("/exit")
	public void leaveGame() throws IOException {
		this.saveGame();
	}*/

	@PUT
	@Path("/undo")
	@Produces({MediaType.APPLICATION_JSON})
	public Response undoGame() {
		game.undo();
		return Response.ok().entity(game).build();
	}

	@PUT
	@Path("/redo")
	@Produces({MediaType.APPLICATION_JSON})
	public Response redoGame() {
		game.redo();
		return Response.ok().entity(game).build();
	}

	public Board setUpBoard(final String typeBoard) throws NoGameCreatedException {
		if ("Standard".equals(typeBoard)) {
			final Standard builder = new Standard();
			return builder.placerPieces();
		} else if ("Random".equals(typeBoard)) {
			final Random builder = new Random();
			return builder.placerPieces();
		} else if ("AmongUs".equals(typeBoard)) {
			final AmongUs builder = new AmongUs();
			return builder.placerPieces();
		} else {
			throw new NoGameCreatedException("Le type de plateau doit etre Standard, Random ou AmongUs");
		}

	}

}

