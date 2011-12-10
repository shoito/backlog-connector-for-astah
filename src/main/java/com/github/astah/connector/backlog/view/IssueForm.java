package com.github.astah.connector.backlog.view;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;


import org.apache.commons.lang.StringUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.astah.connector.backlog.client.BacklogClient;
import com.github.astah.connector.backlog.controller.IssueController;
import com.github.astah.connector.backlog.model.IssueType;
import com.github.astah.connector.backlog.model.Priority;
import com.github.astah.connector.backlog.model.Project;
import com.github.astah.connector.backlog.model.User;
import com.github.astah.connector.backlog.view.model.IssueTypeComboBoxModel;
import com.github.astah.connector.backlog.view.model.PriorityComboBoxModel;
import com.github.astah.connector.backlog.view.model.UserComboBoxModel;


public class IssueForm extends JDialog {
	private static final Logger logger = LoggerFactory.getLogger(IssueForm.class);
	private static final long serialVersionUID = 1L;
	private Project currentProject;
	private String key;

    public IssueForm(Frame parent, Project project) {
    	super(parent);
    	this.currentProject = project;
        initComponents();
        loadFormData();
        
        BacklogClient client = IssueController.getInstance().getBacklogClient();
		String currentUserName = client.getUserName();
		for (int i = 0; i < assignerComboBoxModel.getSize(); i++) {
			User user = (User) assignerComboBoxModel.getElementAt(i);
			if (currentUserName.equals(user.getName())) {
				assignerComboBoxModel.setSelectedItem(user);
				break;
			}
		}
    }

	public IssueForm(Frame parent, Project project, Map<String, String> form) {
		super(parent);
    	this.currentProject = project;
        initComponents();
        loadFormData();
        
        this.key = (String) form.get("key");
        String summary = (String) form.get("summary");
        String description = (String) form.get("description");
        String assigner = (String) form.get("assigner");
        String issueType = (String) form.get("issueType");
        String priority = (String) form.get("priority");
        summaryField.setText(summary);
        descArea.setText(description);
        if (StringUtils.isNotBlank(assigner)) assignerBox.setSelectedItem(assigner);
        if (StringUtils.isNotBlank(issueType)) issueTypeBox.setSelectedItem(issueType);
        if (StringUtils.isNotBlank(priority)) priorityBox.setSelectedItem(priority);
        
		for (int i = 0; i < assignerComboBoxModel.getSize(); i++) {
			User as = (User) assignerComboBoxModel.getElementAt(i);
			if (assigner.equals(as.getName())) {
				assignerComboBoxModel.setSelectedItem(as);
				break;
			}
		}
        
		for (int i = 0; i < issueTypeComboBoxModel.getSize(); i++) {
			IssueType it = (IssueType) issueTypeComboBoxModel.getElementAt(i);
			if (issueType.equals(it.getName())) {
				issueTypeComboBoxModel.setSelectedItem(it);
				break;
			}
		}
        
		for (int i = 0; i < priorityComboBoxModel.getSize(); i++) {
			Priority pr = (Priority) priorityComboBoxModel.getElementAt(i);
			if (priority.equals(pr.getName())) {
				priorityComboBoxModel.setSelectedItem(pr);
				break;
			}
		}
    }

    private void loadFormData() {
		BacklogClient client = IssueController.getInstance().getBacklogClient();
		try {
			List<User> users = client.getUsers(currentProject.getId());
			List<IssueType> issueTypes = client.getIssueTypes(currentProject.getId());
			
			assignerComboBoxModel.removeAllElements();
			assignerComboBoxModel.addElement(new User());
			for (User user : users) {
				assignerComboBoxModel.addElement(user);
			}
			
			issueTypeComboBoxModel.removeAllElements();
			for (IssueType issueType : issueTypes) {
				issueTypeComboBoxModel.addElement(issueType);
			}
		} catch (XmlRpcException e) {
			logger.warn(e.getMessage(), e);
		}
	}

