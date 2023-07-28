package com.example.tarok.utility;

import android.util.Log;

import com.example.tarok.bots.Bot;
import com.example.tarok.bots.botBrains.NegativeGameModeBrain;

import java.util.ArrayList;
import java.util.List;

public class BotTeammateHandler {

    /**
     * Utility method for setting the teammates of bots after the teams have been revealed,
     * either by a player playing solo, or by the chosen king getting revealed either on the table or
     * in the talon
     * @param botsPassed list of Bots playing, sorted anticlockwise
     * @param player integer representing the player who made the lowest bid, in anticlockwise order
     *               player = 1 -> human player
     *               player = 2 -> player to the human's immediate right
     *               player = 3 -> player opposite the human
     *               player = 4 -> player to the human's immediate left
     * @param teammate integer representing the player who is the teammate
     *                 of the player who made the lowest bid, handled in the same way as the player
     *                 parameter
     */
    public static void handleTeammatesOfBots(List<Bot> botsPassed, int player, int teammate){
        List<Integer> playerIds = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            playerIds.add(i);
        }

        int playerIndex = player - 1;
        int teammateIndex = teammate - 1;

        // dummy bot representing the human player
        List<Bot> bots = new ArrayList<>();
        bots.add(new Bot(null, new NegativeGameModeBrain()));
        bots.addAll(botsPassed);

        bots.get(playerIndex).setTeamMate(teammate);
        bots.get(teammateIndex).setTeamMate(player);

        Bot playerBot = bots.get(playerIndex);
        Bot teammateBot = bots.get(teammateIndex);

        bots.remove(playerBot);
        bots.remove(teammateBot);

        playerIds.remove(Integer.valueOf(player));
        playerIds.remove(Integer.valueOf(teammate));

        for (int i = 0; i < bots.size(); i++){
            for(int j = 0; j < playerIds.size(); j++){
                if(j != i){
                    bots.get(i).setTeamMate(playerIds.get(j));
                }
            }
        }
    }
}
