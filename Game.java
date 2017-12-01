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

	}
}
