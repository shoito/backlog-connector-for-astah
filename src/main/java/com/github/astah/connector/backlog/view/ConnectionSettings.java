package com.github.astah.connector.backlog.view;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.astah.connector.backlog.Activator;
import com.github.astah.connector.backlog.client.BacklogClient;
import com.github.astah.connector.backlog.controller.IssueController;
import com.github.astah.connector.backlog.model.Project;
import com.github.astah.connector.backlog.util.ConfigurationUtils;
import com.github.astah.connector.backlog.view.model.ProjectComboBoxModel;

public class ConnectionSettings extends JDialog {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionSettings.class);
	private static final long serialVersionUID = 1L;
	
    public ConnectionSettings(Frame parent) {
    	super(parent);
        initComponents();
    }

    private void initComponents() {
        formPanel = new JPanel();
        spaceNameLabel = new JLabel();
        spaceNameField = new JTextField();
        userNameLabel = new JLabel();
        userNameField = new JTextField();
        passwordLabel = new JLabel();
        passwordField = new JPasswordField();
        backlogLabel = new JLabel();
        httpLabel = new JLabel();
        testButton = new JButton();
        cancelButton = new JButton();
        okButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/github/astah/connector/backlog/view/Bundle", Locale.getDefault(), Activator.class.getClassLoader());
        setTitle(bundle.getString("ConnectionSettings.title"));

        spaceNameLabel.setLabelFor(spaceNameField);
        spaceNameLabel.setText(bundle.getString("ConnectionSettings.spaceNameLabel.text"));

        userNameLabel.setLabelFor(userNameField);
        userNameLabel.setText(bundle.getString("ConnectionSettings.userNameLabel.text"));

        passwordLabel.setLabelFor(passwordField);
        passwordLabel.setText(bundle.getString("ConnectionSettings.passwordLabel.text"));

        backlogLabel.setText(bundle.getString("ConnectionSettings.backlogLabel.text"));

        httpLabel.setText(bundle.getString("ConnectionSettings.httpLabel.text"));

        GroupLayout formPanelLayout = new GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(formPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(spaceNameLabel)
                    .addComponent(userNameLabel)
                    .addComponent(passwordLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(formPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordField)
                    .addComponent(userNameField)
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addComponent(httpLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spaceNameField, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backlogLabel)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(spaceNameLabel)
                    .addComponent(httpLabel)
                    .addComponent(spaceNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(backlogLabel))
                .addGap(18, 18, 18)
                .addGroup(formPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameLabel)
                    .addComponent(userNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(formPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        testButton.setText(bundle.getString("ConnectionSettings.testButton.text"));
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(bundle.getString("ConnectionSettings.cancelButton.label"));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText(bundle.getString("ConnectionSettings.okButton.label"));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(testButton)
                .addGap(90, 90, 90)
                .addComponent(cancelButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(okButton)
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(formPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(formPanel, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(testButton)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addGap(8, 8, 8))
        );

        pack();
        
        Map<String, String> config = ConfigurationUtils.load();
		spaceNameField.setText(config.get(ConfigurationUtils.SPACE));
		userNameField.setText(config.get(ConfigurationUtils.USER_NAME));
		passwordField.setText(config.get(ConfigurationUtils.PASSWORD));	
    }

    private void testButtonActionPerformed(ActionEvent evt) {
    	String space = spaceNameField.getText();
    	String userName = userNameField.getText();
    	String password = new String(passwordField.getPassword());
    	
    	if (StringUtils.isBlank(space) || StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
    		JOptionPane.showMessageDialog(this, "Backlogに接続できませんでした。\n入力内容を確認してください。");
    		return;
    	}
    	
    	BacklogClient client = new BacklogClient(space, userName, password);
    	if (client.isConnectable()) {
    		JOptionPane.showMessageDialog(this, "Backlogに接続できました。\nOKボタンを押してダイアログを閉じてください。");
    	} else {
    		JOptionPane.showMessageDialog(this, "Backlogに接続できませんでした。\n入力内容を確認してください。");
    	}
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        dispose();
    }

    private void okButtonActionPerformed(ActionEvent evt) {
    	String space = spaceNameField.getText();
    	String userName = userNameField.getText();
    	String password = new String(passwordField.getPassword());
    	
    	ConfigurationUtils.save(space, userName, password);
    	
    	BacklogClient client = new BacklogClient(space, userName, password);
    	IssueController.getInstance().setBacklogClient(client);
    	ProjectComboBoxModel projectModel = IssueController.getInstance().getProjectModel();
    	try {
			List<Project> projects = client.getProjects();
			projectModel.removeAllElements();
			for(Project project : projects) {
				projectModel.addElement(project);
			}
		} catch (Exception e) {
			projectModel.reset();
			logger.warn(e.getMessage(), e);
		}
    	
        dispose();
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConnectionSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConnectionSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConnectionSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConnectionSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConnectionSettings(null).setVisible(true);
            }
        });
    }

    private JLabel backlogLabel;
    private JButton cancelButton;
    private JPanel formPanel;
    private JLabel httpLabel;
    private JButton okButton;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JTextField spaceNameField;
    private JLabel spaceNameLabel;
    private JButton testButton;
    private JTextField userNameField;
    private JLabel userNameLabel;
}
