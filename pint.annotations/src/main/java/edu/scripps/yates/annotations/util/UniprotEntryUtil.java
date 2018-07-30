package edu.scripps.yates.annotations.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import edu.scripps.yates.annotations.uniprot.xml.CommentType;
import edu.scripps.yates.annotations.uniprot.xml.DbReferenceType;
import edu.scripps.yates.annotations.uniprot.xml.Entry;
import edu.scripps.yates.annotations.uniprot.xml.EvidencedStringType;
import edu.scripps.yates.annotations.uniprot.xml.FeatureType;
import edu.scripps.yates.annotations.uniprot.xml.GeneNameType;
import edu.scripps.yates.annotations.uniprot.xml.GeneType;
import edu.scripps.yates.annotations.uniprot.xml.PropertyType;
import edu.scripps.yates.annotations.uniprot.xml.ProteinType.SubmittedName;
import edu.scripps.yates.annotations.uniprot.xml.SubcellularLocationType;
import edu.scripps.yates.annotations.uniprot.xml.Uniprot;
import gnu.trove.set.hash.THashSet;
import uk.ac.ebi.kraken.interfaces.go.GoCategory;
import uk.ac.ebi.kraken.interfaces.go.GoTerm;
import uk.ac.ebi.kraken.model.go.GoIdImpl;
import uk.ac.ebi.kraken.model.go.GoTermImpl;
import uk.ac.ebi.kraken.model.go.GoTermNameImpl;

public class UniprotEntryUtil {
	public static void removeNonUsedElements(Uniprot uniprot, boolean removeReferences, boolean removeDBReferences) {
		if (uniprot != null && uniprot.getEntry() != null) {
			for (final Entry entry : uniprot.getEntry()) {
				removeNonUsedElements(entry, removeReferences, removeDBReferences);
			}
		}
	}

	public static void removeNonUsedElements(Entry entry, boolean removeReferences, boolean removeDBReferences) {
		if (entry != null) {
			if (removeReferences && entry.getReference() != null) {
				entry.getReference().clear();
			}
			if (removeDBReferences && entry.getDbReference() != null) {
				entry.getDbReference().clear();
			}
		}
	}

