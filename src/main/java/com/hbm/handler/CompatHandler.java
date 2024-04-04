package com.hbm.handler;


// Imports for compat management.
// -----------------------------------------------------------------
import com.hbm.blocks.network.BlockCableGauge;
import com.hbm.blocks.network.FluidDuctGauge;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.storage.TileEntityBarrel;
import com.hbm.tileentity.machine.storage.TileEntityMachineBattery;
import com.hbm.tileentity.machine.storage.TileEntityMachineFluidTank;
// -----------------------------------------------------------------

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import cpw.mods.fml.common.Optional;

/**
 * General handler for OpenComputers compatibility.
 */
public class CompatHandler {


    /**
     * Stores the TEs for compat and their classes/names for the connector. When adding new compat, remember to add a new entry here.
     * <p>
     * Stores the class (Object) and the name of the component (String).
     */
    public enum Compats {
        //Special
        INVALID(null, null, null), //please let this never get used, it will 99% cause a NullPointerException
        CONNECTOR(TileEntityOCConnector.class, null, null), //why would you even? this is only possible by placing two next to each other

        //RBMK
        RBMK_FUEL(TileEntityRBMKRod.class, "rbmk_fuel_rod", TileEntityRBMKRod.callbacks()),
        RBMK_CONTROL(TileEntityRBMKControl.class, "rbmk_control_rod", TileEntityRBMKControl.callbacks()),
        RBMK_HEATER(TileEntityRBMKHeater.class, "rbmk_heater", TileEntityRBMKHeater.callbacks()),
        RBMK_BOILER(TileEntityRBMKBoiler.class, "rbmk_boiler", TileEntityRBMKBoiler.callbacks()),
        RBMK_OUTGASSER(TileEntityRBMKOutgasser.class, "rbmk_outgasser", TileEntityRBMKOutgasser.callbacks()),
        RBMK_COOLER(TileEntityRBMKCooler.class, "rbmk_cooler", TileEntityRBMKCooler.callbacks()),
        RBMK_CONSOLE(TileEntityRBMKConsole.class, "rbmk_console", TileEntityRBMKConsole.callbacks()),
        RBMK_CRANE(TileEntityCraneConsole.class, "rbmk_crane", TileEntityCraneConsole.callbacks()),

        //DFC
        DFC_EMITTER(TileEntityCoreEmitter.class, "dfc_emitter", TileEntityCoreEmitter.callbacks()),
        DFC_STABILIZER(TileEntityCoreStabilizer.class, "dfc_stabilizer", TileEntityCoreStabilizer.callbacks()),
        DFC_INJECTOR(TileEntityCoreInjector.class, "dfc_injector", TileEntityCoreInjector.callbacks()),
        DFC_RECEIVER(TileEntityCoreReceiver.class, "dfc_receiver", TileEntityCoreReceiver.callbacks()),

        //Other reactors
        ITER(TileEntityITER.class, "ntm_fusion", TileEntityITER.callbacks()),
        PWR(TileEntityPWRController.class, "ntm_pwr_control", TileEntityPWRController.callbacks()),
        RESEARCH(TileEntityReactorResearch.class, "research_reactor", TileEntityReactorResearch.callbacks()),
        RESEARCH_CONTROL(TileEntityReactorControl.class, "reactor_control", TileEntityReactorControl.callbacks()),
        ZIRNOX(TileEntityReactorZirnox.class, "zirnox_reactor", TileEntityReactorZirnox.callbacks()),

        //Turbines
        SMALL_TURBINE(TileEntityMachineTurbine.class, "ntm_turbine", TileEntityMachineTurbine.callbacks()), // no clue how these will operate together since they have the same compat name
        TURBINE(TileEntityMachineLargeTurbine.class, "ntm_turbine", TileEntityMachineLargeTurbine.callbacks()),
        LARGE_TURBINE(TileEntityChungus.class, "ntm_turbine", TileEntityChungus.callbacks()),
        GAS_TURBINE(TileEntityMachineTurbineGas.class, "ntm_gas_turbine", TileEntityMachineTurbineGas.callbacks()),

        //Other machines
        GEIGER(TileEntityGeiger.class, "ntm_geiger", TileEntityGeiger.callbacks()),
        RADAR(TileEntityMachineRadarNT.class, "ntm_radar", TileEntityMachineRadarNT.callbacks()),
        LARGE_RADAR(TileEntityMachineRadarLarge.class, "ntm_radar", TileEntityMachineRadarLarge.callbacks()),
        MICROWAVE(TileEntityMicrowave.class, "ntm_microwave", TileEntityMicrowave.callbacks()),
        CABLE_GAUGE(BlockCableGauge.TileEntityCableGauge.class, "power_gauge", BlockCableGauge.TileEntityCableGauge.callbacks()),
        PIPE_GAUGE(FluidDuctGauge.TileEntityPipeGauge.class, "ntm_fluid_gauge", FluidDuctGauge.TileEntityPipeGauge.callbacks()),

        //Launch pads TODO: add other launch pads into OC compat
        LAUNCH_TABLE(TileEntityLaunchTable.class, "large_launch_pad", TileEntityLaunchTable.callbacks()),

        //Storage
        BARREL(TileEntityBarrel.class, "ntm_fluid_tank", TileEntityBarrel.callbacks()),
        TANK(TileEntityMachineFluidTank.class, "ntm_fluid_tank", TileEntityMachineFluidTank.callbacks()),
        BATTERY(TileEntityMachineBattery.class, "ntm_energy_storage", TileEntityMachineBattery.callbacks());


        //Also add new compat here; if not added then connectors will not be able to see it.
        public static final Compats[] VALID_COMPATS = {
                CONNECTOR,
                RBMK_FUEL,
                RBMK_CONTROL,
                RBMK_HEATER,
                RBMK_BOILER,
                RBMK_OUTGASSER,
                RBMK_COOLER,
                RBMK_CONSOLE,
                RBMK_CRANE,
                DFC_EMITTER,
                DFC_STABILIZER,
                DFC_INJECTOR,
                DFC_RECEIVER,
                ITER,
                PWR,
                RESEARCH,
                RESEARCH_CONTROL,
                ZIRNOX,
                SMALL_TURBINE,
                TURBINE,
                LARGE_TURBINE,
                GAS_TURBINE,
                GEIGER,
                RADAR,
                LARGE_RADAR,
                MICROWAVE,
                CABLE_GAUGE,
                PIPE_GAUGE,
                LAUNCH_TABLE,
                BARREL,
                TANK,
                BATTERY
        };

        public final Class<?> Class;
        public final String name;
        public final String[] Methods;

        Compats(Class<?> object, String Compat_name, String[] methods) {
            Class = object;
            name = Compat_name;
            Methods = methods; //grr
        }
    }

    /**
     * Mostly for RBMKs, though it can support any machine.
     */
    @Optional.Method(modid = "OpenComputers")
    public static Object[] steamTypeToInt(FluidType type) {
        if(type == Fluids.STEAM) {return new Object[] {0};}
        else if(type == Fluids.HOTSTEAM) {return new Object[] {1};}
        else if(type == Fluids.SUPERHOTSTEAM) {return new Object[] {2};}
        return new Object[] {3};
    }
    /**
     * Inverse of `steamTypeToInt()`
     */
    @Optional.Method(modid = "OpenComputers")
    public static FluidType intToSteamType(int arg) {
        switch(arg) {
            default:
                return Fluids.STEAM;
            case(1):
                return Fluids.HOTSTEAM;
            case(2):
                return Fluids.SUPERHOTSTEAM;
            case(3):
                return Fluids.ULTRAHOTSTEAM;
        }
    }
}
