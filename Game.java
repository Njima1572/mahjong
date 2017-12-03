public class Game{
	public int ID;
	public Table table;
	private boolean gameIsGoing;
	public Player[] players = new Player[4];
	public Game(int id)
	{
		for(int i=0; i<4; i++)
		{
			players[i] = new Player(table);
		}
		table = new Table(players);
		table.init(false);
	}

	public void visualize()
	{

	}

	public static void main(String[] args)
	{
		Game game = new Game(1);
		for(int i=0; i<game.players[0].tehai.length - 1; i++)
		{
			System.out.println(game.players[0].tehai[i].toString());
		}
		System.out.println("--------------------");
		System.out.println("--------------------");
		game.players[0].riipai();
		for(int i=0; i<game.players[0].tehai.length - 1; i++)
		{
			System.out.println(game.players[0].tehai[i].toString());
		}
		System.out.println(game.players[0].getShanten());

		game.table.justDiscarded = new Hai(2, "sangen");

		System.out.println("Can chi?: " + (game.players[0].chiCheck(game.table.justDiscarded)));
		System.out.println("Can pon?: " + (game.players[0].ponCheck(game.table.justDiscarded)));
		System.out.println("Can kan?: " + (game.players[0].kanCheck(game.table.justDiscarded)));
		while(game.gameIsGoing){
			game.players[game.table.turn].turnMove(true);
		}
	}
}
