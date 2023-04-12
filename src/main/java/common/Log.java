package common;

import java.util.Date;

public class Log {
    String name;

    public Log(int serverPort, String name) {
        this.name = name;
        if (serverPort != 0) {
            this.name += " port " + serverPort;
        }
    }

    public void Info(String message, Object ... b) {
        String prefix = String.format("[%d][INFO] %s | Thread: %d | ", new Date().getTime(), this.name, Thread.currentThread().getId());
        System.out.printf(prefix + message + "\n", b);
    }

    public void Error(String message, Object ... b) {
        String prefix = String.format("[%d][ERR ] %s | Thread: %d | ", new Date().getTime(), this.name, Thread.currentThread().getId());
        System.out.printf(prefix + message + "\n", b);
    }
}
