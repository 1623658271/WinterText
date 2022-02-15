package com.example.wintertext.beans;

/**
 * description ： TODO:小兵属性类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/4 15:20
 */
public class Dogface{
    private String name;
    private int attack;
    private int life;
    private int defense;
    private int grade;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }
}
