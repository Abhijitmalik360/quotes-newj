package com.attitud.ssc.roomdb;

public class MyRepository {
    private MyDao myDao;

    public MyRepository (MyDao myDao){
        this.myDao = myDao;
    }


    public void addFavoriteData(FavoriteData data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                myDao.addFavorite(data);
            }
        }).start();
    }

    public void deleteFavoriteData(FavoriteData data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                myDao.deleteFavorite(data);
            }
        }).start();
    }
    public void deleteFavoriteDataById(String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                myDao.deleteDataById(id);
            }
        }).start();
    }



}
