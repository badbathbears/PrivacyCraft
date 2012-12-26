package de.badbathbears.privacy.gui;

import net.minecraft.src.GuiButton;

public class NumpadGuiButton extends GuiButton {

	private int number;

	public NumpadGuiButton(int par1, int par2, int par3, int par4, int par5, String par6Str, int number) {
		super(par1, par2, par3, par4, par5, par6Str);
		this.number = number;
	}

	public NumpadGuiButton(int par1, int par2, int par3, String par4Str, int number) {
		super(par1, par2, par3, par4Str);
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
