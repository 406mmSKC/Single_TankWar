package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Model.ImageCache;
import cn.edu.ncepu.sa.Model.Shot;

import java.awt.*;
//废案
public class ShotDraw implements Idraw {
    private Shot shot;
    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) g2.create();//复制画笔
        g.translate(shot.x, shot.y);
        g.drawImage(ImageCache.get("shot"), -6, -6, null);
    }
}
