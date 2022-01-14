package com.dreammaster.scripts;

import com.dreammaster.main.MainRegistry;

public class ScriptLoader {
    static public void run(){
        IScriptLoader[] scripts = new IScriptLoader[]{
                new ScriptBiblioCraft(),
                new ScriptBiblioWoodsNatura(),
                new ScriptBiblioWoodsBoP(),
                new ScriptBiblioWoodsForestry(),
                new ScriptHoloInventory(),
                new ScriptSleepingBags(),
                new ScriptSpiceOfLife(),
                new ScriptEnderZoo(),
                new ScriptTranslocator(),
                new ScriptForgeMultipart(),
                new ScriptBetterQuesting(),
                new ScriptJABBA()
        };

        for (IScriptLoader script: scripts){
            if (script.isScriptLoadable()){
                script.loadScript();
                MainRegistry.Logger.info(script.getScriptName()+" took "+script.getExecutionTime()+" ms.");
            }
            else {
                MainRegistry.Logger.info("missing requirements for the script "+script.getScriptName()+". It won't be loaded");
            }
        }
    }
}
