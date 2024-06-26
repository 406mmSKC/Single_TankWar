package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Control.GameSave_and_Load;
import cn.edu.ncepu.sa.Model.GameSave;
import cn.edu.ncepu.sa.Model.GameState;
import cn.edu.ncepu.sa.Model.Mapc;
import cn.edu.ncepu.sa.Model.WarData;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * 游戏窗口
 */
public class GameView extends JFrame {
    /**
     * 数据区引用
     */
    WarData warData;

    /**
     * 游戏画板
     */
    public GamePanel panel = new GamePanel();

    //边框宽度,最好设置成常量
    //public int boardWidth=10;
    /**
     * 画布宽度
     */
    public int width = 860;

    /**
     * 画布高度
     */
    public int height = 640;

    /**
     * 初始化显示组件
     *
     * @param warData 数据区引用
     */

    public GameView(WarData warData) {
        this.warData = warData;
        panel.setWarData(warData);

        //点击右上角强退时保存存档
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("save!");
                GameSave_and_Load.clickSave(warData);
                // 现在可以安全地关闭窗口了
                dispose();
                System.exit(0);
            }
        });



        //panel.setMapc(mapc);
        // 窗体初始化和
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.setTitle("坦克大战 V1.0");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);

        //添加边框


        int w = this.getContentPane().getSize().width;
        int h = this.getContentPane().getSize().height;

        int cw = width - w;
        int ch = height - h;
        this.setSize(width + cw, height + ch);
    }

    // 私有数据区

    private int _frames = 0;//累计帧数，自主选择是否显示
    private float _dt = 0;//累计时长
    private float _frameRate = 0.0f;//帧率

    /**
     * 更新画板
     *
     * @param timeFlaps 流逝时间
     */
    public void update(double timeFlaps) {

        _frames++;
        _dt += timeFlaps;
        if (_dt >= 1.0f) {
            _frameRate = _frames / _dt;
            _frames = 0;
            _dt = 0f;
        }
        panel.setFrameRate(_frameRate);
        panel.repaint();
    }
}
