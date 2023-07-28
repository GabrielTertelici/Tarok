package com.example.tarok;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.tarok.bots.Bot;
import com.example.tarok.bots.botBrains.NegativeGameModeBrain;
import com.example.tarok.bots.botBrains.NormalGameModeBrain;
import com.example.tarok.utility.BotTeammateHandler;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BotTeammateHandlerTest {

    private Bot botP2;
    private Bot botP3;
    private Bot botP4;

    @Before
    public void initialise(){
        botP2 = new Bot(null, new NegativeGameModeBrain());
        botP3 = new Bot(null, new NegativeGameModeBrain());
        botP4 = new Bot(null, new NegativeGameModeBrain());
    }

    @Test
    public void player1PicksPlayer1(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 1, 1);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(3, 4));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2, 4));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2, 3));
    }

    @Test
    public void player1PicksPlayer2(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 1, 2);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(4));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(3));
    }

    @Test
    public  void player1PicksPlayer3(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 1, 3);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(4));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2));
    }

    @Test
    public  void player1PicksPlayer4(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 1, 4);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(3));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
    }

    @Test
    public  void player2PicksPlayer1(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 2, 1);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(4));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(3));
    }

    @Test
    public  void player2PicksPlayer2(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 2, 2);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1, 4));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1, 3));
    }

    @Test
    public  void player2PicksPlayer3(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 2, 3);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(3));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
    }

    @Test
    public  void player2PicksPlayer4(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 2, 4);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(4));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2));
    }

    @Test
    public  void player3PicksPlayer1(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 3, 1);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(4));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2));
    }

    @Test
    public  void player3PicksPlayer2(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 3, 2);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(3));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
    }

    @Test
    public  void player3PicksPlayer3(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 3, 3);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1, 4));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(3));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1, 2));
    }

    @Test
    public  void player3PicksPlayer4(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 3, 4);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(4));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(3));
    }

    @Test
    public  void player4PicksPlayer1(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 4, 1);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(3));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
    }

    @Test
    public  void player4PicksPlayer2(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 4, 2);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(4));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(2));
    }

    @Test
    public  void player4PicksPlayer3(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 4, 3);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(4));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(3));
    }

    @Test
    public  void player4PicksPlayer4(){
        BotTeammateHandler.handleTeammatesOfBots(List.of(botP2, botP3, botP4), 4, 4);

        assertThat(botP2.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1, 3));
        assertThat(botP3.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(1, 2));
        assertThat(botP4.getTeammates()).containsExactlyInAnyOrderElementsOf(List.of(4));
    }
}
