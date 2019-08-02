package com.egorius.rawstory.bot.handler;

public interface Answer {
    String[] getMsg();
    class Success implements Answer {
        private String[] msg;

        public Success(String[] msg) {
            this.msg = msg;
        }

        public String[] getMsg() {
            return msg;
        }
    }

    class Failed implements Answer{
        private String[] msg;

        public Failed(String[] msg) {
            this.msg = msg;
        }

        public String[] getMsg() {
            return msg;
        }
    }
}
