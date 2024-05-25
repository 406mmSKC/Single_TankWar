package cn.edu.ncepu.sa.Model;

import cn.edu.ncepu.sa.utils.Utils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * 数据组件，除了引用传递还可以使用单例类
 */
public class WarData {

    public  HashSet<Element> elements = new HashSet<>();
    public  Tank userTank ;//= new Tank(600, 200, 0, 110, 0.5, TankTeam.RED.ordinal(),0);
    public final int enemyMoveGap=100;
    public final int enemyAttackRange=800;
    public final int maxAmmoNum=3;
    public final int MaxGrass=70;
    public final int MaxRiver=50;
    public Mapc mapc;

    public void setUserTank() {
        this.userTank.x=600;
        this.userTank.y=200;
        this.userTank.dir=0;
        this.userTank.hp=110;
        this.userTank.hp_recovery_per_sec=0.5;
        this.userTank.team=TankTeam.RED.ordinal();
        this.userTank.killNum=0;
        //this.userTank.name="Player";
    }

    public WarData() {
        // 构造我方坦克
        //setUserTank();
        userTank= new Tank(600, 200, 0, 110, 0.5, TankTeam.RED.ordinal(),0,"Player");
        userTank.moving=false;
        elements.add(userTank);
        mapc=new Mapc(MaxGrass,MaxRiver);
        // 构造敌方坦克
        AddSomeEnemyTanks();
    }


    /**
     * 增加一辆敌方坦克
     *
     * @param x                   x
     * @param y                   y
     * @param dir                 方向
     * @param hp                  初始血量
     * @param hp_recovery_per_sec 修复血量
     * @param team                分组
     */
    public void AddAEnemyTank(int x, int y, double dir, double hp, double hp_recovery_per_sec, int team,int killNum,String name) {
        if(mapc.isRiver(x,y)){
            AddEnemyTankAgain();
            return;
        }
        Tank t = new Tank(x, y, dir, hp, hp_recovery_per_sec, team,killNum,name);
        t.moving = true;    // 默认是移动状态
        elements.add(t);
    }

    /**
     * 构造敌方坦克，之后要依据配置/地图来构造tank
     */
    public void AddSomeEnemyTanks() {
        // 构造一些敌方坦克
        Random r=new Random();
        AddAEnemyTank(r.nextInt(600), r.nextInt(600), 0, 200, 0.1, TankTeam.BLUE.ordinal(),0,"enemy");
        AddAEnemyTank(r.nextInt(600), r.nextInt(600), 0, 200, 0.1, TankTeam.BLUE.ordinal(),0,"enemy");
        AddAEnemyTank(r.nextInt(600), r.nextInt(600), 0, 200, 0.1, TankTeam.BLUE.ordinal(),0,"enemy");
        AddAEnemyTank(r.nextInt(600),r.nextInt(600), 0, 200, 0.1, TankTeam.BLUE.ordinal(),0,"enemy");
        AddAEnemyTank(r.nextInt(600), r.nextInt(600), 0, 200, 0.1, TankTeam.BLUE.ordinal(),0,"enemy");

    }

    public void AddEnemyTankAgain() {
        // 构造一些敌方坦克
        Random r=new Random();
        AddAEnemyTank(r.nextInt(600), r.nextInt(600), 0, 200, 0.1, TankTeam.BLUE.ordinal(),0,"enemy");
    }

    /**
     * 敌方坦克动起来，请尝试修改为每个坦克独立线程控制，自主活动
     */
    public synchronized void runEnemyTank(int viewWidth, int viewHeight,int shot_Speed) {

        if (userTank.Destroyed) {
            return;
        }

        for (Element elemnet : elements) {
            // 找坦克
            if (elemnet instanceof Tank) {
                // 找敌方坦克
                if (((Tank) elemnet).getTeam() == TankTeam.BLUE.ordinal()) {
                    Tank t = (Tank) elemnet;

                    // 防止跑出地图
                    if (t.x < 0) {
                        t.dir = Directions.RIGHT.getAngleValue();
                    }
                    else if (t.y < 0) {
                        t.dir = Directions.DOWN.getAngleValue();
                    }
                    else if (t.x >= viewWidth) {
                        t.dir = Directions.LEFT.getAngleValue();
                    }
                    else if (t.y >= viewHeight) {
                        t.dir = Directions.UP.getAngleValue();
                    }

                    // 运动几步随机开炮，50应该设置为参数或者常量
                    if (t.moveSteps > enemyMoveGap) {
                        // 方向随机
                        double random = Math.random() * 360;
                        t.dir = random;
                        t.turretDir = random;
                        t.moving = true;
                        t.moveSteps = 0;

                        // 如果我方坦克进入射程，800应该设置为常量
                        if (t.distance(userTank) < enemyAttackRange&&userTank.Viewed==true) {
                            // 自动瞄准
                            t.turretDir = Utils.ppDir(t.x, t.y, userTank.x, userTank.y) + 90;

                            // 开炮,同一时间一个坦克存在的炮弹不得超过三发
                            //本来想在shot类中做限制，但是考虑到让shot类直接修改tank类不太好，作罢
                            if (t.shotNum < maxAmmoNum) {
                                Shot shot = new Shot(t, shot_Speed);
                                //t.addKillNum();
                                elements.add(shot);
                            }
                            return;
                        }
                    }
                }
            }
            //子弹越界销毁
            else if(elemnet instanceof Shot){
                if (elemnet.x < 0||elemnet.y < 0||elemnet.x >= viewWidth||elemnet.y >= viewHeight) {
                    elemnet.destroy();
                    Shot sh=(Shot)elemnet;
                    sh.tank.subShotNum();
                }
            }
            else if(elemnet instanceof Shot_High_Speed){
                if (elemnet.x < 0||elemnet.y < 0||elemnet.x >= viewWidth||elemnet.y >= viewHeight) {
                    elemnet.destroy();
                    Shot_High_Speed sh=(Shot_High_Speed) elemnet;
                    sh.tank.subShotNum();
                }
            }
        }
    }

