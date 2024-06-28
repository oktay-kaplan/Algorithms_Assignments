import java.util.*;

public class MissionSynthesis {

    private final List<MolecularStructure> humanStructures;
    private final ArrayList<MolecularStructure> diffStructures;



    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();
        List<Bond> potentialBonds = new ArrayList<>();

        Set<Molecule> linkedMolecules = new HashSet<>();

        List<Molecule> selectedMolecules = new ArrayList<>();
        selectedMolecules.addAll(getLowestBondStrength(humanStructures));
        selectedMolecules.addAll(getLowestBondStrength(diffStructures));

        for (int i = 0; i < selectedMolecules.size(); i++) {
            Molecule molecule1 = selectedMolecules.get(i);
            for (int j = i + 1; j < selectedMolecules.size(); j++) {
                Molecule molecule2 = selectedMolecules.get(j);
                double bondStrength = getBondStrength(molecule1, molecule2);
                potentialBonds.add(new Bond(molecule1, molecule2, bondStrength));
            }
        }

        potentialBonds.sort(Comparator.comparingDouble(Bond::getWeight));

        for (Bond potentialBond : potentialBonds) {
            Molecule molecule1 = potentialBond.getFrom();
            Molecule molecule2 = potentialBond.getTo();

            if (linkedMolecules.contains(molecule1) && linkedMolecules.contains(molecule2)) {
                continue;
            }

            serum.add(potentialBond);

            linkedMolecules.add(molecule1);
            linkedMolecules.add(molecule2);

            if (linkedMolecules.size() == selectedMolecules.size()) {
                break;
            }
        }

        return serum;
    }

    private List<Molecule> getLowestBondStrength(List<MolecularStructure> structures) {
        List<Molecule> selectedMolecules = new ArrayList<>();
        for (MolecularStructure structure : structures) {
            Molecule moleculeWithLowestBondStrength = structure.getMoleculeWithWeakestBondStrength();
            selectedMolecules.add(moleculeWithLowestBondStrength);
        }
        return selectedMolecules;
    }


    private double getBondStrength(Molecule molecule1, Molecule molecule2) {
        return (molecule1.getBondStrength() + molecule2.getBondStrength()) / 2.0;
    }

    public void printSynthesis(List<Bond> serum) {
        System.out.println("Typical human molecules selected for synthesis: " + getMoleculeIds(humanStructures));
        System.out.println("Vitales molecules selected for synthesis: " + getMoleculeIds(diffStructures));
        System.out.println("Synthesizing the serum...");
        double totalBondStrength = 0;
        for (Bond bond : serum) {
            Molecule molecule1 = bond.getFrom();
            Molecule molecule2 = bond.getTo();
            double bondWeight = bond.getWeight();
            String id1 = molecule1.getId().substring(1);
            int id1Int = Integer.parseInt(id1);

            String id2 = molecule2.getId().substring(1);
            int id2Int = Integer.parseInt(id2);

            if (id1Int < id2Int) {
                System.out.printf("Forming a bond between %s - %s with strength %.2f%n",
                        molecule1.getId(), molecule2.getId(), bondWeight);
            } else {
                System.out.printf("Forming a bond between %s - %s with strength %.2f%n",
                        molecule2.getId(), molecule1.getId(), bondWeight);
            }
            totalBondStrength += bondWeight;
        }
        System.out.printf("The total serum bond strength is %.2f%n", totalBondStrength);
    }

    private List<String> getMoleculeIds(List<MolecularStructure> structures) {
        List<String> ids = new ArrayList<>();
        for (MolecularStructure structure : structures) {
            ids.add(structure.getMoleculeWithWeakestBondStrength().getId());
        }
        return ids;
    }
}
