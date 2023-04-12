package common;

import java.util.Date;

public class Log {
    int port;
    String name;

    public Log(int serverPort, String name) {
        this.port = serverPort;
        this.name = name;
    }

    public void Info(String message, Object ... b) {
        String prefix = String.format("[%d][INFO] %s port: %d | Thread: %d | ", new Date().getTime(), this.name, this.port, Thread.currentThread().getId());
        System.out.printf(prefix + message + "\n", b);
    }

    public void Error(String message, Object ... b) {
        String prefix = String.format("[%d][ERR ] %s port: %d | Thread: %d | ", new Date().getTime(), this.name, this.port, Thread.currentThread().getId());
        System.out.printf(prefix + message + "\n", b);
    }
}
