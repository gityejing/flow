package com.maven.flow.editor.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.maven.flow.editor.SystemGloable;
import com.maven.flow.hibernate.dao.TblEmployeeInfo;
import com.maven.flow.service.EmployeeInfoService;

public class ClientLogin extends Dialog implements ActionListener, KeyListener {

	private JPanel MainPanel;

	private final Image Background = this.getToolkit().getImage(
			UIUtil.class.getResource("/resources/login.png"));

	private JLabel ALabel = new JLabel("用户名称");

	private JLabel BLabel = new JLabel("用户口令");

	private JLabel CLabel = new JLabel("服务器IP");

	private String[] users = { "24401000234", "24401002101" };

	// private JComboBox UserNameEdit = new JComboBox(users);
	private JTextField UserNameEdit = new JTextField();

	private JPasswordField PasswordEdit = new JPasswordField();

	private String[] IPS = { "127.0.0.1", "192.168.123.106" };

	// private JComboBox ServerIP = new JComboBox(IPS);
	private JTextField ServerIPEdit = new JTextField();

	//
	private JButton SureButton = new JButton("登录");

	private JButton CancelButton = new JButton("退出");

	private JCheckBox AutoLoginBox = new JCheckBox("自动登录");

	private JLabel messageLbl = new JLabel("《欢迎使用流程编辑器1.0》");

	private JLabel errormessageLbl = new JLabel("请输入用户名和口令");

	private boolean flag = false;//登录是否的标记.

	public ClientLogin() {
		init();
	}

	public void init() {

		//
		this.setTitle("流程编辑器1.0");
		MainPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int width = getSize().width;
				int height = getSize().height;
				g.drawImage(Background, 0, 0, width, height, this);
				g.setColor(Color.white);
				g.fillRect(90, 125, 130, 20);
				g.fillRect(90, 150, 130, 20);
				g.fillRect(90, 175, 130, 20);

			}
		};
		MainPanel.setLayout(null);
		MainPanel.setOpaque(true);
		getContentPane().add(MainPanel);
		UserNameEdit.setEditable(true);
		ServerIPEdit.setEditable(true);
		ServerIPEdit.setEditable(true);
		MainPanel.add(UserNameEdit);
		MainPanel.add(PasswordEdit);
		MainPanel.add(ServerIPEdit);
		MainPanel.add(SureButton);
		MainPanel.add(CancelButton);
		MainPanel.add(AutoLoginBox);
		//
		MainPanel.add(ALabel);
		MainPanel.add(BLabel);
		MainPanel.add(CLabel);
		//
		MainPanel.add(messageLbl);
		MainPanel.add(errormessageLbl);

		messageLbl.setBounds(15, 20, 200, 25);
		errormessageLbl.setBounds(15, 60, 200, 25);
		//
		SureButton.setBounds(245, 120, 75, 28);
		CancelButton.setBounds(245, 155, 75, 28);
		AutoLoginBox.setBounds(245, 185, 75, 28);
		AutoLoginBox.setVisible(false);
		//
		ALabel.setBounds(30, 125, 80, 20);
		UserNameEdit.setBounds(90, 125, 130, 20);
		UserNameEdit.setBorder(BorderFactory.createLineBorder(Color.gray));
		UserNameEdit.setOpaque(false);
		BLabel.setBounds(30, 150, 80, 20);
		PasswordEdit.setBounds(90, 150, 130, 20);
		PasswordEdit.setBorder(BorderFactory.createLineBorder(Color.gray));
		PasswordEdit.setOpaque(false);
		CLabel.setBounds(30, 175, 80, 20);
		ServerIPEdit.setBounds(90, 175, 130, 20);
		ServerIPEdit.setBorder(BorderFactory.createLineBorder(Color.gray));
		ServerIPEdit.setOpaque(false);
		SureButton.addActionListener(this);
		CancelButton.addActionListener(this);
		this.setSize(335, 247);
		this.CenterComponent();
		//
		this.UserNameEdit.addKeyListener(this);
		this.PasswordEdit.addKeyListener(this);
		this.ServerIPEdit.addKeyListener(this);
		this.SureButton.addKeyListener(this);
		//
		this.UserNameEdit.setText("admin");
		this.PasswordEdit.setText("1");
		this.ServerIPEdit.setText("127.0.0.1");
		// this.SureButton.doClick();
		setVisible(true);

	}

	public static boolean CheckUser() {
		ClientLogin login = new ClientLogin();
		return login.flag;
	}
	
	public void actionPerformed(ActionEvent e) {
		String pwd = new String(PasswordEdit.getPassword());
		if (e.getSource() == this.SureButton) {
			try {
				// 输入用户ID
				if (this.UserNameEdit.getText().trim().equals("")) {
					errormessageLbl.setText("请输入用户名称!");
					this.UserNameEdit.requestFocus();
					// this.AutoLogin = false;
				} else if (pwd.trim().equals("")) {
					errormessageLbl.setText("请输入用户口令!");
					this.PasswordEdit.requestFocus();
					// this.AutoLogin = false;
				} else if (this.ServerIPEdit.getText().trim().equals("")) {

					errormessageLbl.setText("请输入服务器地址!");
					// this.AutoLogin = false;
				} else {
					// 输入用户口令

					//
					EmployeeInfoService service = new EmployeeInfoService();
					TblEmployeeInfo employeeInfo = service
							.getEmployeeInfo(UserNameEdit.getText());
					this.flag = employeeInfo.getFloginPwd().equals(
							PasswordEdit.getText());
					//
					if (flag)//如果登录成功
					{
						SystemGloable.CurUserInfo = employeeInfo;//
						this.dispose();
					} else {
						errormessageLbl.setText("输入的用户名或口令有误，请重新输入！");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				errormessageLbl.setText("输入的用户名或口令有误，请重新输入！");
			}

		} else if (e.getSource() == this.CancelButton) {//取消登录。
			System.exit(0);
		}

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// 回车事件
			if (e.getSource() == UserNameEdit) {
				PasswordEdit.requestFocus();
			} else if (e.getSource() == PasswordEdit) {
				SureButton.requestFocus();
			} else if (e.getSource() == SureButton) {
				SureButton.doClick();
			}

		}

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
