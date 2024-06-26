package cn.edu.ncepu.sa.Model;
import java.awt.*;

public class Shot_High_Speed extends Element{



/**
 * 子弹类
 */
    /**
     * 移动方向
     */
    public double dir;

    /**
     * 移动速度（秒）
     */
    public double speed = 400.0;

    /**
     * 子弹伤害
     */
    public double damage = 200.0;
    /**
     * 打出这个子弹的坦克引用，用来敌我识别
     */
    public Tank tank;

    /**
     * 构造函数
     *
     * @param tank  发射子弹的坦克
     * @param speed 子弹速度
     */
    public Shot_High_Speed(Tank tank, double speed) {
        this.tank = tank;
        this.x = tank.x;
        this.y = tank.y;
        this.dir = tank.turretDir;
        this.speed = speed;
    }
    public Shot_High_Speed(){}
    /**
     * 更新子弹位置；每一帧都会执行
     */
    public void update(double timeFlaps) {
        //计算新坐标
        double len = speed * timeFlaps;
        this.move(dir, len);

        //超出范围的无效子弹
        if (x < -100 || x > 2000 || y < -100 || y > 2000) {
            this.destroy();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) g2.create();//复制画笔
        g.translate(x, y);
        g.drawImage(ImageCache.get("shot_high_speed"), -6, -6, null);
    }
}
