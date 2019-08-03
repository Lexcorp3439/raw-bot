package com.egorius.rawstory.bot.handler;

public class Action {
    public Act act;
    public String[] messages;

    Action(Act act, String[] messages) {
        this.act = act;
        this.messages = messages;
    }

    public enum Act {
        Message, Photo, No
    }
}
