package edu.scripps.yates.proteindb.queries.semantic.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.scripps.yates.proteindb.persistence.mysql.Protein;
import edu.scripps.yates.proteindb.persistence.mysql.ProteinAccession;
import edu.scripps.yates.proteindb.persistence.mysql.Psm;
import edu.scripps.yates.proteindb.persistence.mysql.utils.PersistenceUtils;
import edu.scripps.yates.proteindb.queries.semantic.LinkBetweenQueriableProteinSetAndPSM;
import edu.scripps.yates.proteindb.queries.semantic.QueriableProteinSet;
import edu.scripps.yates.proteindb.queries.semantic.QueriablePsm;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;

public class QueriesUtil {
	public static final Logger log = Logger.getLogger(QueriesUtil.class);
	public static final boolean QUERY_CACHE_ENABLED = true;
	public static final int TEST_MODE_NUM_PSMS = 500;
	public static final int TEST_MODE_NUM_PROTEINS = 20;

	public static List<LinkBetweenQueriableProteinSetAndPSM> createProteinPSMLinks(
			Map<String, Set<Protein>> proteinMap) {
		log.info("Creating proteinPSMLinks from a proteinMap of size: " + proteinMap.size());
		List<LinkBetweenQueriableProteinSetAndPSM> ret = new ArrayList<LinkBetweenQueriableProteinSetAndPSM>();
		final Collection<Set<Protein>> proteinSets = proteinMap.values();
		int numProteins = 0;
		int numPSMs = 0;
		// clear links before create them
		for (Set<Protein> proteinSet : proteinSets) {
			QueriableProteinSet.getInstance(proteinSet, true).clearLinks();
			for (Protein protein : proteinSet) {
				numProteins++;
				final Set<Psm> psms = protein.getPsms();
				for (Psm psm : psms) {
					numPSMs++;
					QueriablePsm.getInstance(psm, true).clearLinks();
				}
			}
		}
		log.info("proteinPSMLinks cleared for  " + numProteins + " proteins and " + numPSMs + " PSMs");

		Map<String, Set<LinkBetweenQueriableProteinSetAndPSM>> linkMapByProteinAcc = new THashMap<String, Set<LinkBetweenQueriableProteinSetAndPSM>>();
		for (Set<Protein> proteinSet : proteinSets) {
			for (Protein protein : proteinSet) {
				final Set<Psm> psms = protein.getPsms();
				for (Psm psm : psms) {
					// if (psm.getProteins().isEmpty()) {
					// log.info("PSM without proteins: PSM id: " + psm.getId());
					// }
					final LinkBetweenQueriableProteinSetAndPSM proteinSet2PsmLink = new LinkBetweenQueriableProteinSetAndPSM(
							proteinSet, psm);
					ret.add(proteinSet2PsmLink);
					final String accession = proteinSet2PsmLink.getQueriableProtein().getPrimaryAccession();
					if (linkMapByProteinAcc.containsKey(accession)) {
						linkMapByProteinAcc.get(accession).add(proteinSet2PsmLink);
					} else {
						Set<LinkBetweenQueriableProteinSetAndPSM> linkSet = new THashSet<LinkBetweenQueriableProteinSetAndPSM>();
						linkSet.add(proteinSet2PsmLink);
						linkMapByProteinAcc.put(accession, linkSet);
					}
				}
			}
		}
		// iterate over the set of links for the same protein
		for (Set<LinkBetweenQueriableProteinSetAndPSM> linkSet : linkMapByProteinAcc.values()) {
			for (LinkBetweenQueriableProteinSetAndPSM proteinPSMLink : linkSet) {
				proteinPSMLink.setLinkSetForSameProtein(linkSet);
			}
		}
		log.info(ret.size() + " proteinPSMLinks created");
		return ret;
	}

	public static ProteinAccession getPrimaryProteinAccession(QueriableProteinSet queriableProteinInt) {
		ProteinAccession primaryAcc = null;
		final Set<ProteinAccession> proteinAccessions = queriableProteinInt.getProteinAccessions();
		for (ProteinAccession proteinAccession : proteinAccessions) {
			if (proteinAccession.isIsPrimary()) {
				if (primaryAcc == null) {
					primaryAcc = proteinAccession;
				} else {
					log.warn("Protein contains two primary accessions " + primaryAcc.getAccession() + " and "
							+ proteinAccession.getAccession());
					// as a temporary action, keep the primary acc first in
					// the alphabet, to be consistent
					if (primaryAcc.getAccession().compareTo(proteinAccession.getAccession()) > 0) {
						primaryAcc = proteinAccession;
					}
					log.info("Keeping " + primaryAcc.getAccession());
				}
			}
		}
		if (primaryAcc != null) {
			return primaryAcc;
		}
		List<ProteinAccession> list = new ArrayList<ProteinAccession>();
		list.addAll(proteinAccessions);
		Collections.sort(list, new Comparator<ProteinAccession>() {

			@Override
			public int compare(ProteinAccession o1, ProteinAccession o2) {
				String acc1 = "";
				String acc2 = "";
				if (o1 != null && o1.getAccession() != null) {
					acc1 = o1.getAccession();
				}
				if (o2 != null && o2.getAccession() != null) {
					acc2 = o2.getAccession();
				}
				return acc1.compareTo(acc2);
			}
		});
		if (!list.isEmpty()) {
			primaryAcc = list.get(0);
		}
		return primaryAcc;
	}

	public static Map<String, Set<Protein>> getProteinSubList(Map<String, Set<Protein>> proteinsByProjectCondition,
			int maxIndex) {
		Map<String, Set<Protein>> ret = new THashMap<String, Set<Protein>>();
		List<Protein> list = new ArrayList<Protein>();
		for (Set<Protein> proteinSet : proteinsByProjectCondition.values()) {
			list.addAll(proteinSet);
		}
		PersistenceUtils.addToMapByPrimaryAcc(ret,
				list.subList(0, Math.min(proteinsByProjectCondition.size(), maxIndex)));
		return ret;
	}

}
