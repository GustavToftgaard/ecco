package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.ArtifactPlugin;
import com.google.inject.Module;

public class JoliePlugin extends ArtifactPlugin {
    public static final String DESCRIPTION = "Adds support for Jolie artefacts";

    private final JolieModule module = new JolieModule();

    @Override
    public String getPluginId() {
        return JoliePlugin.class.getName();
    }

    @Override
    public Module getModule() {
        return module;
    }

    @Override
    public String getName() {
        return JoliePlugin.class.getSimpleName();
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}