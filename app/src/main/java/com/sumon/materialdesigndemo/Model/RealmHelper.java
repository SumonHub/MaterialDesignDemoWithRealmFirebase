package com.sumon.materialdesigndemo.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by SumOn on 24/11/2017
 */
public class RealmHelper {

    private Realm realm;
    private RealmResults<DataModel> itemRealmResults;
    private Boolean saved = null;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    //SAVE
    public void save(final DataModel item) {
        if (item == null) {
            saved = false;
        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    try {
                        DataModel s = realm.copyToRealm(item);
                        saved = true;

                    } catch (RealmException e) {
                        e.printStackTrace();
                        saved = false;
                    }
                }
            });
        }
    }

    public boolean isSave(final DataModel item) {
        DataModel singleItemExist = realm.where(DataModel.class)
                .equalTo("title", item.getTitle())
                .equalTo("subTitle1", item.getSubTitle1())
                .equalTo("subTitle2", item.getSubTitle2())
                .findFirst();
        Log.i("isSave", String.valueOf(singleItemExist));
        return singleItemExist == null;
    }

    public void delete(final DataModel item) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<DataModel> results = realm.where(DataModel.class)
                        .equalTo("title", item.getTitle())
                        .equalTo("subTitle1", item.getSubTitle1())
                        .equalTo("subTitle2", item.getSubTitle2())
                        .findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    //RETRIEVE
    public void retrieveFromDB() {
        itemRealmResults = realm.where(DataModel.class).findAll();
    }

    //REFRESH
    public ArrayList<DataModel> justRefresh() {
        ArrayList<DataModel> latest = new ArrayList<>();
        latest.addAll(itemRealmResults);

        return latest;
    }
}