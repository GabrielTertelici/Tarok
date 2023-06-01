package com.example.tarok.utility;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.tarok.R;
import com.example.tarok.gameObjects.Card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DeckUtils {

    public static List<Card> getLegalCards(List<Card> playerCards, Card firstCard){
        if(firstCard==null)
            return playerCards;

        List<Card> result = new ArrayList<>();
        if(hasCardOfSuite(playerCards,firstCard.getSuite())){
            for(Card c:playerCards){
                if(c.getSuite().equals(firstCard.getSuite())){
                    result.add(c);
                }
            }
            return result;
        }
        else if(hasCardOfSuite(playerCards,CardSuite.Tarot)){
            for(Card c:playerCards){
                if(c.getSuite().equals(CardSuite.Tarot)){
                    result.add(c);
                }
            }
            return result;
        }
        else
            return playerCards;
    }

    public static boolean hasCardOfSuite(List<Card> cards, CardSuite suite){
        for(Card c:cards){
            if(c.getSuite().equals(suite))
                return true;
        }
        return false;
    }

    public static List<Card> getDeck(Context context){
        List<Card> result = new ArrayList<>();

        result.addAll(decodeTarots(context));
        result.addAll(decodeHearts(context));
        result.addAll(decodeDiamonds(context));
        result.addAll(decodeClubs(context));
        result.addAll(decodeSpades(context));

        return result;
    }

    private static List<Card> decodeTarots(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.i),true, CardSuite.Tarot,1));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.ii),true,CardSuite.Tarot,2));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.iii),true,CardSuite.Tarot,3));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.iv),true,CardSuite.Tarot,4));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.v),true,CardSuite.Tarot,5));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.vi),true,CardSuite.Tarot,6));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.vii),true,CardSuite.Tarot,7));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.viii),true,CardSuite.Tarot,8));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.ix),true,CardSuite.Tarot,9));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.x),true,CardSuite.Tarot,10));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xi),true,CardSuite.Tarot,11));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xii),true,CardSuite.Tarot,12));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xiii),true,CardSuite.Tarot,13));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.xiv),true,CardSuite.Tarot,14));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xv),true,CardSuite.Tarot,15));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xvi),true,CardSuite.Tarot,16));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.xvii),true,CardSuite.Tarot,17));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xviii),true,CardSuite.Tarot,18));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xix),true,CardSuite.Tarot,19));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.xx),true,CardSuite.Tarot,20));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xxi),true,CardSuite.Tarot,21));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xxii),true,CardSuite.Tarot,22));

        return result;
    }

    private static List<Card> decodeHearts(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.heart_1),false,CardSuite.Heart,1));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_2),false,CardSuite.Heart,2));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_3),false,CardSuite.Heart,3));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.heart_4),false,CardSuite.Heart,4));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_boy),true,CardSuite.Heart,5));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_horse),true,CardSuite.Heart,6));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.heart_queen),true,CardSuite.Heart,7));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_king),true,CardSuite.Heart,8));

        return result;
    }

    private static List<Card> decodeDiamonds(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.diamond_1),false,CardSuite.Diamond,1));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.diamond_2),false,CardSuite.Diamond,2));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.diamond_3),false,CardSuite.Diamond,3));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.diamond_4),false,CardSuite.Diamond,4));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.diamond_boy),true,CardSuite.Diamond,5));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.diamond_horse),true,CardSuite.Diamond,6));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.diamond_queen),true,CardSuite.Diamond,7));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.diamond_king),true,CardSuite.Diamond,8));

        return result;
    }

    private static List<Card> decodeClubs(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.club_1),false,CardSuite.Club,1));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.club_2),false,CardSuite.Club,2));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.club_3),false,CardSuite.Club,3));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.club_4),false,CardSuite.Club,4));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.club_boy),true,CardSuite.Club,5));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.club_horse),true,CardSuite.Club,6));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.club_queen),true,CardSuite.Club,7));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.club_king),true,CardSuite.Club,8));

        return result;
    }

    private static List<Card> decodeSpades(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.spade_1),false,CardSuite.Spade,1));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.spade_2),false,CardSuite.Spade,2));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.spade_3),false,CardSuite.Spade,3));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.spade_4),false,CardSuite.Spade,4));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.spade_boy),true,CardSuite.Spade,5));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.spade_horse),true,CardSuite.Spade,6));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.spade_queen),true,CardSuite.Spade,7));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.spade_king),true,CardSuite.Spade,8));

        return result;
    }

    /**
     * This method calculates the points value of a card
     * @param suite The suite of the card
     * @param value The value of the card = what kind of card it is
     * @return The points value
     */
    public static int calculatePoints(CardSuite suite, int value) {
        if(suite.equals(CardSuite.Tarot)){
            if(value==1 || value==21 || value==22)
                return 5;
            else
                return 1;
        }
        if(value<=4)
            return 1;
        return value-3;
    }

    public static int sumPoints(List<Card> cards){
        int result = 0;
        for(Card c:cards){
            result+=c.getPoints();
        }
        result=result-2*(cards.size()/3);
        if(cards.size()%3!=0)
            result--;
        Log.println(Log.DEBUG,"Cards","Size:"+cards.size()+" Cards: "+cards.toString());
        return result;
    }

    public static int getWinningPlayer(List<PlayedCard> tableCards) {
        List<Card> cards = new ArrayList<>(4);
        cards.addAll(tableCards.stream().map(x->x.card).collect(Collectors.toList()));
        Card c = getWinningCard(cards);
        for(PlayedCard pc:tableCards){
            if(pc.card.equals(c))
                return pc.playerId;
        }
        return -1;
    }

    private static Card getWinningCard(List<Card> cards) {
        Card result=null;
        if(hasCardOfSuite(cards,CardSuite.Tarot)){
            for(Card c:cards){
                if(c.getSuite().equals(CardSuite.Tarot) && (result==null || c.getValue()>result.getValue()))
                    result=c;
            }
        }
        else{
            CardSuite correctSuite = cards.get(0).getSuite();
            result = cards.get(0);
            for(Card c:cards){
                if(c.getSuite().equals(correctSuite) && c.getValue()>result.getValue())
                    result = c;
            }
        }
        return result;
    }
}
