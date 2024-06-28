import java.util.*;

public class MolecularData {

    private final List<Molecule> molecules;
    private final Map<String, List<String>> moleculeBondsMap;

    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
        this.moleculeBondsMap = createMoleculeBondsMap();
    }
    public List<Molecule> getMolecules() {
        return molecules;
    }
    private Map<String, List<String>> createMoleculeBondsMap() {
        Map<String, List<String>> bondsMap = new HashMap<>();
        for (Molecule molecule : molecules) {
            bondsMap.put(molecule.getId(), molecule.getBonds());
        }
        return bondsMap;
    }

    public List<MolecularStructure> identifyMolecularStructures() {
        List<MolecularStructure> structures = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (Molecule molecule : molecules) {
            if (!visited.contains(molecule.getId())) {
                MolecularStructure structure = new MolecularStructure();
                depthFirstSearchForMolecularStructure(molecule, visited, structure);
                structures.add(structure);
            }
        }

        return structures;
    }

    private void depthFirstSearchForMolecularStructure(Molecule molecule, Set<String> visited, MolecularStructure structure) {
        Stack<Molecule> stack = new Stack<>();
        stack.push(molecule);

        while (!stack.isEmpty()) {
            Molecule current = stack.pop();
            String currentId = current.getId();

            if (!visited.contains(currentId)) {
                visited.add(currentId);
                structure.addMolecule(current);

                List<String> bonds = moleculeBondsMap.get(currentId);
                Set<String> connectedMolecules = new HashSet<>();

                if (bonds != null) {
                    for (String bondId : bonds) {
                        if (!visited.contains(bondId)) {
                            Molecule bondedMolecule = findMoleculeById(bondId);
                            if (bondedMolecule != null) {
                                stack.push(bondedMolecule);
                                connectedMolecules.add(bondId);
                            }
                        }
                    }
                }

                for (Molecule m : molecules) {
                    String mId = m.getId();
                    if (!visited.contains(mId) && !structure.hasMolecule(mId)) {
                        List<String> mBonds = moleculeBondsMap.get(mId);
                        if (mBonds != null && mBonds.stream().anyMatch(connectedMolecules::contains)) {
                            stack.push(m);
                        }
                    }
                }
            }
        }
    }


    private Molecule findMoleculeById(String id) {
        for (Molecule molecule : molecules) {
            if (molecule.getId().equals(id)) {
                return molecule;
            }
        }
        return null;
    }

    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
        System.out.println(molecularStructures.size() + " molecular structures have been discovered in " + species + ".");
        for (int i = 0; i < molecularStructures.size(); i++) {
            System.out.println("Molecules in Molecular Structure " + (i + 1) + ": " + molecularStructures.get(i).toString());
        }
    }

    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targetStructures) {
        ArrayList<MolecularStructure> anomalies = new ArrayList<>();
        for (MolecularStructure targetStructure : targetStructures) {
            if (!sourceStructures.contains(targetStructure)) {
                anomalies.add(targetStructure);
            }
        }
        return anomalies;
    }

    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        System.out.println("Molecular structures unique to Vitales individuals:");
        for (MolecularStructure structure : molecularStructures) {
            System.out.println(structure.toString());
        }
    }
}