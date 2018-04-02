public class Game{
	public int ID;
	public Table table;
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
		/*
		Game game = new Game(1);
		
		game.players[0].getTehai().sort();
		System.out.println(game.players[0].getTehai().toString());
		System.out.println("Shantensuu is " + game.players[0].getTehai().getShantensuu());
		*/
		
		
		System.out.println("stats:");
		Game[] games = new Game[10000];
		int totalShantensuu = 0;
		int totalLoopNum = 0;
		for(int i=0; i<games.length; i++)
		{
			games[i] = new Game(i);
			totalShantensuu += games[i].players[0].getTehai().getShantensuu();
		}
		System.out.println("Empirical average shantensuu is: "+(float)totalShantensuu/(float)10000);
		
	}
}
