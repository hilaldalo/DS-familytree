
public class Print {

	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_PURPLE = "\u001B[35m";

	public static void purple(String s) {

		System.out.print(ANSI_PURPLE + s + ANSI_RESET);

	}

	public static void red(String s) {

		System.out.print(ANSI_RED + s + ANSI_RESET);

	}

	public static void green(String s) {

		System.out.print(ANSI_GREEN + s + ANSI_RESET);

	}

	public static void yellow(String s) {

		System.out.print(ANSI_YELLOW + s + ANSI_RESET);

	}

}