	public static String getENSGID(Entry entry) {
		if (entry != null) {
			if (entry.getDbReference() != null) {
				for (final DbReferenceType dbReference : entry.getDbReference()) {
					if ("Ensembl".equals(dbReference.getType())) {
						if (dbReference.getProperty() != null) {
							for (final PropertyType property : dbReference.getProperty()) {
								if ("gene ID".equals(property.getType())) {
									return property.getValue();
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static String getProteinSequence(Entry entry) {
		if (entry != null) {
			if (entry.getSequence() != null) {
				if (entry.getSequence().getValue() != null) {
					final String seq = entry.getSequence().getValue().replace("\n", "");
					return seq;
				}
			}
		}
		return null;
	}

	public static Double getMolecularWeightInDalton(Entry entry) {
		if (entry != null) {
			if (entry.getSequence() != null) {
				if (entry.getSequence().getMass() > 0) {
					return Double.valueOf(entry.getSequence().getMass());
				}
			}
		}
		return null;
	}

	public static List<String> getGeneName(Entry entry, boolean justPrimary, boolean secondaryIfPrimaryIsNull) {
		final List<String> ret = new ArrayList<String>();
		if (entry != null) {
			final List<GeneType> gene = entry.getGene();
			if (gene != null) {
				for (final GeneType geneType : gene) {
					for (final GeneNameType geneNameType : geneType.getName()) {
						boolean isPrimary = false;
						if ("primary".equals(geneNameType.getType())) {
							isPrimary = true;
						}
						if (!justPrimary || (justPrimary && isPrimary)) {
							ret.add(geneNameType.getValue());
							if (justPrimary) {
								return ret;
							}
						}
					}
				}
				if (justPrimary && secondaryIfPrimaryIsNull && ret.isEmpty()) {
					final List<String> geneNames2 = getGeneName(entry, false, false);
					if (geneNames2.isEmpty()) {
					} else {
						ret.add(geneNames2.get(0));
					}
				}
			}
		}
		return ret;
	}

	/**
	 * <dbReference type="Proteomes" id="UP000005640">
	 * <property type= "component" value="Chromosome 1"/> </dbReference>
	 * 
	 * @param entry
	 * @return
	 */
	public static String getChromosomeName(Entry entry) {
		if (entry != null) {
			if (entry.getDbReference() != null) {
				for (final DbReferenceType dbReference : entry.getDbReference()) {
					if ("Proteomes".equals(dbReference.getType())) {
						if (dbReference.getProperty() != null) {
							for (final PropertyType property : dbReference.getProperty()) {
								if (property.getValue().contains("Chromosome")) {
									return property.getValue();
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static String getUniprotEvidence(Entry entry) {
		if (entry != null) {
			if (entry.getProteinExistence() != null) {
				return entry.getProteinExistence().getType();
			}
		}
		return null;
	}

	public static String getProteinDescription(Entry entry) {
		if (entry != null) {
			if (entry.getProtein() != null) {
				if (entry.getProtein().getRecommendedName() != null) {
					if (entry.getProtein().getRecommendedName().getFullName() != null) {
						return entry.getProtein().getRecommendedName().getFullName().getValue();
					}
				} else {
					if (entry.getProtein().getSubmittedName() != null) {
						for (final SubmittedName submittedName : entry.getProtein().getSubmittedName()) {
							final EvidencedStringType fullName = submittedName.getFullName();
							if (fullName != null) {
								final String fullNameValue = fullName.getValue();
								if (fullNameValue != null && !"".equals(fullNameValue)) {
									return fullNameValue;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static List<CommentType> getComments(Entry entry,
			uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentType type) {
		final List<CommentType> ret = new ArrayList<CommentType>();
		final String typeString = type.toXmlDisplayName();
		if (entry != null) {
			if (entry.getComment() != null) {
				for (final CommentType comment : entry.getComment()) {
					if (comment.getType().equals(typeString)) {
						ret.add(comment);
					}
				}
			}
		}
		return ret;
	}

	public static List<FeatureType> getFeatures(Entry entry,
			uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureType type) {
		final List<FeatureType> ret = new ArrayList<FeatureType>();
		final String typeString = type.getValue();
		if (entry != null) {
			if (entry.getFeature() != null) {
				for (final FeatureType feature : entry.getFeature()) {
					if (feature.getType().equals(typeString)) {
						ret.add(feature);
					}
				}
			}
		}
		return ret;
	}

	public static String getPrimaryAccession(Entry entry) {
		if (entry != null) {
			if (entry.getAccession() != null) {
				if (!entry.getAccession().isEmpty()) {
					return entry.getAccession().get(0);
				}
			}
		}
		return null;
	}

	public static String getTaxonomy(Entry entry) {
		if (entry != null) {
			if (entry.getOrganism() != null && entry.getOrganism().getName() != null) {
				if (!entry.getOrganism().getName().isEmpty()) {
					return entry.getOrganism().getName().get(0).getValue();
				}
			}
		}
		return null;
	}

	public static List<String> getNames(Entry entry) {
		if (entry != null) {
			if (entry.getName() != null) {
				return entry.getName();
			}
		}
		return null;
	}

	public static List<String> getCellularLocations(Entry entry) {
		final List<String> ret = new ArrayList<String>();
		if (entry != null) {
			if (entry.getComment() != null) {
				for (final CommentType comment : entry.getComment()) {
					if (comment.getType() != null && "subcellular location".equals(comment.getType())) {
						final List<SubcellularLocationType> subcellularLocations = comment.getSubcellularLocation();
						if (subcellularLocations != null) {
							for (final SubcellularLocationType subcellularLocation : subcellularLocations) {
								final List<EvidencedStringType> locations = subcellularLocation.getLocation();
								if (locations != null) {
									for (final EvidencedStringType evidencedStringType : locations) {
										if (!ret.contains(evidencedStringType.getValue())) {
											ret.add(evidencedStringType.getValue());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Retrieves the GO ID terms of all the cellularLocations
	 * 
	 * @param entry
	 * @return
	 */
	public static Set<GoTerm> getGOTermsForCellularLocations(Entry entry) {
		return getGOTermsForCellularLocations(entry, null);
	}

	private static List<PropertyType> filterPropertiesByType(Collection<PropertyType> properties, String type) {
		final List<PropertyType> ret = new ArrayList<PropertyType>();
		for (final PropertyType propertyType : properties) {
			if (propertyType.getType().equals(type)) {
				ret.add(propertyType);
			}
		}
		return ret;
	}

	/**
	 * Retrieves the GO ID terms of all the cellularLocations that are from an
	 * specific source.<br>
	 * 
	 * 
	 * @param entry
	 * @param sources
	 *            any list of sources. Valid sources can be UniProtKB,
	 *            UniProtKB-KW, Ensembl, Reactome, UniProtKB-SubCell, HPA,
	 *            ProtInc, or NULL if you want any source to be considered.<br>
	 *            According to Uniprot webpage:
	 *            <ul>
	 *            <li>"UniprotKB" source is inferred from direct assay,</li>
	 *            <li>"UniprotKB-SubCell","UniprotKB-KW" and "Ensembl" are
	 *            inferred from electronic annotation,</li>
	 *            <li>"Reactome" is traceable from author statement.</li>
	 *            </ul>
	 * @return
	 */
	public static Set<GoTerm> getGOTermsForCellularLocations(Entry entry, String... sources) {
		final Set<GoTerm> ret = new THashSet<GoTerm>();
		if (entry != null) {
			if (entry.getDbReference() != null) {
				for (final DbReferenceType dbReference : entry.getDbReference()) {
					if (dbReference.getType() != null && "GO".equals(dbReference.getType())) {
						final List<PropertyType> properties = dbReference.getProperty();
						if (properties != null) {
							final List<PropertyType> propertiesTerm = filterPropertiesByType(properties, "term");
							final List<PropertyType> propertiesProject = filterPropertiesByType(properties, "project");
							for (final PropertyType property : propertiesTerm) {
								if (property.getValue().startsWith("C:")) { // cellular
									// component
									final String termName = property.getValue().substring(2);
									if (sources == null) {
										final GoTerm goTerm = new GoTermImpl();
										final GoIdImpl goId = new GoIdImpl();
										goId.setValue(dbReference.getId());
										goTerm.setGoId(goId);
										goTerm.setGoCategory(GoCategory.C);
										final GoTermNameImpl goTermName = new GoTermNameImpl();
										goTermName.setValue(termName);
										goTerm.setGoTermName(goTermName);
										ret.add(goTerm);

									} else {
										boolean found = false;
										for (final String source : sources) {
											for (final PropertyType propertyProject : propertiesProject) {
												if (source.equals(propertyProject.getValue())) {
													found = true;
													break;
												}
											}
										}
										if (found) {
											final GoTerm goTerm = new GoTermImpl();
											final GoIdImpl goId = new GoIdImpl();
											goId.setValue(dbReference.getId());
											goTerm.setGoId(goId);
											goTerm.setGoCategory(GoCategory.C);
											final GoTermNameImpl goTermName = new GoTermNameImpl();
											goTermName.setValue(termName);
											goTerm.setGoTermName(goTermName);
											ret.add(goTerm);
										}
									}
								}

							}
						}
					}
				}
			}
		}
		return ret;
	}
}
