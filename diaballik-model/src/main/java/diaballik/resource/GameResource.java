package diaballik.resource;


import diaballik.model.*;
import diaballik.serialization.DiabalikJacksonProvider;



import javax.inject.Singleton;
import javax.ws.rs.*;
import java.io.File;
import java.io.IOException;
/*import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;*/


@Singleton
@Path("game")
public class GameResource {
	private Game game;

	public GameResource() {

	}

	@PUT
	@Path("newGamePvP/{idGame}/{nom1}/{nom2}/{typeplateau}")
	public void newPvPGame(@PathParam("idGame") final int idGame, @PathParam("nom1") final String nomJ1, @PathParam("nom2") final String nomJ2, @PathParam("typeplateau") final String typeBoard) {
		this.game = new Game(false, idGame, nomJ1, nomJ2);
		this.setUpBoard(typeBoard);
	}

	@PUT
	@Path("newGamePvIA/{idGame}/{nom1}/{typeplateau}/{strategieIA}")
	public void newPvIAGame(@PathParam("idGame") final int idGame, @PathParam("nom1") final String nomJ1, @PathParam("typeplateau") final String typeBoard, @PathParam("strategieIA") final String strat) {
		this.game = new Game(true, idGame, nomJ1, "");
		this.setUpBoard(typeBoard);
	}

	@POST
	@Path("/{id}")
	public void loadGame(@PathParam("id") final int idGame) throws IOException {
		//final String content = Files.readString(Paths.get("Game " + Integer.toString(idGame)), Charset.forName("utf8"));
		//this.game = new DiabalikJacksonProvider().getMapper().readValue(content, Game.class);
	}

	@PUT
	@Path("/mooveBall/{x1}/{x2}/{y1}/{y2}")
	public void playMoveBall(@PathParam("x1") final int oldX, @PathParam("x2") final int newX, @PathParam("y1") final int oldY, @PathParam("y2") final int newY) {
		final MoveBall move = new MoveBall(oldX, oldY, newX, newY);
		this.game.play(move);
	}

	@PUT
	@Path("/moovePiece/{x1}/{x2}/{y1}/{y2}")
	public void playMovePiece(@PathParam("x1") final int oldX, @PathParam("x2") final int newX, @PathParam("y1") final int oldY, @PathParam("y2") final int newY) {
		final MovePion move = new MovePion(oldX, oldY, newX, newY);
		this.game.play(move);
	}

	@GET
	@Path("")
	public void saveGame() throws IOException {
		//System.getProperty("user.dir"
		new DiabalikJacksonProvider().getMapper().writeValue(new File("Game " + Integer.toString(this.game.getIdGame())), this.game);
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
	}
	@PUT
	@Path("redo")
	public void redoGame() {
		this.game.redo();
	}

	public void setUpBoard(final String typeBoard) {
		if (typeBoard.equals("Standard")) {
			final Standard builder = new Standard();
			builder.placerPieces(this.game.getBoard());
		} else if (typeBoard.equals("Random")) {
			final Random builder = new Random();
			builder.placerPieces(this.game.getBoard());
		} else {
			final AmongUs builder = new AmongUs();
			builder.placerPieces(this.game.getBoard());
		}

	}

}
