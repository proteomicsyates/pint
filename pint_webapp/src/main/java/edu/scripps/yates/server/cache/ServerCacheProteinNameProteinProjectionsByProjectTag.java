package edu.scripps.yates.server.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import edu.scripps.yates.shared.cache.Cache;
import edu.scripps.yates.shared.model.ProteinProjection;
import edu.scripps.yates.shared.util.SharedConstants;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;

public class ServerCacheProteinNameProteinProjectionsByProjectTag
		implements Cache<Map<String, Set<ProteinProjection>>, String> {
	private static final Map<String, Map<String, Set<ProteinProjection>>> map = new THashMap<String, Map<String, Set<ProteinProjection>>>();
	private static ServerCacheProteinNameProteinProjectionsByProjectTag instance;

	private ServerCacheProteinNameProteinProjectionsByProjectTag() {

	}

	public static ServerCacheProteinNameProteinProjectionsByProjectTag getInstance() {
		if (instance == null) {
			instance = new ServerCacheProteinNameProteinProjectionsByProjectTag();
		}
		return instance;
	}

	@Override
	public void addtoCache(Map<String, Set<ProteinProjection>> t, String key) {
		if (SharedConstants.SERVER_CACHE_ENABLED)
			map.put(key, t);

	}

	@Override
	public Map<String, Set<ProteinProjection>> getFromCache(String key) {
		return map.get(key);
	}

	@Override
	public void removeFromCache(String key) {
		map.remove(key);

	}

	@Override
	public boolean contains(String key) {

		return map.containsKey(key);
	}

	@Override
	public boolean containsAll(Collection<String> keys) {
		for (String key : keys) {
			if (!contains(key))
				return false;
		}
		return true;
	}

	@Override
	public Set<Map<String, Set<ProteinProjection>>> getFromCache(Collection<String> keys) {
		Set<Map<String, Set<ProteinProjection>>> ret = new THashSet<Map<String, Set<ProteinProjection>>>();
		for (String key : keys) {
			if (contains(key))
				ret.add(getFromCache(key));
		}
		return ret;
	}

	@Override
	public String processKey(String key) {

		return key;
	}

	@Override
	public void clearCache() {
		map.clear();
	}
}
