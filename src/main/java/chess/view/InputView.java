package chess.view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readGameCommand() {
        return scanner.nextLine();
    }
}