    /**
     * 更新坦克的位置
     *
     * @param timeFlaps 运行时间间隔
     */
    public void updatePositions(double timeFlaps) {
        // 所有元素依据流逝时间更新状态
        for (Element elemnet : elements) {
            elemnet.update(timeFlaps);
        }

    }
    //每隔一段时间检查一下坦克数量，如果不足五辆就再添加一辆
    public void regularSpawning(){
        int tankCnt=0;
        for(Element elt :elements){
            if(elt instanceof Tank){
                tankCnt++;
            }
        }
        if(tankCnt<6){
            Random r=new Random();
            AddAEnemyTank(r.nextInt(600),r.nextInt(600),0,200,0.1,TankTeam.BLUE.ordinal(),0,"enemy");
        }
    }

    //如果集合中的所有坦克team都相同，就返回team，否则返回-1,平局返回-2
    public int isEnd(){
        synchronized(this){
        if(elements.isEmpty()){
            return -2;//平局
        }
        Tank firstTank=null;
        for (Element element : elements) {
            if (element instanceof Tank) {
                if (firstTank == null) {
                    firstTank = (Tank) element;
                } else {
                    if (((Tank) element).getTeam() != firstTank.getTeam()) {
                        return -1;
                    }
                }
            }
        }

        return firstTank.getTeam();
    }}



    /**
     * 碰撞检测
     */
    public int getUserKillNum(){
        return userTank.killNum;
    }
    //其实应当新建一个碰撞管理器类，但是这里逻辑并不复杂，就不添加了
    public  synchronized void CollisionDetection() {
        //遍历每一个子弹
        boolean isKilled=false;
        for (Element shot : elements) {
            if (shot instanceof Shot||shot instanceof Shot_High_Speed) {
                // 寻找每一辆坦克
                for (Element tank : elements) {
                    //进行敌我识别
                    if(shot instanceof Shot){
                        if ((tank instanceof Tank) && (tank != ((Shot) shot).tank)) {
                             // 距离过近，则认为打中，20应该设置为常量
                             if (shot.distance(tank) < 20) {
                                    //添加了击杀判定
                                     isKilled=((Tank) tank).damage(((Shot) shot).damage); //使坦克受到伤害
                                     if(isKilled==true){
                                        ((Shot) shot).tank.addKillNum();
                                     }
                                 shot.destroy(); //销毁当前子弹
                                 ((Shot) shot).tank.subShotNum();
                            }
                        }
                    }//这里添加了另一种子弹，由右键发射
                    else if(shot instanceof Shot_High_Speed){
                        if ((tank instanceof Tank) && (tank != ((Shot_High_Speed) shot).tank)) {
                            // 距离过近，则认为打中，20应该设置为常量
                            if (shot.distance(tank) < 20) {

                                    isKilled=((Tank) tank).damage(((Shot_High_Speed) shot).damage); //使坦克受到伤害
                                    if(isKilled==true){
                                         ((Shot_High_Speed) shot).tank.addKillNum();
                                    }
                                shot.destroy(); //销毁当前子弹
                                ((Shot_High_Speed) shot).tank.subShotNum();
                            }
                        }
                    }
                }
            }
        }
        //检测草丛和河流碰撞
        for(Element etank:elements){
            if(etank instanceof Tank){
                 Tank tank_t=(Tank)etank;
                if(mapc.isGrass((int)tank_t.x,(int)tank_t.y)){
                     //System.out.println("enter grass");
                    tank_t.Viewed=false;
                }
                else {
                    tank_t.Viewed=true;
                }
                if(mapc.isRiver((int)tank_t.x,(int)tank_t.y)){

                    //tank_t.dir=-tank_t.dir;
                    tank_t.cancelMove();

                }
            }
        }
    }

    /**
     * 依据元素的状态，处理是否还保留在数据区中
     */
    //public synchronized void updateDataSet()
    public  synchronized void updateDataSet() {
        // 删除中间的元素会有问题
        /*for (Element element : elements) {
            if (element.Destroyed) {
                elements.remove(element);
            } else {

            }
        }*/

        // 请大家思考，为什么要采取这种方法
        // 因为在for循环时改变了遍历目标的大小，所以采用更安全的迭代器，但是仍然解决不了线程访问
        Iterator<Element> it = elements.iterator();
        while (it.hasNext()) {
            Element tmp = it.next();
            if (tmp.isDestroyed()) {
                if (tmp != userTank) {
                    it.remove();
                }
            }
        }
        // 上文方法依然存在数据并行操作问题，如何加锁？

    }
}