    private void initComponents() {
        summaryLabel = new JLabel();
        descLabel = new JLabel();
        summaryField = new JTextField();
        descScrollPane = new JScrollPane();
        descArea = new JTextArea();
        assignerLabel = new JLabel();
        assignerBox = new JComboBox();
        assignerComboBoxModel = new UserComboBoxModel();
        cancelButton = new JButton();
        okButton = new JButton();
        issueTypeLabel = new JLabel();
        issueTypeBox = new JComboBox();
        issueTypeComboBoxModel = new IssueTypeComboBoxModel();
        priorityLabel = new JLabel();
        priorityBox = new JComboBox();
        priorityComboBoxModel = new PriorityComboBoxModel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/github/astah/connector/backlog/view/Bundle");
        setTitle(bundle.getString("IssueForm.title"));

        summaryLabel.setLabelFor(summaryField);
        summaryLabel.setText(bundle.getString("IssueForm.summaryLabel.text"));

        descLabel.setLabelFor(descArea);
        descLabel.setText(bundle.getString("IssueForm.descLabel.text"));

        descArea.setColumns(20);
        descArea.setRows(5);
        descArea.setLineWrap(true);
        descScrollPane.setViewportView(descArea);

        assignerLabel.setLabelFor(assignerBox);
        assignerLabel.setText(bundle.getString("IssueForm.assignerLabel.text"));

        assignerBox.setModel(assignerComboBoxModel);

        cancelButton.setText(bundle.getString("IssueForm.cancelButton.text"));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText(bundle.getString("IssueForm.okButton.text"));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        issueTypeLabel.setText(bundle.getString("IssueForm.issueTypeLabel.text"));

        issueTypeBox.setModel(issueTypeComboBoxModel);

        priorityLabel.setText(bundle.getString("IssueForm.priorityLabel.text"));
        
        priorityBox.setModel(priorityComboBoxModel);
        priorityBox.setSelectedIndex(1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(issueTypeLabel)
                            .addComponent(priorityLabel)
                            .addComponent(summaryLabel)
                            .addComponent(descLabel)
                            .addComponent(assignerLabel))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(descScrollPane, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                            .addComponent(summaryField, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                            .addComponent(assignerBox, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(priorityBox, GroupLayout.Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(issueTypeBox, GroupLayout.Alignment.LEADING, 0, 157, Short.MAX_VALUE))))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(242, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addComponent(okButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(summaryField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(summaryLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(descLabel)
                    .addComponent(descScrollPane, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(assignerLabel)
                    .addComponent(assignerBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(issueTypeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(issueTypeLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(priorityLabel)
                    .addComponent(priorityBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton)
                    .addComponent(okButton)))
        );

        pack();
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        dispose();
    }
    
    private void okButtonActionPerformed(ActionEvent evt) {
    	// TODO Validate
		String summary = summaryField.getText();
		String description = descArea.getText();
		User assigner = (User) assignerBox.getSelectedItem();
		IssueType issueType = (IssueType) issueTypeBox.getSelectedItem();
		Priority priority = (Priority) priorityBox.getSelectedItem();
		
		Map<String, Object> form = new HashMap<String, Object>();
		form.put("summary", summary);
		form.put("description", description);
		form.put("assignerId", (assigner.getId() != 0) ? assigner.getId() : "");
		form.put("issueTypeId", issueType.getId());
		form.put("priorityId", priority.getId());
		
		IssueController controller = IssueController.getInstance();
		BacklogClient client = controller.getBacklogClient();
		
    	if (StringUtils.isBlank(key)) { // 新規作成
    		form.put("projectId", currentProject.getId());
    		try {
				client.createIssue(form);
				dispose();
			} catch (XmlRpcException e) {
				logger.warn(e.getMessage(), e);
			}
    	} else { // 更新
    		form.put("key", key);
    		try {
				client.updateIssue(form);
				dispose();
			} catch (XmlRpcException e) {
				logger.warn(e.getMessage(), e);
			}
    	}
    	
    	IssueController.getInstance().loadIssuesFromCurrentProject();
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
            java.util.logging.Logger.getLogger(IssueForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssueForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssueForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssueForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssueForm(null, new Project(1, "", "", "", true)).setVisible(true);
            }
        });
    }

    private JComboBox assignerBox;
    private JLabel assignerLabel;
    private UserComboBoxModel assignerComboBoxModel;
    private JButton cancelButton;
    private JTextArea descArea;
    private JLabel descLabel;
    private JScrollPane descScrollPane;
    private JComboBox issueTypeBox;
    private IssueTypeComboBoxModel issueTypeComboBoxModel;
    private JLabel issueTypeLabel;
    private JButton okButton;
    private JComboBox priorityBox;
    private PriorityComboBoxModel priorityComboBoxModel;
    private JLabel priorityLabel;
    private JTextField summaryField;
    private JLabel summaryLabel;
}
