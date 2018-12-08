package edu.scripps.yates.censuschro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.scripps.yates.census.read.model.IsobaricQuantifiedProtein;
import edu.scripps.yates.census.read.model.interfaces.QuantifiedPSMInterface;
import edu.scripps.yates.census.read.model.interfaces.QuantifiedProteinInterface;
import edu.scripps.yates.utilities.fasta.FastaParser;
import edu.scripps.yates.utilities.grouping.GroupablePeptide;
import edu.scripps.yates.utilities.grouping.ProteinEvidence;
import edu.scripps.yates.utilities.grouping.ProteinGroup;
import edu.scripps.yates.utilities.ipi.IPI2UniprotACCMap;
import edu.scripps.yates.utilities.proteomicsmodel.Accession;
import edu.scripps.yates.utilities.proteomicsmodel.Amount;
import edu.scripps.yates.utilities.proteomicsmodel.Condition;
import edu.scripps.yates.utilities.proteomicsmodel.Gene;
import edu.scripps.yates.utilities.proteomicsmodel.MSRun;
import edu.scripps.yates.utilities.proteomicsmodel.Organism;
import edu.scripps.yates.utilities.proteomicsmodel.PSM;
import edu.scripps.yates.utilities.proteomicsmodel.Peptide;
import edu.scripps.yates.utilities.proteomicsmodel.Protein;
import edu.scripps.yates.utilities.proteomicsmodel.ProteinAnnotation;
import edu.scripps.yates.utilities.proteomicsmodel.Ratio;
import edu.scripps.yates.utilities.proteomicsmodel.Score;
import edu.scripps.yates.utilities.proteomicsmodel.Threshold;
import edu.scripps.yates.utilities.proteomicsmodel.enums.AccessionType;
import edu.scripps.yates.utilities.proteomicsmodel.factories.AccessionEx;
import edu.scripps.yates.utilities.proteomicsmodel.factories.GeneEx;
import edu.scripps.yates.utilities.proteomicsmodel.factories.OrganismEx;
import edu.scripps.yates.utilities.proteomicsmodel.factories.PeptideEx;
import edu.scripps.yates.utilities.proteomicsmodel.staticstorage.StaticProteomicsModelStorage;
import edu.scripps.yates.utilities.proteomicsmodel.utils.ModelUtils;
import edu.scripps.yates.utilities.util.Pair;
import gnu.trove.set.hash.THashSet;

public class ProteinImplFromQuantifiedProtein implements Protein {
	private final static Logger log = Logger.getLogger(ProteinImplFromQuantifiedProtein.class);
	private final QuantifiedProteinInterface quantProtein;
	private Accession primaryAccession;
	private final Set<Ratio> ratios = new THashSet<Ratio>();
	private MSRun msRun;
	private final Set<Condition> conditions = new THashSet<Condition>();
	private final Set<Amount> amounts = new THashSet<Amount>();
	private final Set<ProteinAnnotation> annotations = new THashSet<ProteinAnnotation>();
	private final Set<Threshold> thresholds = new THashSet<Threshold>();
	private ProteinEvidence evidence;
	private ProteinGroup proteinGroup;
	private Organism organism;
	private final Set<Peptide> peptides = new THashSet<Peptide>();
	private final Set<PSM> psms = new THashSet<PSM>();
	private final Set<Score> scores = new THashSet<Score>();
	private boolean psmsParsed = false;
	private boolean peptidesParsed = false;
	private boolean organismParsed = false;
	private double mw;
	private boolean mwParsed = false;
	private double pi;
	private boolean piParsed = false;
	private int length;
	private boolean lengthParsed = false;
	private final Set<Gene> genes = new THashSet<Gene>();
	private boolean genesParsed = false;
	private final List<Accession> secondaryAccessions = new ArrayList<Accession>();

	public ProteinImplFromQuantifiedProtein(QuantifiedProteinInterface quantProtein, MSRun msRun, Condition condition) {
		this.quantProtein = quantProtein;
		if (condition != null)
			addCondition(condition);
		this.msRun = msRun;
	}

