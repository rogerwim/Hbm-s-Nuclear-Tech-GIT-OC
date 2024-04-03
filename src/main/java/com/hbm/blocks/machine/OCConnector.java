package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityOCConnector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class OCConnector extends BlockContainer {

	public OCConnector(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityOCConnector();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}
}
