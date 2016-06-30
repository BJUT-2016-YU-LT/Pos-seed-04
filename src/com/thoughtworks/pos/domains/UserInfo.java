package com.thoughtworks.pos.domains;

/**
 * Created by pyggy on 2016/6/29.
 */
public class UserInfo {
    private  String name;
    private  String bar;
    private  boolean isVIP;
    private int points;
    public UserInfo(){}
    public UserInfo(String bar,String name,boolean isVip,int points){
        this.setBar(bar);
        this.setName(name);
        this.setIsVIP(isVip);
        this.setPoints(points);
    }

    public String getUserBar(){return this.bar;}
    public String getUserName(){return this.name;}
    public boolean getUserIsVIP(){return this.isVIP;}
    public int getUserPoints(){return this.points;}

 public int ChangePoint(double moneyIn){
 int units=(int)(moneyIn/5.00)   ;
     int pointChange=0;
     if(this.points>=0&&this.points<=200)
         pointChange= units*1;
     else if(this.points>200&&this.points<=500)
         pointChange= units*3;
     else if(this.points>500)
         pointChange= units*5;
     this.points+=pointChange;
     return this.points;

 }
    public void setBar(String bar){this.bar=bar;}

    public void setName(String name){this.name=name;}

    public void setIsVIP(boolean isVIP){this.isVIP=isVIP;}

    public void setPoints(int points){this.points = points;}
}
