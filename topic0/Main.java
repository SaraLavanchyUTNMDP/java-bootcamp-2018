
public class Main {

	public static void main(String[] args) {
		Proxy aConnection = new Proxy();
		if(aConnection.getMyConnectionDirector() != null)
			System.out.print("i'm functioning");
		else 
			System.out.print("I failed");

	}

}
