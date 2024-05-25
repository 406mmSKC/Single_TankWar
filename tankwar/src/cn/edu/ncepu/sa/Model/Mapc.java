package cn.edu.ncepu.sa.Model;

import cn.edu.ncepu.sa.GameView.MapDraw;

import java.util.Random;

public class Mapc {
    public Terrain [][]terrains;

    public static  int width=43;//65

    public  static final int height=32;

    //private  final int MAX_GRASS_COUNT = 100;

    //private  final int MAX_RIVER_COUNT = 50;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isGrass(int x,int y){
        if(terrains[y/MapDraw.tile][x/MapDraw.tile]==Terrain.GRASS){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isRiver(int x,int y){
        if(terrains[y/ MapDraw.tile][x/MapDraw.tile]==Terrain.RIVER){
            return true;
        }
        else {
            return false;
        }
    }
    public Mapc(){}
    public Mapc(int MAX_GRASS_COUNT,int MAX_RIVER_COUNT){
        terrains = new Terrain[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                terrains[y][x] = Terrain.PLAINS;
            }
        }
        Random rand = new Random();
        int grassCount = 0;
        while (grassCount < MAX_GRASS_COUNT) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            if (terrains[y][x] == Terrain.PLAINS) {
                terrains[y][x] = Terrain.GRASS;
                grassCount++;
            }
        }

        // 生成河流（这里简单起见，使用固定河流形状或算法，而不是完全随机）
        // 你可以实现更复杂的河流生成算法，比如使用Perlin Noise等
        // 这里只是简单示例，随机放置一些河流单元格
        int riverCount = 0;
        while (riverCount < MAX_RIVER_COUNT) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            if (terrains[y][x] == Terrain.PLAINS) {
                // 假设河流是一个3x3的矩形区域（简单起见）
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx;
                        int ny = y + dy;
                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            terrains[ny][nx] = Terrain.RIVER;
                        }
                    }
                    riverCount++;
                    // 注意：由于我们一次放置了一个3x3的矩形区域，所以需要适当减少循环次数
                    if (riverCount >= MAX_RIVER_COUNT) {
                        break;
                    }
                }
                if (riverCount >= MAX_RIVER_COUNT) {
                    break;
                }
            }
        }
        //防止玩家出生点是河流
        if(isRiver(600,200)){
            //terrains[200/MapDraw.tile][600/MapDraw.tile]=Terrain.PLAINS;
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int nx = 600 + dx;
                    int ny = 200 + dy;
                    if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                        terrains[ny][nx] = Terrain.PLAINS;
                    }
                }
        }
        /*for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(terrains[y][x]+" ");
            }
            System.out.println();
        }*/

    }
    }

    public Mapc(int ty){
        terrains = new Terrain[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                terrains[y][x] = Terrain.PLAINS;
            }
        }
        //草丛
        for (int y = 180; y < 500; y++) {
            for (int x = 200; x < 510; x++) {
                terrains[y][x] = Terrain.GRASS;
            }
        }
        //河流
        for (int y = 300; y < 310; y++) {
            for (int x = 320; x < 325; x++) {
                terrains[y][x] = Terrain.RIVER;
            }
        }
    }
}
