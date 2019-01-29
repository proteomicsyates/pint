package edu.scripps.yates.client.ui.wizard.pages.inputfiles;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import edu.scripps.yates.client.pint.wizard.PintContext;
import edu.scripps.yates.client.pint.wizard.PintImportCfgUtil;
import edu.scripps.yates.client.ui.wizard.Wizard.ButtonType;
import edu.scripps.yates.client.ui.wizard.form.RatioSelectorForFileForm;
import edu.scripps.yates.client.ui.wizard.pages.AbstractWizardPage;
import edu.scripps.yates.client.ui.wizard.pages.PageIDController;
import edu.scripps.yates.client.ui.wizard.pages.PageTitleController;
import edu.scripps.yates.client.ui.wizard.pages.WizardPageConditions;
import edu.scripps.yates.client.ui.wizard.pages.panels.WizardExtractIdentificationDataFromDTASelectPanel;
import edu.scripps.yates.client.ui.wizard.pages.panels.WizardFormPanel;
import edu.scripps.yates.client.ui.wizard.pages.panels.WizardQuestionPanel;
import edu.scripps.yates.client.ui.wizard.styles.WizardStyles;
import edu.scripps.yates.shared.model.FileFormat;
import edu.scripps.yates.shared.model.projectCreator.excel.FileTypeBean;

public class WizardPageCensusOutFileProcessor extends AbstractWizardPage {

	private FlexTable panel;
	private final FileTypeBean file;
	private final String text1;
	private String text2;
	private String text3;

	private int row;
	private ClickHandler noClickHandler;
	private ClickHandler yesClickHandler;
	protected boolean extractQuantData;
	private FlexTable questionPanel;
	private boolean isReadyForNextStep = false;

	public WizardPageCensusOutFileProcessor(PintContext context, int fileNumber, FileTypeBean file) {
		super(fileNumber + " " + file.getName());
		this.file = file;

		text1 = "Processing input file " + fileNumber + "/"
				+ PintImportCfgUtil.getFiles(context.getPintImportConfiguration()).size() + " '" + file.getName() + "'";

	}

	@Override
	protected Widget createPage() {
		final SimplePanel ret = new SimplePanel();
		panel = new FlexTable();
		ret.add(panel);
		panel.setStyleName(WizardStyles.wizardRegularPage);
		//
		row = 0;
		final Label welcomeLabel1 = new Label(text1);
		welcomeLabel1.setStyleName(WizardStyles.WizardWelcomeLabel2);
		panel.setWidget(row, 0, welcomeLabel1);
		//
		if (text2 != null) {
			row++;
			final Label welcomeLabel2 = new Label(text2);
			welcomeLabel2.setStyleName(WizardStyles.WizardExplanationLabel);
			panel.setWidget(row, 0, welcomeLabel2);
		}
		//
		if (text3 != null) {
			row++;
			final Label welcomeLabel3 = new Label(text3);
			welcomeLabel3.setStyleName(WizardStyles.WizardExplanationLabel);
			panel.setWidget(row, 0, welcomeLabel3);
		}
		return ret;
	}

	@Override
	public PageID getPageID() {
		return PageIDController.getPageIDForInputFileProcessor(getTitle());
	}

	@Override
	protected void registerPageTitle(String title) {
		PageTitleController.addPageTitle(this.getPageID(), title);
	}

	@Override
	public void beforeShow() {
		checkFile();

		panel.setWidget(row, 0, questionPanel);
		// disable next button
		wizard.setButtonEnabled(ButtonType.BUTTON_NEXT, false);
		wizard.setButtonOverride(true);
		//
		super.beforeShow();
	}

