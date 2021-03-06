package edu.scripps.yates.server.projectCreator.adapter.excel2bean;

import edu.scripps.yates.excel.proteindb.importcfg.jaxb.AmountType;
import edu.scripps.yates.proteindb.persistence.mysql.adapter.Adapter;
import edu.scripps.yates.shared.model.projectCreator.excel.AmountCombinationTypeBean;
import edu.scripps.yates.shared.model.projectCreator.excel.AmountTypeBean;

public class AmountTypeBeanAdapter implements Adapter<AmountTypeBean> {
	private final AmountType amountType;
	private final String hibAmountType;

	public AmountTypeBeanAdapter(AmountType amountType) {
		this.amountType = amountType;
		hibAmountType = null;
	}

	public AmountTypeBeanAdapter(String amountType) {
		this.amountType = null;
		hibAmountType = amountType;
	}

	@Override
	public AmountTypeBean adapt() {
		final AmountTypeBean ret = new AmountTypeBean();

		if (amountType != null) {
			ret.setColumnRef(amountType.getColumnRef());
			if (amountType.getCombinationType() != null) {
				ret.setCombinationType(AmountCombinationTypeBean.fromValue(amountType.getCombinationType().name()));
			}
			if (amountType.getType() != null) {
				ret.setType(edu.scripps.yates.shared.model.AmountType.fromValue(amountType.getType().name()));
			}
		} else if (hibAmountType != null) {

			ret.setType(edu.scripps.yates.shared.model.AmountType.fromValue(hibAmountType));

		}
		return ret;
	}

}
