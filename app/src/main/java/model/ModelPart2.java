package model;

/**
 * Created by dainguyen on 6/14/17.
 */

public class ModelPart2 {
    private int id;
    private String token;
    private String script;
    private String sol;
    private  int level;
    private int time;

    public ModelPart2(int id, String token, String script, String sol, int level, int time) {
        this.id = id;
        this.token = token;
        this.script = script;
        this.sol = sol;
        this.level = level;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSol() {
        return sol;
    }

    public void setSol(String sol) {
        this.sol = sol;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
