package diaballik.resource;



import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.model.*;
import diaballik.serialization.DiabalikJacksonProvider;
import io.swagger.annotations.Api;


import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
//import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import java.io.File;
import java.io.IOException;
/*import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;*/


@Singleton
@Path("game")
@Api()
public class GameResource {
	private Game game;

	//public GameResource() {

	//}

	@PUT
	@Path("newGamePvP/{idGame}/{nom1}/{nom2}/{typeplateau}")
	//public Response newPvPGame(...)
	public void newPvPGame(@PathParam("idGame") final int idGame, @PathParam("nom1") final String nomJ1, @PathParam("nom2") final String nomJ2, @PathParam("typeplateau") final String typeBoard) throws NoGameCreatedException {
		Board b = this.setUpBoard(typeBoard);
		this.game = new Game(false, idGame, nomJ1, nomJ2, b);
		//Game.INSTANCE.Game(false, idGame, nomJ1, nomJ2, b);
		if (this.game == null) {
			throw new NoGameCreatedException();
		}
		Response.ok().build();
	}

	@PUT
	@Path("newGamePvIA/{idGame}/{nom1}/{typeplateau}/{strategieIA}")
	public void newPvIAGame(@PathParam("idGame") final int idGame, @PathParam("nom1") final String nomJ1, @PathParam("typeplateau") final String typeBoard, @PathParam("strategieIA") final String strat) throws NoGameCreatedException {
		Board b = this.setUpBoard(typeBoard);
		this.game = new Game(true, idGame, nomJ1, "", b);
		if (this.game == null) {
			throw new NoGameCreatedException();
		}
		Response.ok().build();
	}

	@POST
	@Path("/{id}")
	public void loadGame(@PathParam("id") final int idGame) throws IOException {
		//final String content = Files.readString(Paths.get("Game " + Integer.toString(idGame)), Charset.forName("utf8"));
		//this.game = new DiabalikJacksonProvider().getMapper().readValue(content, Game.class);
		//ToDo
	}

	@PUT
	@Path("/mooveBall/{x1}/{x2}/{y1}/{y2}")
	public void playMoveBall(@PathParam("x1") final int oldX, @PathParam("x2") final int newX, @PathParam("y1") final int oldY, @PathParam("y2") final int newY) {
		final Command move = new MoveBall(oldX, oldY, newX, newY);
		this.game.play(move);
		Response.ok().build();
	}

	@PUT
	@Path("/moovePiece/{x1}/{x2}/{y1}/{y2}")
	public void playMovePiece(@PathParam("x1") final int oldX, @PathParam("x2") final int newX, @PathParam("y1") final int oldY, @PathParam("y2") final int newY) {
		final MovePion move = new MovePion(oldX, oldY, newX, newY);
		this.game.play(move);
		Response.ok().build();
	}

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String saveGame() throws IOException {
		//System.getProperty("user.dir"
		//new DiabalikJacksonProvider().getMapper().writeValue(new File("Game " + Integer.toString(this.game.getIdGame())), this.game);
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		return mapper.writeValueAsString(this);
	}

	@GET
	@Path("currentPlayer")
	@Produces({MediaType.APPLICATION_JSON})
	public Color getCurrentPlayer() {
		return this.game.getColor();
	}


	@PUT
	@Path("/exit")
	public void leaveGame() throws IOException {
		this.saveGame();
	}

	@PUT
	@Path("/undo")
	public void undoGame() {
		this.game.undo();
		Response.ok().build();
	}
	@PUT
	@Path("redo")
	public void redoGame() {
		this.game.redo();
		Response.ok().build();
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
