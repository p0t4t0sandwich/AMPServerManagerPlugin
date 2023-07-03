package dev.neuralnexus.serverpanelmanager.bungee.listeners.player;

import dev.neuralnexus.serverpanelmanager.bungee.player.BungeePlayer;
import dev.neuralnexus.serverpanelmanager.common.listneners.player.SPMPlayerLoginListener;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Listens for player logins.
 */
public class BungeePlayerLoginListener implements Listener, SPMPlayerLoginListener {
    /**
     * Called when a player logs in.
     * @param event The event.
     */
    @EventHandler
    public void onPlayerLogin(ServerSwitchEvent event) {
        // If player is switching servers, don't run this function
        if (event.getFrom() != null) return;

        // Get Player and current server
        ProxiedPlayer player = event.getPlayer();
        String toServer = event.getPlayer().getServer().getInfo().getName();

        BungeePlayer abstractPlayer = new BungeePlayer(player);
        abstractPlayer.setServerName(toServer);

        // Pass player to helper function
        SPMPlayerLogin(abstractPlayer);
    }
}
