package com.example.wintertext.beans;

/**
 * description ： TODO:玩家属性类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/3 21:02
 */
//为游戏新建一个玩家属性类
public class GamePlayer {
    private int attack, Life, defense, strike, money, steal, exc, max_exc,grade;
    private String[] equipments;
    private String name;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExc() {
        return exc;
    }

    public int getMax_exc() {
        return max_exc;
    }

    public int getSteal() {
        return steal;
    }

    public String[] getEquipments() {
        return equipments;
    }

    public void setEquipments(String[] equipments) {
        this.equipments = equipments;
    }

    public void setExc(int exc) {
        this.exc = exc;
    }

    public void setMax_exc(int max_exc) {
        this.max_exc = max_exc;
    }

    public void setSteal(int steal) {
        this.steal = steal;
    }

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
