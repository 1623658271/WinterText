package com.example.wintertext.beans;


import org.litepal.crud.LitePalSupport;

//为游戏1新建一个属性实体类
public class GamePlayer {
    private int attack, Life, defense, strike, money;

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getStrike() {
        return strike;
    }

    public void setLife(int life) {
        Life = life;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setStrike(int strike) {
        this.strike = strike;
    }

    public int getLife() {
        return Life;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }
}
