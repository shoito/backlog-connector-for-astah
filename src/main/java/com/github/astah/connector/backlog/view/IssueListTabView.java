package com.github.astah.connector.backlog.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.jude.api.inf.ui.IPluginExtraTabView;
import com.change_vision.jude.api.inf.ui.ISelectionListener;
import com.github.astah.connector.backlog.Activator;
import com.github.astah.connector.backlog.controller.IssueController;
import com.github.astah.connector.backlog.model.Project;
import com.github.astah.connector.backlog.view.component.IssueTable;
import com.github.astah.connector.backlog.view.model.ProjectComboBoxModel;

@SuppressWarnings("serial")
public class IssueListTabView extends JPanel implements IPluginExtraTabView {
	private static final Logger logger = LoggerFactory.getLogger(IssueListTabView.class);
	private IssueController controller;
	private ProjectComboBoxModel projectModel;
	
	public static void main(String[] args) throws Throwable {
		IssueListTabView issueList = new IssueListTabView();
		JFrame frame = new JFrame("Backlog");
		frame.setSize(800, 480);
		frame.add(issueList);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		IssueController.getInstance().loadIssuesFromCurrentProject();
	}
	
	public IssueListTabView() {
		this.controller = IssueController.getInstance();
    	this.projectModel = controller.getProjectModel();
        initComponents();
	}

    private void initComponents() {
        buttonBar = new JToolBar();
        createButton = new JButton();
        toolBarFilter = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
        projectBox = new JComboBox();
        settingButton = new JButton();
        reloadButton = new JButton();
        scrollPane = new JScrollPane();
        issueTable = new IssueTable(controller.getIssueTableModel());

        buttonBar.setFloatable(false);
        buttonBar.setRollover(true);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/github/astah/connector/backlog/view/Bundle", Locale.getDefault(), Activator.class.getClassLoader());
        createButton.setText(bundle.getString("IssueList.createButton.text"));
        createButton.setFocusable(false);
        createButton.setHorizontalTextPosition(SwingConstants.CENTER);
        createButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });
        buttonBar.add(createButton);
        buttonBar.add(toolBarFilter);

        projectBox.setModel(projectModel);
        projectBox.setMaximumSize(new Dimension(320, 32767));
		projectBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					Object project = event.getItem();
					if (!(project instanceof Project)) {
						return;
					}
					
					issueTable.setCurrentProject((Project) project);
				}
			}
		});
        buttonBar.add(projectBox);

        settingButton.setText(bundle.getString("IssueList.settingButton.text"));
        settingButton.setFocusable(false);
        settingButton.setHorizontalTextPosition(SwingConstants.CENTER);
        settingButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        settingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                settingButtonActionPerformed(evt);
            }
        });
        buttonBar.add(settingButton);

        reloadButton.setText(bundle.getString("IssueList.reloadButton.text"));
        reloadButton.setFocusable(false);
        reloadButton.setHorizontalTextPosition(SwingConstants.CENTER);
        reloadButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        reloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reloadButtonActionPerformed(evt);
            }
        });
        buttonBar.add(reloadButton);

        scrollPane.setViewportView(issueTable);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
            .addComponent(buttonBar, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(buttonBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
        );
    }

	private void settingButtonActionPerformed(ActionEvent evt) {
		Window window = SwingUtilities.windowForComponent(this);
        ConnectionSettings dialog = new ConnectionSettings((Frame) window);
        dialog.setLocationByPlatform(true);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void reloadButtonActionPerformed(ActionEvent evt) {
        controller.loadIssuesFromCurrentProject();
    }

    private void createButtonActionPerformed(ActionEvent evt) {
    	Object selectedProject = projectModel.getSelectedItem();
    	Window window = SwingUtilities.windowForComponent(this);
    	if (!(selectedProject instanceof Project)) {
    		JOptionPane.showMessageDialog(window, "課題を作成するプロジェクトを選択してください。", "情報", JOptionPane.INFORMATION_MESSAGE);
    		return;
    	}
    	
        IssueForm dialog = new IssueForm((Frame) window, (Project) projectModel.getSelectedItem());
        dialog.setLocationByPlatform(true);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private JToolBar buttonBar;
    private JButton createButton;
    private IssueTable issueTable;
    private JComboBox projectBox;
    private JButton reloadButton;
    private JScrollPane scrollPane;
    private JButton settingButton;
    private Box.Filler toolBarFilter;

	@Override
	public void activated() {
	}

	@Override
	public void addSelectionListener(ISelectionListener listener) {
	}

	@Override
	public void deactivated() {
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public String getDescription() {
		return "Backlogの課題をastahから扱えるビュー";
	}

	@Override
	public String getTitle() {
		return "Backlogコネクタ";
	}
}
