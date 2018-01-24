package com.sumon.materialdesigndemo.Model;

import io.realm.RealmObject;

/**
 * Created by SumOn on 10/23/2017.
 */

public class DataModel extends RealmObject {
    private String title;
    private String subTitle1;
    private String subTitle2;

    public DataModel() {
    }

    public DataModel(String title, String subTitle1, String subTitle2) {
        this.title = title;
        this.subTitle1 = subTitle1;
        this.subTitle2 = subTitle2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle1() {
        return subTitle1;
    }

    public void setSubTitle1(String subTitle1) {
        this.subTitle1 = subTitle1;
    }

    public String getSubTitle2() {
        return subTitle2;
    }

    public void setSubTitle2(String subTitle2) {
        this.subTitle2 = subTitle2;
    }
}
