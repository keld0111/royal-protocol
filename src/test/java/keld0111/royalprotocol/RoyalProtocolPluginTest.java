package keld0111.royalprotocol;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class RoyalProtocolPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(RoyalProtocolPlugin.class);
		RuneLite.main(args);
	}
}