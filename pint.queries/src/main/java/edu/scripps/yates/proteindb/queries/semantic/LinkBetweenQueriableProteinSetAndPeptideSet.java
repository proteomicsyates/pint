package edu.scripps.yates.proteindb.queries.semantic;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.scripps.yates.proteindb.persistence.mysql.Peptide;
import edu.scripps.yates.proteindb.persistence.mysql.Protein;

public class LinkBetweenQueriableProteinSetAndPeptideSet {
	private static final Logger log = Logger.getLogger(LinkBetweenQueriableProteinSetAndPeptideSet.class);
	private final QueriableProteinSet queriableProteinSet;
	private final QueriablePeptideSet queriablePeptide;
	// private final Map<Boolean, Set<QueriableProtein2PSMLink>>
	// evaluatedProtein2PSMLinks = new THashMap<Boolean,
	// Set<QueriableProtein2PSMLink>>();
	private Set<LinkBetweenQueriableProteinSetAndPeptideSet> linkSetForSameProtein;

	public LinkBetweenQueriableProteinSetAndPeptideSet(Collection<Protein> proteins, Collection<Peptide> peptides) {
		this(QueriableProteinSet.getInstance(proteins, false), QueriablePeptideSet.getInstance(peptides, false));
	}

	public LinkBetweenQueriableProteinSetAndPeptideSet(QueriableProteinSet queriableProteinSet,
			QueriablePeptideSet queriablePeptideSet) {
		this.queriableProteinSet = queriableProteinSet;
		queriablePeptide = queriablePeptideSet;

		this.queriableProteinSet.addLinkToPeptide(this);
		queriablePeptide.addLink(this);

	}

	/**
	 * @return the protein
	 */

	public QueriableProteinSet getQueriableProtein() {
		return queriableProteinSet;
	}

	/**
	 * @return the psm
	 */

	public QueriablePeptideSet getQueriablePeptide() {
		return queriablePeptide;
	}

	// /**
	// * Gets the {@link Protein} in the {@link ProteinPSMLink} after removing
	// the
	// * actual {@link Psm} according to its remaining {@link ProteinPSMLink}
	// *
	// * @return
	// */
	// public Protein getProteinAccordingToItsLinks() {
	// final Set<ProteinPSMLink> linksOfTheProtein =
	// queriableProtein.getLinks();
	// final Protein protein = queriableProtein.getProtein();
	// final Iterator<Psm> psmIterator = protein.getPsms().iterator();
	// while (psmIterator.hasNext()) {
	// final Psm psm = psmIterator.next();
	// boolean found = false;
	// for (ProteinPSMLink proteinPSMLink : linksOfTheProtein) {
	// if (psm.equals(proteinPSMLink.getPsm())) {
	// found = true;
	// break;
	// }
	// }
	// if (!found) {
	// // remove psm
	// psmIterator.remove();
	// }
	// }
	// return protein;
	// }

	/**
	 * Removes the {@link LinkBetweenQueriableProteinSetAndPeptideSet} from the
	 * {@link QueriableProteinSet} and {@link QueriablePsm}
	 */
	public void detachFromProteinAndPeptide() {
		queriableProteinSet.removeLink(this);
		queriablePeptide.removeProteinSetLink(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return hashCode() + ": Link between " + queriableProteinSet + " and " + queriablePeptide;
	}

	/**
	 * Get the {@link Protein} in the
	 * {@link LinkBetweenQueriableProteinSetAndPeptideSet}
	 *
	 * @return
	 */
	public Set<Protein> getIndividualProteins() {
		return queriableProteinSet.getIndividualProteins();
	}

	/**
	 * @return the linkSetForSameProtein
	 */
	public Set<LinkBetweenQueriableProteinSetAndPeptideSet> getLinkSetForSameProtein() {
		return linkSetForSameProtein;
	}

	/**
	 * @param linkSetForSameProtein
	 *            the linkSetForSameProtein to set
	 */
	public void setLinkSetForSameProtein(Set<LinkBetweenQueriableProteinSetAndPeptideSet> linkSetForSameProtein) {
		this.linkSetForSameProtein = linkSetForSameProtein;
	}

	public void invalidateLink() {
		final List<Peptide> peptides = getQueriablePeptide().getIndividualPeptides();
		for (final Peptide peptide : peptides) {
			// PersistenceUtils.detachPSM(psm, false, false, false);
			final List<Protein> allProteins = getQueriableProtein().getAllProteins();
			final Set<Protein> proteinsFromPeptide = peptide.getProteins();
			for (final Protein protein : allProteins) {
				protein.getPeptides().remove(peptide);
				proteinsFromPeptide.remove(protein);
			}
		}

	}

	// public void setProtein2PsmResult(QueriableProtein2PSMLink
	// queriableProtein2PSMLink, boolean result) {
	// if (evaluatedProtein2PSMLinks.containsKey(result)) {
	// evaluatedProtein2PSMLinks.get(result).add(queriableProtein2PSMLink);
	// } else {
	// Set<QueriableProtein2PSMLink> set = new
	// HashSet<QueriableProtein2PSMLink>();
	// set.add(queriableProtein2PSMLink);
	// evaluatedProtein2PSMLinks.put(result, set);
	// }
	// }
	//
	// public Set<QueriableProtein2PSMLink> getProtein2PsmResult(boolean valid)
	// {
	// return evaluatedProtein2PSMLinks.get(valid);
	// }

}
