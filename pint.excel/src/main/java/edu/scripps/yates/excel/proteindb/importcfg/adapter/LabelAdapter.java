package edu.scripps.yates.excel.proteindb.importcfg.adapter;

import java.util.Map;

import edu.scripps.yates.excel.proteindb.importcfg.jaxb.LabelType;
import edu.scripps.yates.utilities.proteomicsmodel.Label;
import edu.scripps.yates.utilities.proteomicsmodel.factories.LabelEx;
import gnu.trove.map.hash.THashMap;

public class LabelAdapter implements edu.scripps.yates.utilities.pattern.Adapter<Label> {
	private static final Map<String, Label> map = new THashMap<String, Label>();
	private final LabelType labelCfg;

	public LabelAdapter(LabelType labelCfg) {
		this.labelCfg = labelCfg;
	}

	@Override
	public Label adapt() {
		if (map.containsKey(labelCfg.getId())) {
			return map.get(labelCfg.getId());
		}
		LabelEx ret = new LabelEx(labelCfg.getId());
		ret.setMassDiff(labelCfg.getMassDiff());
		map.put(labelCfg.getId(), ret);
		return ret;
	}

	protected static void clearStaticInformation() {
		map.clear();
	}
}
