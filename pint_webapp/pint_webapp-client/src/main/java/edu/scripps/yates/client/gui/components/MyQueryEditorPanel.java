package edu.scripps.yates.client.gui.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestBox.DefaultSuggestionDisplay;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBoxBase;

import edu.scripps.yates.Pint;
import edu.scripps.yates.client.gui.templates.MyClientBundle;
import edu.scripps.yates.shared.model.ProteinProjection;
import edu.scripps.yates.shared.util.sublists.QueryResultSubLists;

public class MyQueryEditorPanel extends FlowPanel {
	private final CommandSuggestion simpleQueryByProteinNameCommandSuggestion = new CommandSuggestion();
	private final CommandSuggestion simpleQueryByAccCommandSuggestion = new CommandSuggestion();
	private final CommandSuggestion simpleQueryByGeneNameCommandSuggestion = new CommandSuggestion();

	private final CommandSuggestion commandSuggestion = new CommandSuggestion();

	private final TextArea complexQueryTextBox;
	// private final SuggestBox suggestBox ;

	private final InlineHTML inlinelabelSendingStatus;
	private final Anchor linkToProteinResults;
	private final Anchor linkToProteinGroupResults;
	private final ListBox uniprotVersionListBox;
	private final FlowPanel flowPanelLinks;
	private final Label lblProteins;
	private final Label lblProteinGroups;
	private static final String defaultInlineSendingStatus = "Write a query and click on 'Send query'";
	private final Label lblDownloadDataHere;
	private final FlexTable resultSummaryGrid;
	private final Label lblNumberOfProteinGroups;
	private final Label numProteinGroupsLabel;
	private final Label lblNumberOfProteins;
	private final Label numProteinsLabel;
	private Label lblNumberOfPsms;
	private Label numPSMsLabel;
	private final Label lblNumberOfDifferent;
	private final Label numDifferentSequencesLabel;
	private final FlowPanel flowPanel_1;
	private final FlexTable flexTable;
	private final Label lblNumberOfFeatures;

	private final SuggestBox simpleQueryByProteinNameTextBox;
	private final SuggestBox simpleQueryByAccTextBox;
	private final SuggestBox simpleQueryByGeneNameTextBox;

	private final Label queryDisabledLabel;

	private final Button buttonSendQuery;
	private final Map<String, Set<ProteinProjection>> proteinProjectionsByAcc = new HashMap<String, Set<ProteinProjection>>();
	private final Map<String, Set<ProteinProjection>> proteinProjectionsByProteinName = new HashMap<String, Set<ProteinProjection>>();
	private final Map<String, Set<ProteinProjection>> proteinProjectionsByGeneName = new HashMap<String, Set<ProteinProjection>>();
	private final Image simpleQueryByProteinNameReadyState;
	private final Button sendSimpleQueryByProteinNameButton;
	private final Image simpleQueryByAccReadyState;
	private final Button sendSimpleQueryByAccButton;
	private final Image simpleQueryByGeneNameReadyState;
	private final Button sendSimpleQueryByGeneNameButton;

