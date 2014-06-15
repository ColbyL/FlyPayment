package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;
//import org.bukkit.plugin.java.JavaPlugin;

public class Configuration /*extends JavaPlugin*/ {

    public boolean getReloadSafetyTeleport() {
        boolean reloadSafetyTeleport;
        reloadSafetyTeleport = Main.getInstance().getConfig().getBoolean("OnReloadTeleportToGround");
        return reloadSafetyTeleport;
    }

    public String getItemChargeEnum(int tier) {
        String itemChargeEnum;
        itemChargeEnum = Main.getInstance().getConfig().getString("ChargeItemNameTier" + tier);
        return itemChargeEnum;
    }

    public int getItemChargeAmount(int tier) {
        int itemChargeAmount;
        itemChargeAmount = Main.getInstance().getConfig().getInt("AmountOfItemsToChargeTier" + tier);
        return itemChargeAmount;
    }

    public long getTimerAmount(int tier) {
        long timerAmount;
        timerAmount = Main.getInstance().getConfig().getLong("FlightTimerTier" + tier);
        return timerAmount;
    }

    public int getExpChargeAmount(int tier) {
        int expChargeAmount;
        expChargeAmount = Main.getInstance().getConfig().getInt("ChargeEXPTier" + tier);
        return expChargeAmount;
    }

    public int getMoneyChargeAmount(int tier) {
        int moneyChargeAmount;
        moneyChargeAmount = Main.getInstance().getConfig().getInt("ChargeMoneyTier" + tier);
        return moneyChargeAmount;
    }

    public boolean getEconomyAccount(int tier) {
        boolean economyAccount;
        economyAccount = Main.getInstance().getConfig().getBoolean("EconomyAccountTier" + tier);
        return economyAccount;
    }

    public String getEconomyAccountName(int tier) {
        String economyAccountName;
        economyAccountName = Main.getInstance().getConfig().getString("EconomyAccountNameTier" + tier);
        return economyAccountName;
    }

    public int getTier(Player p) {
        int tier = 0;
        if (p.hasPermission("FlyP.Fly.1")) {
            tier = 1;
        } else if (p.hasPermission("FlyP.Fly.2")) {
            tier = 2;
        } else if (p.hasPermission("FlyP.Fly.3")) {
            tier = 3;
        } else if (p.hasPermission("FlyP.Fly.4")) {
            tier = 4;

        } else if (p.hasPermission("FlyP.Fly.5")) {
            tier = 5;
        }

        return tier;
    }
    
    public boolean getFlyBanRequireTime() {
        
        boolean flyBanRequireTime =  Main.getInstance().getConfig().getBoolean("FlyBanRequireTime");
        
        return flyBanRequireTime;
    }
    
    public int getFlyBanMaxTime() {
        
        int flyBanMaxTime =  Main.getInstance().getConfig().getInt("FlyBanMaxTime");
        
        return flyBanMaxTime;
    }
    
    //**Messages
    
    public String getPlayerErrorConfigMessage() {
        
        String playerErrorMessageString =  Main.getInstance().getConfig().getString("PlayerErrorMessage");
        
        return playerErrorMessageString;
    }
    
    public String getCommandUsageErrorConfigMessage() {
        
        String playerCommandUsageMessageString =  Main.getInstance().getConfig().getString("CommandUsageErrorMessage");
        
        return playerCommandUsageMessageString;
    }
    
    public String getPermissionErrorConfigMessage() {
        
        String permissionErrorMessageString =  Main.getInstance().getConfig().getString("PermissionErrorMessage");
        
        return permissionErrorMessageString;
    }
    
    public String getFlyingAlreadyEnabledConfigMessage(){
        
        String flyingAlreadyEnabledString =  Main.getInstance().getConfig().getString("FlyingAlreadyEnabledMessage");
        
        return flyingAlreadyEnabledString;
        
    }
    
    public String getFlyingEnabledConfigMessage(){
        
        String flyingEnabledString =  Main.getInstance().getConfig().getString("FlyingEnabledMessage");
        
        return flyingEnabledString;
        
    }
    
