package edu.scripps.yates.client.gui.columns;

import java.util.List;

import org.reactome.web.analysis.client.model.PathwaySummary;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.Header;

import edu.scripps.yates.client.gui.reactome.ReactomePanel;
import edu.scripps.yates.shared.columns.ColumnName;
import edu.scripps.yates.shared.columns.ColumnWithVisibility;
import edu.scripps.yates.shared.columns.PathwaysColumns;
import edu.scripps.yates.shared.model.AmountType;

public class PathWaysColumnManager extends AbstractColumnManager<PathwaySummary> {
	private final String sessionID;

	public PathWaysColumnManager(String sessionID) {

		super(null);
		this.sessionID = sessionID;
		List<ColumnWithVisibility> columns = PathwaysColumns.getInstance().getColumns();
		for (ColumnWithVisibility columnWithVisibility : columns) {
			if (columnWithVisibility.getColumn() == ColumnName.PATHWAY_ID) {
				final CustomClickableImageReactomeColumn<PathwaySummary> customTextButtonColumn = new CustomClickableImageReactomeColumn<PathwaySummary>(
						sessionID, columnWithVisibility.getColumn(), columnWithVisibility.isVisible(), null);
				customTextButtonColumn.setFieldUpdater(getMyFieldUpdater());
				super.addColumn(customTextButtonColumn);
			} else {
				super.addColumn(createColumn(columnWithVisibility.getColumn(), columnWithVisibility.isVisible()));
			}
		}

	}

	public FieldUpdater<PathwaySummary, ImageResource> getMyFieldUpdater() {
		FieldUpdater<PathwaySummary, ImageResource> ret = new FieldUpdater<PathwaySummary, ImageResource>() {

			@Override
			public void update(int index, final PathwaySummary pathway, ImageResource image) {
				ReactomePanel.getInstance(sessionID).selectPathWay(pathway.getStId(), pathway.getDbId());
			}
		};
		return ret;
	}

	@Override
	protected MyColumn<PathwaySummary> createColumn(ColumnName columnName, boolean visible) {
		Header<String> footer = null;
		if (footerManager != null) {
			footer = footerManager.getFooter(columnName);
		}
		return new PathWayTextColumn(columnName, visible, footer);
	}

	@Override
	public CustomTextColumn<PathwaySummary> addScoreColumn(ColumnName columnName, boolean visibleState,
			String scoreName) {
		// not implemented
		return null;
	}

	@Override
	public CustomTextColumn<PathwaySummary> addAmountColumn(ColumnName columnName, boolean visibleState,
			String conditionName, AmountType amountType, String projectName) {
		// not implemented
		return null;
	}

	@Override
	public CustomTextColumn<PathwaySummary> addRatioColumn(ColumnName columnName, boolean visibleState,
			String condition1Name, String condition2Name, String projectTag, String ratioName) {
		// not implemented
		return null;
	}

	@Override
	public CustomTextColumn<PathwaySummary> addRatioScoreColumn(ColumnName columnName, boolean visibleState,
			String condition1Name, String condition2Name, String projectTag, String ratioName) {
		// not implemented
		return null;
	}

}