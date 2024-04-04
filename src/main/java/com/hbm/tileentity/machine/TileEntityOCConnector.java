package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.CompatHandler;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.LoggingUtil;
import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({
		@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"),
		@Optional.Interface(iface = "li.cil.oc.api.network.ManagedPeripheral", modid = "OpenComputers")
})
public class TileEntityOCConnector extends TileEntityLoadedBase implements SimpleComponent, ManagedPeripheral {

	boolean connected = false;
	String connectedEnum;
	TileEntity connectedTE;
	int time = 0;

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			if (time % 20 == 0) {
				for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
					Block b = worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
					if (b instanceof BlockDummyable || b.hasTileEntity(0)) {
						TileEntity tile;
						if (b instanceof BlockDummyable) {
							int[] pos = ((BlockDummyable)b).findCore(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
							if (pos != null) {
								tile = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
							} else {
								LoggingUtil.errorWithHighlight("Null position for OC connector when trying to find core block.");
								return;
							}
						} else {
							tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
						}
						Class<?> tileClass = tile.getClass();
						boolean valid = false;
						for (CompatHandler.Compats c : CompatHandler.Compats.VALID_COMPATS) {
							if (c.Class.equals(tileClass) || c.Class.equals(tileClass.getSuperclass())) {
								if (!c.Class.equals(TileEntityOCConnector.class)) { //fuck you
									valid = true;
									connected = true;
									connectedEnum = c.name();
									connectedTE = tile;
									break;
								}
							}
						}
						if(!valid) {
							connected = false;
							connectedEnum = null;
							connectedTE = null;
						}
					}
				}
				time = 0;
			}
			time++;
		}
	}

	@Override
	public String getComponentName() {
		return CompatHandler.Compats.valueOf(connectedEnum).name + "_connector";
	}

	@Override
	public String[] methods() {
		if(connected)
			return CompatHandler.Compats.valueOf(connectedEnum).Methods;
		else
			return null;
	}

	@Override
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		if(connected)
			return new Object[] {true};
		else
			return null;

	}
}
