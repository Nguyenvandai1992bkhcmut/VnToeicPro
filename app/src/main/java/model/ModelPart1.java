package model;

/**
 * Created by dainguyen on 5/27/17.
 */

public class ModelPart1 {
    private  int id;
    private String token;
    private String aScript;
    private String bScript;
    private String cScript;
    private String dScript;
    private String sol;
    private int level;
    private int time;

    public ModelPart1(int id, String token, String aScript, String bScript, String cScript, String dScript, String sol, int level, int time) {
        this.id = id;
        this.token = token;
        this.aScript = aScript;
        this.bScript = bScript;
        this.cScript = cScript;
        this.dScript = dScript;
        this.sol = sol;
        this.level = level;
        this.time = time;
    }

    public String getaScript() {
        return aScript;
    }

    public void setaScript(String aScript) {
        this.aScript = aScript;
    }

    public String getbScript() {
        return bScript;
    }

    public void setbScript(String bScript) {
        this.bScript = bScript;
    }

    public String getcScript() {
        return cScript;
    }

    public void setcScript(String cScript) {
        this.cScript = cScript;
    }

    public String getdScript() {
        return dScript;
    }

    public void setdScript(String dScript) {
        this.dScript = dScript;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