	public MyQueryEditorPanel(ClickHandler sendQueryclickHandler, ClickHandler sendSimpleQueryByProteinNameClickHandler,
			ClickHandler sendSimpleQueryByAccClickHandler, ClickHandler sendSimpleQueryByGeneNameClickHandler) {
		setStyleName("QueryPanel");
		setWidth("100%");
		// tabLayoutPanel.add(this, "Query", false);

		final InlineHTML nlnhtmlWriteYourQuery = new InlineHTML("Write your queries in the boxes below:");
		this.add(nlnhtmlWriteYourQuery);

		final CaptionPanel captionPanelSimpleQueryEditor = new CaptionPanel("Simple Query Editor");
		captionPanelSimpleQueryEditor.setStyleName("QueryPanel-horizontal");
		this.add(captionPanelSimpleQueryEditor);
		captionPanelSimpleQueryEditor.setSize("90%", "30%");
		final FlowPanel superFlow = new FlowPanel();
		superFlow.setStyleName("verticalComponent");

		// search by protein name
		final FlowPanel flow1 = new FlowPanel();
		flow1.setStyleName("horizontalComponent");
		final Label label = new Label("Type here to search for protein names: ");
		label.getElement().getStyle().setProperty("margin", "10px");
		label.setStyleName("horizontalComponent");
		flow1.add(label);
		simpleQueryByProteinNameTextBox = new SuggestBox(simpleQueryByProteinNameCommandSuggestion);
		simpleQueryByProteinNameTextBox.setEnabled(false);
		simpleQueryByProteinNameTextBox.setLimit(50);
		simpleQueryByProteinNameTextBox.setStyleName("horizontalComponent");
		((DefaultSuggestionDisplay) simpleQueryByProteinNameTextBox.getSuggestionDisplay()).setAnimationEnabled(true);
		simpleQueryByProteinNameTextBox.getElement().getStyle().setProperty("margin", "10px");
		flow1.add(simpleQueryByProteinNameTextBox);
		simpleQueryByProteinNameReadyState = new Image(MyClientBundle.INSTANCE.smallLoader());
		simpleQueryByProteinNameReadyState.setTitle("Initializing queries by protein name...");
		flow1.add(simpleQueryByProteinNameReadyState);
		sendSimpleQueryByProteinNameButton = new Button("Go");
		sendSimpleQueryByProteinNameButton.setTitle("Search by protein name");
		sendSimpleQueryByProteinNameButton.setEnabled(false);
		sendSimpleQueryByProteinNameButton.addClickHandler(sendSimpleQueryByProteinNameClickHandler);
		sendSimpleQueryByProteinNameButton.getElement().getStyle().setProperty("margin", "10px");
		flow1.add(sendSimpleQueryByProteinNameButton);
		superFlow.add(flow1);

		// search by protein name
		final FlowPanel flow2 = new FlowPanel();
		flow2.setStyleName("horizontalComponent");
		final Label label2 = new Label("Type here to search for protein accessions: ");
		label2.getElement().getStyle().setProperty("margin", "10px");
		label2.setStyleName("horizontalComponent");
		flow2.add(label2);
		simpleQueryByAccTextBox = new SuggestBox(simpleQueryByAccCommandSuggestion);
		simpleQueryByAccTextBox.setEnabled(false);
		simpleQueryByAccTextBox.setLimit(50);
		simpleQueryByAccTextBox.setStyleName("horizontalComponent");
		((DefaultSuggestionDisplay) simpleQueryByAccTextBox.getSuggestionDisplay()).setAnimationEnabled(true);
		simpleQueryByAccTextBox.getElement().getStyle().setProperty("margin", "10px");
		flow2.add(simpleQueryByAccTextBox);
		simpleQueryByAccReadyState = new Image(MyClientBundle.INSTANCE.smallLoader());
		simpleQueryByAccReadyState.setTitle("Initializing queries by protein accession...");
		flow2.add(simpleQueryByAccReadyState);
		sendSimpleQueryByAccButton = new Button("Go");
		sendSimpleQueryByAccButton.setTitle("Search by protein accession");
		sendSimpleQueryByAccButton.setEnabled(false);
		sendSimpleQueryByAccButton.addClickHandler(sendSimpleQueryByAccClickHandler);
		sendSimpleQueryByAccButton.getElement().getStyle().setProperty("margin", "10px");
		flow2.add(sendSimpleQueryByAccButton);
		superFlow.add(flow2);

		// search by protein name
		final FlowPanel flow3 = new FlowPanel();
		flow3.setStyleName("horizontalComponent");
		final Label label3 = new Label("Type here to search for gene names: ");
		label3.getElement().getStyle().setProperty("margin", "10px");
		label3.setStyleName("horizontalComponent");
		flow3.add(label3);
		simpleQueryByGeneNameTextBox = new SuggestBox(simpleQueryByGeneNameCommandSuggestion);
		simpleQueryByGeneNameTextBox.setEnabled(false);
		simpleQueryByGeneNameTextBox.setLimit(50);
		simpleQueryByGeneNameTextBox.setStyleName("horizontalComponent");
		((DefaultSuggestionDisplay) simpleQueryByGeneNameTextBox.getSuggestionDisplay()).setAnimationEnabled(true);
		simpleQueryByGeneNameTextBox.getElement().getStyle().setProperty("margin", "10px");
		flow3.add(simpleQueryByGeneNameTextBox);
		simpleQueryByGeneNameReadyState = new Image(MyClientBundle.INSTANCE.smallLoader());
		simpleQueryByGeneNameReadyState.setTitle("Initializing queries by gene name...");
		flow3.add(simpleQueryByGeneNameReadyState);
		sendSimpleQueryByGeneNameButton = new Button("Go");
		sendSimpleQueryByGeneNameButton.setTitle("Search by gene name");
		sendSimpleQueryByGeneNameButton.setEnabled(false);
		sendSimpleQueryByGeneNameButton.addClickHandler(sendSimpleQueryByGeneNameClickHandler);
		sendSimpleQueryByGeneNameButton.getElement().getStyle().setProperty("margin", "10px");
		flow3.add(sendSimpleQueryByGeneNameButton);
		superFlow.add(flow3);
		captionPanelSimpleQueryEditor.setContentWidget(superFlow);

		final CaptionPanel captionPanelQueryEditor = new CaptionPanel("Advanced Query Editor");
		captionPanelQueryEditor.setStyleName("QueryPanel-horizontal");
		this.add(captionPanelQueryEditor);
		captionPanelQueryEditor.setSize("90%", "30%");

		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("verticalComponent");
		queryDisabledLabel = new Label(
				"Regular queries are disabled for this project. Try inspecting single proteins in the text editor above");
		queryDisabledLabel.setVisible(false);
		flowPanel.add(queryDisabledLabel);
		// suggestBox = new SuggestBox(commandSuggestion,
		// new com.google.gwt.user.client.ui.TextArea());
		complexQueryTextBox = new TextArea();
		complexQueryTextBox.setHeight("100%");
		complexQueryTextBox.setWidth("100%");
		flowPanel.add(complexQueryTextBox);

		captionPanelQueryEditor.setContentWidget(flowPanel);
		getComplexQueryTextBox().setSize("400px", "132px");

		// uniprot versions
		final FlowPanel flow5 = new FlowPanel();
		flow5.setStyleName("QueryPanel-vertical");
		final InlineHTML inLineHtml = new InlineHTML("Available Uniprot Annotations for the selected projects:");
		flow5.add(inLineHtml);
		uniprotVersionListBox = new ListBox();
		uniprotVersionListBox.setMultipleSelect(false);
		flow5.add(uniprotVersionListBox);
		this.add(flow5);

		flowPanel = new FlowPanel();
		flowPanel.setStyleName("QueryPanel-vertical");
		add(flowPanel);

		buttonSendQuery = new Button("Send query");
		buttonSendQuery.addClickHandler(sendQueryclickHandler);

		inlinelabelSendingStatus = new InlineHTML(defaultInlineSendingStatus);

		inlinelabelSendingStatus.setWidth("354px");

		final Grid buttonAndStatusGrid = new Grid(1, 2);
		buttonAndStatusGrid.setCellSpacing(5);
		flowPanel.add(buttonAndStatusGrid);
		buttonAndStatusGrid.setWidget(0, 0, inlinelabelSendingStatus);
		buttonAndStatusGrid.setWidget(0, 1, buttonSendQuery);

		flowPanel_1 = new FlowPanel();
		flowPanel_1.setStyleName("QueryPanel-vertical");
		add(flowPanel_1);

		flexTable = new FlexTable();
		flowPanel_1.add(flexTable);

		final Label lblForHelpAbout = new Label(
				"Use the pulldown menus on the left for building your queries. For more complex queries download guide here:");
		lblForHelpAbout.setWordWrap(false);
		flexTable.setWidget(0, 0, lblForHelpAbout);

		final Anchor lblLink = new Anchor(true);
		lblLink.setHref("PINT_help_Query_Commands.pdf");
		lblLink.setTarget("_blank");
		lblLink.setText("(here)");
		lblLink.setStyleName("linkPINT");
		flexTable.setWidget(0, 1, lblLink);

		flowPanelLinks = new FlowPanel();
		flowPanelLinks.setStyleName("QueryPanel-vertical");
		add(flowPanelLinks);
		flowPanelLinks.setSize("100%", "142px");

		resultSummaryGrid = new FlexTable();
		resultSummaryGrid.setStyleName("QueryPanel-horizontal");
		resultSummaryGrid.getElement().getStyle().setMargin(0, Unit.PX);
		resultSummaryGrid.getElement().getStyle().setPadding(0, Unit.PX);
		flowPanelLinks.add(resultSummaryGrid);
		resultSummaryGrid.setWidth("");

		lblNumberOfFeatures = new Label("Number of features in current dataset:");
		lblNumberOfFeatures.setStyleName("PSEAQuantPanel-Title-Label");
		resultSummaryGrid.setWidget(0, 0, lblNumberOfFeatures);

		lblNumberOfProteinGroups = new Label("Number of protein groups:");
		resultSummaryGrid.setWidget(1, 0, lblNumberOfProteinGroups);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_MIDDLE);

