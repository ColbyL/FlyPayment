package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;

public class Messages {

    private Utilities Utilities = new Utilities();

    private Configuration Configuration = new Configuration();

    // /Messages
    public String playerErrorMessage() {
        // Gets message from config
        String playerErrorMessage = Configuration.getPlayerErrorConfigMessage();
        // returns message from config
        return playerErrorMessage;
    }

    public String commandUsageErrorMessage(Player p) {
        // Gets message from config
        String commandUsageErrorMessage = Configuration.getCommandUsageErrorConfigMessage();

        String commandUsageErrorMessageEnd = commandUsageErrorMessage.replace("%player%", p.getName());

        return commandUsageErrorMessageEnd;
    }

    public String permissionErrorMessage(Player p) {
        // Gets message from config
        String permissionErrorMessage = Configuration.getPermissionErrorConfigMessage();

        String permissionErrorMessageEnd = permissionErrorMessage.replace("%player%", p.getName());
        // returns message from config
        return permissionErrorMessageEnd;
    }

    public String flyingAlreadyEnabledMessage(Player p) {
        // Gets message from config
        String flyingAlreadyEnabledMessage = Configuration.getFlyingAlreadyEnabledConfigMessage();
        // replaces %player% with command sender string name
        String flyingAlreadyEnabledMessageEnd = flyingAlreadyEnabledMessage.replace("%player%", p.getName());
        // returns message from config
        return flyingAlreadyEnabledMessageEnd;

    }

    public String flyingEnabledMessage(Player p) {
        // Gets message from config
        String flyingEnabledMessage = Configuration.getFlyingEnabledConfigMessage();

        String flyingEnabledMessageEnd = flyingEnabledMessage.replace("%player%", p.getName());
        // returns message from config
        return flyingEnabledMessageEnd;

    }

    public String itemRequirementMessage(Player p, int itemChargeAmount, String itemName) {
        // Converts primitive int to string integer
        String itemChargeAmountString = Utilities.convertIntToString(itemChargeAmount);
        // Gets message from config
        String itemChargeAmountMessage = Configuration.getItemRequirementMessage();
        // replaces %player% with command sender string name; replaces
        // %ItemAmount% with item name required by tier set for command sender;
        // %ItemName% with item name required by tier set for command sender
        String itemChargeAmountMessageEnd = itemChargeAmountMessage.replace("%player%", p.getName())
                .replace("%ItemAmount%", itemChargeAmountString).replace("%ItemName%", itemName);
        // returns message from config
        return itemChargeAmountMessageEnd;

    }

    public String EXPRequirementMessage(Player p, int remainingEXPRequired) {
        // converts primitive int to string integer
        String RemainingEXP = Utilities.convertIntToString(remainingEXPRequired);
        // Gets message from config
        String EXPRequirementMessage = Configuration.getEXPRequirementMessage();
        // replaces %player% with command sender string name; replaces
        // %RemainingEXPNeeded% with exp needed left to make command sender fly
        String EXPRequirementMessageEnd = EXPRequirementMessage.replace("%player%", p.getName()).replace("%RemainingEXPNeeded%",
                RemainingEXP);
        // returns message from config
        return EXPRequirementMessageEnd;

    }

    public String moneyRequirementMessage(Player p, double moneyRequired, String moneyPlural, String moneySingular) {
        // converts primitive int to string integer
        String moneyRequiredString = Utilities.convertDoubleToString(moneyRequired);
        // Gets message from config s
        String moneyRequiredMessage = Configuration.getMoneyRequiredMessage();
        // replaces %player% with command sender string name; replaces
        // %RemainingEXPNeeded% with exp needed left to make command sender fly
        String moneyRequiredMessageEnd = moneyRequiredMessage.replace("%player%", p.getName())
                .replace("%RemainingMoneyNeeded%", moneyRequiredString).replace("%MoneyNamePlural%", moneyPlural)
                .replace("%MoneyNameSingular%", moneySingular);
        // returns message from config
        return moneyRequiredMessageEnd;

    }

    public String flyingEnabledNoTimelimitMessage(Player p) {
        // Gets message from config
        String flyingEnabledNoTimelimit = Configuration.getFlyingEnabledNoTimeLimitMessage();

        String flyingEnabledNoTimelimitEnd = flyingEnabledNoTimelimit.replace("%player%", p.getName());
        // returns message from config
        return flyingEnabledNoTimelimitEnd;

    }

    public String invalidArgumentMessage(Player p) {
        // Gets message from config
        String invalidArgument = Configuration.getInvalidArgumentMessage();

        String invalidArgumentEnd = invalidArgument.replace("%player%", p.getName());
        // returns message from config
        return invalidArgumentEnd;

    }

    public String flyingOffMessage(Player p) {
        // Gets message from config
        String flyingOffMessage = Configuration.getFlyingOffMessage();

        String flyingOffMessageEnd = flyingOffMessage.replace("%player%", p.getName());
        // returns message from config
        return flyingOffMessageEnd;

    }

