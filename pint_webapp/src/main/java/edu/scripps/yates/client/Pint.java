package edu.scripps.yates.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import edu.scripps.yates.client.gui.AboutPanel;
import edu.scripps.yates.client.gui.BrowsePanel;
import edu.scripps.yates.client.gui.HelpPanel;
import edu.scripps.yates.client.gui.MainPanel;
import edu.scripps.yates.client.gui.PopUpPanelRedirector;
import edu.scripps.yates.client.gui.ProjectCreatorWizard;
import edu.scripps.yates.client.gui.QueryPanel;
import edu.scripps.yates.client.gui.components.MyDialogBox;
import edu.scripps.yates.client.gui.components.MyWindowScrollPanel;
import edu.scripps.yates.client.gui.components.pseaquant.PSEAQuantFormPanel;
import edu.scripps.yates.client.gui.configuration.ConfigurationClientManager;
import edu.scripps.yates.client.gui.configuration.ConfigurationPanel;
import edu.scripps.yates.client.history.TargetHistory;
import edu.scripps.yates.client.interfaces.InitializableComposite;
import edu.scripps.yates.client.util.ClientToken;
import edu.scripps.yates.client.util.StatusReportersRegister;
import edu.scripps.yates.shared.util.CryptoUtil;

public class Pint implements EntryPoint {

	private QueryPanel queryPanel;
	private MainPanel mainPanel;
	private BrowsePanel browsePanel;

	private MyWindowScrollPanel scroll;
	private String sessionID;

	private MyDialogBox loadingDialog;

	private ConfigurationPanel configurationPanel;

	@Override
	public void onModuleLoad() {

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				Throwable unwrapped = unwrap(e);
				GWT.log("Ex caught!" + e.getMessage());

				Window.alert(unwrapped.getMessage());
				StatusReportersRegister.getInstance().notifyStatusReporters(unwrapped);
			}

