
import java.io.IOException;

public class ClearConsole {
	public void clean() {
		try {
			String os = System.getProperty("os.name");
			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		} catch (IOException | InterruptedException e) {
			// 예외 처리
		}
	}
}
