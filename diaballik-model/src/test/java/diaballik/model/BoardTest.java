package diaballik.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.resource.GameResource;
import diaballik.serialization.DiabalikJacksonProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

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

		Game h = new Game(false, 5,"Antoine","Adrien",b);


		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(h);
		System.out.println(serializedObject);
		final Object readValue = mapper.readValue(serializedObject, h.getClass());

		assertEquals(h, readValue);

	}
}