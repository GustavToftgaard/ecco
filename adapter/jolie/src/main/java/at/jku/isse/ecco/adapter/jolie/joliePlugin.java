package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.ArtifactPlugin;
import com.google.inject.Module;

public class joliePlugin extends ArtifactPlugin {
    public static final String DESCRIPTION = "Adds support for Jolie artefacts";

    private final jolieModule module = new jolieModule();

    @Override
    public String getPluginId() {
        return joliePlugin.class.getName();
    }

    @Override
    public Module getModule() {
        return module;
    }

    @Override
    public String getName() {
        return joliePlugin.class.getSimpleName();
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}