		numProteinGroupsLabel = new Label("-");
		resultSummaryGrid.setWidget(1, 1, numProteinGroupsLabel);
		resultSummaryGrid.getCellFormatter().setWidth(4, 1, "");

		lblNumberOfProteins = new Label("Number of proteins:");
		resultSummaryGrid.setWidget(2, 0, lblNumberOfProteins);

		numProteinsLabel = new Label("-");
		resultSummaryGrid.setWidget(2, 1, numProteinsLabel);

		if (Pint.getPSMCentric()) {
			lblNumberOfPsms = new Label("Number of PSMs:");
			resultSummaryGrid.setWidget(3, 0, lblNumberOfPsms);

			numPSMsLabel = new Label("-");
			resultSummaryGrid.setWidget(3, 1, numPSMsLabel);
		}
		lblNumberOfDifferent = new Label("Number of peptides:");
		resultSummaryGrid.setWidget(4, 0, lblNumberOfDifferent);

		numDifferentSequencesLabel = new Label("-");
		resultSummaryGrid.setWidget(4, 1, numDifferentSequencesLabel);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_LEFT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(4, 1, HasVerticalAlignment.ALIGN_MIDDLE);

		lblDownloadDataHere = new Label("Download data here:");
		lblDownloadDataHere.setStyleName("PSEAQuantPanel-Title-Label");
		resultSummaryGrid.setWidget(0, 2, lblDownloadDataHere);

