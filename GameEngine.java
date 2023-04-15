import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEngine {

    private Map<String, Integer> players = new HashMap<>();

    public String run(List<String> playerNames) {

        if (playerNames == null || playerNames.size() <= 1) {
            return "Minimum 2 players required for this game";
        }

        initPlayers(playerNames);

        int currentPlayer = 1;
        while (!players.containsValue(100)) {
            String currentPlayerName = playerNames.get(currentPlayer - 1);
            Integer currentPlayerPos = players.get(currentPlayerName);

            int diceCounter = rollDice();
            if (currentPlayerPos == 0 && diceCounter != 6) {
                currentPlayer = moveToNextPlayer(playerNames, currentPlayer);
                System.out.print("Dice rolled to " + diceCounter + ". ");
                System.out.println("Player " + currentPlayerName + " still at start position");
                continue;
            } else if (currentPlayerPos + diceCounter <= 100) {
                currentPlayerPos += diceCounter;
                currentPlayerPos = checkSnakeEncounter(currentPlayerPos, currentPlayerName);
                currentPlayerPos = checkLadderEncounter(currentPlayerPos, currentPlayerName);
                players.put(currentPlayerName, currentPlayerPos);
                System.out.println(currentPlayerName + " moves to " + currentPlayerPos);
            }
            currentPlayer = moveToNextPlayer(playerNames, currentPlayer);
        }

        return getWinner(players);
    }

    private static int moveToNextPlayer(List<String> playerNames, int currentPlayer) {
        if (currentPlayer == playerNames.size()) {
            currentPlayer = 1;
        } else {
            currentPlayer++;
        }
        return currentPlayer;
    }

    private String getWinner(Map<String, Integer> players) {
        for (Map.Entry<String, Integer> entry : players.entrySet()) {
            if (entry.getValue() == 100) {
                return entry.getKey();
            }
        }
        return "";
    }

    private void initPlayers(List<String> playerNames) {
        for (String playerName : playerNames) {
            if (players.containsKey(playerName)) {
                throw new IllegalArgumentException("Duplicate player name " + playerName);
            }
            players.put(playerName, 0);
        }
    }

    private Integer rollDice() {
        return 1 + (int) (Math.random() * ((6 - 1) + 1));
    }

    private Integer checkSnakeEncounter(int pos, String playerName) {
        switch (pos) {
            case 15 -> {
                System.out.println("snake bit " + playerName + " moves to " + 4);
                return 4;
            }
            case 23 -> {
                System.out.println("snake bit " + playerName + " moves to " + 10);
                return 10;
            }
            case 42 -> {
                System.out.println("snake bit " + playerName + " moves to " + 12);
                return 12;
            }
            case 78 -> {
                System.out.println("snake bit " + playerName + " moves to " + 64);
                return 64;
            }
            case 97 -> {
                System.out.println("snake bit " + playerName + " moves to " + 58);
                return 58;
            }
            default -> {
                return pos;
            }
        }
    }

    private Integer checkLadderEncounter(int pos, String playerName) {
        switch (pos) {
            case 12 -> {
                System.out.println("ladder up " + playerName + " moves to " + 24);
                return 24;
            }
            case 32 -> {
                System.out.println("ladder up " + playerName + " moves to " + 47);
                return 47;
            }
            case 55 -> {
                System.out.println("ladder up " + playerName + " moves to " + 69);
                return 69;
            }
            case 79 -> {
                System.out.println("ladder up " + playerName + " moves to " + 89);
                return 89;
            }
            default -> {
                return pos;
            }
        }
    }

}
