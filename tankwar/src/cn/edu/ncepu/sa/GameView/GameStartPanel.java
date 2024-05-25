package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Control.GameSave_and_Load;
import cn.edu.ncepu.sa.Control.GameStart_and_End;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

public class GameStartPanel extends JFrame {
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton onlineRedButton;
    private JButton onlineBlueButton;
    private boolean gameStart = false;

    public GameStartPanel() {
        setTitle("坦克大战 - 开始界面");
        setSize(400, 400); // 调整高度以适应新增按钮
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Tank War", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 153, 76));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        //ImageIcon newGameIcon = new ImageIcon("path_to_new_game_icon.png");
        newGameButton = new JButton("新游戏");
        configureButton(newGameButton, new Color(51, 204, 51));
        buttonPanel.add(newGameButton);

        //ImageIcon loadGameIcon = new ImageIcon("path_to_load_game_icon.png");
        loadGameButton = new JButton("读取存档");
        configureButton(loadGameButton, new Color(0, 153, 204));
        buttonPanel.add(loadGameButton);

        // 新增联机对战按钮
        //ImageIcon onlineRedIcon = new ImageIcon("path_to_online_red_icon.png"); // 替换为红色联机图标路径
        onlineRedButton = new JButton("联机对战(红)");
        configureButton(onlineRedButton, new Color(255, 0, 0)); // 红色背景
        buttonPanel.add(onlineRedButton);

       // ImageIcon onlineBlueIcon = new ImageIcon("path_to_online_blue_icon.png"); // 替换为蓝色联机图标路径
        onlineBlueButton = new JButton("联机对战(蓝)");
        configureButton(onlineBlueButton, new Color(0, 0, 255)); // 蓝色背景
        buttonPanel.add(onlineBlueButton);

        contentPane.add(buttonPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        setVisible(true);
        setupActionListeners();
    }

    private void configureButton(JButton button, Color bgColor) {
        button.setOpaque(true);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20);
        button.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
    }

    // 示例动作监听器，具体逻辑需您实现
    private void setupActionListeners() {
        newGameButton.addActionListener(e -> {
            System.out.println("开始新游戏...");
            gameStart = true;
            GameStart_and_End.newStart();
            setVisible(false);
        });

        loadGameButton.addActionListener(e -> {
            System.out.println("读取存档...");
            GameSave_and_Load.clickLoad();
            gameStart = true;
            setVisible(false);
        });

        onlineRedButton.addActionListener(e -> {
            System.out.println("联机对战(红) 功能待实现...");
            // 实现联机对战(红)逻辑
        });

        onlineBlueButton.addActionListener(e -> {
            System.out.println("联机对战(蓝) 功能待实现...");
            // 实现联机对战(蓝)逻辑
        });
    }




}

