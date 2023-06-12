package com.example.tarok.utility;

import com.example.tarok.gameObjects.Card;

public class PlayedCard {
    public int playerId;
    public Card card;

    public PlayedCard(int playerId, Card card) {
        this.playerId = playerId;
        this.card = card;
    }

    @Override
    public String toString() {
        return "{" +
                "playerId=" + playerId +
                ", card=" + card +
                '}';
    }
}
