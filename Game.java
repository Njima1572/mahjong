public class Game{
	public int ID;
	public Table table;
	public Player[] players = new Player[4];
	public Game(int id)
	{
		table = new Table();
		for(int i=0; i<4; i++)
		{
			players[i] = new Player(table);
		}
		table.init();
	}

	public void visualize()
	{

	}

	public static void main(String[] args)
	{
		Game game = new Game(1);
		for(int i=0; i<game.players[0].tehai.length; i++)
		{
			System.out.println(game.players[0].tehai[i].toString());
		}

	}
}
