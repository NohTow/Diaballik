package diaballik.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import diaballik.model.Board;
import diaballik.model.Game;
import diaballik.model.IA;
import diaballik.model.Noob;
import diaballik.model.Advanced;
import diaballik.model.Color;
import diaballik.model.Pawn;
import diaballik.model.Command;
import diaballik.model.MovePion;
import diaballik.model.MoveBall;
import diaballik.model.Standard;
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
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;


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
			if ("Advanced".equals(strat)) {
				((IA) game.getJoueur2()).changeLevel(new Advanced());
			}

		} catch (NoGameCreatedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return Response.ok().entity(game).build();
	}


	@GET
	@Path("save/{dir}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listFinishedGame(@PathParam("dir") final String dir) {
		final File dossier = new File("./" + dir);
		final String[] listNom = dossier.list();
		return Response.ok().entity(listNom).build();
	}

	@PUT
	@Path("save/{dir}/{file}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveGame(@PathParam("dir") final String dir, @PathParam("file") final String file) throws IOException {
		game.saveGame(dir + '/' + file);
		return Response.ok().entity(game).build();
	}


	@GET
	@Path("load/{file}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadGame(@PathParam("file") final String file) throws IOException {
		game = new DiabalikJacksonProvider().getMapper().readValue(new File("./savedgame/" + file), Game.class);
		if (game.isFinished().isPresent()) {
			return Response.ok().entity(game.isFinished().get()).build();
		}
		return Response.ok().entity(game).build();
	}

	@GET
	@Path("replay/{file}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response replay(@PathParam("file") final String file) throws IOException {
		game = new DiabalikJacksonProvider().getMapper().readValue(new File("./finishedgame/" + file), Game.class);
		IntStream.iterate(0, x -> x + 1).limit(game.getSave().size()).forEach(x -> {
			game.undo();
		});
		return Response.ok().entity(game).build();
	}

	@PUT
	@Path("replay/forward")
	@Produces(MediaType.APPLICATION_JSON)
	public Response forwardReplay() {
		game.redo();
		return Response.ok().entity(game).build();
	}

	@PUT
	@Path("replay/backward")
	@Produces(MediaType.APPLICATION_JSON)
	public Response backwardReplay() {
		game.undo();
		return Response.ok().entity(game).build();
	}

	@DELETE
	@Path("delete/{dir}/{file}")
	public Response deleteGame(@PathParam("dir") final String dir, @PathParam("file") final String file) {
		final File fileToDelete = new File("./" + dir + "/" + file + ".json");
		final boolean delete = fileToDelete.delete();
		if (delete) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_MODIFIED).entity("La suppression n'a pas eu lieu").build();
		}
	}

	@PUT
	@Path("mooveIA")
	@Produces(MediaType.APPLICATION_JSON)
	public Response iaPlay() {
		if (game.getColor().equals(Color.Green)) {
			((IA) game.getJoueur2()).getLevel().exec(game);
			game.flushUndo();
			return Response.ok().entity(game).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("Ce n'est pas au tour de l'IA !").build();
	}

	@PUT
	@Path("moove/{x1}/{x2}/{y1}/{y2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response play(@PathParam("x1") final int oldX, @PathParam("x2") final int newX, @PathParam("y1") final int oldY, @PathParam("y2") final int newY) {
		if (game.getBoard().getPiece(oldX, oldY).hasBall()) {
			game.play(new MoveBall(oldX, oldY, newX, newY));
		} else {
			game.play(new MovePion(oldX, oldY, newX, newY));
		}
		game.flushUndo();
		if (game.isFinished().isPresent()) {
			return Response.ok().entity(game.isFinished().get()).build();
		}
		return Response.ok().entity(game).build();
	}

	@PUT
	@Path("/moveBall/{x1}/{x2}/{y1}/{y2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response playMoveBall(@PathParam("x1") final int oldX, @PathParam("x2") final int newX, @PathParam("y1") final int oldY, @PathParam("y2") final int newY) {
		final MoveBall move = new MoveBall(oldX, oldY, newX, newY);
		game.play(move);
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
		if (game.hasIA() && game.getColor() == Color.Green) {
			return Response.status(Response.Status.BAD_REQUEST).entity("C'est au tour de l'IA de jouer !").build();
		}
		if (p != null) {
			final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
			final ArrayList<Command> list = p.movePlayable(game);
			if (p.hasBall()) {
				final ArrayList<MoveBall> res = new ArrayList<MoveBall>();
				list.forEach(c -> {
					res.add((MoveBall) c);
				});
				final String serializedList = mapper.writeValueAsString(list);
				return Response.ok().entity(serializedList).build();
			} else {
				final ArrayList<MovePion> res = new ArrayList<MovePion>();
				list.forEach(c -> {
					res.add((MovePion) c);
				});
				final String serializedList = mapper.writeValueAsString(list);
				return Response.ok().entity(serializedList).build();
			}

		}
		return Response.status(Response.Status.BAD_REQUEST).entity("Il n'y a pas de pièce ici !").build();
	}

	@PUT
	@Path("/undo")
	@Produces({MediaType.APPLICATION_JSON})
	public Response undoGame() {
		if (game.getSave().size() != 0) {
			game.undo();
			return Response.ok().entity(game).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("Aucun mouvement à undo !").build();
	}

	@PUT
	@Path("/redo")
	@Produces({MediaType.APPLICATION_JSON})
	public Response redoGame() {
		if (game.getUndo().size() != 0) {
			game.redo();
			if (game.isFinished().isPresent()) {
				return Response.ok().entity(game.isFinished().get()).build();
			}
			return Response.ok().entity(game).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("Aucun mouvement à redo !").build();
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