		lblProteins = new Label("Proteins:");
		resultSummaryGrid.setWidget(1, 2, lblProteins);

		lblProteinGroups = new Label("Protein Groups:");
		resultSummaryGrid.setWidget(2, 2, lblProteinGroups);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_LEFT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_LEFT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(1, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(2, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(2, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(3, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(3, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getFlexCellFormatter().setColSpan(1, 2, 1);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getFlexCellFormatter().setColSpan(0, 0, 2);

		linkToProteinResults = new Anchor(true);
		linkToProteinResults.setStyleName("linkPINT");
		resultSummaryGrid.setWidget(1, 3, linkToProteinResults);

		linkToProteinGroupResults = new Anchor(true);
		linkToProteinGroupResults.setStyleName("linkPINT");
		resultSummaryGrid.setWidget(2, 3, linkToProteinGroupResults);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(1, 3, HasHorizontalAlignment.ALIGN_LEFT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(1, 3, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(2, 3, HasHorizontalAlignment.ALIGN_LEFT);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(2, 3, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		resultSummaryGrid.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);
		resultSummaryGrid.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		resultSummaryGrid.getFlexCellFormatter().setColSpan(0, 2, 2);

	}

	public void enableQueries(boolean enable) {
		queryDisabledLabel.setVisible(!enable);
		complexQueryTextBox.setVisible(enable);
		buttonSendQuery.setEnabled(enable);
	}

	/**
	 * @return the inlinelabelSendingStatus
	 */
	public void setSendingStatusText(String text) {
		if (text != null)
			inlinelabelSendingStatus.setText(text);
		else
			inlinelabelSendingStatus.setText(defaultInlineSendingStatus);
	}

	/**
	 * @return the textAreaQuery
	 */
	public TextBoxBase getComplexQueryTextBox() {
		return complexQueryTextBox;
	}

	public void addSuggestionsToComplexQuery(Collection<String> text) {
		commandSuggestion.addAll(text);
	}

	public void addSuggestionToComplexQuery(String text) {
		commandSuggestion.add(text);
	}

	// private void addSimpleQuerySuggestions(Collection<String> texts) {
	// for (String text : texts) {
	// addSimpleQueryByProteinNameSuggestion(text);
	// }
	// }

	private void addSimpleQueryByProteinNameSuggestion(String text) {
		if (text != null) {
			simpleQueryByProteinNameCommandSuggestion.add(text);
		}
	}

	private void addSimpleQueryByGeneNameSuggestion(String text) {
		if (text != null) {
			simpleQueryByGeneNameCommandSuggestion.add(text);
		}
	}

	private void addSimpleQueryByAccSuggestion(String text) {
		if (text != null) {
			simpleQueryByAccCommandSuggestion.add(text);
		}
	}

	public void addSimpleQueryByProteinNameSuggestionsAsProteinProjections(
			Map<String, Set<ProteinProjection>> proteinProjections) {
		for (final String proteinName : proteinProjections.keySet()) {
			addSimpleQueryByProteinNameSuggestion(proteinName);
			for (final ProteinProjection p : proteinProjections.get(proteinName)) {
				addProteinProjectionByProteinName(p);
			}
		}
	}

	public void addSimpleQueryByAccSuggestionsAsProteinProjections(
			Map<String, Set<ProteinProjection>> proteinProjections) {
		for (final String acc : proteinProjections.keySet()) {
			addSimpleQueryByAccSuggestion(acc);
			for (final ProteinProjection p : proteinProjections.get(acc)) {
				addProteinProjectionByAcc(p);
			}
		}
	}

	public void addSimpleQueryByGeneNameSuggestionsAsProteinProjections(
			Map<String, Set<ProteinProjection>> proteinProjections) {
		for (final String geneName : proteinProjections.keySet()) {
			addSimpleQueryByGeneNameSuggestion(geneName);
			for (final ProteinProjection p : proteinProjections.get(geneName)) {
				addProteinProjectionByGeneName(p);
			}
		}
	}

	public void addUniprotVersion(String item, String value) {
		uniprotVersionListBox.addItem(item, value);
	}

	public void addUniprotVersion(String item) {
		uniprotVersionListBox.addItem(item);
		uniprotVersionListBox.setSelectedIndex(0);
	}

	public void clearUniprotVersionList() {
		uniprotVersionListBox.clear();

	}

	public String getSelectedUniprotVersion() {
		final int selectedIndex = uniprotVersionListBox.getSelectedIndex();
		if (selectedIndex > -1)
			return uniprotVersionListBox.getValue(selectedIndex);
		return "";
	}

	public void setLinksToResultsVisible(boolean visible) {
		linkToProteinGroupResults.setVisible(visible);
		linkToProteinResults.setVisible(visible);
	}

	/**
	 * Update the labels with the numbers of proteins/peptides/etc obtained
	 * 
	 * @param queryResult
	 */
	public void updateQueryResultSummary(QueryResultSubLists queryResult) {
		if (queryResult != null) {
			numProteinGroupsLabel.setText(String.valueOf(queryResult.getNumTotalProteinGroups()));
			numProteinsLabel.setText(String.valueOf(queryResult.getNumTotalProteins()));
			if (Pint.getPSMCentric()) {
				numPSMsLabel.setText(String.valueOf(queryResult.getNumTotalPSMs()));
			}
			final StringBuilder numdiffSequencesText = new StringBuilder();
			numdiffSequencesText.append(queryResult.getNumDifferentSequences());
			if (queryResult.getNumDifferentSequences() != queryResult
					.getNumDifferentSequencesDistinguishingModifieds()) {
				numdiffSequencesText.append(" (" + queryResult.getNumDifferentSequencesDistinguishingModifieds() + ")");
			}
			numDifferentSequencesLabel.setText(numdiffSequencesText.toString());
			numDifferentSequencesLabel.setTitle(
					"Between parenthesis is the number of " + "different peptide sequences by considering different "
							+ "sequences differentially modified peptides");
		} else {
			numProteinGroupsLabel.setText("-");
			numProteinsLabel.setText("-");
			if (Pint.getPSMCentric()) {
				numPSMsLabel.setText("-");
			}
			numDifferentSequencesLabel.setText("-");
		}
	}

	public void setLinksToProteinResults(String href, String text) {

		linkToProteinResults.setVisible(true);
		linkToProteinResults.setHref(href);
		linkToProteinResults.setTarget("_blank");
		linkToProteinResults.setText(text);

	}

	public void setLinksToProteinGroupResults(String href, String text) {

		linkToProteinGroupResults.setVisible(true);
		linkToProteinGroupResults.setHref(href);
		linkToProteinGroupResults.setTarget("_blank");
		linkToProteinGroupResults.setText(text);

	}

	/**
	 * @return the simpleQueryTextBox
	 */
	public SuggestBox getSimpleQueryTextBox() {
		return simpleQueryByProteinNameTextBox;
	}

	private void addProteinProjectionByAcc(ProteinProjection p) {
		addToMap(proteinProjectionsByAcc, p, p.getAcc());
	}

	private void addProteinProjectionByProteinName(ProteinProjection p) {
		addToMap(proteinProjectionsByProteinName, p, p.getDescription());
	}

	private void addProteinProjectionByGeneName(ProteinProjection p) {
		addToMap(proteinProjectionsByGeneName, p, p.getGene());
	}

	private void addToMap(Map<String, Set<ProteinProjection>> map, ProteinProjection p, String key) {
		if (map.containsKey(key)) {
			map.get(key).add(p);
		} else {
			final Set<ProteinProjection> set = new HashSet<ProteinProjection>();
			set.add(p);
			map.put(key, set);
		}

	}

	private Set<ProteinProjection> getProteinProjectionsByAcc(String key) {
		final Set<ProteinProjection> set = new HashSet<ProteinProjection>();

		if (proteinProjectionsByAcc.containsKey(key)) {
			set.addAll(proteinProjectionsByAcc.get(key));
		}

		return set;
	}

	private Set<ProteinProjection> getProteinProjectionsByProteinName(String key) {
		final Set<ProteinProjection> set = new HashSet<ProteinProjection>();
		if (proteinProjectionsByProteinName.containsKey(key)) {
			set.addAll(proteinProjectionsByProteinName.get(key));
		}
		return set;
	}

	private Set<ProteinProjection> getProteinProjectionsByGeneName(String key) {
		final Set<ProteinProjection> set = new HashSet<ProteinProjection>();
		if (proteinProjectionsByGeneName.containsKey(key)) {
			set.addAll(proteinProjectionsByGeneName.get(key));
		}

		return set;
	}

	public String getTranslatedQueryFromProteinName() {
		final String queryText = simpleQueryByProteinNameTextBox.getText();
		final Set<ProteinProjection> proteinProjectionsByKey = getProteinProjectionsByProteinName(queryText);
		return getQueryFromProteinProjections(proteinProjectionsByKey);
	}

	public String getTranslatedQueryFromAcc() {
		final String queryText = simpleQueryByAccTextBox.getText();
		final Set<ProteinProjection> proteinProjectionsByKey = getProteinProjectionsByAcc(queryText);
		return getQueryFromProteinProjections(proteinProjectionsByKey);
	}

	public String getTranslatedQueryFromGeneName() {
		final String queryText = simpleQueryByGeneNameTextBox.getText();
		final Set<ProteinProjection> proteinProjectionsByKey = getProteinProjectionsByGeneName(queryText);
		return getQueryFromProteinProjections(proteinProjectionsByKey);
	}

	private String getQueryFromProteinProjections(Set<ProteinProjection> proteinProjections) {
		if (proteinProjections == null || proteinProjections.isEmpty()) {
			return null;
		}
		final Set<String> accs = new HashSet<String>();
		// get all the accs
		for (final ProteinProjection p : proteinProjections) {
			accs.add(p.getAcc());
		}
		// build the query
		final StringBuilder sb = new StringBuilder();
		sb.append("ACC[");
		int i = 0;
		for (final String acc : accs) {
			i++;
			sb.append(acc);
			if (i != accs.size()) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public void enableSimpleQueriesByProteinName(boolean enable) {
		simpleQueryByProteinNameTextBox.setEnabled(enable);
		sendSimpleQueryByProteinNameButton.setEnabled(true);
		simpleQueryByProteinNameReadyState.setVisible(false);
	}

	public void enableSimpleQueriesByAcc(boolean enable) {
		simpleQueryByAccTextBox.setEnabled(enable);
		sendSimpleQueryByAccButton.setEnabled(true);
		simpleQueryByAccReadyState.setVisible(false);
	}

	public void enableSimpleQueriesByGeneName(boolean enable) {
		simpleQueryByGeneNameTextBox.setEnabled(enable);
		sendSimpleQueryByGeneNameButton.setEnabled(true);
		simpleQueryByGeneNameReadyState.setVisible(false);
	}
}
