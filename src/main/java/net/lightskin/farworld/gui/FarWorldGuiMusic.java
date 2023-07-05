package net.lightskin.farworld.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;

public class FarWorldGuiMusic extends GuiScreen{
	public FarWorldGuiMusic(String display) {
		this.width = Minecraft.getMinecraft().displayWidth;
		this.height = Minecraft.getMinecraft().displayHeight;
		this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
		this.labelList.add(new GuiLabel(fontRenderer, 0, 0, 0, 200, 100, 0xAAAAAA));
	}
}
