package edu.scripps.yates.server.adapters;

import java.util.Set;

import edu.scripps.yates.proteindb.persistence.mysql.Condition;
import edu.scripps.yates.proteindb.persistence.mysql.MsRun;
import edu.scripps.yates.proteindb.persistence.mysql.Project;
import edu.scripps.yates.proteindb.persistence.mysql.adapter.Adapter;
import edu.scripps.yates.shared.model.ProjectBean;
import gnu.trove.map.hash.TIntObjectHashMap;

public class ProjectBeanAdapter implements Adapter<ProjectBean> {
	private final Project project;
	private static ThreadLocal<TIntObjectHashMap<ProjectBean>> map = new ThreadLocal<TIntObjectHashMap<ProjectBean>>();

	public ProjectBeanAdapter(Project project) {
		this.project = project;
		initializeMap();
	}

	private void initializeMap() {
		if (map.get() == null) {
			map.set(new TIntObjectHashMap<ProjectBean>());
		}
	}

	@Override
	public ProjectBean adapt() {
		if (map.get().containsKey(project.getId()))
			return map.get().get(project.getId());
		ProjectBean ret = new ProjectBean();
		map.get().put(project.getId(), ret);
		ret.setDescription(project.getDescription());
		ret.setName(project.getName());
		ret.setPubmedLink(project.getPubmedLink());
		ret.setReleaseDate(project.getReleaseDate());
		ret.setUploadedDate(project.getUploadedDate());
		ret.setIsHidden(project.isHidden());
		ret.setTag(project.getTag());
		ret.setDbId(project.getId());
		ret.setPublicAvailable(!project.isPrivate_());
		ret.setBig(project.isBig());
		final Set<Condition> conditions = project.getConditions();
		if (conditions != null) {
			for (Condition condition : conditions) {
				ret.getConditions().add(new ConditionBeanAdapter(condition).adapt());
			}

		}
		final Set<MsRun> msRuns = project.getMsRuns();
		if (msRuns != null) {
			for (MsRun msRun : msRuns) {
				ret.getMsRuns().add(new MSRunBeanAdapter(msRun).adapt());
			}

		}
		return ret;
	}

	public static void clearStaticMap() {
		if (map.get() != null) {
			map.get().clear();
		}
	}
}
