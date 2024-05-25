package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Model.*;

import java.awt.*;
//应要求写出此类，用于将绘图方法迁移到view层，方便管理
public class EleDraw {
    //本类中包含各种元素的绘图方式
    public static void draw(Graphics2D g2, Shot_High_Speed shot) {
        if(shot.Viewed==false){
            return;
        }
        Graphics2D g = (Graphics2D) g2.create();//复制画笔
        g.translate(shot.x,shot. y);
        g.drawImage(ImageCache.get("shot_high_speed"), -6, -6, null);
    }
    public static void draw(Graphics2D g2, Shot shot) {
        if(shot.Viewed==false){
            return;
        }
        Graphics2D g = (Graphics2D) g2.create();//复制画笔
        g.translate(shot.x, shot.y);
        g.drawImage(ImageCache.get("shot"), -6, -6, null);
    }
    public static void draw(Graphics2D g2, Tank tank){
        //System.out.println("画:"+x+","+y);
        float alpha = 1.0f;
        if(tank.Viewed==false&&!tank.name.equals("Player")){
            return;
        }
        else if(tank.Viewed==false&&tank.name.equals("Player")){
             alpha = 0.5f; // 50% 透明度

        }
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(ac);

        Image img1 = null;
        Image img2 = null;
        if (tank.getTeam() == TankTeam.RED.ordinal()) {
            img1 = ImageCache.get("tank_red");
            img2 = ImageCache.get("turret_red");
        }
        if (tank.getTeam()== TankTeam.BLUE.ordinal()) {
            img1 = ImageCache.get("tank_blue");
            img2 = ImageCache.get("turret_blue");
        }
        Graphics2D g = (Graphics2D) g2.create();//复制画笔
        g.translate(tank.x, tank.y);
        //绘制坦克身体
        g.rotate(Math.toRadians(tank.dir));
        g.drawImage(img1, -18, -19, null);
        g.rotate(Math.toRadians(-tank.dir));

        // 绘制血条
        g.drawRect(-22, -34, 44, 8);
        //g.setColor(Color.RED);此处添加了绿色黄色红色
        if(tank.hp/tank.hpmax>0.7){
            g.setColor((Color.GREEN));
        }
        /*else if(hp/hpmax>0.3){//黄色看不清
            g.setColor((Color.yellow));
        }*/
        else{
            g.setColor((Color.red));
        }
        int whp = (int) (43.08 * (tank.hp / tank.hpmax));
        //此处添加了血量数字
        Font font=new Font("Arial",Font.BOLD,14);
        g.setFont(font);
        // g.setColor(Color.BLACK);
        //本来想把血量数字作为黑色显示的，但是不知道为什么会连同血条一起改变颜色，还是算了
        //以下是数字本身内容
        if (tank.hp<0)
            tank.hp=0;
        String booldString=new String((int)tank.hp+"/"+(int)tank.hpmax);
        FontMetrics metrics=g.getFontMetrics();
        int textWidth=metrics.stringWidth(booldString);
        int textHeigth=metrics.getHeight();
        int textX=-21+(whp-textWidth)/2;
        int textY=-33+(7-textHeigth)/2;
        g.drawString(booldString,textX,textY);

        g.fillRect(-21, -33, whp, 7);
        g.rotate(Math.toRadians(tank.turretDir));
        g.drawImage(img2, -32, -32, null);

    }
}
