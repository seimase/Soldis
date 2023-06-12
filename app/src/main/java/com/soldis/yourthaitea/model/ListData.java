package com.soldis.yourthaitea.model;

/**
 * Created by User on 9/19/2016.
 */
public class ListData {
    private String nama="";
    private String alamat="";
    private String jekel="";
    private String Atr1="";
    private String Atr2="";
    private String Atr3="";
    private String Atr4="";
    private String Atr5="";
    private String Atr6="";
    private int  Img;

    public String getAtr5() {
        return Atr5;
    }

    public void setAtr5(String atr5) {
        Atr5 = atr5;
    }

    public String getAtr6() {
        return Atr6;
    }

    public void setAtr6(String atr6) {
        Atr6 = atr6;
    }

    public String getAtr4() {
        return(Atr4);
    }

    public void setAtr4(String nama) {
        this.Atr4=nama;
    }

    public String getAtr3() {
        return(Atr3);
    }

    public void setAtr3(String nama) {
        this.Atr3=nama;
    }

    public String getAtr2() {
        return(Atr2);
    }

    public void setAtr2(String nama) {
        this.Atr2=nama;
    }

    public String getAtr1() {
        return(Atr1);
    }

    public void setAtr1(String nama) {
        this.Atr1=nama;
    }

    public String getNama() {
        return(nama);
    }

    public void setNama(String nama) {
        this.nama=nama;
    }

    public String getAlamat() {
        return(alamat);
    }

    public void setAlamat(String alamat) {
        this.alamat=alamat;
    }

    public String getJekel() {
        return(jekel);
    }

    public void setJekel(String jekel) {
        this.jekel=jekel;
    }

    public void setImg(int  jekel) {
        this.Img=jekel;
    }

    public int getImg() {
        return(Img);
    }

}