	@Override
	public Set<Score> getScores() {
		return scores;
	}

	@Override
	public Set<Amount> getAmounts() {

		return amounts;
	}

	@Override
	public List<GroupablePeptide> getGroupablePeptides() {
		return quantProtein.getGroupablePeptides();
	}

	@Override
	public int getUniqueID() {
		return -1;
	}

	@Override
	public Accession getPrimaryAccession() {
		if (quantProtein.getAccession().equals("IPI00114389.4")) {
			log.info(quantProtein);
		}
		if (primaryAccession == null) {
			primaryAccession = new AccessionEx(quantProtein.getAccession(),
					AccessionType.fromValue(quantProtein.getAccessionType()));
			((AccessionEx) primaryAccession).setDescription(quantProtein.getDescription());
			final Pair<String, String> acc = FastaParser.getACC(primaryAccession.getAccession());
			if (acc.getSecondElement().equals("IPI")) {
				Pair<Accession, Set<Accession>> pair = IPI2UniprotACCMap.getInstance()
						.getPrimaryAndSecondaryAccessionsFromIPI(primaryAccession);
				if (pair.getFirstelement() != null) {
					primaryAccession = pair.getFirstelement();
				}
				if (pair.getSecondElement() != null) {
					secondaryAccessions.addAll(pair.getSecondElement());
				}
			}
		}
		return primaryAccession;
	}

	@Override
	public String getAccession() {
		return getPrimaryAccession().getAccession();
	}

	@Override
	public List<Accession> getSecondaryAccessions() {
		return secondaryAccessions;
	}

	@Override
	public Set<Gene> getGenes() {
		if (!genesParsed) {
			final String geneFromFastaHeader = FastaParser.getGeneFromFastaHeader(quantProtein.getDescription());
			if (geneFromFastaHeader != null) {
				GeneEx gene = new GeneEx(geneFromFastaHeader);
				genes.add(gene);
			}

			genesParsed = true;
		}
		return genes;
	}

	@Override
	public Set<ProteinAnnotation> getAnnotations() {
		return annotations;
	}

	@Override
	public Set<Ratio> getRatios() {

		return ratios;
	}

	@Override
	public Set<Threshold> getThresholds() {
		return thresholds;
	}

	@Override
	public Boolean passThreshold(String thresholdName) {
		return null;
	}

	@Override
	public Set<PSM> getPSMs() {
		if (!psmsParsed) {
			final Set<QuantifiedPSMInterface> quantPSMs = quantProtein.getQuantifiedPSMs();
			for (QuantifiedPSMInterface quantPSM : quantPSMs) {
				for (Condition condition : getConditions()) {
					if (StaticProteomicsModelStorage.containsPSM(msRun, condition.getName(), quantPSM.getKey())) {
						final PSM psm = StaticProteomicsModelStorage.getSinglePSM(msRun, condition.getName(),
								quantPSM.getKey());
						psm.addProtein(this);
						psms.add(psm);
					} else {
						final PSMImplFromQuantifiedPSM psm = new PSMImplFromQuantifiedPSM(quantPSM, msRun);
						psm.addProtein(this);
						psms.add(psm);
						StaticProteomicsModelStorage.addPSM(psm, msRun, condition.getName());
					}
				}
			}
			psmsParsed = true;
		}
		return psms;
	}

	@Override
	public Set<Peptide> getPeptides() {
		// ModelUtils.createPeptides(this);
		if (!peptidesParsed) {

			for (PSM psm : getPSMs()) {
				final String sequence = psm.getSequence();
				for (Condition condition : getConditions()) {
					Peptide peptide = null;
					if (StaticProteomicsModelStorage.containsPeptide(msRun, condition.getName(), sequence)) {
						peptide = StaticProteomicsModelStorage.getSinglePeptide(msRun, condition.getName(), sequence);
					} else {
						peptide = new PeptideEx(sequence, msRun);

						StaticProteomicsModelStorage.addPeptide(peptide, msRun, condition.getName());
					}
					peptide.addPSM(psm);
					peptide.addProtein(this);
					psm.setPeptide(peptide);
					peptides.add(peptide);
				}
			}

			peptidesParsed = true;
		}
		return peptides;
	}

