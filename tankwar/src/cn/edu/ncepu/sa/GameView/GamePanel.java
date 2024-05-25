package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Model.*;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏画板
 */
public class GamePanel extends JPanel {
    /**
     * 数据区引用,
     * 放到参数区也可以
     */

    private WarData warData;
    //private Mapc mapc=new Mapc(70,50);
    private  boolean flag=false;
    /**
     * 游戏帧率
     */
    private double frameRate = 0.0;

    public long gameStartTime=System.currentTimeMillis();
    /**
     * 初始化数据引用
     *
     * @param warData 注意是引用传递
     */
    public void setWarData(WarData warData) {
        this.warData = warData;
        // 单例类的用法
        // this.warData = WarDataSingleton.getInstance();
    }


    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }


    public void paint(Graphics g) {
        synchronized (warData)
        {
            super.paint(g);//保留原来的paint，g相当于画笔
            final int tile = 64;
            Graphics2D g2 = (Graphics2D) g;
            //绘制边框
            int boardWidth = 0;
            g.setColor(Color.black);
            g.drawRect(boardWidth, boardWidth, 860, 640);

            //if(!flag){
            MapDraw.draw(g2, warData.mapc);
            //flag=true;
            //}

            // 绘制每一个游戏元素
            if (warData != null && warData.elements.size() > 0) {
                for (Element element : warData.elements) {
                    //element.draw(g2);//让每个节点都自我绘制
                    if (element instanceof Shot) {
                        EleDraw.draw(g2, (Shot) element);
                    } else if (element instanceof Tank) {
                        EleDraw.draw(g2, (Tank) element);
                    } else if (element instanceof Shot_High_Speed) {
                        EleDraw.draw(g2, (Shot_High_Speed) element);
                    }
                }
            }

        /*for (int y = 0; y <640; y++) {
            for (int x = 0; x < 860; x++) {
                System.out.println(x+" "+y);
                Terrain terrain = mapc.terrains[y][x];
                int tileX = x * tile;
                int tileY = y * tile;

                switch (terrain) {
                    case PLAINS:
                        // 默认情况下，不需要绘制平地（可以用背景色代替）
                        break;
                    case GRASS:
                        g.drawImage(ImageCache.get("grass"), tileX, tileY, tile, tile, this);
                        break;
                    case RIVER:
                        g.drawImage(ImageCache.get("water"), tileX, tileY, tile, tile, this);
                        break;
                }
            }*/

            // 显示帧率
            String str = String.format("fps:%.2f", frameRate);
            g.drawString(str, 10, 15);
            //显示击杀数
            String strKill = String.format("kill:%d", warData.getUserKillNum());
            g.drawString(strKill, 10, 30);
            //显示游戏时间
            long currTime = System.currentTimeMillis();
            int interval = (int) (currTime - gameStartTime);
            String gameTime = String.format("%2d:%2d:%2d", interval / 3600000, (interval / 60000) % 60, (interval / 1000) % 60);
            g.drawString(gameTime, 10, 45);
        }
    }
    }

