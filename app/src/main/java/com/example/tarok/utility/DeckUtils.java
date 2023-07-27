package com.example.tarok.utility;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.tarok.R;
import com.example.tarok.gameObjects.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    public static List<Card> getFivePointCards(List<Card> cards){
        List<Card> result = new ArrayList<>();
        for(Card c:cards){
            if(c.getPoints()==5){
                result.add(c);
            }
        }
        return result;
    }

    public static List<Card> getDeck(Context context){
        List<Card> result = new ArrayList<>();


        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        options.inJustDecodeBounds = false;

        result.addAll(decodeTarots(context,options));
        result.addAll(decodeHearts(context,options));
        result.addAll(decodeDiamonds(context,options));
        result.addAll(decodeClubs(context,options));
        result.addAll(decodeSpades(context,options));

        return result;
    }

    private static List<Card> decodeTarots(Context context, BitmapFactory.Options options){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.i, options), CardSuite.Tarot,1));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.ii, options),CardSuite.Tarot,2));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.iii, options),CardSuite.Tarot,3));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.iv, options),CardSuite.Tarot,4));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.v, options),CardSuite.Tarot,5));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.vi, options),CardSuite.Tarot,6));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.vii, options),CardSuite.Tarot,7));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.viii, options),CardSuite.Tarot,8));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.ix, options),CardSuite.Tarot,9));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.x, options),CardSuite.Tarot,10));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xi, options),CardSuite.Tarot,11));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xii, options),CardSuite.Tarot,12));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xiii, options),CardSuite.Tarot,13));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.xiv, options),CardSuite.Tarot,14));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xv, options),CardSuite.Tarot,15));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xvi, options),CardSuite.Tarot,16));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.xvii, options),CardSuite.Tarot,17));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xviii, options),CardSuite.Tarot,18));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xix, options),CardSuite.Tarot,19));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.xx, options),CardSuite.Tarot,20));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xxi, options),CardSuite.Tarot,21));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.xxii, options),CardSuite.Tarot,22));

        return result;
    }

    private static List<Card> decodeHearts(Context context, BitmapFactory.Options options){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.heart_1, options),CardSuite.Heart,1));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_2, options),CardSuite.Heart,2));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_3, options),CardSuite.Heart,3));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.heart_4, options),CardSuite.Heart,4));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_boy, options),CardSuite.Heart,5));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_horse, options),CardSuite.Heart,6));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.heart_queen, options),CardSuite.Heart,7));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_king, options),CardSuite.Heart,8));

        return result;
    }

    private static List<Card> decodeDiamonds(Context context, BitmapFactory.Options options){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.diamond_1, options),CardSuite.Diamond,1));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.diamond_2, options),CardSuite.Diamond,2));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.diamond_3, options),CardSuite.Diamond,3));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.diamond_4, options),CardSuite.Diamond,4));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.diamond_boy, options),CardSuite.Diamond,5));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.diamond_horse, options),CardSuite.Diamond,6));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.diamond_queen, options),CardSuite.Diamond,7));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.diamond_king, options),CardSuite.Diamond,8));

        return result;
    }

    private static List<Card> decodeClubs(Context context, BitmapFactory.Options options){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.club_1, options),CardSuite.Club,1));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.club_2, options),CardSuite.Club,2));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.club_3, options),CardSuite.Club,3));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.club_4, options),CardSuite.Club,4));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.club_boy, options),CardSuite.Club,5));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.club_horse, options),CardSuite.Club,6));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.club_queen, options),CardSuite.Club,7));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.club_king, options),CardSuite.Club,8));

        return result;
    }

    private static List<Card> decodeSpades(Context context, BitmapFactory.Options options){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.spade_1, options),CardSuite.Spade,1));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.spade_2, options),CardSuite.Spade,2));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.spade_3, options),CardSuite.Spade,3));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.spade_4, options),CardSuite.Spade,4));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.spade_boy, options),CardSuite.Spade,5));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.spade_horse, options),CardSuite.Spade,6));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(),R.drawable.spade_queen, options),CardSuite.Spade,7));
        result.add(new Card(context,BitmapFactory.decodeResource(context.getResources(), R.drawable.spade_king, options),CardSuite.Spade,8));

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
        return result;
    }

    public static int tablePoints(List<Card> cards){
        int result = 0;
        for(Card c:cards){
            result+=c.getPoints();
        }
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

    public static Card getWinningCard(List<Card> cards) {
        Card result=checkJokerRule(cards);
        if(result!=null)//There was a 1, 21 and joker => result = 1
            return result;
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

    public static Card checkJokerRule(List<Card> cards) {
        boolean has1=false;
        boolean has21=false;
        boolean hasJoker=false;
        Card card1=null;
        for(Card c:cards){
            if(c.getSuite().equals(CardSuite.Tarot)){
                if(c.getValue()==1){
                    has1=true;
                    card1=c;
                }
                else if(c.getValue()==21)
                    has21=true;
                else if(c.getValue()==22)
                    hasJoker=true;
            }

        }
        if(has1 && has21 && hasJoker)
            return card1;
        return null;
    }

    public static Map<CardSuite,Integer> countSuiteCards(List<Card> deck){
        Map<CardSuite,Integer> result= new HashMap<>();
        for(CardSuite e:CardSuite.values()){
            result.put(e,0);
        }
        for(Card c:deck){
            result.put(c.getSuite(),result.get(c.getSuite())+1);
        }
        return result;
    }
}
