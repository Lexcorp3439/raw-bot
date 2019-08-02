package com.egorius.rawstory.bot.connect;

public interface Request {
    class Success implements Request {
        private Object object;

        public Success(Object object) {
            this.object = object;
        }

        public Object getObject() {
            return object;
        }
    }
    class Failed implements Request {
        private String msg;

        public Failed(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }
}
