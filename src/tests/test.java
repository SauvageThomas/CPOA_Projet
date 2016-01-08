package tests;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String[] shapes = "CircleSquareRectangleHexagon".split(",", 2);
		for(String s : shapes) {
			System.out.println(s);
		}
	}

}
