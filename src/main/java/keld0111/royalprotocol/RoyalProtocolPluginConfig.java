package keld0111.royalprotocol;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("royalprotocol")
public interface RoyalProtocolPluginConfig extends Config
{
	@ConfigItem(
			position = 1,
			keyName = "royalBlood",
			name = "Royal Blood",
			description = "Your influence is set to max and not determined by equipment."
	)
	default boolean royalBlood()
	{
		return false;
	}
}
