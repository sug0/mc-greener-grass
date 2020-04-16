package to.us.gandas.greener_grass;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GreenerGrassMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("Making grass greener since 2020!");
    }
}
