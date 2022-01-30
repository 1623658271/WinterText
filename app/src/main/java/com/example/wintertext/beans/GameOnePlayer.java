package com.example.wintertext.beans;

//为游戏1新建一个玩家属性操作类
public class GameOnePlayer {
    private int groupLife = 100;
    private int location;
    private int fireAttackNumber = 4;
    private int quicklyAttackNumber = 4;
    private boolean isFireAttack = false;
    private boolean isQuicklyAttack = false;

    //设置初始位置
    public GameOnePlayer(int location){
        this.location = location;
    }
    //封装其他属性

    public int getGroupLife() {
        return groupLife;
    }

    public void setGroupLife(int groupLife) {
        this.groupLife = groupLife;
    }

    public int getFireAttackNumber() {
        return fireAttackNumber;
    }

    public void setFireAttackNumber(int fireAttackNumber) {
        this.fireAttackNumber = fireAttackNumber;
    }

    public boolean isFireAttack() {
        return isFireAttack;
    }

    public boolean isQuicklyAttack() {
        return isQuicklyAttack;
    }
}
