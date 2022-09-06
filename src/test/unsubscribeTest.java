package test;

public class unsubscribeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		String data = "(id 1)";
		String id = null;
		
		data = data.split("id ")[1];
		data = data.replace(")", "");

		System.out.println(data);
			

		}



	
}
