package cn.edu.ncepu.sa.Control;

import cn.edu.ncepu.sa.GameView.GameEndPanel;
import cn.edu.ncepu.sa.GameView.GameView;
import cn.edu.ncepu.sa.Model.*;
import cn.edu.ncepu.sa.utils.Utils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 游戏控制器
 * 读取键盘、鼠标信息，控制主坦克
 * 线程后台刷新子弹、坦克位置
 * 线程后台计算碰撞检测
 */
public class WarControl extends Thread {

    /**
     * 显示组件引用
     */
    GameView win;

    /**
     * 数据组件引用
     */
    WarData warData;

    /**
     * 默认构造函数
     */
    ThreadPool executor=new ThreadPool();

    private final int maxAmmoNum=3;
    private final int ShotSpeed=200;
    private final int HighShotSpeed=400;
    private final int SpawningTime=50000;

    public WarControl() {

    }

    public WarControl(GameView win,WarData warData){
        this.win=win;
        this.warData=warData;
    }
    /**
     * 初始化控制器
     *
     * @param win     显示组件引用
     * @param warData 数据组件引用
     */
    public void StartWar(GameView win, WarData warData) {
        this.win = win;
        this.warData = warData;
        Tank tank = warData.userTank;
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (warData.userTank.Destroyed) {
                    return;
                }
//                System.out.println(e.getX()+","+e.getY());
                double x = e.getX() - 9;
                double y = e.getY() - 38;
                tank.turretDir = Utils.ppDir(tank.x, tank.y, x, y) + 90;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (tank.Destroyed) {
                    tank.moving = false;
                    return;
                }
                int button=e.getButton();
                //这里应要求改成鼠标右键发射炮弹,鼠标左键移动
                switch(button){
                    case MouseEvent.BUTTON1:
                        tank.dir = tank.angle(e.getX(),e.getY());

                        tank.moving = true;
                        //这里最好改成常量
                        if (e.getX() < 0||e.getY() < 0||e.getY() >= win.height||e.getX() >= win.width) {
                            tank.moving = false;
                        }
                        tank.tx=e.getX();
                        tank.ty=e.getY();
                        break;
                    case MouseEvent.BUTTON3:
                        if(tank.shotNum<maxAmmoNum){
                            Shot_High_Speed shot_h=new Shot_High_Speed(tank,HighShotSpeed);
                            tank.addShotNum();
                            warData.elements.add(shot_h);
                        }
                        break;
                    default:
                        break;
                 }
            }
        };

        win.addMouseListener(adapter); //点击事件
        win.addMouseMotionListener(adapter); //移动事件
        win.addMouseWheelListener(adapter); //滚轮事件

        win.addKeyListener(new KeyAdapter() {
            @Override //键盘按下
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
                if (tank.Destroyed) {
                    tank.moving = false;
                    return;
                }
                switch (e.getKeyChar()) {
                    case 'w':
                    case 'W':
                        tank.dir = Directions.UP.getAngleValue();
                        tank.moving = true;
                        if (tank.y < 0) {
                            //tank.dir = Directions.DOWN.getAngleValue();
                            tank.moving = false;
                        }
                        break;
                    case 'a':
                    case 'A':
                        tank.dir = Directions.LEFT.getAngleValue();
                        tank.moving = true;
                       if (tank.x < 0) {
                        //tank.dir = Directions.RIGHT.getAngleValue();
                        tank.moving = false;
                    }
                        break;
                    case 's':
                    case 'S':
                        tank.dir = Directions.DOWN.getAngleValue();
                        tank.moving = true;
                        if (tank.y >= win.height) {
                            // tank.dir = Directions.UP.getAngleValue();
                            tank.moving = false;
                        }
                        break;
                    case 'd':
                    case 'D':
                        tank.dir = Directions.RIGHT.getAngleValue();
                        tank.moving = true;
                        if (tank.x >= win.width) {
                            //tank.dir = Directions.LEFT.getAngleValue();
                            tank.moving = false;
                        }
                        break;
                    case ' ':
                        if(tank.shotNum<maxAmmoNum){
                            Shot shot = new Shot(tank, ShotSpeed);
                            tank.addShotNum();
                            warData.elements.add(shot);
                        }
                        break;
                }


            }

            @Override //键盘抬起
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'w':
                    case 'W':
                    case 'a':
                    case 'A':
                    case 's':
                    case 'S':
                    case 'd':
                    case 'D':
                        tank.moving = false;
                        break;
                }
            }
        });

        this.start();
    }


    /**
     * 线程任务
     */
    @Override
    public synchronized void run() {
        super.run();

        long lastUpdate = System.currentTimeMillis();//当前系统时间
        long lastSpawn=lastUpdate;
        int fps = 60;//理论帧数
        while (true) {
            long interval = 1000 / fps;//理论间隔
            long curr = System.currentTimeMillis();
            long _time = curr - lastUpdate;
            if (_time < interval) {
                // 不到刷新时间，休眠
                try {
                    Thread.sleep(1);
                } catch (Exception e) {

                }

            } else {

                // 更新游戏状态
                lastUpdate = curr;

                // 流逝时间
                float dt = _time * 0.001f;

                //调度任务，如果有些任务计算量大，可以开线程池
               // warData.runEnemyTank(win.width, win.height,100);
                //把运行敌方坦克放入线程池
                executor.executorService.submit(new T_RunEnemyTank(warData,win.width,win.height,100));
                warData.updatePositions(dt);
                //自动刷怪
                if(curr-lastSpawn>SpawningTime){
                     warData.regularSpawning();
                     lastSpawn=curr;
                }
                // 碰撞检测

                //warData.CollisionDetection();
                executor.executorService.submit(new T_Collision(warData));

                // 依据元素的状态，更新数据区
                //warData.updateDataSet();
                executor.executorService.submit(new T_UpdateDataSet(warData));

                // 刷新界面
               // win.update(dt);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // 更新UI组件
                        // 例如：gameView.repaint();
                        win.update(dt);
                        //System.out.println("UI updated in Event Dispatch Thread.");
                    }
                });

                GameStart_and_End.settleGame(warData,win,executor);
                //如果游戏已经结束，终止循环
                if(warData.isEnd()!=-1){
                    return;
                }
            }

        }
    }
}
