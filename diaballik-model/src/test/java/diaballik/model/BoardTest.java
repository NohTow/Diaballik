package diaballik.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.resource.GameResource;
import diaballik.serialization.DiabalikJacksonProvider;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


class BoardTest {

	/*@org.junit.jupiter.api.Test
	public void testMain() throws Exception{
		//Standard builder = new Standard();
		//Board b = builder.placerPieces();
		//Game g = new Game(false, 1, "Antoine","Adrien",b);
		/*Pawn p1 = new Pawn(0, 0, true, Color.Yellow);
		Pawn p2 = new Pawn(1,0,false, Color.Yellow);
		Pawn p3 = new Pawn(1,1,false,Color.Green);

		g.getBoard().setPiece(0,0,p1);

		g.getBoard().setPiece(1,0,p2);
		g.getBoard().setPiece(1,1,p3);
		ArrayList<Command> l = p1.movePlayable(g);
		//g.play(l.get(0));
		System.out.println(p2.hasBall());
		g.play(p2.movePlayable(g).get(0));
		System.out.println(p2.getX());
		System.out.println(p2.getY());

		System.out.println(l.size());


		//assertEquals(p1.movePlayable(g).size(),3);
	}
	@Test
	public void testTest(){
		Game g = new Game(false, 1, "Antoine","Adrien");
		AmongUs builder = new AmongUs();
		builder.placerPieces(g.getBoard());
		ArrayList<Pawn> l = g.getBoard().getList();
		l.stream().filter(p->p.getColor() == Color.Yellow).forEach(p->{
			System.out.println(p.getColor()+" "+ p.getX()+" "+p.getY()+" "+ p.hasBall());
		});
	}*/
	@Test
	public void testMarshall() throws IOException {
		//final Humain h = new Humain("Antoine",Color.Yellow);
		Standard builder = new Standard();
		Board b = builder.placerPieces();

		Game h = new Game(true, 5,"Antoine","",b);
		((IA)h.getJoueur2()).changeLevel(new Noob());

		//try {
			//h.play(new MovePion(0,0,4,4));
		//} catch (MoveNotPossibleException e) {
			//e.printStackTrace();
		//}
		//Pawn b = new Pawn(3,5,true,Color.Yellow);
		//h.getBoard().getPiece(0,0).setHasBall(true);

		//h.play(new MovePion(0,2,1,2));
		//h.play(new MovePion(0,0,1,0));
		//System.out.println(h.getBoard().getPiece(1,0).movePlayable(h));
		//MovePion m = new MovePion(1,1,1,1);
		//h.play(new MovePion(6,6,5,6));
		h.play(new MovePion(0,0,1,0));
		//h.play(new MovePion(6,0,5,0));
		h.play(new MovePion(0,3,1,3));
		ArrayList<MovePion> list = new ArrayList<MovePion>();
		list.add(new MovePion(1,1,1,1));
		list.add(new MovePion(1,1,1,0));
		//System.out.println(h.getBoard().getPiece(1,3).canPassTo(h.getBoard().getPiece(1,0),h.getBoard()));
		//System.out.println( h.getBoard().getPiece(1,3).movePlayable(h));
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(list);
		JsonReader jsoR = Json.createReader(new StringReader(serializedObject));
		JsonArray ja = jsoR.readArray();
		IntStream.range(0,ja.size()).forEach(i->{
			System.out.println(ja.getJsonObject(i).get("newX"));
		});

		System.out.println((ja.getJsonObject(0)).get("newX"));
		//ArrayList<MovePion> kr = mapper.readValue(serializedObject, ArrayList.class);
		//System.out.println(kr.get(0).getNewX());
		//.out.println(mapper.readValue(serializedObject, Command.class));
		//System.out.println(serializedObject);
		//System.out.println(serializedObject);
		//final ArrayList<MovePion> temp = mapper.readValue(serializedObject, ArrayList.class);

		//System.out.println(temp.get(0).getClass());
		//System.out.println(((Game) h).getSave().peek().getNewX());
		//assertEquals(h, readValue);*/


	}
}