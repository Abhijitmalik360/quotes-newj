package com.attitud.ssc.cls;

import com.attitud.ssc.roomdb.FavoriteData;

public class MyFunction {

    private MyFunction(){}

    public static FavoriteData getFavoriteDataFromQuoteData(QuoteData quoteData){
        FavoriteData favoriteData = new FavoriteData(quoteData.getId(),quoteData.getQuote(),quoteData.getAuthor());
        return favoriteData;
    }
    public static QuoteData getQuoteDataFromFavoriteData(FavoriteData favoriteData ){
      QuoteData quoteData = new QuoteData();
      quoteData.setId(favoriteData.getId());
      quoteData.setQuote(favoriteData.getQuote());
      quoteData.setAuthor(favoriteData.getAuthor());
        return quoteData;
    }

}
