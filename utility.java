import java.util.Scanner;

public class MiniMaxGame {

    public static int getUtility(String board) {
        // Returns the utility of Player 1 in the terminal state.
        return countCoins(board, 'B');
    }

    public static boolean isTerminalState(String board) {
        // Checks if the board is in a terminal state.
        return !board.contains("0");
    }

    public static int countCoins(String board, char player) {
        // Counts the number of coins for the given player.
        return (int) board.chars().filter(c -> c == player).count();
    }

    public static String flipCoins(String board, int index, char player) {
        // Flips coins in the given direction.
        char opponent = (player == 'B') ? 'W' : 'B';
        char[] newBoard = board.toCharArray();

        if (index > 0 && newBoard[index - 1] == opponent) {
            newBoard[index - 1] = player;
        }
        if (index < newBoard.length - 1 && newBoard[index + 1] == opponent) {
            newBoard[index + 1] = player;
        }

        return new String(newBoard);
    }

    public static int miniMax(String board, int depth, boolean maximizingPlayer) {
        // Mini-max algorithm implementation.
        if (depth == 0 || isTerminalState(board)) {
            return getUtility(board);
        }

        char player = maximizingPlayer ? 'B' : 'W';
        int bestValue = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < board.length(); i++) {
            if (board.charAt(i) == '0') {
                String successor = flipCoins(board.substring(0, i) + player + board.substring(i + 1), i, player);
                int value = miniMax(successor, depth - 1, !maximizingPlayer);

                if (maximizingPlayer) {
                    bestValue = Math.max(bestValue, value);
                } else {
                    bestValue = Math.min(bestValue, value);
                }
            }
        }

        return bestValue;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the initial board state: ");
        String initialBoard = scanner.nextLine();

        System.out.print("Enter the depth for the mini-max algorithm: ");
        int depth = scanner.nextInt();

        int player1Utility = miniMax(initialBoard, depth, true);
        System.out.println("Utility of Player 1 (MAX): " + player1Utility);

        scanner.close();
    }
}
