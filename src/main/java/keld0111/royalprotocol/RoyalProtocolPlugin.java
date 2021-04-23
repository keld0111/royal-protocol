package keld0111.royalprotocol;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.*;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.*;

@Slf4j
@PluginDescriptor(
		name = "Royal Protocol",
		description = "Introducing traditional forms of addressing royalty in Gielinor.",
		tags = {"gear","king","bow","royalty"},
		enabledByDefault = false
)

public class RoyalProtocolPlugin extends Plugin
{

	private Set<Player> players = new HashSet<>();
	private Map<Player,Integer> hailCount = new HashMap<>();
	private Random rand = new Random();
	private int localInfluence = 0;
	private Map<Player,Integer> outsiderInfluence = new HashMap<>();

	@Inject
	private Client client;

	@Inject
	private RoyalProtocolPluginConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Royal protocol engaged.");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Royal protocol disengaged.");
	}

	@Subscribe
	public void onPlayerSpawned(PlayerSpawned event)
	{
		Player p = event.getPlayer();
		if(p != client.getLocalPlayer())
		{
			players.add(p);
			hailCount.put(p,0);
		}
	}

	@Subscribe
	public void onPlayerDespawned(PlayerDespawned event)
	{
		players.remove(event.getPlayer());
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		for(Player player : players)
		{
			attemptToAddress(player);
		}
	}

	void attemptToAddress(Player player)
	{
		LocalPoint a = player.getLocalLocation();
		LocalPoint b = client.getLocalPlayer().getLocalLocation();

		if(player.getAnimation() != -1) { return; }
		if(hailCount.get(player) > 0) { return; }
		if(a.distanceTo(b) > 300) { return; } //TODO: use localInfluence for dynamic range
		if(localInfluence > 500 || config.royalBlood()) //TODO: calculate local/outsideInfluence
		{
			setPlayerMoveFrom(RoyalMove.values(), player);
			hailCount.put(player, hailCount.get(player)+1);
		}
	}

	private void setPlayerMoveFrom(Move [] moves, Player player)
	{
		Move move = moves[rand.nextInt(moves.length)];
		player.setAnimation(move.getAnimId());
		player.setActionFrame(0);

		if(move.getGfxId() != -1)
		{
			player.setGraphic(move.getGfxId());
			player.setSpotAnimFrame(0);
		}
	}

	private int determineLocalInfluence()
	{
		//TODO: Return influence value of local player
		return 0;
	}

	private int determineOutsiderInfluence(Player player)
	{
		//TODO: Return influence value of other players
		return 0;
	}

	@Provides
	RoyalProtocolPluginConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RoyalProtocolPluginConfig.class);
	}
}