	private void checkFile() {
		final int numConditions = PintImportCfgUtil.getConditions(getPintImportConfg()).size();
		if (numConditions < 2) {
			String explanation = "Census out files contain at least quantitative information from 2 experimental conditions, "
					+ "having relative abundance ratios of peptides and proteins between 2 conditions.";
			if (numConditions == 1) {
				explanation += "\nHowever, we detected only ONE experimental condition: '"
						+ PintImportCfgUtil.getConditions(getPintImportConfg()).get(0).getId() + "'.";
			} else if (numConditions == 0) {
				explanation += "\nHowever, we didn't detected any experimental condition defined yet.";
			}
			final String question = "Click here to go to the " + PageTitleController.getPageTitleByPageID(
					PageIDController.getPageIDByPageClass(WizardPageConditions.class)) + " wizard page.";
			questionPanel = new WizardQuestionPanel(question, explanation, true);
			((WizardQuestionPanel) questionPanel).addOKClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					// go to conditions
					getWizard().showPage(PageIDController.getPageIDByPageClass(WizardPageConditions.class), false, true,
							true);
				}
			});

		} else if (numConditions == 2) {
			final String explanation = "Census out files contain relative abundance ratios of peptides and proteins between 2 conditions.";
			final String question = "Which condition is the one in the numerator and which one is in the denominator of the relative abundance ratios contained in this input file?";

		} else if (numConditions > 2) {
			final String explanation = "Census out files contain relative abundance ratios of peptides and proteins between 2 conditions. However, we detected "
					+ numConditions + " defined in this wizard.";
			final String question = "Which condition is the one in the numerator and which one is in the denominator of the relative abundance ratios contained in this input file?";
			questionPanel = new WizardQuestionPanel(question, explanation, true);
			((WizardQuestionPanel) questionPanel).addOKClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					final String question = "Numerator experimental condition:";
					final String explanation = "Experimental condition (and Sample) that is in the numerator of the ratios of the file '"
							+ file.getId() + "'.";
					final WizardFormPanel panel = new WizardFormPanel(
							new RatioSelectorForFileForm(getContext(), question, explanation), getWizard());
					panel.setWidget(row, 0, panel);
				}
			});

		}

		yesClickHandler = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				extractQuantData = true;
				showExtractQuantificationData();
				isReadyForNextStep = true;
				updateNextButtonState();
			}
		};
		noClickHandler = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				extractQuantData = false;
				showExtractIdentificationData(false);
				isReadyForNextStep = true;
				updateNextButtonState();
			}
		};

	}

	@Override
	public void beforeFirstShow() {
		if (noClickHandler != null && yesClickHandler != null) {
			row++;
			questionPanel = new WizardQuestionPanel(question, explanation);
			((WizardQuestionPanel) questionPanel).addNoClickHandler(noClickHandler);
			((WizardQuestionPanel) questionPanel).addYesClickHandler(yesClickHandler);

		}
		super.beforeFirstShow();
	}

	protected void showExtractIdentificationData(boolean createNSAFQuantValues) {
		if (file.getFormat() == FileFormat.DTA_SELECT_FILTER_TXT) {
			final WizardExtractIdentificationDataFromDTASelectPanel extractIDPanel = new WizardExtractIdentificationDataFromDTASelectPanel(
					wizard.getContext(), file, createNSAFQuantValues);
			panel.setWidget(row, 0, extractIDPanel);
		} else {
			// TODO
		}

	}

	protected void showExtractQuantificationData() {
		// TODO Auto-generated method stub

	}

	protected void showFastaDefinition() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WizardPageCensusOutFileProcessor) {
			final WizardPageCensusOutFileProcessor page2 = (WizardPageCensusOutFileProcessor) obj;
			if (page2.getPageID().equals(getPageID())) {
				return true;
			}
			return false;
		}
		return super.equals(obj);
	}

	protected void updateNextButtonState() {
		final boolean readyForNextStep = isReadyForNextStep();
		this.wizard.setButtonEnabled(ButtonType.BUTTON_NEXT, readyForNextStep);
	}

	private boolean isReadyForNextStep() {
		return isReadyForNextStep;
	}
}
