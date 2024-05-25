package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Control.GameStart_and_End;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameEndPanel extends JFrame {
    private JButton  replayButton ;
    private JButton  exitButton ;
    public GameEndPanel(String winner, String gameTimeInSeconds, int kills) {
        setTitle("游戏结束");
        setSize(400, 300);
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建标签
        JLabel congratulationLabel = new JLabel(winner, JLabel.CENTER);
        congratulationLabel.setFont(new Font("Arial", Font.BOLD, 17));
        JLabel gameTimeLabel = new JLabel("游戏时间: " + gameTimeInSeconds , JLabel.CENTER);
        JLabel killsLabel = new JLabel("击杀数: " + kills, JLabel.CENTER);

        // 创建按钮
         replayButton = new JButton("重玩");
         exitButton = new JButton("关闭");

        // 设置按钮监听器
        replayButton.addActionListener(e -> {
            //threadPool.executorService.shutdown();
            GameStart_and_End.newStart();
            dispose();
            System.out.println("准备重玩游戏...");
        });

        exitButton.addActionListener(e -> System.exit(0));

        // 创建主面板，使用垂直BoxLayout布局
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // 添加祝贺标签
        mainPanel.add(congratulationLabel);
        mainPanel.add(Box.createVerticalStrut(10)); // 添加垂直间距

        // 添加游戏时间和击杀数标签到另一个面板中，并设置居中布局
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        statsPanel.add(gameTimeLabel);
        statsPanel.add(killsLabel);
        mainPanel.add(statsPanel);
        mainPanel.add(Box.createVerticalStrut(20)); // 添加垂直间距

        // 创建按钮面板，并设置水平BoxLayout布局
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(replayButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel);

        // 设置窗口内容面板
        setContentPane(mainPanel);

        // 显示窗口
        setVisible(true);
    }


}
