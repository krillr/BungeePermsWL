package io.quintus.bungeepermswl;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class BungeePermsWL extends Plugin implements Listener {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if(event.getPlayer().hasPermission("bungeepermswl.override")) { return; }
        if(!event.getPlayer().hasPermission("bungeepermswl.whitelist")) {
            event.getPlayer().disconnect(new TextComponent( "You are not on the whitelist" ));
        }
    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        if(event.getPlayer().hasPermission("bungeepermswl.override")) { return; }
        if(!event.getPlayer().hasPermission("bungeepermswl.whitelist." + event.getTarget().getName())) {
            event.setCancelled(true);
            ServerInfo otherServer = event.getPlayer().getReconnectServer();
            if (otherServer == event.getTarget() || otherServer == null) {
                event.getPlayer().disconnect(new TextComponent("You do not have access to that server."));
            } else {
                event.getPlayer().connect(otherServer);
            }
        }
    }

}