			public Throwable unwrap(Throwable e) {
				if (e instanceof UmbrellaException) {
					UmbrellaException ue = (UmbrellaException) e;
					if (ue.getCauses().size() == 1) {
						return unwrap(ue.getCauses().iterator().next());
					}
				}
				return e;
			}
		});
		GWT.log("Module base URL is: " + GWT.getModuleBaseURL());
		GWT.log("Host page base UTL is:  " + GWT.getHostPageBaseURL());
		GWT.log("Module base for static files is:  " + GWT.getModuleBaseForStaticFiles());
		GWT.log("Module name is:  " + GWT.getModuleName());
		GWT.log("Version is:  " + GWT.getVersion());
		GWT.log("Production mode: " + GWT.isProdMode());

		// login
		login();

	}

	public void startupConfiguration(final boolean forceToShowPanel) {

		showLoadingDialog("Configuring PINT. Please wait...");
		ConfigurationServiceAsync service = ConfigurationServiceAsync.Util.getInstance();
		final ConfigurationClientManager configurationManager = ConfigurationClientManager.getInstance();
		service.getAdminPassword(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String encryptedAdminPassword) {
				String decryptedAdminPassword = CryptoUtil.decrypt(encryptedAdminPassword);
				if (decryptedAdminPassword == null) {
					decryptedAdminPassword = "";
				}
				configurationManager.setAdminPassword(decryptedAdminPassword);
				if (configurationManager.isConfigurationCheckingIsFinished()) {
					if (forceToShowPanel || configurationManager.isSomeConfigurationMissing()) {
						showConfigurationPanel();
					} else {
						hiddeLoadingDialog();
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				StatusReportersRegister.getInstance().notifyStatusReporters(caught);
				GWT.log("Error setting up PINT: " + caught.getMessage());
				hiddeLoadingDialog();
			}
		});
		service.getOMIMKey(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String omimKey) {
				if (omimKey == null) {
					omimKey = "";
				}
				configurationManager.setOMIMKey(omimKey);
				if (configurationManager.isConfigurationCheckingIsFinished()) {
					if (forceToShowPanel || configurationManager.isSomeConfigurationMissing()) {
						showConfigurationPanel();
					} else {
						hiddeLoadingDialog();
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				StatusReportersRegister.getInstance().notifyStatusReporters(caught);
				GWT.log("Error setting up PINT: " + caught.getMessage());
				hiddeLoadingDialog();
			}
		});

	}

	protected void showConfigurationPanel() {
		if (configurationPanel == null) {
			configurationPanel = new ConfigurationPanel();
		}
		hiddeLoadingDialog();
		configurationPanel.center();
	}

	private void showLoadingDialog(String text) {
		if (loadingDialog == null) {
			loadingDialog = new MyDialogBox(text, true, true, null);
		}
		loadingDialog.setText(text);
		loadingDialog.center();
	}

	private void hiddeLoadingDialog() {
		loadingDialog.hide();
	}

	private void loadGUI() {
		// setup historychangehandler
		setUpHistory();

		RootLayoutPanel rootPanel = RootLayoutPanel.get();
		rootPanel.setSize("100%", "100%");
		rootPanel.animate(100);
		scroll = new MyWindowScrollPanel();
		rootPanel.add(scroll);

		// loading dialog
		// showLoadingDialog("Loading PINT components...");
		// MAIN PANEL
		mainPanel = new MainPanel(this);
		scroll.add(mainPanel);

		// / PROJECT CREATOR #projectCreator
		// createProjectPanel = new ProjectCreator();

		final String projectParameter = com.google.gwt.user.client.Window.Location.getParameter("project");
		parseEncryptedProjectValues(projectParameter);
		if (!"".equals(History.getToken())) {
			History.fireCurrentHistoryState();
		}
	}

	private void parseEncryptedProjectValues(String projectEncryptedValue) {

		if (projectEncryptedValue != null && !"".equals(projectEncryptedValue)) {
			String decodedProjectTag = "";
			if (projectEncryptedValue.contains("CFTR")) {
				decodedProjectTag = "_CFTR_";
			} else {
				decodedProjectTag = CryptoUtil.decrypt(projectEncryptedValue);

			}
			if (decodedProjectTag == null) {
				final MyDialogBox instance = new MyDialogBox(
						"PINT doesn't recognize the encrypted code as a valid project identifier", true, true, null);
				instance.setShowLoadingBar(false);
				instance.center();
				return;
			}
			Set<String> projectTags = new HashSet<String>();
			if (decodedProjectTag.contains(",")) {
				String[] split = decodedProjectTag.split(",");
				for (String string : split) {
					projectTags.add(string);
				}
			} else {
				projectTags.add(decodedProjectTag);
			}
			queryPanel = new QueryPanel(sessionID, projectTags);
			History.newItem(TargetHistory.QUERY.getTargetHistory());
		}

	}

	private Set<String> getProjectValues(String projectValue) {
		Set<String> projectTags = new HashSet<String>();
		if (projectValue.contains("?")) {
			projectValue = projectValue.substring(projectValue.indexOf("?") + 1);
		}
		if (projectValue.contains("project")) {
			projectValue = projectValue.substring(projectValue.indexOf("project") + "project".length() + 1);
		}

		if (projectValue != null && !"".equals(projectValue)) {

			if (projectValue.contains(",")) {
				String[] split = projectValue.split(",");
				for (String string : split) {
					projectTags.add(string.trim());
				}
			} else {
				projectTags.add(projectValue);
			}

		}
		return projectTags;
	}

	private void parseProjectValues(String projectValue) {

		Set<String> projectTags = getProjectValues(projectValue);
		if (!projectTags.isEmpty()) {
			if (queryPanel != null && queryPanel.hasLoadedThisProjects(projectTags)) {
			} else {
				queryPanel = new QueryPanel(sessionID, projectTags);
			}
			History.newItem(TargetHistory.QUERY.getTargetHistory());
		} else {
			PopUpPanelRedirector popup = new PopUpPanelRedirector(true, true, true, "No projects selected",
					"You need to select one project before to query the data.\nClick here to go to Browse menu.",
					TargetHistory.BROWSE);
			popup.show();
		}

	}

	private void login() {
		showLoadingDialog("Entering into PINT. Please wait...");
		ProteinRetrievalServiceAsync service = ProteinRetrievalServiceAsync.Util.getInstance();
		String clientToken = ClientToken.getToken();

		String userName = "guest";
		String password = "guest";
		service.login(clientToken, userName, password, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String sessionID) {
				GWT.log("New session id:" + sessionID);
				Pint.this.sessionID = sessionID;
				loadGUI();
				hiddeLoadingDialog();
				startupConfiguration(false);
			}

			@Override
			public void onFailure(Throwable caught) {
				StatusReportersRegister.getInstance().notifyStatusReporters(caught);
				GWT.log("Error in login: " + caught.getMessage());
				hiddeLoadingDialog();
			}
		});

	}

	private void setUpHistory() {

		History.addValueChangeHandler(new ValueChangeHandler<String>() {

			private HelpPanel helpPanel;
			private AboutPanel aboutPanel;
			private ProjectCreatorWizard createProjectWizardPanel;

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {

				String historyToken = event.getValue();

				GWT.log("HISTORY VALUE: " + historyToken);
				// Parse the history token
				if (historyToken.contains(TargetHistory.QUERY.getTargetHistory())) {
					// queryPanel is suppose to be already created
					if (queryPanel == null || queryPanel.getLoadedProjects().isEmpty()) {
						PopUpPanelRedirector popup = new PopUpPanelRedirector(true, true, true, "No projects selected",
								"You need to select one project before to query the data.\nClick here to go to Browse menu.",
								TargetHistory.BROWSE);
						popup.show();
					} else {
						loadPanel(queryPanel);
					}
				}
				if (historyToken.contains(TargetHistory.BROWSE.getTargetHistory())) {
					if (browsePanel == null)
						browsePanel = new BrowsePanel();
					loadPanel(browsePanel);
				}
				if (historyToken.contains(TargetHistory.HOME.getTargetHistory())) {
					if (mainPanel == null)
						mainPanel = new MainPanel(Pint.this);
					loadPanel(mainPanel);
					mainPanel.loadStatistics();
				}
				if (historyToken.contains(TargetHistory.RELOAD.getTargetHistory())) {
					parseProjectValues(historyToken);

				}
				if (historyToken.equals(TargetHistory.SUBMIT.getTargetHistory())) {
					if (createProjectWizardPanel == null) {
						// not create again to not let the user loose everything
						createProjectWizardPanel = new ProjectCreatorWizard(sessionID);
					}
					loadPanel(createProjectWizardPanel);
					// if (createProjectPanel == null)
					// createProjectPanel = new ProjectCreator();
					// loadPanel(createProjectPanel);
				}
				if (historyToken.equals(TargetHistory.HELP.getTargetHistory())) {
					if (helpPanel == null) {
						helpPanel = new HelpPanel();
					}
					loadPanel(helpPanel);
				}
				if (historyToken.equals(TargetHistory.ABOUT.getTargetHistory())) {
					if (aboutPanel == null) {
						aboutPanel = new AboutPanel();
					}
					loadPanel(aboutPanel);
				}
				if (historyToken.startsWith(TargetHistory.LOAD_PROJECT.getTargetHistory())) {
					parseProjectValues(historyToken);

				}
				if (historyToken.contains(TargetHistory.PSEAQUANT.getTargetHistory())) {
					PSEAQuantFormPanel pseaQuant = new PSEAQuantFormPanel(getProjectValues(historyToken));
					loadPanel(pseaQuant);
				}
			}
		});

	}

	protected void loadPanel(InitializableComposite widget) {
		RootLayoutPanel.get().clear();

		if (widget instanceof QueryPanel) {
			RootLayoutPanel.get().add(widget);
		} else {
			// in any other case, add with a scrollpanel
			RootLayoutPanel.get().add(scroll);
			scroll.clear();
			scroll.add(widget);

		}
		if (widget != null) {
			widget.initialize();
		}
	}

}