	@Override
	public void addPeptide(Peptide peptide) {
		if (!peptides.contains(peptide))
			peptides.add(peptide);
	}

	@Override
	public int getLength() {
		if (!lengthParsed) {
			if (quantProtein.getLength() != null)
				length = quantProtein.getLength();
			lengthParsed = true;
		}
		return length;
	}

	@Override
	public double getPi() {
		if (!piParsed) {
			if (quantProtein instanceof IsobaricQuantifiedProtein) {
				if (((IsobaricQuantifiedProtein) quantProtein).getPi() != null)
					pi = Double.valueOf(((IsobaricQuantifiedProtein) quantProtein).getPi().toString());
			}
			piParsed = true;
		}
		return pi;
	}

	@Override
	public double getMW() {
		if (!mwParsed) {
			if (quantProtein instanceof IsobaricQuantifiedProtein) {
				if (((IsobaricQuantifiedProtein) quantProtein).getMolwt() != null)
					mw = Double.valueOf(((IsobaricQuantifiedProtein) quantProtein).getMolwt().toString());
			}
			mwParsed = true;
		}
		return mw;
	}

	@Override
	public String getSequence() {
		return null;
	}

	@Override
	public void setEvidence(ProteinEvidence evidence) {
		this.evidence = evidence;

	}

	@Override
	public ProteinEvidence getEvidence() {
		return evidence;
	}

	@Override
	public void setProteinGroup(ProteinGroup proteinGroup) {
		this.proteinGroup = proteinGroup;

	}

	@Override
	public ProteinGroup getProteinGroup() {
		return proteinGroup;
	}

	@Override
	public Organism getOrganism() {
		if (!organismParsed) {
			String taxonomy = null;
			if (quantProtein.getTaxonomies() != null && !quantProtein.getTaxonomies().isEmpty()) {
				taxonomy = quantProtein.getTaxonomies().iterator().next();
			}
			if (taxonomy != null && !"".equals(taxonomy)) {
				organism = new OrganismEx(taxonomy);
			}
			if (FastaParser.isContaminant(getAccession())) {
				organism = ModelUtils.ORGANISM_CONTAMINANT;
			}
			organismParsed = true;
		}
		return organism;
	}

	@Override
	public Set<Condition> getConditions() {
		return conditions;
	}

	@Override
	public MSRun getMSRun() {
		return msRun;
	}

	@Override
	public void setMSRun(MSRun msRun) {
		this.msRun = msRun;

	}

	@Override
	public void addCondition(Condition condition) {
		if (!conditions.contains(condition))
			conditions.add(condition);
		if (condition.getSample() != null && condition.getSample().getOrganism() != null)
			setOrganism(condition.getSample().getOrganism());

	}

	@Override
	public void addRatio(Ratio ratio) {
		if (!ratios.contains(ratio))
			ratios.add(ratio);
	}

	@Override
	public void addAmount(Amount amount) {
		if (!amounts.contains(amount))
			amounts.add(amount);
	}

	@Override
	public void addScore(Score score) {
		if (!scores.contains(score))
			scores.add(score);

	}

	@Override
	public void addPSM(PSM psm) {
		if (!psms.contains(psm)) {
			psms.add(psm);

		}

	}

	@Override
	public void setOrganism(Organism organism) {
		if (FastaParser.isContaminant(getAccession())) {
			this.organism = ModelUtils.ORGANISM_CONTAMINANT;
			return;
		}
		this.organism = organism;

	}

	@Override
	public void setMw(double mw) {
		this.mw = mw;

	}

	@Override
	public void setPi(double pi) {
		this.pi = pi;

	}

	@Override
	public void setLength(int length) {
		this.length = length;

	}

	@Override
	public void addGene(Gene gene) {
		if (!genes.contains(gene))
			genes.add(gene);

	}

}
