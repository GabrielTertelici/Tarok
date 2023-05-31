package com.example.tarok.utility;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.tarok.R;
import com.example.tarok.gameObjects.Card;

import java.util.ArrayList;
import java.util.List;

public class DeckUtils {

    public static List<Card> getLegalCards(List<Card> playerCards, Card firstCard){
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
}
