package de.badbathbears.privacy.gui;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import de.badbathbears.privacy.core.CommonProxy;
import de.badbathbears.privacy.core.PrivacyCraft;
import de.badbathbears.privacy.lock.BlockLock;
import de.badbathbears.privacy.lock.TileEntityLock;

@SideOnly(Side.CLIENT)
public class GuiCodeLock extends GuiContainer {
	public static final int ID = CommonProxy.GUI_KEY_LOCK;
	private TileEntityLock tile;
	private String code = "";
	private World world;
	private int x;
	private int y;
	private int z;
	private boolean set;
	private EntityPlayer player;
	private GuiTextField guiTextField;

	public GuiCodeLock(World world, EntityPlayer player, TileEntityLock tile, int x, int y, int z, boolean set) {
		super(new ContainerCodeLock(tile));
		this.world = world;
		this.player = player;
		this.tile = tile;
		this.x = x;
		this.y = y;
		this.z = z;
		this.set = set;
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id == 13) {
			if (this.code.length() == 4) {
				if (!set) {
					tile.setSet(true);
					tile.setKeyCode(code);
					BlockLock.sendPacket(player, code, true, x, y, z, false);
					this.mc.thePlayer.closeScreen();
				} else {
					if (code.equals(tile.getKeyCode())) {
						BlockLock.toggleLocked(world, x, y, z, player);
						this.mc.thePlayer.closeScreen();
					} else {
						this.code = "";
					}
				}
			} else {
				this.code = "";
			}
		} else if (button.id == 12) {
			tile.setSet(true);
			BlockLock.sendPacket(player, code, true, x, y, z, false);
			this.mc.thePlayer.closeScreen();
		} else if (button.id == 10) {
			if (code.length() > 0) {
				this.code = this.code.substring(0, this.code.length() - 1);
			}
		} else {
			if (button instanceof NumpadGuiButton) {
				if(this.code.length() < 4) {
					this.code += ((NumpadGuiButton) button).getNumber();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		this.controlList.add(new NumpadGuiButton(1, this.width / 2 - 35, this.height / 2 - 70, 20, 20, "1", 1));
		this.controlList.add(new NumpadGuiButton(2, this.width / 2 - 10, this.height / 2 - 70, 20, 20, "2", 2));
		this.controlList.add(new NumpadGuiButton(3, this.width / 2 + 15, this.height / 2 - 70, 20, 20, "3", 3));

		this.controlList.add(new NumpadGuiButton(4, this.width / 2 - 35, this.height / 2 - 45, 20, 20, "4", 4));
		this.controlList.add(new NumpadGuiButton(5, this.width / 2 - 10, this.height / 2 - 45, 20, 20, "5", 5));
		this.controlList.add(new NumpadGuiButton(6, this.width / 2 + 15, this.height / 2 - 45, 20, 20, "6", 6));

		this.controlList.add(new NumpadGuiButton(7, this.width / 2 - 35, this.height / 2 - 20, 20, 20, "7", 7));
		this.controlList.add(new NumpadGuiButton(8, this.width / 2 - 10, this.height / 2 - 20, 20, 20, "8", 8));
		this.controlList.add(new NumpadGuiButton(9, this.width / 2 + 15, this.height / 2 - 20, 20, 20, "9", 9));

		this.controlList.add(new NumpadGuiButton(10, this.width / 2 - 35, this.height / 2 + 5, 20, 20, "C", 0));
		this.controlList.add(new NumpadGuiButton(11, this.width / 2 - 10, this.height / 2 + 5, 20, 20, "0", 0));
		this.controlList.add(new NumpadGuiButton(12, this.width / 2 + 15, this.height / 2 + 5, 20, 20, "X", 0));

		this.controlList.add(new NumpadGuiButton(13, this.width / 2 + 11, this.height / 2 + 32, 25, 20, "OK", 0));
		this.allowUserInput = false;
		// drawCode();

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int var4 = this.mc.renderEngine.getTexture(PrivacyCraft.texturePath + "KeyLock.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2 + 26;
		int var6 = (this.height - this.ySize) / 2 + 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		if (this.guiTextField == null) {
			this.guiTextField = new GuiTextField(this.mc.fontRenderer, this.width / 2 - 159, this.height / 2 - 5, 40, 20);
		}
		this.guiTextField.setMaxStringLength(5);
		this.guiTextField.setText(" " + code);
		this.guiTextField.drawTextBox();
	}
}
