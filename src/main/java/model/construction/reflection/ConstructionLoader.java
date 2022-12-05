package main.java.model.construction.reflection;

import java.io.File;
import java.util.Set;

import main.java.model.construction.Construction;

public class ConstructionLoader {

	private static ConstructionLoader instance;
	private final String NOM_PACKAGE = "src/main/java/model/construction";

	private ConstructionLoader() {
	}

	public static ConstructionLoader getInstance() {
		if (instance == null) {
			instance = new ConstructionLoader();
		}
		return instance;
	}

	public void chargerConstructions(Set<Construction> constructions) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		File f = new File(this.NOM_PACKAGE);
		File[] files = f.listFiles();
		for (File file : files) {
			if (file.getName().contains(".java") && !file.getName().contains("Construction")) {
				String nomClass = file.getName().replace(".java", "");
				Class clazz = Class.forName("main.java.model.construction." + nomClass);
				Object obj = clazz.newInstance();
				Construction c = (Construction)obj;
				constructions.add(c);
			}
		}
	}

}
