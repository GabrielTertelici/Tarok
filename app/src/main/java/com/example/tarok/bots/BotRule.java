package com.example.tarok.bots;


import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.PlayedCard;

import java.util.List;
import java.util.Set;

public interface BotRule {

    List<Card> sortedList(List<Card> legalCards, List<PlayedCard> tableCards, Set<Integer> teammates);
}
