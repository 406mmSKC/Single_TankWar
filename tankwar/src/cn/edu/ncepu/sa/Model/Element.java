package cn.edu.ncepu.sa.Model;

import com.alibaba.fastjson.annotation.JSONType;

import java.awt.*;

/**
 * 地图元素类
 */

public class Element implements IElement {

    /**
     * 位置x
     */
    public double x;

    /**
     * 位置 y
     */
    public double y;

    /**
     * 宽度
     */
    public int width = 10;

    /**
     * 高度
     */
    public int height = 10;

    /**
     * 是否需要销毁，False：显示； True：删除
     */
    public boolean Destroyed = false;

    //增设“可见”属性，不可见不一定被摧毁
    public boolean Viewed=true;

    public double tx=-1;
    public double ty=-1;
    public Element() {
    }

    public boolean isDestroyed(){
        return Destroyed;
    }
    /**
     * 使用子类的位置更新方法
     *
     * @param timeFlaps 流逝时间间隔
     */
    public void update(double timeFlaps) {

    }


    /**
     * 在地图上绘制该元素
     * 使用子类的绘制方法
     *
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {

    }

    /**
     * 该元素生命周期结束，可以回收资源
     */
    public void destroy() {
        Destroyed = true; Viewed=false;  // 这会触发数据区中删除，该工作在控制器中完成
    }

    /**
     * 向某方向移动一段距离
     *
     * @param dir 方向
     * @param len 距离
     */
    public void move(double dir, double len) {
        //此处添加限制，使鼠标点击后不会停不下来
        if(Math.abs(x-tx)<2&&Math.abs(y-ty)<2){
            return;
        }
        x = x + len * Math.cos((dir - 90) * Math.PI / 180);
        y = y + len * Math.sin((dir - 90) * Math.PI / 180);

    }

    /**
     * 计算两个结点的距离
     *
     * @param target 目标元素
     * @return 距离
     */
    public double distance(Element target) {
        // this,target
        double a = this.x - target.x;
        double b = this.y - target.y;
        return Math.sqrt(a * a + b * b);

    }

    /**
     * 与另一个元素的夹角
     *
     * @param target 目标元素
     * @return
     */
    public double angle(Element target) {
        double len_x = target.x - x;
        double len_y = target.y - y;
        double radian = Math.atan2(len_y, len_x);//弧度
        return radian * 180 / Math.PI;//角度

    }
    //此处重载了一个获取角度的函数
    public double angle(int tx,int ty) {
        double len_x = tx - x;
        double len_y = ty - y;
        double radian = Math.atan2(len_y, len_x);//弧度
        System.out.println(90+Math.toDegrees(radian));
       // return radian * 180 / Math.PI;//角度
        return 90+Math.toDegrees(radian);
    }
    /*
     * 指定中心点获取矩形，弃用，碰撞检测使用了距离法
     */
    public Rectangle getRect() {
        return new Rectangle((int) (x - (width / 2)), (int) y - (height / 2), (int) width, (int) height);
    }
}
