package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Model.ImageCache;
import cn.edu.ncepu.sa.Model.Mapc;
import cn.edu.ncepu.sa.Model.Terrain;

import javax.swing.*;
import java.awt.*;

public class MapDraw extends JPanel {
   public static final int tile =25;//放缩20倍，减小地图加载量
   public static  void draw(Graphics2D g,Mapc mapc){

       for (int y = 0; y <Mapc.height; y++) {
           for (int x = 0; x < Mapc.width; x++) {
               //System.out.println(x+" "+y);
               Terrain terrain = mapc.terrains[y][x];
               int tileX = x * tile;
               int tileY = y * tile;

               switch (terrain) {
                   case PLAINS:
                      // g.drawImage(ImageCache.get("shot"), tileX, tileY, tile, tile, null);
                       // 默认情况下，不需要绘制平地（可以用背景色代替）
                       break;
                   case GRASS:
                       g.drawImage(ImageCache.get("grass"), tileX, tileY, tile, tile, null);
                       break;
                   case RIVER:
                       g.drawImage(ImageCache.get("water"), tileX, tileY, tile, tile, null);
                       break;
               }
           }
   }
}}