    public String getItemRequirementMessage(){
        
        String itemRequirementMessage =  Main.getInstance().getConfig().getString("ItemRequirementMessage");
        
        return itemRequirementMessage;
        
    }
    
    public String getEXPRequirementMessage(){
        
        String EXPRequirementMessage =  Main.getInstance().getConfig().getString("EXPRequirementMessage");
        
        return EXPRequirementMessage;
        
    }
    
    public String getMoneyRequiredMessage(){

        String moneyRequiredMessage =  Main.getInstance().getConfig().getString("MoneyRequirementMessage");
        
        return moneyRequiredMessage;
        
    }
    
    public String getFlyingEnabledNoTimeLimitMessage(){
        
        String flyingEnabledNoTimeLimitMessage =  Main.getInstance().getConfig().getString("FlyingEnabledNoTimeLimitMessage");
        
        return flyingEnabledNoTimeLimitMessage;
        
    }
    
    public String getInvalidArgumentMessage(){
        
        String invalidArgumentMessage =  Main.getInstance().getConfig().getString("InvalidArgumentMessage");
        
        return invalidArgumentMessage;
        
    }

  public String getFlyingOffMessage(){
        
        String flyingOffMessage =  Main.getInstance().getConfig().getString("FlyingOffIn10Message");
        
        return flyingOffMessage;
        
    }
    
  public String getItemRemovedMessage(){
        
        String itemRemovedMessage =  Main.getInstance().getConfig().getString("ItemRemovedMessage");
        
        return itemRemovedMessage;
        
    }
  
  public String getEXPRemovedMessage(){
      
      String EXPRemovedMessage =  Main.getInstance().getConfig().getString("EXPRemovedMessage");
      
      return EXPRemovedMessage;
      
  }
  
  public String getMoneyRemovedMessage(){
      
      String moneyRemovedMessage =  Main.getInstance().getConfig().getString("MoneyRemovedMessage");
      
      return moneyRemovedMessage;
      
  }
  
  public String getTimeLeftMessage(){

      String timeLeftMessage =  Main.getInstance().getConfig().getString("TimeLeftMessage");
      
      return timeLeftMessage;
      
  }
  
  public String getFlyingAlreadyDisabledMessage(){
      
      String flyingAlreadyDisabledMessage =  Main.getInstance().getConfig().getString("FlyingAlreadyDisabledMessage");
      
      return flyingAlreadyDisabledMessage;
      
  }
  
  public String getFlyingDisabledMessage(){
      
      String flyingDisabledMessage =  Main.getInstance().getConfig().getString("FlyingDisabledMessage");
      
      return flyingDisabledMessage;
      
  }
  
  public String getSecondsMessage(){
      
      String secondsMessage =  Main.getInstance().getConfig().getString("SecondsMessage");
      
      return secondsMessage;
      
  }
  
  public String getMinutesMessage(){
      
      String minutesMessage =  Main.getInstance().getConfig().getString("MinutesMessage");
      
      return minutesMessage;
      
  }
  public String getFlyingHasNoTimeLimitMessage(){
      
      String flyingHasNoTimeLimitMessage =  Main.getInstance().getConfig().getString("FlyingHasNoTimeLimitMessage");
      
      return flyingHasNoTimeLimitMessage;
      
  }
  
  public String getTimeLeftNotFlyingMessage(){
      
      String timeLeftNotFlyingMessage =  Main.getInstance().getConfig().getString("TimeLeftNotFlyingMessage");
      
      return timeLeftNotFlyingMessage;
      
  }
  
  public String getTimeLeftCommandMessage(){
      
      String timeLeftCommandMessage =  Main.getInstance().getConfig().getString("TimeLeftCommandMessage");
      
      return timeLeftCommandMessage;
      
  }
  
  public String getRequiredBanTimeCommandMessage(){
      
      String requiredBanTimeCommandMessage = Main.getInstance().getConfig().getString("FlyBanRequiredTimeMessage");
      
      return requiredBanTimeCommandMessage;
  }
  
  public String getBanAddedMessage(){
      
      String getBanAddedMessage = Main.getInstance().getConfig().getString("BanAddedMessage");
      
      return getBanAddedMessage;
  }
  
}
