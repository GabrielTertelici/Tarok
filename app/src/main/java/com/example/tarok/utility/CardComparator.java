package com.example.tarok.utility;

import com.example.tarok.gameObjects.Card;

import java.util.Comparator;

public class CardComparator<Card> implements Comparator<com.example.tarok.gameObjects.Card> {


    @Override
    public int compare(com.example.tarok.gameObjects.Card c1, com.example.tarok.gameObjects.Card c2) {
        if(c1.getPoints()==c2.getPoints()){
            if(c1.getSuite()==c2.getSuite())
                return -Integer.compare(c1.getValue(),c2.getValue());
            else
                return -c1.getSuite().compareTo(c2.getSuite());
        }
        else return -Integer.compare(c1.getPoints(),c2.getPoints());
    }
}
