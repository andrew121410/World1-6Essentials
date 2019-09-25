package World16FireAlarms.Screen;

import org.bukkit.block.Sign;

public class SignCache {

    private String line0;
    private String line1;
    private String line2;
    private String line3;

    public SignCache(Sign sign) {
        this.line0 = sign.getLine(0);
        this.line1 = sign.getLine(1);
        this.line2 = sign.getLine(2);
        this.line3 = sign.getLine(3);
    }

    public void fromSign(Sign sign) {
        this.line0 = sign.getLine(0);
        this.line1 = sign.getLine(1);
        this.line2 = sign.getLine(2);
        this.line3 = sign.getLine(3);
    }

    public String getLine0() {
        return line0;
    }

    public void setLine0(String line0) {
        this.line0 = line0;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public void update(Sign sign) {
        sign.setLine(0, this.line0);
        sign.setLine(1, this.line1);
        sign.setLine(2, this.line2);
        sign.setLine(3, this.line3);
        sign.update();
    }
}
