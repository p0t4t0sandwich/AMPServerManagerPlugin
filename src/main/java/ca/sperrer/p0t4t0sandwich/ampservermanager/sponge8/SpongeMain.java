package ca.sperrer.p0t4t0sandwich.ampservermanager.sponge8;

import ca.sperrer.p0t4t0sandwich.ampservermanager.AMPServerManager;
import static ca.sperrer.p0t4t0sandwich.ampservermanager.VersionUtils.*;

import com.google.inject.Inject;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.LinearComponents;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

/**
 * The main class of your Sponge plugin.
 *
 * <p>All methods are optional -- some common event registrations are included as a jumping-off point.</p>
 */
@Plugin("ampservermanager")
public class SpongeMain {

    private final PluginContainer container;
    private final Logger logger;

    @Inject
    SpongeMain(final PluginContainer container, final Logger logger) {
        this.container = container;
        this.logger = logger;
    }

    public AMPServerManager ampServerManager;

    // Singleton instance
    private static SpongeMain instance;
    public static SpongeMain getInstance() {
        return instance;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        // Check to see if the server is Magma, Mohist, or Arclight, then disable plugin
        if (isMagma() || isMohist() || isArclight()) {
            logger.info("Sponge does not run on Hybrid Servers, disabling plugin.\n Please use the Bukkit or Forge version of this plugin instead.");
            return;
        }

        // Singleton instance
        instance = this;

        // Start AMPAPAI Server Manager
        ampServerManager = new AMPServerManager("configs", logger);

        Task.builder().execute(
                () -> ampServerManager.start()
        );

        logger.info("Constructing AMPServerManager");
    }

    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
        // Register a simple command
        // When possible, all commands should be registered within a command register event
        final Parameter.Value<String> nameParam = Parameter.string().key("name").build();
        event.register(this.container, Command.builder()
                .addParameter(nameParam)
                .permission("AMPServerManager.command.greet")
                .executor(ctx -> {
                    final String name = ctx.requireOne(nameParam);
                    ctx.sendMessage(Identity.nil(), LinearComponents.linear(
                            NamedTextColor.AQUA,
                            Component.text("Hello "),
                            Component.text(name, Style.style(TextDecoration.BOLD)),
                            Component.text("!")
                    ));

                    return CommandResult.success();
                })
                .build(), "greet", "wave");
    }
}
