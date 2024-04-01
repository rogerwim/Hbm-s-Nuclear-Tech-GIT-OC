package com.hbm.handler;

import net.minecraft.world.GameRules;
import net.minecraft.world.World;


// stuff here is for controlling the meltdowns of reactors/machines
// the gamerules are all default false, meaning meltdowns will be default enabled
public class GameruleHandler {

    public static final String KEY_WATZ_MELTDOWN = "M_disableWatzMeltdown";
    public static final String KEY_RBMK_MELTDOWN = "M_disableRBMKMeltdown";
    public static final String KEY_PWR_MELTDOWN = "M_disablePWRMeltdown";
    public static final String KEY_FUSION_MELTDOWN = "M_disableFusionMeltdown";
    public static final String KEY_ZIRNOX_MELTDOWN = "M_disableZIRNOXMeltdown";
    public static final String KEY_RESEARCH_MELTDOWN = "M_disableResearchMeltdown";
    public static final String KEY_DARK_FUSION_CORE_MELTDOWN = "M_disableDFCMeltdown";
    public static final String KEY_PILE_MELTDOWN = "M_disablePileMeltdown";
    public static final String KEY_SAVE_MELTDOWN_RULES = "M_saveMeltdownRules";

    public static void createDials(World world) {
        GameRules rules = world.getGameRules();

        if(!rules.getGameRuleBooleanValue(KEY_SAVE_MELTDOWN_RULES)) {
            rules.setOrCreateGameRule(KEY_WATZ_MELTDOWN, "false");
            rules.setOrCreateGameRule(KEY_RBMK_MELTDOWN, "false");
            rules.setOrCreateGameRule(KEY_PWR_MELTDOWN, "false");
            rules.setOrCreateGameRule(KEY_FUSION_MELTDOWN, "false");
            rules.setOrCreateGameRule(KEY_ZIRNOX_MELTDOWN, "false");
            rules.setOrCreateGameRule(KEY_RESEARCH_MELTDOWN, "false");
            rules.setOrCreateGameRule(KEY_DARK_FUSION_CORE_MELTDOWN, "false");
            rules.setOrCreateGameRule(KEY_PILE_MELTDOWN, "false");
            rules.setOrCreateGameRule(KEY_SAVE_MELTDOWN_RULES, "true");
        }
    }

    /**
     * Whether or not the watz reactor should meltdown (false = disabled).
     * @param world
     * @return
     */
    public static boolean getWatzMeltdownDisabled(World world) {
        return world.getGameRules().getGameRuleBooleanValue(KEY_WATZ_MELTDOWN);
    }

    /**
     * Whether or not the RBMK reactor should meltdown (false = disabled).
     * @param world
     * @return
     */
    public static boolean getRBMKMeltdownDisabled(World world) {
        return world.getGameRules().getGameRuleBooleanValue(KEY_RBMK_MELTDOWN);
    }

    /**
     * Whether or not the PWR should meltdown (false = disabled).
     * @param world
     * @return
     */
    public static boolean getPWRMeltdownDisabled(World world) {
        return world.getGameRules().getGameRuleBooleanValue(KEY_PWR_MELTDOWN);
    }

    /**
     * Whether or not the fusion reactor (ITER) should meltdown (false = disabled).
     * @param world
     * @return
     */
    public static boolean getFusionMeltdownDisabled(World world) {
        return world.getGameRules().getGameRuleBooleanValue(KEY_FUSION_MELTDOWN);
    }

    /**
     * Whether or not the ZIRNOX reactor should meltdown (false = disabled).
     * @param world
     * @return
     */
    public static boolean getZIRNOXMeltdownDisabled(World world) {
        return world.getGameRules().getGameRuleBooleanValue(KEY_ZIRNOX_MELTDOWN);
    }

    /**
     * Whether or not the research reactor should meltdown (false = disabled).
     * @param world
     * @return
     */
    public static boolean getResearchMeltdownDisabled(World world) {
        return world.getGameRules().getGameRuleBooleanValue(KEY_RESEARCH_MELTDOWN);
    }

    /**
     * Whether or not the DFC reactor should meltdown (false = disabled).
     * @param world
     * @return
     */
    public static boolean getDFCMeltdownDisabled(World world) {
        return world.getGameRules().getGameRuleBooleanValue(KEY_DARK_FUSION_CORE_MELTDOWN);
    }

    /**
     * Whether or not the pile reactor should meltdown (false = disabled).
     * @param world
     * @return
     */
    public static boolean getPileMeltdownDisabled(World world) {
        return world.getGameRules().getGameRuleBooleanValue(KEY_PILE_MELTDOWN);
    }
}
