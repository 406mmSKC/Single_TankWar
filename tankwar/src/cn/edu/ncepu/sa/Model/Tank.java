package cn.edu.ncepu.sa.Model;


import com.alibaba.fastjson.annotation.JSONType;

import java.awt.*;
import java.lang.*;
/**
 * 坦克类
 */
//@JSONType(typeName = "Tank")
public class Tank extends Element {
    /**
     * 坦克方向
     */
    public double dir = 90;

   //public int tx,ty;
    /**
     * 炮筒方向
     */
    public double turretDir;

    /**
     * 是否在移动
     */
    public boolean moving = false;

    /**
     * 移动步数
     */
    public long moveSteps = 0;

    /**
     * 每秒移动速度,注意要比子弹慢一些
     */
    public double speed = 200;

    /**
     * 生命数，装甲
     */
    public double hp = 600;
    public double hpmax = 200;

    /**
     * 每秒回复生命
     */
    public double hp_recovery_per_sec = 0.1;

    /**
     * 队伍，1红，2蓝
     */
    public int team = 1;

    public int killNum=0;

    public int shotNum=0;

    public String name;

    public double lastX=0;
    public double lastY=0;

    public Tank() {

    }

    /**
     * 构造坦克
     *
     * @param x                   x坐标
     * @param y                   y坐标
     * @param dir                 方向
     * @param hp                  初始血量
     * @param hp_recovery_per_sec 每秒恢复血量
     * @param team                组别
     */
    public Tank(int x, int y, double dir, double hp, double hp_recovery_per_sec, int team,int killNum,String name) {
        this.x = x;
        this.y = y;

        this.dir = dir;
        this.speed = speed;
        this.hp = hp;
        this.hp_recovery_per_sec = hp_recovery_per_sec;
        this.team = team;
        this.killNum=killNum;
        this.name=name;
    }

    /*public Tank(int x, int y, double dir, double hp, double hp_recovery_per_sec, int team,int killNum,String name) {
        this.x = x;
        this.y = y;

        this.dir = dir;
        this.speed = speed;
        this.hp = hp;
        this.hp_recovery_per_sec = hp_recovery_per_sec;
        this.team = team;
        this.killNum=killNum;
        this.name=name;
    }*/

    /**
     * 此坦克受到伤害
     */
    public boolean damage(double val) {
        this.hp -= val;
        if (this.hp <= 0) {
            System.out.println(team+"方坦克销毁");
            this.destroy();
            return true;
        }
        return false;
    }

    //修改击杀数和获取击杀数
    public synchronized void addKillNum(){
        killNum++;
    }
    public int getKillNum(){
        return killNum;
    }
    //控制炮弹数量
    public void addShotNum(){shotNum++;}
    public void subShotNum(){shotNum--;}
    public int getShotNum(){return shotNum;}
    public int getTeam(){return team;}
    /**
     * 更新坦克位置
     *
     * @param timeFlaps 流逝时间间隔
     */
    public  void update(double timeFlaps) {
        // 若已死亡，则不再动作
        if (Destroyed) {
            return;
        }

        //生命回复
        recoverLife();

        //更新坦克位置
        if (moving){
            double len = speed * timeFlaps;
            moveSteps++;
            this.move(dir, len);
        }
    }
    //重写了移动方法
    public void move(double dir, double len) {
        //此处添加限制，使鼠标点击后不会停不下来
        if(Math.abs(x-tx)<2&&Math.abs(y-ty)<2){
            return;
        }
        lastX=x;
        lastY=y;
        x = x + len * Math.cos((dir - 90) * Math.PI / 180);
        y = y + len * Math.sin((dir - 90) * Math.PI / 180);

    }

    //返回上一步移动的位置
    public  void cancelMove(){
        x=lastX;
        y=lastY;
    }
    /**
     * 定时自动回血
     */
    public void recoverLife() {
        hp += hp_recovery_per_sec;
        if (hp > hpmax) {
            hp = hpmax;
        }
    }

    //本绘图方法暂时作废
    @Override
    public void draw(Graphics2D g2) {
        //System.out.println("画:"+x+","+y);
        Image img1 = null;
        Image img2 = null;
        if (team == TankTeam.RED.ordinal()) {
            img1 = ImageCache.get("tank_red");
            img2 = ImageCache.get("turret_red");
        }
        if (team == TankTeam.BLUE.ordinal()) {
            img1 = ImageCache.get("tank_blue");
            img2 = ImageCache.get("turret_blue");
        }
        Graphics2D g = (Graphics2D) g2.create();//复制画笔
        g.translate(x, y);
        //绘制坦克身体
        g.rotate(Math.toRadians(dir));
        g.drawImage(img1, -18, -19, null);
        g.rotate(Math.toRadians(-dir));

        // 绘制血条
        g.drawRect(-22, -34, 44, 8);
        //g.setColor(Color.RED);此处添加了绿色黄色红色
        if(hp/hpmax>0.7){
            g.setColor((Color.GREEN));
        }
        /*else if(hp/hpmax>0.3){//黄色看不清
            g.setColor((Color.yellow));
        }*/
        else{
            g.setColor((Color.red));
        }
        int whp = (int) (43.08 * (hp / hpmax));
        //此处添加了血量数字
        Font font=new Font("Arial",Font.BOLD,14);
        g.setFont(font);
        // g.setColor(Color.BLACK);
        //本来想把血量数字作为黑色显示的，但是不知道为什么会连同血条一起改变颜色，还是算了
        //以下是数字本身内容
        if (hp<0)
            hp=0;
        String booldString=new String((int)hp+"/"+(int)hpmax);
        FontMetrics metrics=g.getFontMetrics();
        int textWidth=metrics.stringWidth(booldString);
        int textHeigth=metrics.getHeight();
        int textX=-21+(whp-textWidth)/2;
        int textY=-33+(7-textHeigth)/2;
        g.drawString(booldString,textX,textY);

        g.fillRect(-21, -33, whp, 7);
        g.rotate(Math.toRadians(this.turretDir));
        g.drawImage(img2, -32, -32, null);
    }
}
