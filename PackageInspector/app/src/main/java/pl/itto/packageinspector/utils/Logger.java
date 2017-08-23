package pl.itto.packageinspector.utils;

/**
 * Created by PL_itto on 8/23/2017.
 */

public class Logger {
    String log;

    public Logger() {
        log = "";
    }

    @Override
    public String toString() {
        return log;
    }

    public void appendLog(String log) {
        this.log += log + "\n";
    }

    public void clearLog(){
        log="";
    }
}
