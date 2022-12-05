package main.java.model.construction.reflection;

import java.io.File;
import java.util.Set;

import main.java.model.construction.Construction;

public class ConstructionLoader {

	private static ConstructionLoader instance;
	private final String NOM_PACKAGE = "main.java.model.construction.";
	private final String PATH = "src/main/java/model/construction";

	private ConstructionLoader() {
	}

	/**
	 * Singleton pour avoir l'instance du loader
	 * 
	 * @return ConstructionLoader
	 */
	public static ConstructionLoader getInstance() {
		if (instance == null) {
			instance = new ConstructionLoader();
		}
		return instance;
	}

	/**
	 * Alimente l'ensemble des constructions de la ville en chargeant par réflexion
	 * les classes qui héritent de Construction
	 * 
	 * @param constructions
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void chargerConstructions(Set<Construction> constructions)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		File f = new File(this.PATH);
		File[] files = f.listFiles();
		for (File file : files) {
			if (file.getName().contains(".java") && !file.getName().contains("Construction")) {
				String nomClass = file.getName().replace(".java", "");
				Class clazz = Class.forName(this.NOM_PACKAGE + nomClass);
				Object obj = clazz.newInstance();
				Construction c = (Construction) obj;
				constructions.add(c);
			}
		}
	}

}
