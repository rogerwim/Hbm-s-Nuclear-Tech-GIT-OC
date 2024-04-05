package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.CompatHandler;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityLoadedBase;
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
	ManagedPeripheral Peripheral;

	@Override
	@Optional.Method(modid = "OpenComputers")
	public void updateEntity() {
		if (!worldObj.isRemote) {
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				Block b = worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				if (b instanceof BlockDummyable || b.hasTileEntity(0)) {
					TileEntity tile;
					if (b instanceof BlockDummyable) {
						int[] pos = ((BlockDummyable)b).findCore(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
						if (pos != null) {
							tile = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
						} else {
							MainRegistry.logger.warn("Null value for position of core for block at: " + (xCoord + dir.offsetX) + ", " + (yCoord + dir.offsetY) + ", " + (zCoord + dir.offsetZ));
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
								break;
							}
						}
					}
					if (!valid && connected) {
						connected = false;
						connectedEnum = null;
						Peripheral = null;
						MainRegistry.logger.debug("TE disconnected at block: " + (xCoord + dir.offsetX) + ", " + (yCoord + dir.offsetY) + ", " + (zCoord + dir.offsetZ));
					} else if (valid && !connected) {
						connected = true;
						connectedEnum = c.name();
						Peripheral = (/* bruh */ManagedPeripheral) tile;
						MainRegistry.logger.debug("TE connected at block: " + (xCoord + dir.offsetX) + ", " + (yCoord + dir.offsetY) + ", " + (zCoord + dir.offsetZ));
					}
				}
			}
		}
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		if(!connected)
			return "none_connector";
		return CompatHandler.Compats.valueOf(connectedEnum).name + "_connector";
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		if(connected)
			return CompatHandler.Compats.valueOf(connectedEnum).Methods;
		else
			return null;
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		if(connected)
			return Peripheral.invoke(method, context, args);
		else
			return null;

	}
}