    public String itemRemovedMessage(Player p, String itemName, int itemChargeAmount) {
        // Gets message from config
        String itemRemovedMessage = Configuration.getItemRemovedMessage();
        // Converts int to String
        String itemChargeAmountString = Utilities.convertIntToString(itemChargeAmount);
        String itemRemovedMessageEnd = itemRemovedMessage.replace("%player%", p.getName()).replace("%ItemName%", itemName)
                .replace("%ItemChargeAmount%", itemChargeAmountString);
        // returns message from config
        return itemRemovedMessageEnd;

    }

    public String EXPRemovedMessage(Player p, int EXPChargeAmount) {
        // Gets message from config
        String EXPRemovedMessage = Configuration.getEXPRemovedMessage();
        // Converts int to String
        String EXPChargeAmountString = Utilities.convertIntToString(EXPChargeAmount);
        // replaces config holders
        String EXPRemovedMessageEnd = EXPRemovedMessage.replace("%player%", p.getName())
                .replace("%EXPChargeAmount%", EXPChargeAmountString);
        // returns message from config
        return EXPRemovedMessageEnd;

    }

    public String moneyRemovedMessage(Player p, int moneyChargeAmount, String moneyPlural, String moneySingular) {
        // Gets message from config
        String moneyRemovedMessage = Configuration.getMoneyRemovedMessage();
        // Converts int to String
        String moneyChargeAmountString = Utilities.convertIntToString(moneyChargeAmount);
        // replaces config holders
        String moneyRemovedMessageEnd = moneyRemovedMessage.replace("%player%", p.getName())
                .replace("%MoneyChargeAmount%", moneyChargeAmountString).replace("%MoneyNamePlural%", moneyPlural)
                .replace("%MoneyNameSingular%", moneySingular);
        // returns message from config
        return moneyRemovedMessageEnd;

    }

    public String timeLeftMessage(Player p, String timeLeft, String minutesOrSeconds) {
        // Gets message from config
        String timeLeftMessage = Configuration.getTimeLeftMessage();
        // replaces config holders
        String timeLeftMessageEnd = timeLeftMessage.replace("%player%", p.getName()).replace("%FlightTime%", timeLeft)
                .replace("%MinutesOrSeconds%", minutesOrSeconds);
        // returns message from config
        return timeLeftMessageEnd;

    }

    public String flyingAlreadyDisabledMessage(Player p) {
        // Gets message from config
        String flyingAlreadyDisabledMessage = Configuration.getFlyingAlreadyDisabledMessage();

        String flyingAlreadyDisabledMessageEnd = flyingAlreadyDisabledMessage.replace("%player%", p.getName());
        // returns message from config
        return flyingAlreadyDisabledMessageEnd;

    }

    public String flyingDisabledMessage(Player p) {
        // Gets message from config
        String flyingDisabledMessage = Configuration.getFlyingDisabledMessage();
        // replaces string
        String flyingDisabledMessageEnd = flyingDisabledMessage.replace("%player%", p.getName());
        // returns message from config
        return flyingDisabledMessageEnd;

    }

    public String secondsMessage() {
        // Gets message from config
        String secondsMessage = Configuration.getSecondsMessage();
        // returns secondsMessage
        return secondsMessage;

    }

    public String minutesMessage() {
        // Gets message from config
        String minutesMessage = Configuration.getMinutesMessage();
        // returns secondsMessage
        return minutesMessage;

    }

    public String timeLeftCommandMessage(Player p, String timeLeft) {
        // Gets message from config
        String timeLeftCommandMessage = Configuration.getTimeLeftCommandMessage();
        //replaces string
        String timeLeftCommandMessageEnd = timeLeftCommandMessage.replace("%player%", p.getName()).replace("%timeLeftCommand%", timeLeft);
        // returns secondsMessage
        return timeLeftCommandMessageEnd;

    }

    public String timeLeftNotFlyingMessage(Player p) {
        // Gets message from config
        String timeLeftNotFlyingMessage = Configuration.getTimeLeftNotFlyingMessage();
        //replaces string
        String timeLeftNotFlyingEnd = timeLeftNotFlyingMessage.replace("%player%", p.getName());
        // returns secondsMessage
        return timeLeftNotFlyingEnd;

    }

    public String flyingHasNoTimeLimitMessage(Player p) {
        // Gets message from config
        String flyingHasNoTimeLimitMessage = Configuration.getFlyingHasNoTimeLimitMessage();
        //replaces string
        String flyingHasNoTimeLimitMessageEnd = flyingHasNoTimeLimitMessage.replace("%player%", p.getName());
        // returns secondsMessage
        return flyingHasNoTimeLimitMessageEnd;

    }

    public String requiredBanTimeCommandMessage(Player p) {
        // Gets message from config
        String requiredBanTimeCommandMessage = Configuration.getRequiredBanTimeCommandMessage();
        //replaces string
        String requiredBanTimeCommandMessageEnd = requiredBanTimeCommandMessage.replace("%player%", p.getName());
        // returns secondsMessage
        return requiredBanTimeCommandMessageEnd;

    }

    public String banAddedMessage(Player p, String bannedP, String time) {
        // Gets message from config
        String banAddedMessage = Configuration.getBanAddedMessage();
        //replaces string
        String banAddedMessageEnd = banAddedMessage.replace("%player%", p.getName()).replace("%BannedPlayerName%", bannedP).replace("%TimeBanned%", time);
        // returns secondsMessage
        return banAddedMessageEnd;

    }
}
